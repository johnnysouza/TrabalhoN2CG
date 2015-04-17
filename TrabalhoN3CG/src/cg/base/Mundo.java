package cg.base;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Mundo implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private final float			ortho2D_minX	= -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL					gl;
	private GLU					glu;
	private GLAutoDrawable		glDrawable;
	private Camera				camera;
	private List<ObjetoGrafico>	objetos;
	private ObjetoGrafico		emCriacao;

	void adicionarObjetoGráfico(final ObjetoGrafico objetoGrafico) {
		if (objetoGrafico != null) {
			objetos.add(objetoGrafico);
		}
	}

	void removerObjetoGráfico(final ObjetoGrafico objetoGrafico) {
		objetos.remove(objetoGrafico);
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(0.85f, 0.85f, 0.85f, 1.0f); // Cinza claro de fundo
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(final KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			objetos.add(emCriacao);
			emCriacao.calcularBBox();
			emCriacao = null;
		}

	}

	@Override
	public void keyReleased(final KeyEvent e) {
		// TODO Auto-generated method stub

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

		if (emCriacao != null) {
			emCriacao.desenhar(gl);
		}
		for (ObjetoGrafico objetoGrafico : objetos) {
			objetoGrafico.desenhar(gl);
		}
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1, final boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(final GLAutoDrawable arg0, final int arg1, final int arg2, final int arg3, final int arg4) {
		// TODO Auto-generated method stub

	}

	public void SRU() {
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
	public void mouseDragged(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		if (emCriacao != null) {
			Ponto ponto = new Ponto(e.getX(), e.getY());
			emCriacao.addPonto(ponto);
			try {
				glDrawable.display();
			} finally {
				emCriacao.removePonto(ponto);
			}
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if ((emCriacao == null) || !selecionouObjeto()) {
			Ponto ponto = new Ponto(e.getX(), e.getY(), 0, 0);
			if (emCriacao == null) {
				emCriacao = new ObjetoGrafico();
			}
			emCriacao.addPonto(ponto);
			glDrawable.display();
		}
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

}
