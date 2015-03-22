package cg.exer6;

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

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private float ortho2D_minX = -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Ponto2D[] pontos = new Ponto2D[4];
	private int pontoSelecionado;
	private int antigoX;
	private int antigoY;

	public Main() {
		pontos[0] = new Ponto2D(-100, -100);
		pontos[1] = new Ponto2D(-100, 100);
		pontos[2] = new Ponto2D(100, 100);
		pontos[3] = new Ponto2D(100, -100);
	}

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Branco de fundo
	}

	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(ortho2D_minX, ortho2D_maxX, ortho2D_minY, ortho2D_maxY);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		SRU();

		// seu desenho ...
		gl.glPointSize(10.0f);
		gl.glLineWidth(3.0f);

		//Ponto selecionado
		gl.glBegin(GL.GL_POINTS);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Vermelho
		gl.glVertex2f(pontos[pontoSelecionado].getX(), pontos[pontoSelecionado].getY());
		gl.glEnd();

		//Poliedro
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 1.0f, 1.0f); // Ciano
		for (int i = 0; i < pontos.length; i++) {
			gl.glVertex2f(pontos[i].getX(), pontos[i].getY());
		}
		gl.glEnd();

		//TODO Spline
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_1: // Ponto 1
			pontoSelecionado = 0;
			break;
		case KeyEvent.VK_2: // Ponto 2
			pontoSelecionado = 1;
			break;
		case KeyEvent.VK_3: // Ponto 3
			pontoSelecionado = 2;
			break;
		case KeyEvent.VK_4: // Ponto 4
			pontoSelecionado = 3;
			break;
		}
		glDrawable.display(); // redesenhar ...
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void SRU() {
		// gl.glDisable(gl.GL_TEXTURE_2D);
		// gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
		// gl.glDisable(gl.GL_LIGHTING);

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
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int movtoX = e.getX() - antigoX;
		int movtoY = e.getY() - antigoY;
		pontos[pontoSelecionado].setX(pontos[pontoSelecionado].getX() + movtoX);
		pontos[pontoSelecionado].setY(pontos[pontoSelecionado].getY() - movtoY);

		antigoX = e.getX();
		antigoY = e.getY();

		glDrawable.display(); // redesenhar ...
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
