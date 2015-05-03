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
 * Representa o mundo com o espaço gráfico e todos seus os objetos gráficos.
 */
public class Mundo implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	public static final int MARGEMSELECAOPONTO = 15; // margem de erro para seleção de pontos, em pixels
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
	private Ponto pontoSelecionado;
	
	private int antigoX;
	private int antigoY;

	public Mundo() {
		camera = new Camera(-400, 400, -400, 400);
		corAtual = Cor.AZUL;
		objetos = new ArrayList<ObjetoGrafico>();
	}

	void adicionarObjetoGráfico(final ObjetoGrafico objetoGrafico) {
		if (objetoGrafico != null) {
			objetos.add(objetoGrafico);
		}
	}

	void removerObjetoGráfico(final ObjetoGrafico objetoGrafico) {
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
	 * E = alterna entre modo de criação e edição
	 * W = move câmera para cima
	 * S = move câmera para baixo
	 * A = move câmera para esquerda
	 * D = move câmera para direita
	 * I = aumenta zoom
	 * O = diminui zoom
	 * P = altera primitiva gráfica entre LineLoop e LineStrip
	 * Delete = deleta o polígono ou vértice selecionado (se houver)
	 * Escape = retirar seleção do polígono e vertice (se houver)
	 * T = terminar criação do objeto em criação atual (se houver)
	 * Q = adicionar objeto filho
	 * M = aumentar a escala do objeto gráfico selecionado
	 * N = diminuir a escala do objeto gráfico selecionado
	 * G = rotacionar o objeto no sentido anti-horário
	 * H = rotacionar o objeto no sentido horário
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
				if (modoEdicao) { //Apenas no modo de edição pode deletar
					deletarItem();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if (modoEdicao) { //Apenas no modo de edição pode haver seleção
					retirarSelecao();
				}
				break;
			case KeyEvent.VK_T:
				terminarCriacaoObjeto();
				break;
			case KeyEvent.VK_Q:
				if (objetos.size() > 0) { //Apenas pode-se criar um objeto filho, se já existir algum para ser o pai
					objetoFilho = true;
				}
				break;
			case KeyEvent.VK_M:
				if (modoEdicao && objetoSelecionado != null) { //Está no modo de edição e possui objeto selecionado
					objetoSelecionado.escalarObjeto(1.2);
				}
				break;
			case KeyEvent.VK_N:
				if (modoEdicao && objetoSelecionado != null) { //Está no modo de edição e possui objeto selecionado
					objetoSelecionado.escalarObjeto(0.8);
				}
				break;
			case KeyEvent.VK_G:
				if (modoEdicao && objetoSelecionado != null) { //Está no modo de edição e possui objeto selecionado
					objetoSelecionado.rotacionarObjeto(10);
				}
				break;
			case KeyEvent.VK_H:
				if (modoEdicao && objetoSelecionado != null) { //Está no modo de edição e possui objeto selecionado
					objetoSelecionado.rotacionarObjeto(-10);
				}
				break;
			default: 
				//Atalho não suportado
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
		if (modoEdicao) {
			int movimentoX = (e.getX() - antigoX) * 2;
			int movimentoY = (e.getY() - antigoY) * -2;
			
			if (objetoSelecionado != null) {
				if (pontoSelecionado != null) {
					pontoSelecionado.setX(pontoSelecionado.getX() + movimentoX);
					pontoSelecionado.setY(pontoSelecionado.getY() + movimentoY);
					objetoSelecionado.calcularBBox();
				} else {
					objetoSelecionado.transladarObjeto(movimentoX, movimentoY, 0);
				}
			}
			
			antigoX = e.getX();
			antigoY = e.getY();
		}

		glDrawable.display();
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		if (!modoEdicao && objetoEmCriacao != null) { 
			//se estiver no modo de criação e já existir um objeto gráfico em criação então atualiza o final do rastro da nova aresta
			int x = (e.getX() - (Executor.LARGURA_JANELA / 2)) * 2;
			int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -2;
			fimRastro = new Ponto(x, y);
		}
		
		glDrawable.display();
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (modoEdicao) {
			int i = 0;
			final int x = (e.getX() - (Executor.LARGURA_JANELA / 2)) * 2;
			final int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -2;
			ObjetoGrafico obj = null;

			while ((obj == null) && (i < objetos.size())) {
				obj = objetos.get(i).verificarSelecao(x, y);
				if (obj != null) {
					if (objetoSelecionado != null) {
						objetoSelecionado.setSelecionado(false);
					}
					objetoSelecionado = obj;
					objetoSelecionado.setSelecionado(true);
				}
				i++;
			}
			
		} else {
			//TODO
			
			if (objetoFilho) { // verifica se está adicionando um objeto filho 
				
				//se sim então verifica se tem um objeto selecionado para ser o pai
					//se sim então verifica se já começou a criação do objeto filho 
						// se sim então adiciona um novo vértice para o filho
						// senão cria um novo filho
					//senão verifica se selecionou algum objeto para ser o pai

			} else {
				if (objetoEmCriacao == null) { // verifica se não existe objeto em criação então cria
					objetoEmCriacao = new ObjetoGrafico(corAtual);
				}
				
				System.out.println("x = " + e.getX() + ", y = " + e.getY());
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
		if (modoEdicao) {
			//Pontos de referência para a movimentoção do objetos gráficos/vértices
			antigoX = e.getX();
			antigoY = e.getY();
			
			if (objetoSelecionado != null) {
				final int x = (e.getX() - (Executor.LARGURA_JANELA / 2)) * 2;
				final int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -2;
				
				for (Ponto p : objetoSelecionado.getPontos()) {
					if ((x > (p.getX() - MARGEMSELECAOPONTO)) && (x < (p.getX() + MARGEMSELECAOPONTO)) && (y > (p.getY() - MARGEMSELECAOPONTO)) && (y < (p.getY() + MARGEMSELECAOPONTO))) {
						pontoSelecionado = p;
					}
				}
			}
		}
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
			for (ObjetoGrafico objetoGrafico : objetos) {
				objetoGrafico.limparTransformações();
			}
			retirarSelecao();
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
		if (objetoSelecionado != null) {
			objetoSelecionado.setSelecionado(false);
			objetoSelecionado = null;
		}
		pontoSelecionado = null;
	}
	
	private void deletarItem() {
		if (pontoSelecionado != null) {
			objetoSelecionado.removePonto(pontoSelecionado);
		} else {
			if (objetos.contains(objetoSelecionado)) {
				objetos.remove(objetoSelecionado);
				objetoSelecionado = null;
			} else {
				boolean result = false;
				int i = 0;
				while (!result && (i < objetos.size())) {
					result = objetos.get(i).removerObjetoGraficoFilho(objetoSelecionado);
					i++;
					if (result) {
						objetoSelecionado = null;
					}
				}
			}
		}
	}
	
	private void terminarCriacaoObjeto() {
		if (!modoEdicao && objetoEmCriacao != null) { //tem um objeto em criação
			objetoEmCriacao.setPrimitivaGrafica(primitivaGrafica);
			objetoEmCriacao.calcularBBox();
			if (objetoFilho && objetoSelecionado == null) { // verifica se é um objeto filho e se tem um objeto selecionado para ser pai dele
				//TODO encontrar onde na lista de objetos qual é o pai e então inserir um filho para ele
				objetoFilho = false; //desmarca a flag que indica que o objeto gráfico em criação será um filho
			} else {
				// senão adiciona o objeto na lista de objetos do mundo
				objetos.add(objetoEmCriacao);
				objetoEmCriacao = null;
			}
		}
	}
}
