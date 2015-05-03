package cg.base;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import cg.Executor;

/**
 * Representa o mundo com o espa�o gr�fico e todos seus os objetos gr�ficos.
 */
public class Mundo implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	public static final int MARGEMSELECAOPONTO = 15; // margem de erro para sele��o de pontos, em pixels
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
		camera = new Camera(-200, 200, -200, 200);
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
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
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
	 * M = aumentar a escala do objeto gr�fico selecionado
	 * N = diminuir a escala do objeto gr�fico selecionado
	 * G = rotacionar o objeto no sentido anti-hor�rio
	 * H = rotacionar o objeto no sentido hor�rio
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_F1:
				imprimirAtalhos();
				break;
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
				break;
			case KeyEvent.VK_M:
				if (modoEdicao && objetoSelecionado != null) { //Est� no modo de edi��o e possui objeto selecionado
					objetoSelecionado.escalarObjeto(1.2);
				}
				break;
			case KeyEvent.VK_N:
				if (modoEdicao && objetoSelecionado != null) { //Est� no modo de edi��o e possui objeto selecionado
					objetoSelecionado.escalarObjeto(0.8);
				}
				break;
			case KeyEvent.VK_G:
				if (modoEdicao && objetoSelecionado != null) { //Est� no modo de edi��o e possui objeto selecionado
					objetoSelecionado.rotacionarObjeto(10);
				}
				break;
			case KeyEvent.VK_H:
				if (modoEdicao && objetoSelecionado != null) { //Est� no modo de edi��o e possui objeto selecionado
					objetoSelecionado.rotacionarObjeto(-10);
				}
				break;
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
		gl.glVertex2f(-150.0f, 0.0f);
		gl.glVertex2f(150.0f, 0.0f);
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -150.0f);
		gl.glVertex2f(0.0f, 150.0f);
		gl.glEnd();
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (modoEdicao) {
			int movimentoX = (e.getX() - antigoX);
			int movimentoY = (e.getY() - antigoY) * -1;
			
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
			//se estiver no modo de cria��o e j� existir um objeto gr�fico em cria��o ent�o atualiza o final do rastro da nova aresta
			int x = (e.getX() - (Executor.LARGURA_JANELA / 2));
			int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -1;
			fimRastro = new Ponto(x, y);
		}
		
		if (gl != null) { //Evitar excess�o ao mover o mouse antes de terminar a inicializa��o do JOGL
			glDrawable.display();
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (modoEdicao) {
			int i = 0;
			final int x = (e.getX() - (Executor.LARGURA_JANELA / 2));
			final int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -1;
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
			if (objetoFilho) { // verifica se est� adicionando um objeto filho 
				
				if (objetoSelecionado != null) {//se sim ent�o verifica se tem um objeto selecionado para ser o pai

					if (objetoEmCriacao == null) { 
						objetoEmCriacao = new ObjetoGrafico(corAtual);
					}
					
					int x = (e.getX() - (Executor.LARGURA_JANELA / 2));
					int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -1;
					
					inicioRastro = new Ponto(x, y);
					Ponto ponto = new Ponto(x, y);
					objetoEmCriacao.addPonto(ponto);
					
				} else { //sen�o verifica se selecionou algum objeto para ser o pai
					int i = 0;
					final int x = (e.getX() - (Executor.LARGURA_JANELA / 2));
					final int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -1;
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
				}

			} else {
				if (objetoEmCriacao == null) { // verifica se n�o existe objeto em cria��o ent�o cria
					objetoEmCriacao = new ObjetoGrafico(corAtual);
				}
				
				int x = (e.getX() - (Executor.LARGURA_JANELA / 2));
				int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -1;
				
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
			//Pontos de refer�ncia para a movimento��o do objetos gr�ficos/v�rtices
			antigoX = e.getX();
			antigoY = e.getY();
			
			if (objetoSelecionado != null) {
				final int x = (e.getX() - (Executor.LARGURA_JANELA / 2));
				final int y = (e.getY() - (Executor.ALTURA_JANELA / 2)) * -1;
				
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
				objetoGrafico.limparTransforma��es();
			}
		} else {
			modoEdicao = true;
		}
		retirarSelecao();
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
			objetoSelecionado.calcularBBox();
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
		if (!modoEdicao && objetoEmCriacao != null) { //tem um objeto em cria��o
			objetoEmCriacao.setPrimitivaGrafica(primitivaGrafica);
			objetoEmCriacao.calcularBBox();

			if (objetoFilho && objetoSelecionado != null) { // verifica se � um objeto filho e se tem um objeto selecionado para ser pai dele
				objetoSelecionado.setSelecionado(false);
				objetoFilho = false; //desmarca a flag que indica que o objeto gr�fico em cria��o ser� um filho

				objetoSelecionado.addObjetoGraficoFilho(objetoEmCriacao);
				objetoEmCriacao = null;
				objetoSelecionado = null;
			} else {
				// sen�o adiciona o objeto na lista de objetos do mundo
				objetos.add(objetoEmCriacao);
				objetoEmCriacao = null;
			}
		}
	}
	
	private void imprimirAtalhos() {
		JFrame janelaAjuda = new JFrame("Atalhos");
		janelaAjuda.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaAjuda.setBounds(720, 100, 300, 300);
		
		JPanel painelAtalhos = new JPanel(new GridLayout(17, 1));
		
		JLabel c = new JLabel("C = alterar cor", JLabel.LEFT);
		painelAtalhos.add(c);
		JLabel e = new JLabel("E = alterar modo cria��o/edi��o", JLabel.LEFT);
		painelAtalhos.add(e);
		JLabel w = new JLabel("W = mover c�mera para cima", JLabel.LEFT);
		painelAtalhos.add(w);
		JLabel s = new JLabel("S = mover c�mera para baixo", JLabel.LEFT);
		painelAtalhos.add(s);
		JLabel a = new JLabel("A = mover c�mera para esquerda", JLabel.LEFT);
		painelAtalhos.add(a);
		JLabel d = new JLabel("D = mover c�mera para direita", JLabel.LEFT);
		painelAtalhos.add(d);
		JLabel i = new JLabel("I = aumentar zoom", JLabel.LEFT);
		painelAtalhos.add(i);
		JLabel o = new JLabel("O = diminuir zoom", JLabel.LEFT);
		painelAtalhos.add(o);
		JLabel p = new JLabel("P = alterar primitiva gr�fica", JLabel.LEFT);
		painelAtalhos.add(p);
		JLabel delete = new JLabel("DELETE = deletar o pol�gono ou v�rtice", JLabel.LEFT);
		painelAtalhos.add(delete);
		JLabel esc = new JLabel("ESC = retirar sele��o do pol�gono e v�rtice", JLabel.LEFT);
		painelAtalhos.add(esc);
		JLabel t = new JLabel("T = terminar cria��o do objeto", JLabel.LEFT);
		painelAtalhos.add(t);
		JLabel q = new JLabel("Q = adicionar objeto filho", JLabel.LEFT);
		painelAtalhos.add(q);
		JLabel m = new JLabel("M = aumentar escala", JLabel.LEFT);
		painelAtalhos.add(m);
		JLabel n = new JLabel("N = diminuir escala", JLabel.LEFT);
		painelAtalhos.add(n);
		JLabel g = new JLabel("G = rotacionar no sentido anti-hor�rio", JLabel.LEFT);
		painelAtalhos.add(g);
		JLabel h = new JLabel("H = rotacionar no sentido hor�rio", JLabel.LEFT);
		painelAtalhos.add(h);
		
		janelaAjuda.getContentPane().add(painelAtalhos, BorderLayout.SOUTH);
		janelaAjuda.setResizable(false);
		janelaAjuda.setVisible(true);
	}
}
