package cg.business;

import java.util.Random;

import cg.base.Cor;
import cg.base.Peca;
import cg.mundos.Dado;
import cg.mundos.Tabuleiro;

public class LudoBusiness {

	private boolean minhaVez = true;
	private boolean aguardandoSelecao;
	private int valorDado;
	private Tabuleiro tabuleiro;
	private Dado dado;

	public boolean podeRolarDado() {
		return minhaVez && !aguardandoSelecao;
	}

	public int rolarDado() {
		valorDado = valorAleatorio(7);
		return valorDado;
	}

	public int valorAleatorio(final int max) {
		Random random = new Random();
		int retorno = random.nextInt(max);
		while(retorno == 0){
			retorno = random.nextInt(max);
		}
		return retorno;
	}

	public void setTabuleiro(final Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public void setDado(final Dado dado) {
		this.dado = dado;
	}

	public int getValorDado() {
		return valorDado;
	}

	public void setValorDado(final int valorDado) {
		this.valorDado = valorDado;
	}

	public Cor verificarFimPartida() {
		if (corVenceu(tabuleiro.getPecasAmarelas())) {
			return Cor.AMARELO;
		} else if (corVenceu(tabuleiro.getPecasAzuis())) {
			return Cor.AZUL;
		} else if (corVenceu(tabuleiro.getPecasVerdes())) {
			return Cor.VERDE;
		} else if (corVenceu(tabuleiro.getPecasVermelhas())) {
			return Cor.VERMELHO;
		}

		return null;
	}

	private boolean corVenceu(final Peca[] pecas) {
		boolean venceu = true;
		for (Peca posicao : pecas) {
			if (posicao.getPosicao() != 58) {
				venceu = false;
			}
		}
		return venceu;
	}

	public boolean isMinhaVez() {
		return minhaVez;
	}

	public void setMinhaVez(final boolean minhaVez) {
		this.minhaVez = minhaVez;
	}

	public void setAguardandoSelecao(final boolean aguardandoSelecao) {
		this.aguardandoSelecao = aguardandoSelecao;
	}

	public boolean isAguardandoSelecao() {
		return aguardandoSelecao;
	}

}
