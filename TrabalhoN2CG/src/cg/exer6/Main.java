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

import cg.util.Ponto2D;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private float ortho2D_minX = -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private final Ponto2D[] pontos = new Ponto2D[4];
	private int pontoSelecionado;
	private int antigoX;
	private int antigoY;
	private int zoom = 100;

	public Main() {
		int x = 180;
		int y = 75;
		pontos[0] = new Ponto2D(x, y);
		pontos[1] = new Ponto2D(x / 1.2, y);
		pontos[2] = new Ponto2D(x / 1.2, 0);
		pontos[3] = new Ponto2D(0, 0);
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Branco de fundo
	}

	@Override
	public void display(final GLAutoDrawable arg0) {
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

		// Ponto selecionado
		gl.glBegin(GL.GL_POINTS);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Vermelho
		gl.glVertex2d(pontos[pontoSelecionado].getX(), pontos[pontoSelecionado].getY());
		gl.glEnd();

		// Poliedro
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 1.0f, 1.0f); // Ciano
		for (int i = 0; i < pontos.length; i++) {
			gl.glVertex2d(pontos[i].getX(), pontos[i].getY());
		}
		gl.glEnd();

		//Spline
		gl.glBegin(GL.GL_LINE_STRIP);

		gl.glColor3f(1.0f, 1.0f, 0.0f); // Amarelo
		for (float t = 0.0f; t < 1.001f; t = t + 0.05f) {
			Ponto2D p0p1 = calculaPontoSpline(pontos[0], pontos[1], t);
			Ponto2D p1p2 = calculaPontoSpline(pontos[1], pontos[2], t);
			Ponto2D p2p3 = calculaPontoSpline(pontos[2], pontos[3], t);

			Ponto2D p0p1p2 = calculaPontoSpline(p0p1, p1p2, t);
			Ponto2D p1p2p3 = calculaPontoSpline(p1p2, p2p3, t);

			Ponto2D p0p1p2p3 = calculaPontoSpline(p0p1p2, p1p2p3, t);
			gl.glVertex2d(p0p1p2p3.getX(), p0p1p2p3.getY());
		}

		gl.glEnd();
	}

	private Ponto2D calculaPontoSpline(final Ponto2D ponto1, final Ponto2D ponto2, final float t) {
		double x = ponto1.getX() + ((ponto2.getX() - ponto1.getX()) * t);
		double y = ponto1.getY() + ((ponto2.getY() - ponto1.getY()) * t);

		return new Ponto2D(x, y);
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_I: // Mais zoom
			if (zoom < 400) {
				zoom++;
				ortho2D_minX++;
				ortho2D_minY++;
				ortho2D_maxX--;
				ortho2D_maxY--;
			}
			break;
		case KeyEvent.VK_O: // Menos zoom
			if (zoom > -300) {
				zoom--;
				ortho2D_minX--;
				ortho2D_minY--;
				ortho2D_maxX++;
				ortho2D_maxY++;
			}
			break;
		case KeyEvent.VK_E: // Move para esquerda
			ortho2D_maxX++;
			ortho2D_minX++;
			break;
		case KeyEvent.VK_D: // Move para direita
			ortho2D_maxX--;
			ortho2D_minX--;
			break;
		case KeyEvent.VK_C: // Move para cima
			ortho2D_maxY--;
			ortho2D_minY--;
			break;
		case KeyEvent.VK_B: // Move para baixo
			ortho2D_maxY++;
			ortho2D_minY++;
			break;
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

	@Override
	public void reshape(final GLAutoDrawable arg0, final int arg1, final int arg2, final int arg3, final int arg4) {
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1, final boolean arg2) {
	}

	@Override
	public void keyReleased(final KeyEvent arg0) {
	}

	@Override
	public void keyTyped(final KeyEvent arg0) {
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
	public void mouseClicked(final MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		int movtoX = e.getX() - antigoX;
		int movtoY = e.getY() - antigoY;
		pontos[pontoSelecionado].setX(pontos[pontoSelecionado].getX() + movtoX);
		pontos[pontoSelecionado].setY(pontos[pontoSelecionado].getY() - movtoY);

		antigoX = e.getX();
		antigoY = e.getY();

		glDrawable.display(); // redesenhar ...
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}

}
