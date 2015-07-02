package cg.base;

import javax.media.opengl.GL;

import cg.mundos.Tabuleiro;

import com.sun.opengl.util.GLUT;

public class Peca {

	private static final double RAIO = 0.75;

	private final int id;
	private int posicao;
	private final Cor cor;
	private final float[] translate;

	private final Tabuleiro tabuleiro;

	public Peca(final Tabuleiro tabuleiro, final int id, final Cor cor) {
		this.tabuleiro = tabuleiro;
		this.id = id;
		this.cor = cor;
		posicao = 0;
		translate = new float[3];
	}

	public int getId() {
		return id;
	}

	public Cor getCor() {
		return cor;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(final int posicao) {
		if (this.posicao == 58) {
			throw new IllegalArgumentException("Peça já está fora de jogo");
		}
		if ((posicao < 0) || (posicao > 58)) {
			throw new IllegalArgumentException(
					"Posição inválida para peça. O valor deve ser de 0 até 58");
		}
		this.posicao = posicao;
	}

	public void desenharPeca(final GL gl, final GLUT glut) {
		calcularTranslacao();

		// Desenha uma esfera que representa uma peça
		gl.glPushMatrix();
		gl.glColor3f(cor.getR(), cor.getG(), cor.getB());

		gl.glTranslatef(translate[0], translate[1], translate[2]);
		glut.glutSolidSphere(RAIO, 30, 30);
		gl.glPopMatrix();
	}

	public boolean estaDentroPeca(final double x, final double y) {
		double xPeca = translate[0];
		double yPeca = translate[1];

		double dist = Math.pow(xPeca - x, 2) + Math.pow(yPeca - y, 2);
		if (dist < (RAIO * RAIO)) {
			return true;
		}
		return false;
	}

	public boolean incrementarPosicao(int valor) throws ValorDadoInvalido {
		if (posicao == 58) {
			System.err.println("Essa peça está fora do jogo e não pode mais ser movimentada");
			return false;
		}

		int novaPosicao;
		if (posicao == 0){
			if ((valor == 1) || (valor == 6)) { // Peca encontrasse no inicio e pode ser movida apenas com valor 1 ou 6
				novaPosicao = 1; // Movimenta apenas uma casa
			}else{
				throw new ValorDadoInvalido();
			}
		} else if (posicao < 53) { // Pode avançar sem problemas
			novaPosicao = posicao + valor;
		} else {
			if (valor == 1) {
				novaPosicao = posicao + valor; // pode incrementar pois não precisa verificar retorno
			} else if ((posicao + valor) > 58) { // ajusta o valor para a peça retornar
				valor -= (58 - posicao) * 2;
				valor *= -1;
			}
			novaPosicao = posicao + valor;
		}
		if (tabuleiro.posicaoDisponivel(cor,id - 1, novaPosicao)){
			posicao = novaPosicao;
			return true;
		}
		return false;
	}

	private void calcularTranslacao() {
		switch (cor.getNome()) {
		case "Verde":
			calcularTranslacaoVerde();
			break;
		case "Vermelho":
			calcularTranslacaoVermelho();
			break;
		case "Azul":
			calcularTranslacaoAzul();
			break;
		case "Amarelo":
			calcularTranslacaoAmarelo();
			break;

		default:
			break;
		}
	}

	private void calcularTranslacaoAmarelo() {
		switch (posicao) {
		case 0:
			if (id == 1) {
				translate[0] = -7.1f;
				translate[1] = -9.8f;
				translate[2] = 10.0f;
			} else if (id == 2) {
				translate[0] = -5.6f;
				translate[1] = -11.3f;
				translate[2] = 10.0f;
			} else if (id == 3) {
				translate[0] = -7.1f;
				translate[1] = -12.8f;
				translate[2] = 10.0f;
			} else {
				translate[0] = -8.6f;
				translate[1] = -11.3f;
				translate[2] = 10.0f;
			}
			break;
		case 1:
			translate[0] = -1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 2:
			translate[0] = 0.0f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 3:
			translate[0] = 1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 4:
			translate[0] = 1.6f;
			translate[1] = -13.7f;
			translate[2] = 10.0f;
			break;
		case 5:
			translate[0] = 1.6f;
			translate[1] = -12.1f;
			translate[2] = 10.0f;
			break;
		case 6:
			translate[0] = 1.6f;
			translate[1] = -10.5f;
			translate[2] = 10.0f;
			break;
		case 7:
			translate[0] = 1.6f;
			translate[1] = -8.9f;
			translate[2] = 10.0f;
			break;
		case 8:
			translate[0] = 1.6f;
			translate[1] = -7.3f;
			translate[2] = 10.0f;
			break;
		case 9:
			translate[0] = 3.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 10:
			translate[0] = 4.7f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 11:
			translate[0] = 6.3f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 12:
			translate[0] = 7.9f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 13:
			translate[0] = 9.5f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 14:
			translate[0] = 11.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 15:
			translate[0] = 11.1f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 16:
			translate[0] = 11.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 17:
			translate[0] = 9.5f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 18:
			translate[0] = 7.9f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 19:
			translate[0] = 6.3f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 20:
			translate[0] = 4.7f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 21:
			translate[0] = 3.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 22:
			translate[0] = 1.5f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 23:
			translate[0] = 1.5f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 24:
			translate[0] = 1.5f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 25:
			translate[0] = 1.5f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 26:
			translate[0] = 1.5f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 27:
			translate[0] = 1.5f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 28:
			translate[0] = -0.1f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 29:
			translate[0] = -1.6f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 30:
			translate[0] = -1.6f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 31:
			translate[0] = -1.6f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 32:
			translate[0] = -1.6f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 33:
			translate[0] = -1.6f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 34:
			translate[0] = -1.6f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 35:
			translate[0] = -3.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 36:
			translate[0] = -4.7f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 37:
			translate[0] = -6.3f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 38:
			translate[0] = -7.9f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 39:
			translate[0] = -9.5f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 40:
			translate[0] = -11.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 41:
			translate[0] = -11.1f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 42:
			translate[0] = -11.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 43:
			translate[0] = -9.5f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 44:
			translate[0] = -7.9f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 45:
			translate[0] = -6.3f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 46:
			translate[0] = -4.7f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 47:
			translate[0] = -3.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 48:
			translate[0] = -1.6f;
			translate[1] = -7.4f;
			translate[2] = 10.0f;
			break;
		case 49:
			translate[0] = -1.6f;
			translate[1] = -9.0f;
			translate[2] = 10.0f;
			break;
		case 50:
			translate[0] = -1.6f;
			translate[1] = -10.6f;
			translate[2] = 10.0f;
			break;
		case 51:
			translate[0] = -1.6f;
			translate[1] = -12.2f;
			translate[2] = 10.0f;
			break;
		case 52:
			translate[0] = -1.6f;
			translate[1] = -13.8f;
			translate[2] = 10.0f;
			break;
		case 53:
			translate[0] = 0.0f;
			translate[1] = -13.8f;
			translate[2] = 10.0f;
			break;
		case 54:
			translate[0] = 0.0f;
			translate[1] = -12.2f;
			translate[2] = 10.0f;
			break;
		case 55:
			translate[0] = 0.0f;
			translate[1] = -10.6f;
			translate[2] = 10.0f;
			break;
		case 56:
			translate[0] = 0.0f;
			translate[1] = -9.0f;
			translate[2] = 10.0f;
			break;
		case 57:
			translate[0] = 0.0f;
			translate[1] = -7.4f;
			translate[2] = 10.0f;
			break;
		case 58:
			// Esconde pois terminou a jogo para essa peça, deve adicionar um pouco no placar após
			translate[0] = 0.0f;
			translate[1] = -5.8f;
			translate[2] = 0.0f;
			break;
		default:
			break;
		}
	}

	private void calcularTranslacaoAzul() {
		switch (posicao) {
		case 0:
			if (id == 1) {
				translate[0] = 7.1f;
				translate[1] = -9.8f;
				translate[2] = 10.0f;
			} else if (id == 2) {
				translate[0] = 8.6f;
				translate[1] = -11.3f;
				translate[2] = 10.0f;
			} else if (id == 3) {
				translate[0] = 7.1f;
				translate[1] = -12.8f;
				translate[2] = 10.0f;
			} else {
				translate[0] = 5.6f;
				translate[1] = -11.3f;
				translate[2] = 10.0f;
			}
			break;
		case 1:
			translate[0] = 11.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 2:
			translate[0] = 11.1f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 3:
			translate[0] = 11.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 4:
			translate[0] = 9.5f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 5:
			translate[0] = 7.9f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 6:
			translate[0] = 6.3f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 7:
			translate[0] = 4.7f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 8:
			translate[0] = 3.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 9:
			translate[0] = 1.5f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 10:
			translate[0] = 1.5f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 11:
			translate[0] = 1.5f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 12:
			translate[0] = 1.5f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 13:
			translate[0] = 1.5f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 14:
			translate[0] = 1.5f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 15:
			translate[0] = -0.1f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 16:
			translate[0] = -1.6f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 17:
			translate[0] = -1.6f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 18:
			translate[0] = -1.6f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 19:
			translate[0] = -1.6f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 20:
			translate[0] = -1.6f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 21:
			translate[0] = -1.6f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 22:
			translate[0] = -3.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 23:
			translate[0] = -4.7f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 24:
			translate[0] = -6.3f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 25:
			translate[0] = -7.9f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 26:
			translate[0] = -9.5f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 27:
			translate[0] = -11.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 28:
			translate[0] = -11.1f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 29:
			translate[0] = -11.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 30:
			translate[0] = -9.5f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 31:
			translate[0] = -7.9f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 32:
			translate[0] = -6.3f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 33:
			translate[0] = -4.7f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 34:
			translate[0] = -3.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 35:
			translate[0] = -1.6f;
			translate[1] = -7.4f;
			translate[2] = 10.0f;
			break;
		case 36:
			translate[0] = -1.6f;
			translate[1] = -9.0f;
			translate[2] = 10.0f;
			break;
		case 37:
			translate[0] = -1.6f;
			translate[1] = -10.6f;
			translate[2] = 10.0f;
			break;
		case 38:
			translate[0] = -1.6f;
			translate[1] = -12.2f;
			translate[2] = 10.0f;
			break;
		case 39:
			translate[0] = -1.6f;
			translate[1] = -13.8f;
			translate[2] = 10.0f;
			break;
		case 40:
			translate[0] = -1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 41:
			translate[0] = 0.0f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 42:
			translate[0] = 1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 43:
			translate[0] = 1.6f;
			translate[1] = -13.7f;
			translate[2] = 10.0f;
			break;
		case 44:
			translate[0] = 1.6f;
			translate[1] = -12.1f;
			translate[2] = 10.0f;
			break;
		case 45:
			translate[0] = 1.6f;
			translate[1] = -10.5f;
			translate[2] = 10.0f;
			break;
		case 46:
			translate[0] = 1.6f;
			translate[1] = -8.9f;
			translate[2] = 10.0f;
			break;
		case 47:
			translate[0] = 1.6f;
			translate[1] = -7.3f;
			translate[2] = 10.0f;
			break;
		case 48:
			translate[0] = 3.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 49:
			translate[0] = 4.7f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 50:
			translate[0] = 6.3f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 51:
			translate[0] = 7.9f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 52:
			translate[0] = 9.5f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 53:
			translate[0] = 9.5f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 54:
			translate[0] = 7.9f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 55:
			translate[0] = 6.3f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 56:
			translate[0] = 4.7f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 57:
			translate[0] = 3.1f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 58:
			translate[0] = 1.5f;
			translate[1] = -4.2f;
			translate[2] = 0.0f;
			break;
		default:
			break;
		}
	}

	private void calcularTranslacaoVermelho() {
		switch (posicao) {
		case 0:
			if (id == 1) {
				translate[0] = 7.1f;
				translate[1] = 4.5f;
				translate[2] = 10.0f;
			} else if (id == 2) {
				translate[0] = 8.6f;
				translate[1] = 3.0f;
				translate[2] = 10.0f;
			} else if (id == 3) {
				translate[0] = 7.1f;
				translate[1] = 1.5f;
				translate[2] = 10.0f;
			} else {
				translate[0] = 5.6f;
				translate[1] = 3.0f;
				translate[2] = 10.0f;
			}
			break;
		case 1:
			translate[0] = 1.5f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 2:
			translate[0] = -0.1f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 3:
			translate[0] = -1.6f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 4:
			translate[0] = -1.6f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 5:
			translate[0] = -1.6f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 6:
			translate[0] = -1.6f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 7:
			translate[0] = -1.6f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 8:
			translate[0] = -1.6f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 9:
			translate[0] = -3.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 10:
			translate[0] = -4.7f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 11:
			translate[0] = -6.3f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 12:
			translate[0] = -7.9f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 13:
			translate[0] = -9.5f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 14:
			translate[0] = -11.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 15:
			translate[0] = -11.1f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 16:
			translate[0] = -11.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 17:
			translate[0] = -9.5f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 18:
			translate[0] = -7.9f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 19:
			translate[0] = -6.3f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 20:
			translate[0] = -4.7f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 21:
			translate[0] = -3.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 22:
			translate[0] = -1.6f;
			translate[1] = -7.4f;
			translate[2] = 10.0f;
			break;
		case 23:
			translate[0] = -1.6f;
			translate[1] = -9.0f;
			translate[2] = 10.0f;
			break;
		case 24:
			translate[0] = -1.6f;
			translate[1] = -10.6f;
			translate[2] = 10.0f;
			break;
		case 25:
			translate[0] = -1.6f;
			translate[1] = -12.2f;
			translate[2] = 10.0f;
			break;
		case 26:
			translate[0] = -1.6f;
			translate[1] = -13.8f;
			translate[2] = 10.0f;
			break;
		case 27:
			translate[0] = -1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 28:
			translate[0] = 0.0f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 29:
			translate[0] = 1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 30:
			translate[0] = 1.6f;
			translate[1] = -13.7f;
			translate[2] = 10.0f;
			break;
		case 31:
			translate[0] = 1.6f;
			translate[1] = -12.1f;
			translate[2] = 10.0f;
			break;
		case 32:
			translate[0] = 1.6f;
			translate[1] = -10.5f;
			translate[2] = 10.0f;
			break;
		case 33:
			translate[0] = 1.6f;
			translate[1] = -8.9f;
			translate[2] = 10.0f;
			break;
		case 34:
			translate[0] = 1.6f;
			translate[1] = -7.3f;
			translate[2] = 10.0f;
			break;
		case 35:
			translate[0] = 3.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 36:
			translate[0] = 4.7f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 37:
			translate[0] = 6.3f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 38:
			translate[0] = 7.9f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 39:
			translate[0] = 9.5f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 40:
			translate[0] = 11.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 41:
			translate[0] = 11.1f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 42:
			translate[0] = 11.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 43:
			translate[0] = 9.5f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 44:
			translate[0] = 7.9f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 45:
			translate[0] = 6.3f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 46:
			translate[0] = 4.7f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 47:
			translate[0] = 3.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 48:
			translate[0] = 1.5f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 49:
			translate[0] = 1.5f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 50:
			translate[0] = 1.5f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 51:
			translate[0] = 1.5f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 52:
			translate[0] = 1.5f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 53:
			translate[0] = 0.0f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 54:
			translate[0] = 0.0f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 55:
			translate[0] = 0.0f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 56:
			translate[0] = 0.0f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 57:
			translate[0] = 0.0f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 58:
			translate[0] = 0.0f;
			translate[1] = -2.7f;
			translate[2] = 0.0f;
			break;
		default:
			break;
		}
	}

	private void calcularTranslacaoVerde() {
		switch (posicao) {
		case 0:
			if (id == 1) {
				translate[0] = -7.1f;
				translate[1] = 4.5f;
				translate[2] = 10.0f;
			} else if (id == 2) {
				translate[0] = -5.6f;
				translate[1] = 3.0f;
				translate[2] = 10.0f;
			} else if (id == 3) {
				translate[0] = -7.1f;
				translate[1] = 1.5f;
				translate[2] = 10.0f;
			} else {
				translate[0] = -8.6f;
				translate[1] = 3.0f;
				translate[2] = 10.0f;
			}
			break;
		case 1:
			translate[0] = -11.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 2:
			translate[0] = -11.1f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 3:
			translate[0] = -11.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 4:
			translate[0] = -9.5f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 5:
			translate[0] = -7.9f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 6:
			translate[0] = -6.3f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 7:
			translate[0] = -4.7f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 8:
			translate[0] = -3.1f;
			translate[1] = -5.9f;
			translate[2] = 10.0f;
			break;
		case 9:
			translate[0] = -1.6f;
			translate[1] = -7.4f;
			translate[2] = 10.0f;
			break;
		case 10:
			translate[0] = -1.6f;
			translate[1] = -9.0f;
			translate[2] = 10.0f;
			break;
		case 11:
			translate[0] = -1.6f;
			translate[1] = -10.6f;
			translate[2] = 10.0f;
			break;
		case 12:
			translate[0] = -1.6f;
			translate[1] = -12.2f;
			translate[2] = 10.0f;
			break;
		case 13:
			translate[0] = -1.6f;
			translate[1] = -13.8f;
			translate[2] = 10.0f;
			break;
		case 14:
			translate[0] = -1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 15:
			translate[0] = 0.0f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 16:
			translate[0] = 1.6f;
			translate[1] = -15.3f;
			translate[2] = 10.0f;
			break;
		case 17:
			translate[0] = 1.6f;
			translate[1] = -13.7f;
			translate[2] = 10.0f;
			break;
		case 18:
			translate[0] = 1.6f;
			translate[1] = -12.1f;
			translate[2] = 10.0f;
			break;
		case 19:
			translate[0] = 1.6f;
			translate[1] = -10.5f;
			translate[2] = 10.0f;
			break;
		case 20:
			translate[0] = 1.6f;
			translate[1] = -8.9f;
			translate[2] = 10.0f;
			break;
		case 21:
			translate[0] = 1.6f;
			translate[1] = -7.3f;
			translate[2] = 10.0f;
			break;
		case 22:
			translate[0] = 3.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 23:
			translate[0] = 4.7f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 24:
			translate[0] = 6.3f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 25:
			translate[0] = 7.9f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 26:
			translate[0] = 9.5f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 27:
			translate[0] = 11.1f;
			translate[1] = -5.8f;
			translate[2] = 10.0f;
			break;
		case 28:
			translate[0] = 11.1f;
			translate[1] = -4.2f;
			translate[2] = 10.0f;
			break;
		case 29:
			translate[0] = 11.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 30:
			translate[0] = 9.5f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 31:
			translate[0] = 7.9f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 32:
			translate[0] = 6.3f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 33:
			translate[0] = 4.7f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 34:
			translate[0] = 3.1f;
			translate[1] = -2.6f;
			translate[2] = 10.0f;
			break;
		case 35:
			translate[0] = 1.5f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 36:
			translate[0] = 1.5f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 37:
			translate[0] = 1.5f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 38:
			translate[0] = 1.5f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 39:
			translate[0] = 1.5f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 40:
			translate[0] = 1.5f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 41:
			translate[0] = -0.1f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 42:
			translate[0] = -1.6f;
			translate[1] = 6.9f;
			translate[2] = 10.0f;
			break;
		case 43:
			translate[0] = -1.6f;
			translate[1] = 5.3f;
			translate[2] = 10.0f;
			break;
		case 44:
			translate[0] = -1.6f;
			translate[1] = 3.7f;
			translate[2] = 10.0f;
			break;
		case 45:
			translate[0] = -1.6f;
			translate[1] = 2.1f;
			translate[2] = 10.0f;
			break;
		case 46:
			translate[0] = -1.6f;
			translate[1] = 0.5f;
			translate[2] = 10.0f;
			break;
		case 47:
			translate[0] = -1.6f;
			translate[1] = -1.1f;
			translate[2] = 10.0f;
			break;
		case 48:
			translate[0] = -3.1f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 49:
			translate[0] = -4.7f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 50:
			translate[0] = -6.3f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 51:
			translate[0] = -7.9f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 52:
			translate[0] = -9.5f;
			translate[1] = -2.7f;
			translate[2] = 10.0f;
			break;
		case 53:
			translate[0] = -9.5f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 54:
			translate[0] = -7.9f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 55:
			translate[0] = -6.3f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 56:
			translate[0] = -4.7f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 57:
			translate[0] = -3.1f;
			translate[1] = -4.3f;
			translate[2] = 10.0f;
			break;
		case 58:
			translate[0] = -1.5f;
			translate[1] = -4.3f;
			translate[2] = 0.0f;
			break;
		default:
			break;
		}
	}
}