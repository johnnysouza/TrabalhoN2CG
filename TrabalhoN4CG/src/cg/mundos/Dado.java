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

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private BufferedImage image;
	private int idTexture[];
	private int width, height;
	private TextureData td;
	private ByteBuffer buffer;

	private final LudoBusiness ludo;

	public Dado(final LudoBusiness ludo) {
		this.ludo = ludo;
		ludo.setDado(this);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (ludo.podeRolarDado()) {
			int valor = rolarDado();
			ludo.dadoRolado(valor);
		}
	}

	private int rolarDado() {
		// TODO Anima��o do dado rolando, retorna o valor do dado
		return 0;
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(final GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		especificaParametrosVisualizacao();

		drawCube();

		gl.glFlush();
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1,
			final boolean arg2) {
		// TODO Auto-generated method stub
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

		loadImage("resources\\dado_1.jpg");

		// Gera identificador de textura
		idTexture = new int[10];
		gl.glGenTextures(1, idTexture, 1);

		// Especifica qual � a textura corrente pelo identificador
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[0]);

		// Envio da textura para OpenGL
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, width, height, 0, GL.GL_BGR,
				GL.GL_UNSIGNED_BYTE, buffer);

		// Define os filtros de magnifica��o e minifica��o
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

	public void loadImage(String fileName) {
		// Tenta carregar o arquivo
		image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.err.println("Erro na leitura do arquivo");
		}

		// Obt�m largura e altura
		width = image.getWidth();
		height = image.getHeight();
		// Gera uma nova TextureData...
		td = new TextureData(0, 0, false, image);
		// ...e obt�m um ByteBuffer a partir dela
		buffer = (ByteBuffer) td.getBuffer();
	}

	private void ligarLuz() {
		float posLight[] = { 5.0f, 5.0f, -10.0f, 0.0f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
		gl.glEnable(GL.GL_LIGHT0);
	}

	private void drawCube() {
		// Desenha um cubo no qual a textura � aplicada
		gl.glEnable(GL.GL_TEXTURE_2D); // Primeiro habilita uso de textura
		gl.glPushMatrix();
		gl.glScalef(0.5f, 0.5f, 0.5f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin(GL.GL_QUADS);
		// Especifica a coordenada de textura para cada v�rtice
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
		// Face posterior
		gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		// Face superior
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		// Face inferior
		gl.glNormal3f(0.0f, -1.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		// Face lateral direita
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		// Face lateral esquerda
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
		// Especifica sistema de coordenadas de proje��o
		gl.glMatrixMode(GL.GL_PROJECTION);
		// Inicializa sistema de coordenadas de proje��o
		gl.glLoadIdentity();

		double angle = 50;
		double fAspect = 1;
		// Especifica a proje��o perspectiva(angulo,aspecto,zMin,zMax)
		glu.gluPerspective(angle, fAspect, 0.2, 500);

		posicionaObservador();
	}

	public void posicionaObservador() {
		// Especifica sistema de coordenadas do modelo
		gl.glMatrixMode(GL.GL_MODELVIEW);
		// Inicializa sistema de coordenadas do modelo
		gl.glLoadIdentity();
		// Especifica posi��o do observador e do alvo
		gl.glTranslatef(-2, -2, -8);
		gl.glRotatef(0, 1, 0, 0);
		gl.glRotatef(0, 0, 1, 0);

	}

}
