package cg.mundos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import cg.business.LudoBusiness;

import com.sun.opengl.util.texture.TextureData;

public class Dado implements GLEventListener, MouseListener {

	private static final int QUANT_FACES_CUBO = 6;

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private BufferedImage image;
	private int idTexture[];
	private int width, height;
	private final TextureData[] td = new TextureData[6];
	private final ByteBuffer[] buffer = new ByteBuffer[6];

	private final LudoBusiness ludo;

	private MouseEvent mouse;

	public Dado(final LudoBusiness ludo) {
		this.ludo = ludo;
		ludo.setDado(this);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	private int rolarDado() {
		// TODO Animação do dado rolando, retorna o valor do dado
		return 0;
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		mouse = e;
		glDrawable.display();
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

	@Override
	public void display(final GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		especificaParametrosVisualizacao();

		drawCube();

		if ((mouse != null) && (mouse.getButton() == MouseEvent.BUTTON1)){
			if (ludo.podeRolarDado()) {//TODO:validar também se foi clicado no mouse
				int valor = rolarDado();
				ludo.dadoRolado(valor);
			}
		}

		gl.glFlush();
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1,
			final boolean arg2) {
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glEnable(GL.GL_DEPTH_TEST);

		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);

		//		gl.glEnable(GL.GL_COLOR_MATERIAL);
		//		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);

		gl.glShadeModel(GL.GL_SMOOTH);

		ligarLuz();

		for (int i = 0; i < QUANT_FACES_CUBO; i++) {
			loadImage("resources\\dado_" + (i + 1) + ".jpg", i);
		}

		// Gera identificador de textura
		idTexture = new int[10];
		//		gl.glGenTextures(1, idTexture, 1);
		//		gl.glGenTextures(1, idTexture, 2);
		//		gl.glGenTextures(1, idTexture, 3);
		//		gl.glGenTextures(1, idTexture, 4);
		//		gl.glGenTextures(1, idTexture, 5);
		gl.glGenTextures(1, idTexture, 6);

		// Especifica qual é a textura corrente pelo identificador
		//		for (int i = 0; i < QUANT_FACES_CUBO; i++) {
		//			gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[i]);
		//		}

		// Envio da textura para OpenGL
		//		for (int i = 0; i < QUANT_FACES_CUBO; i++) {
		//			gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
		//					GL.GL_UNSIGNED_BYTE, buffer[i]);
		//		}

		// Define os filtros de magnificação e minificação
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);
	}

	@Override
	public void reshape(final GLAutoDrawable arg0, final int arg1,
			final int arg2, final int arg3, final int arg4) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);

		glu.gluPerspective(60, width / height, 0.1, 100); // projecao Perpectiva
	}

	public void loadImage(final String fileName, final int pos) {
		// Tenta carregar o arquivo
		image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.err.println("Erro na leitura do arquivo");
		}

		// Obtém largura e altura
		width = image.getWidth();
		height = image.getHeight();
		// Gera uma nova TextureData...
		td[pos] = new TextureData(0, 0, false, image);
		// ...e obtém um ByteBuffer a partir dela
		buffer[pos] = (ByteBuffer) td[pos].getBuffer();
	}

	private void ligarLuz() {
		float posLight[] = { 5.0f, 5.0f, -10.0f, 0.0f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
		gl.glEnable(GL.GL_LIGHT0);
	}

	private void drawCube() {
		// Desenha um cubo no qual a textura é aplicada
		gl.glEnable(GL.GL_TEXTURE_2D); // Primeiro habilita uso de textura
		gl.glPushMatrix();
		gl.glScalef(0.5f, 0.5f, 0.5f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);


		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[0]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer[0]);
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

		// Face posterior
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[5]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer[5]);
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
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[1]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer[1]);
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
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[4]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer[4]);
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
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[3]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer[3]);
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
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[2]);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer[2]);
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

	public void especificaParametrosVisualizacao() {
		// Especifica sistema de coordenadas de projeção
		gl.glMatrixMode(GL.GL_PROJECTION);
		// Inicializa sistema de coordenadas de projeção
		gl.glLoadIdentity();

		double angle = 50;
		double fAspect = 1;
		// Especifica a projeção perspectiva(angulo,aspecto,zMin,zMax)
		glu.gluPerspective(angle, fAspect, 0.2, 500);

		posicionaObservador();
	}

	public void posicionaObservador() {
		// Especifica sistema de coordenadas do modelo
		gl.glMatrixMode(GL.GL_MODELVIEW);
		// Inicializa sistema de coordenadas do modelo
		gl.glLoadIdentity();
		// Especifica posição do observador e do alvo
		gl.glTranslatef(-2, -2, -8);
		gl.glRotatef(0, 1, 0, 0);
		gl.glRotatef(0, 0, 1, 0);

	}

}
