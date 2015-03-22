package cg.exer5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {

	private float ortho2D_minX = -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private int primitiva = 0;

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
		
		gl.glPointSize(5.0f);
		gl.glLineWidth(5.0f);

		// seu desenho ...
		switch (primitiva) {
		case 0:
			System.out.println("GL_POINTS");
			gl.glBegin(GL.GL_POINTS);
			break;
		case 1:
			System.out.println("GL_LINES");
			gl.glBegin(GL.GL_LINES);
			break;
		case 2:
			System.out.println("GL_LINE_LOOP");
			gl.glBegin(GL.GL_LINE_LOOP);
			break;
		case 3:
			System.out.println("GL_LINE_STRIP");
			gl.glBegin(GL.GL_LINE_STRIP);
			break;
		case 4:
			System.out.println("GL_TRIANGLES");
			gl.glBegin(GL.GL_TRIANGLES);
			break;
		case 5:
			System.out.println("GL_TRIANGLE_FAN");
			gl.glBegin(GL.GL_TRIANGLE_FAN);
			break;
		case 6:
			System.out.println("GL_TRIANGLE_STRIP");
			gl.glBegin(GL.GL_TRIANGLE_STRIP);
			break;
		case 7:
			System.out.println("GL_QUADS");
			gl.glBegin(GL.GL_QUADS);
			break;
		case 8:
			System.out.println("GL_QUAD_STRIP");
			gl.glBegin(GL.GL_QUAD_STRIP);
			break;
		case 9:
			System.out.println("GL_POLYGON");
			gl.glBegin(GL.GL_POLYGON);
			break;
		}
		
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Vermelho
		gl.glVertex2f(200.0f, -200.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Verde
		gl.glVertex2f(200.0f, 200.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Azul
		gl.glVertex2f(-200.0f, 200.0f);
		gl.glColor3f(1.0f, 0.0f, 1.0f); // Magenta
		gl.glVertex2f(-200.0f, -200.0f);
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE: // Alterna primitiva
			if (primitiva < 9) {
				primitiva++;
			} else {
				primitiva = 0;
			}
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
