package cg.business;

import cg.base.Cor;
import cg.base.Peca;
import cg.mundos.Dado;
import cg.mundos.Tabuleiro;

public class LudoBusiness {

	private boolean minhaVez = true;
	private boolean escolherPeca;
	private int valorDado;
	private Tabuleiro tabuleiro;
	private Dado dado;

	public boolean podeRolarDado() {
		return minhaVez && !escolherPeca;
	}

	public void dadoRolado(final int valor) {
		valorDado = valor;
		tabuleiro.aguardarSelecao();
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

	public void setValorDado(int valorDado) {
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
	
	private boolean corVenceu(Peca[] pecas) {
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

	public void setMinhaVez(boolean minhaVez) {
		this.minhaVez = minhaVez;
	}
	
}
