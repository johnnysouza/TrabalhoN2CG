package cg.exer3;

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
		gl.glPointSize(2.0f);

		double x = 0;
		double y = 0;

		// Desenha os 3 circulos com raios 100
		gl.glBegin(GL.GL_POINTS);
		for (int ang = 0; ang < 360; ang += 1) {
			x = CircleUtils.RetornaX(ang, 100) - 100;
			y = CircleUtils.RetornaY(ang, 100) + 100;
			gl.glVertex2d(x, y);
		}
		for (int ang = 0; ang < 360; ang += 1) {
			x = CircleUtils.RetornaX(ang, 100) + 100;
			y = CircleUtils.RetornaY(ang, 100) + 100;
			gl.glVertex2d(x, y);
		}
		for (int ang = 0; ang < 360; ang += 1) {
			x = CircleUtils.RetornaX(ang, 100);
			y = CircleUtils.RetornaY(ang, 100) - 100;
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		gl.glColor3f(0.0f, 1.0f, 1.0f); // Ciano
		gl.glLineWidth(3.0f);
		// Desenha o triângulo
		gl.glBegin(GL.GL_LINE_LOOP);
		// Usando line loop para ter apenas 3 pontos desenhados, deixando para o
		// openGl ligar o último ponto ao primeiro
		gl.glVertex2d(-100, 100);
		gl.glVertex2d(100, 100);
		gl.glVertex2d(0, -100);
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
