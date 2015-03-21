package cg.exer4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import cg.util.CircleUtils;

public class Main implements GLEventListener, KeyListener {

	private float ortho2D_minX = -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private int zoom = 100;
	final double ang1 = 0;
	double ang2 = 45;
	final double raio1 = 0;
	double raio2 = 100;
	double movLat = 0;

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
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(3.0f);

		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;

		gl.glBegin(GL.GL_LINES);
		x1 = CircleUtils.RetornaX(ang1, raio1) + movLat;
		y1 = CircleUtils.RetornaY(ang1, raio1);
		x2 = CircleUtils.RetornaX(ang2, raio2) + movLat;
		y2 = CircleUtils.RetornaY(ang2, raio2);
		gl.glVertex2d(x1, y1);
		gl.glVertex2d(x2, y2);
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
		case KeyEvent.VK_Q: // Move reta para esquerda
			movLat--;
			break;
		case KeyEvent.VK_W: // Move reta para esquerda
			movLat++;
			break;
		case KeyEvent.VK_A: // Diminui raio
			raio2--;
			break;
		case KeyEvent.VK_S: // Aumenta raio
			raio2++;
			break;
		case KeyEvent.VK_Z: // Diminui ângulo
			if (ang2 == 0) {
				ang2 = 360;
			}
			ang2--;
			break;
		case KeyEvent.VK_X: // Aumenta ângulo
			if (ang2 == 360) {
				ang2 = 0;
			}
			ang2++;
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

}
