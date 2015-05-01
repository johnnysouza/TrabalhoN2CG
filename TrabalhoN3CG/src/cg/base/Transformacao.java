package cg.base;

/**
 * Respons�vel por definir a matriz de transforma��o global de um objeto gr�fico.
 */
public class Transformacao {
	
	private double[][] matriz;
	
	public Transformacao() {
		//criar matriz identidade como padr�o
		matriz = new double[][]{
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		};
	}
	
	public Transformacao(double[][] matriz) {
		this.matriz = matriz;
	}

	public double[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}

	public void transladar() {
		//TODO
	}
	
	public void escalar() {
		//TODO
	}
	
	public void rotacionar() {
		//TODO
	}

}
