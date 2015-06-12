package cg.business;

import cg.mundos.Dado;
import cg.mundos.Tabuleiro;

public class LudoBusiness {

	private boolean minhaVez;
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

}
