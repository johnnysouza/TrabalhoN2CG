package cg.exer7;

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

import cg.util.CircleUtils;
import cg.util.Ponto2D;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private static final int RAIO = 150;
	
	private float ortho2D_minX = -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private int zoom = 100;

	private int antigoX;
	private int antigoY;

	private Ponto2D centroCirculoInterno;
	private Ponto2D centroCirculoExterno;
	private Ponto2D antigoCentroCirculoInterno;
	private Ponto2D[] BBoxInterno = new Ponto2D[4];

	public Main() {
		centroCirculoExterno = new Ponto2D(200, 200);
		centroCirculoInterno = new Ponto2D(200, 200);
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
		gl.glColor3f(0.0f, 0.0f, 0.0f); // Preto
		gl.glLineWidth(2.0f);

		double x = 0;
		double y = 0;

		// Desenha circulo maior externo
		gl.glBegin(GL.GL_LINE_LOOP);
		for (int ang = 0; ang < 360; ang += 10) {
			x = CircleUtils.RetornaX(ang, RAIO) + 200;
			y = CircleUtils.RetornaY(ang, RAIO) + 200;
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		// Calcula BBox interna do circulo maior externo
		x = CircleUtils.RetornaX(45, RAIO);
		y = CircleUtils.RetornaY(45, RAIO);
		BBoxInterno[0] = new Ponto2D(x, y);
		BBoxInterno[1] = new Ponto2D(-BBoxInterno[0].getX(), BBoxInterno[0].getY());
		BBoxInterno[2] = new Ponto2D(-BBoxInterno[0].getX(), -BBoxInterno[0].getY());
		BBoxInterno[3] = new Ponto2D(BBoxInterno[0].getX(), -BBoxInterno[0].getY());

		for (int i = 0; i < BBoxInterno.length; i++) {
			BBoxInterno[i] = new Ponto2D(BBoxInterno[i].getX() + 200, BBoxInterno[i].getY() + 200);
		}

		double maxX = BBoxInterno[0].getX();
		double maxY = BBoxInterno[0].getX();
		double minX = BBoxInterno[2].getY();
		double minY = BBoxInterno[2].getY();

		if (maxX > centroCirculoInterno.getX() && minX < centroCirculoInterno.getX() && maxY > centroCirculoInterno.getY() && minY < centroCirculoInterno.getY()) {
			antigoCentroCirculoInterno = new Ponto2D(centroCirculoInterno.getX(), centroCirculoInterno.getY());
			desenharCirculoInterno();
			gl.glColor3f(1.0f, 0.0f, 1.0f); // Magenta para BBox
		} else {
			// calcula distância euclidiana com o centro do círculo grande
			double dist = Math.pow(centroCirculoExterno.getX() - centroCirculoInterno.getX(), 2) + Math.pow(centroCirculoExterno.getY() - centroCirculoInterno.getY(), 2);
			if (dist < RAIO * RAIO) {
				antigoCentroCirculoInterno = new Ponto2D(centroCirculoInterno.getX(), centroCirculoInterno.getY());
				desenharCirculoInterno();
			} else {
				desenharCirculoInternoAntigo();
			}
			gl.glColor3f(1.0f, 0.5f, 0.0f); // Laranja para BBox
		}

		gl.glBegin(GL.GL_LINE_LOOP);
		for (int i = 0; i < BBoxInterno.length; i++) {
			gl.glVertex2d(BBoxInterno[i].getX(), BBoxInterno[i].getY());
		}
		gl.glEnd();
	}

	private void desenharCirculoInterno() {
		// desenha circulo menor interno
		gl.glColor3f(0.0f, 0.0f, 0.0f); // Preto
		gl.glBegin(GL.GL_LINE_LOOP);
		for (int ang = 0; ang < 360; ang += 10) {
			double x = CircleUtils.RetornaX(ang, 50) + (centroCirculoInterno.getX());
			double y = CircleUtils.RetornaY(ang, 50) + (centroCirculoInterno.getY());
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		// Desenha ponto central do circulo menor
		gl.glPointSize(5.0f);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2d(centroCirculoInterno.getX(), centroCirculoInterno.getY());
		gl.glEnd();
	}

	private void desenharCirculoInternoAntigo() {
		// desenha circulo menor interno
		gl.glColor3f(0.0f, 0.0f, 0.0f); // Preto
		gl.glBegin(GL.GL_LINE_LOOP);
		for (int ang = 0; ang < 360; ang += 10) {
			double x = CircleUtils.RetornaX(ang, 50) + (antigoCentroCirculoInterno.getX());
			double y = CircleUtils.RetornaY(ang, 50) + (antigoCentroCirculoInterno.getY());
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		// Desenha ponto central do circulo menor
		gl.glPointSize(5.0f);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2d(antigoCentroCirculoInterno.getX(), antigoCentroCirculoInterno.getY());
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {
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

		gl.glColor3f(1.0f, 0.0f, 1.0f); // Magenta
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
	public void mouseDragged(MouseEvent e) {
		int movtoX = e.getX() - antigoX;
		int movtoY = e.getY() - antigoY;

		centroCirculoInterno.setX(centroCirculoInterno.getX() + movtoX);
		centroCirculoInterno.setY(centroCirculoInterno.getY() - movtoY);

		antigoX = e.getX();
		antigoY = e.getY();

		glDrawable.display(); // redesenhar ...
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		centroCirculoInterno = new Ponto2D(200, 200);
		glDrawable.display(); // redesenhar ...
	}

}
