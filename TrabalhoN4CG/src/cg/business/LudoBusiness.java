package cg.business;

import cg.mundos.Dado;
import cg.mundos.Tabuleiro;

public class LudoBusiness {

	private final boolean minhaVez = true;
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
	
	public boolean verificarFimPartida() {
		int[] posicoes = new int[16];
		for (int i = 0; i < 4; i++) {
			posicoes[i] = tabuleiro.getPecasAmarelas()[i].getPosicao();
			posicoes[i+1] = tabuleiro.getPecasAzuis()[i].getPosicao();
			posicoes[i+2] = tabuleiro.getPecasVerdes()[i].getPosicao();
			posicoes[i+3] = tabuleiro.getPecasVermelhas()[i].getPosicao();
		}
		
		boolean fim = false;
		for (int i = 0; i < posicoes.length && !fim; i++) {
			fim = posicoes[i] == 58;
		}
		
		return fim;
	}
	
}
