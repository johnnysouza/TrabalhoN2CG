package cg.mundos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import cg.base.Cor;
import cg.business.LudoBusiness;

import com.sun.opengl.util.GLUT;

public class Tabuleiro implements GLEventListener, KeyListener, MouseListener,
MouseMotionListener {

	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;
	private double xEye, yEye, zEye;
	private double xCenter, yCenter, zCenter;
	private final LudoBusiness ludo;
	private boolean aguardandoSelecao;

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
		if(pecaSelecionada != null){
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
		// TODO caso tenha selecinoado alguma peca do jogador, retorna ela, necessário alterar classe de retorno quando definir
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

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		xEye = 20.0f;
		yEye = 20.0f;
		zEye = 20.0f;
		xCenter = 0.0f;
		yCenter = 0.0f;
		zCenter = 0.0f;

		ligarLuz();

		gl.glEnable(GL.GL_CULL_FACE);
		// gl.glDisable(GL.GL_CULL_FACE);

		gl.glEnable(GL.GL_DEPTH_TEST);
		// gl.glDisable(GL.GL_DEPTH_TEST);
	}

	@Override
	public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width,
			final int height) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);

		// glu.gluOrtho2D(-30.0f, 30.0f, -30.0f, 30.0f);
		glu.gluPerspective(60, width / height, 0.1, 100); // projecao Perpectiva
		// 1 pto fuga 3D
		// gl.glFrustum (-5.0, 5.0, -5.0, 5.0, 10, 100); // projecao Perpectiva
		// 1 pto fuga 3D
		// gl.glOrtho(-30.0f, 30.0f, -30.0f, 30.0f, -30.0f, 30.0f); // projecao
		// Ortogonal 3D

	}

	@Override
	public void display(final GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, 0.0f, 1.0f,
				0.0f);

		drawAxis();
		// SRU();
		gl.glColor3f(1.0f, 0.0f, 0.0f);

		float translacaoCubo1[] = { 0.0f, 0.0f, 0.0f };
		float escalaCubo1[] = { 2.0f, 2.0f, 2.0f };

		drawCube(translacaoCubo1, escalaCubo1, Cor.AMARELO);

		gl.glFlush();
	}

	private void drawCube(final float translacao[], final float escala[], final Cor cor) {
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor.toFloatArray(), 0);
		gl.glEnable(GL.GL_LIGHTING);

		gl.glPushMatrix();
		gl.glScalef(escala[0], escala[1], escala[2]);
		gl.glTranslated(translacao[0], translacao[1], translacao[2]);
		glut.glutSolidCube(1.0f);
		gl.glPopMatrix();

		gl.glDisable(GL.GL_LIGHTING);
	}

	public void drawAxis() {
		// eixo X - Red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();
		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();
		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();
	}

	private void ligarLuz() {
		float posLight[] = { 5.0f, 5.0f, 10.0f, 0.0f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
		gl.glEnable(GL.GL_LIGHT0);
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1, final boolean arg2) {
		// TODO Auto-generated method stub

	}

	public void aguardarSelecao() {
		aguardandoSelecao = true;
		//TODO: animação de aguardando seleção de peça
	}

	private void terminouJogada() {
		aguardandoSelecao = false;
		//TODO: animação de fim de jogada
	}

}
