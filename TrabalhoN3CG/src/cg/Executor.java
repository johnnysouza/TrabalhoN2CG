package cg;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cg.base.Mundo;

public class Executor extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private Mundo renderer = new Mundo();

	public static final int LARGURA_JANELA = 400, ALTURA_JANELA = 400;

	public Executor() {
		// Cria o frame.
		super("CG-N3 - Pressione F1 para exibir atalhos");
		setBounds(300, 100, LARGURA_JANELA, ALTURA_JANELA + 22); // 500 + 22 da borda do t�tulo da janela
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		/*
		 * Cria um objeto GLCapabilities para especificar o numero de bits por
		 * pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8);

		/*
		 * Cria um canvas, adiciona ao frame e objeto "ouvinte" para os eventos
		 * Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas, BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseMotionListener(renderer);
		canvas.requestFocus();
		
		System.out.println("Pressione F1 para exibir ajuda dos atalhos");
		
		setResizable(false);
	}

	public static void main(String[] args) {
		new Executor().setVisible(true);
	}

}
