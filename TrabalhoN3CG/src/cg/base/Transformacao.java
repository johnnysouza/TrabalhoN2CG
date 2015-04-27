package cg.base;

/**
 * Responsável por definir a matriz de transformação global de um objeto gráfico.
 */
public class Transformacao {
	
	private double[][] matriz;
	
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
