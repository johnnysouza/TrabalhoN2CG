package cg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cg.base.Cor;
import cg.business.LudoBusiness;
import cg.mundos.Dado;
import cg.mundos.Tabuleiro;

public class MainFrame extends JFrame {

	private static final MainFrame JANELA = new MainFrame();

	private final JPanel contentPane;
	private final Tabuleiro rendererTabuleiro;
	private final Dado rendererDado;
	private final JLabel activePLayer;
	private int currentPlayer = 0;

	private JPanel info;
	private JLabel gameOver;
	private JLabel vencedor;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 644);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel dado = new JPanel();
		dado.setBounds(600, 400, 200, 200);
		dado.setLayout(new BorderLayout());
		contentPane.add(dado);

		info = new JPanel();
		info.setBounds(600, 0, 200, 400);
		info.setBackground(new Color(140, 140, 140));
		contentPane.add(info);
		info.setLayout(null);

		Font defaultFont = new Font("Times New Roman", Font.BOLD, 24);

		JLabel lblPlayer_1 = new JLabel("Player 1");
		lblPlayer_1.setForeground(Color.GREEN);
		lblPlayer_1.setFont(defaultFont);
		lblPlayer_1.setBounds(20, 30, 90, 18);
		info.add(lblPlayer_1);

		JLabel lblPlayer_2 = new JLabel("Player 2");
		lblPlayer_2.setForeground(Color.RED);
		lblPlayer_2.setFont(defaultFont);
		lblPlayer_2.setBounds(20, 80, 90, 18);
		info.add(lblPlayer_2);

		JLabel lblPlayer_3 = new JLabel("Player 3");
		lblPlayer_3.setForeground(Color.BLUE);
		lblPlayer_3.setFont(defaultFont);
		lblPlayer_3.setBounds(20, 130, 90, 18);
		info.add(lblPlayer_3);

		JLabel lblPlayer_4 = new JLabel("Player 4");
		lblPlayer_4.setFont(defaultFont);
		lblPlayer_4.setForeground(Color.YELLOW);
		lblPlayer_4.setBounds(20, 180, 90, 18);
		info.add(lblPlayer_4);

		JLabel lblpts_1 = new JLabel("0pts");
		lblpts_1.setFont(defaultFont);
		lblpts_1.setForeground(Color.GREEN);
		lblpts_1.setBounds(140, 30, 46, 18);
		info.add(lblpts_1);

		JLabel lblpts_2 = new JLabel("0pts");
		lblpts_2.setFont(defaultFont);
		lblpts_2.setForeground(Color.RED);
		lblpts_2.setBounds(140, 80, 46, 18);
		info.add(lblpts_2);

		JLabel lblpts_3 = new JLabel("0pts");
		lblpts_3.setFont(defaultFont);
		lblpts_3.setForeground(Color.BLUE);
		lblpts_3.setBounds(140, 130, 46, 18);
		info.add(lblpts_3);

		JLabel lblpts_4 = new JLabel("0pts");
		lblpts_4.setFont(defaultFont);
		lblpts_4.setForeground(Color.YELLOW);
		lblpts_4.setBounds(140, 180, 46, 18);
		info.add(lblpts_4);

		JLabel gameOver = new JLabel("Fim de jogo");
		gameOver.setFont(defaultFont);
		gameOver.setForeground(Color.WHITE);
		gameOver.setBounds(40, 280, 150, 30);
		gameOver.setVisible(false);
		info.add(gameOver);

		JLabel vencedor = new JLabel("Você venceu!");
		vencedor.setFont(defaultFont);
		vencedor.setForeground(Color.WHITE);
		vencedor.setBounds(35, 330, 150, 20);
		vencedor.setVisible(false);
		info.add(vencedor);

		ImageIcon img = new ImageIcon("resources\\seta.png");
		activePLayer = new JLabel();
		activePLayer.setIcon(img);
		activePLayer.setBounds(0, 30 + (currentPlayer * 50), 20, 14);
		info.add(activePLayer);

		JPanel game = new JPanel();
		game.setBounds(0, 0, 600, 600);
		game.setLayout(new BorderLayout());
		contentPane.add(game);

		/*
		 * Cria um objeto GLCapabilities para especificar o numero de bits por
		 * pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8);

		LudoBusiness ludo = new LudoBusiness();
		rendererDado = new Dado(ludo);
		rendererTabuleiro = new Tabuleiro(ludo);

		/*
		 * Cria um canvas, adiciona ao frame e objeto "ouvinte" para os eventos
		 * Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		game.add(canvas, BorderLayout.CENTER);
		canvas.addGLEventListener(rendererTabuleiro);
		canvas.addKeyListener(rendererTabuleiro);
		canvas.addMouseListener(rendererTabuleiro);
		canvas.addMouseMotionListener(rendererTabuleiro);
		canvas.requestFocus();

		GLCanvas canvas2 = new GLCanvas(glCaps);
		dado.add(canvas2, BorderLayout.CENTER);
		canvas2.addGLEventListener(rendererDado);
		canvas2.addMouseListener(rendererDado);
		canvas2.addKeyListener(rendererTabuleiro);
		canvas2.requestFocus();

		setResizable(false);
	}

	public void encerrarJogo(final Cor cor) {
		gameOver.setVisible(true);

		String vencedorStr = "";
		if (cor == Cor.VERDE) {
			vencedorStr = "Você venceu!";
		} else {
			vencedorStr = cor.getNome() + " venceu!";
		}

		vencedor.setText(vencedorStr);
		vencedor.setVisible(true);
	}
	
	public void nextPlayer() {
		activePLayer.setLocation(0, 30 + (currentPlayer * 50));
		requestFocus();
		info.revalidate();
		info.repaint();
		if (currentPlayer == 3) {
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}
	}

	public static MainFrame getInstance() {
		return JANELA;
	}
}
