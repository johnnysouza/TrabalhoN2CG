package cg.base;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import cg.Executor;

/**
 * Representa o mundo com o espa�o gr�fico e todos seus os objetos gr�ficos.
 */
public class Mundo implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private GL					gl;
	private GLU					glu;
	private GLAutoDrawable		glDrawable;
	private Camera				camera;
	private List<ObjetoGrafico>	objetos;
	private ObjetoGrafico		objetoEmCriacao;
	private Cor corAtual;
	private boolean modoEdicao = false;
	private int primitivaGrafica = GL.GL_LINE_LOOP;
	private boolean objetoFilho = false;
	private Ponto inicioRastro;
	private Ponto fimRastro;
	private ObjetoGrafico objetoSelecionado;
	
	public Mundo() {
		camera = new Camera(-400, 400, -400, 400);
		corAtual = Cor.AZUL;
		objetos = new ArrayList<ObjetoGrafico>();
	}
 
	void adicionarObjetoGr�fico(final ObjetoGrafico objetoGrafico) {
		if (objetoGrafico != null) {
			objetos.add(objetoGrafico);
		}
	}

	void removerObjetoGr�fico(final ObjetoGrafico objetoGrafico) {
		objetos.remove(objetoGrafico);
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(0.85f, 0.85f, 0.85f, 1.0f); // Cinza claro de fundo
	}

	@Override
	public void keyTyped(final KeyEvent e) {
	}

	/**
	 * Atalhos:
	 * 
	 * C = alterar cor entre azul, verde e vermelho
	 * E = alterna entre modo de cria��o e edi��o
	 * W = move c�mera para cima
	 * S = move c�mera para baixo
	 * A = move c�mera para esquerda
	 * D = move c�mera para direita
	 * I = aumenta zoom
	 * O = diminui zoom
	 * P = altera primitiva gr�fica entre LineLoop e LineStrip
	 * Delete = deleta o pol�gono ou v�rtice selecionado (se houver)
	 * Escape = retirar sele��o do pol�gono e vertice (se houver)
	 * T = terminar cria��o do objeto em cria��o atual (se houver)
	 * Q = adicionar objeto filho
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_C:
				alterarCor();
				break;
			case KeyEvent.VK_E:
				alternarModoEdicao();
				break;
			case KeyEvent.VK_W:
				camera.pan(Camera.MovimentoCamera.MOVER_PARA_CIMA);
				break;
			case KeyEvent.VK_S:
				camera.pan(Camera.MovimentoCamera.MOVER_PARA_BAIXO);
				break;
			case KeyEvent.VK_A:
				camera.pan(Camera.MovimentoCamera.MOVER_PARA_ESQUERDA);
				break;
			case KeyEvent.VK_D:
				camera.pan(Camera.MovimentoCamera.MOVER_PARA_DIREITA);
				break;
			case KeyEvent.VK_I:
				camera.zomm(Camera.ZoomCamera.MAIS_ZOOM);
				break;
			case KeyEvent.VK_O:
				camera.zomm(Camera.ZoomCamera.MENOS_ZOOM);
				break;
			case KeyEvent.VK_P:
				alterarPrimitivaGrafica();
				break;
			case KeyEvent.VK_DELETE:
				if (modoEdicao) { //Apenas no modo de edi��o pode deletar
					deletarItem();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if (modoEdicao) { //Apenas no modo de edi��o pode haver sele��o
					retirarSelecao();
				}
				break;
			case KeyEvent.VK_T:
				terminarCriacaoObjeto();
				break;
			case KeyEvent.VK_Q:
				if (objetos.size() > 0) { //Apenas pode-se criar um objeto filho, se j� existir algum para ser o pai
					objetoFilho = true;
				}
			default: 
				//Atalho n�o suportado
		}
		
		glDrawable.display();
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	}

	@Override
	public void display(final GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(camera.getxMinOrtho2D(), camera.getxMaxOrtho2D(), camera.getyMinOrtho2D(), camera.getyMaxOrtho2D());
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		SRU();

		if (objetoEmCriacao != null) {
			objetoEmCriacao.desenhar(gl);
		}
		desenharRastro();
		
		for (ObjetoGrafico objetoGrafico : objetos) {
			objetoGrafico.desenhar(gl);
		}
	}
	
	private void desenharRastro() {
		if (!modoEdicao && objetoEmCriacao != null && inicioRastro != null && fimRastro != null) {
			Cor cor = objetoEmCriacao.getCor();
			
			gl.glColor3d(cor.getR(), cor.getG(), cor.getB());
			
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2d(inicioRastro.getX(), inicioRastro.getY());
			gl.glVertex2d(fimRastro.getX(), fimRastro.getY());
			gl.glEnd();
		}
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1, final boolean arg2) {
		
	}

	@Override
	public void reshape(final GLAutoDrawable arg0, final int arg1, final int arg2, final int arg3, final int arg4) {
		
	}

	public void SRU() {
		// eixo x
		gl.glLineWidth(1.0f);

		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-200.0f, 0.0f);
		gl.glVertex2f(200.0f, 0.0f);
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -200.0f);
		gl.glVertex2f(0.0f, 200.0f);
		gl.glEnd();
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		if (!modoEdicao && objetoEmCriacao != null) { 
			//se estiver no modo de cria��o e j� existir um objeto gr�fico em cria��o ent�o atualiza o final do rastro da nova aresta
			int x = (e.getX() - (Executor.LARGURA_JANELA / 2)) * 2;
			int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -2;
			fimRastro = new Ponto(x, y);
		}
		
		glDrawable.display();
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (modoEdicao) {
			//TODO 
			//verifica se existe objeto selecionado
			// se tiver verifica se selecionou um v�rtice dele
			// sen�o verifica se selecionou algum objeto
		} else {
			//TODO
			
			if (objetoFilho) { // verifica se est� adicionando um objeto filho 
				
				//se sim ent�o verifica se tem um objeto selecionado para ser o pai
					//se sim ent�o verifica se j� come�ou a cria��o do objeto filho 
						// se sim ent�o adiciona um novo v�rtice para o filho
						// sen�o cria um novo filho
					//sen�o verifica se selecionou algum objeto para ser o pai

			} else {
				if (objetoEmCriacao == null) { // verifica se n�o existe objeto em cria��o ent�o cria
					objetoEmCriacao = new ObjetoGrafico(corAtual);
				}
				
				int x = (e.getX() - (Executor.LARGURA_JANELA / 2)) * 2;
				int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -2;
				
				inicioRastro = new Ponto(x, y);
				Ponto ponto = new Ponto(x, y);
				objetoEmCriacao.addPonto(ponto);
			}
		}
		
		glDrawable.display();
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		
	}

	private void alterarCor() {
		if (corAtual.equals(Cor.AZUL)) {
			corAtual = Cor.VERDE;
		} else if (corAtual.equals(Cor.VERDE)) {
			corAtual = Cor.VERMELHO;
		} else {
			corAtual = Cor.AZUL;
		}
	}

	private void alternarModoEdicao() {
		if (modoEdicao) {
			modoEdicao = false;
		} else {
			modoEdicao = true;
		}
	}
	
	private void alterarPrimitivaGrafica() {
		if (primitivaGrafica == GL.GL_LINE_LOOP) {
			primitivaGrafica = GL.GL_LINE_STRIP;
		} else {
			primitivaGrafica = GL.GL_LINE_LOOP;
		}
	}
	
	private void retirarSelecao() {
		// TODO retirar refer�ncias ao pol�gono e vertice selecionados se houver
	}
	
	private void deletarItem() {
		// TODO primeiro tenta deletar o v�rtice selecionado se houver, sen�o tenta deletar o pol�gono selecionado se houver
	}
	
	private void terminarCriacaoObjeto() {
		if (!modoEdicao && objetoEmCriacao != null) { //tem um objeto em cria��o
			objetoEmCriacao.setPrimitivaGrafica(primitivaGrafica);
			if (objetoFilho && objetoSelecionado == null) { // verifica se � um objeto filho e se tem um objeto selecionado para ser pai dele
				//TODO encontrar onde na lista de objetos qual � o pai e ent�o inserir um filho para ele
				objetoFilho = false; //desmarca a flag que indica que o objeto gr�fico em cria��o ser� um filho
			} else {
				// sen�o adiciona o objeto na lista de objetos do mundo
				objetos.add(objetoEmCriacao);
				objetoEmCriacao = null;
			}
		}
	}
}
