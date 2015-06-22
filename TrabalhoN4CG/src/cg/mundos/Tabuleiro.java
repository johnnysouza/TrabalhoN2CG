package cg.mundos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import cg.base.Cor;
import cg.base.Peca;
import cg.business.LudoBusiness;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.TextureData;

public class Tabuleiro implements GLEventListener, KeyListener, MouseListener,
		MouseMotionListener {

	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;
	private double xEye, yEye, zEye;
	private double xCenter, yCenter, zCenter;
	private final LudoBusiness ludo;
	private BufferedImage image;
	private int idTexture[];
	private int width[] = new int[2];
	private int height[] = new int[2];
	private TextureData[] td = new TextureData[2];
	private ByteBuffer[] buffer = new ByteBuffer[2];
	private boolean aguardandoSelecao;

	private Peca[] pecasVerdes = new Peca[4];
	private Peca[] pecasVermelhas = new Peca[4];
	private Peca[] pecasAzuis = new Peca[4];
	private Peca[] pecasAmarelas = new Peca[4];

	public Tabuleiro(final LudoBusiness ludo) {
		this.ludo = ludo;
		ludo.setTabuleiro(this);
	}

	@Override
	public void mouseDragged(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(final MouseEvent arg0) {
		Object pecaSelecionada = selecionarPeca(arg0);
		if (pecaSelecionada != null) {
			movimentarPeca(pecaSelecionada);
			terminouJogada();
			JogadaComputador();
		}
	}

	private void JogadaComputador() {
		// TODO faz animação da movimentação dos dados e das peças dos players
	}

	private void movimentarPeca(final Object pecaSelecionada) {
		// TODO faz animação da movimentação da peça
	}

	private Object selecionarPeca(final MouseEvent arg0) {
		// TODO caso tenha selecinoado alguma peca do jogador, retorna ela,
		// necessário alterar classe de retorno quando definir
		return null;
	}

	@Override
	public void mouseEntered(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(final KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(final KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(final KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);

		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);

		ligarLuz();
		defineIluminacao();

		// Habilita o modelo de colorização de Gouraud
		gl.glShadeModel(GL.GL_SMOOTH);

		loadImage("resources\\ludo_board.jpg", 0);
		loadImage("resources\\madeira.jpg", 1);

		// Gera identificador de textura
		idTexture = new int[10];
		gl.glGenTextures(1, idTexture, 2);

		// Define os filtros de magnificação e minificação
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);

		xEye = 0.0f;
		yEye = -20.0f;
		zEye = 35.0f;
		xCenter = 0.0f;
		yCenter = 4.0f;
		zCenter = -10.0f;

		for (int i = 0; i < 4; i++) {
			pecasVerdes[i] = new Peca(i + 1, Cor.VERDE);
			pecasVermelhas[i] = new Peca(i + 1, Cor.VERMELHO);
			pecasAzuis[i] = new Peca(i + 1, Cor.AZUL);
			pecasAmarelas[i] = new Peca(i + 1, Cor.AMARELO);
		}
		
		pecasVerdes[0].setPosicao(57);
		pecasVerdes[1].setPosicao(58);
	}

	@Override
	public void reshape(final GLAutoDrawable drawable, final int x,
			final int y, final int width, final int height) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);

		glu.gluPerspective(60, width / height, 0.1, 100); // projecao Perpectiva
	}

	@Override
	public void display(final GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, 0.0f, 1.0f,
				0.0f);

		desenharTabuleiro();

		for (int i = 0; i < 4; i++) {
			pecasVerdes[i].desenharPeca(gl, glut);
			pecasVermelhas[i].desenharPeca(gl, glut);
			pecasAzuis[i].desenharPeca(gl, glut);
			pecasAmarelas[i].desenharPeca(gl, glut);
		}

		gl.glFlush();
	}

	private void desenharTabuleiro() {
		// Desenha um cubo no qual a textura é aplicada
		gl.glEnable(GL.GL_TEXTURE_2D); // Primeiro habilita uso de textura
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		gl.glScalef(16.0f, 16.0f, 3.0f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);

		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[0]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width[0], height[0], 0,
				GL.GL_BGR, GL.GL_UNSIGNED_BYTE, buffer[0]);
		gl.glBegin(GL.GL_QUADS);
		// Especifica a coordenada de textura para cada vértice
		// Face frontal
		gl.glNormal3f(0.0f, 0.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glEnd();

		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[1]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width[1], height[1], 0,
				GL.GL_BGR, GL.GL_UNSIGNED_BYTE, buffer[1]);
		// Face posterior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glEnd();

		// Face superior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glEnd();

		// Face inferior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0.0f, -1.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glEnd();

		// Face lateral direita
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glEnd();

		// Face lateral esquerda
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(-1.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glDisable(GL.GL_TEXTURE_2D); // Desabilita uso de textura
	}

	public void loadImage(final String fileName, int pos) {
		// Tenta carregar o arquivo
		image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.err.println("Erro na leitura do arquivo");
		}

		// Obtém largura e altura
		width[pos] = image.getWidth();
		height[pos] = image.getHeight();
		// Gera uma nova TextureData...
		td[pos] = new TextureData(0, 0, false, image);
		// ...e obtém um ByteBuffer a partir dela
		buffer[pos] = (ByteBuffer) td[pos].getBuffer();
	}

	private void ligarLuz() {
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);
	}

	/**
	 * Método usado para especificar os parâmetros de iluminação.
	 */
	public void defineIluminacao() {
		// Define os parâmetros através de vetores RGBA - o último valor deve
		// ser sempre 1.0f
		float luzAmbiente[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		float luzDifusa[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float luzEspecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float posicaoLuz[] = { 50.0f, 0.0f, 0.0f, 1.0f }; // último
															// parâmetro:
															// 0-direcional,
		float posicaoLuz2[] = { 5.0f, 5.0f, 50.0f, 1.0f };
		float luzEspecular2[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		float luzDifusa2[] = { 1.0f, 1.0f, 1.0f, 1.0f };

		// Ativa o uso da luz ambiente
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, luzAmbiente, 0);

		// Define os parâmetros da luz de número 0
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, luzAmbiente, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, luzDifusa, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, luzEspecular, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posicaoLuz, 0);

		// Define os parâmetros da luz de número 1
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, luzAmbiente, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, luzDifusa2, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, luzEspecular2, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, posicaoLuz2, 0);

		// Brilho do material
		float especularidade[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		int especMaterial = 60;

		// Define a reflectância do material
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, especularidade, 0);
		// Define a concentração do brilho
		gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, especMaterial);
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1,
			final boolean arg2) {
		// TODO Auto-generated method stub

	}

	public void aguardarSelecao() {
		aguardandoSelecao = true;
		// TODO: animação de aguardando seleção de peça
	}

	private void terminouJogada() {
		aguardandoSelecao = false;
		// TODO: animação de fim de jogada
	}

}
