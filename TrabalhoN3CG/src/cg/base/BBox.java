package cg.base;

import java.util.List;

/**
 * Representa a BBox de um objeto gráfico.
 */
public class BBox {

	private double xMinBBox;
	private double xMaxBBox;
	private double yMinBBox;
	private double yMaxBBox;
	private double centroXBBox;
	private double centroYBBox;

	public BBox() {
	}

	public BBox(final double xMinBBox, final double xMaxBBox, final double yMinBBox,
			final double yMaxBBox) {
		this.xMinBBox = xMinBBox;
		this.xMaxBBox = xMaxBBox;
		this.yMinBBox = yMinBBox;
		this.yMaxBBox = yMaxBBox;
	}

	public double getxMinBBox() {
		return xMinBBox;
	}

	public void setxMinBBox(final double xMinBBox) {
		this.xMinBBox = xMinBBox;
	}

	public double getxMaxBBox() {
		return xMaxBBox;
	}

	public void setxMaxBBox(final double xMaxBBox) {
		this.xMaxBBox = xMaxBBox;
	}

	public double getyMinBBox() {
		return yMinBBox;
	}

	public void setyMinBBox(final double yMinBBox) {
		this.yMinBBox = yMinBBox;
	}

	public double getyMaxBBox() {
		return yMaxBBox;
	}

	public void setyMaxBBox(final double yMaxBBox) {
		this.yMaxBBox = yMaxBBox;
	}

	public double getCentroXBBox() {
		return centroXBBox;
	}

	public void setCentroXBBox(final double centroXBBox) {
		this.centroXBBox = centroXBBox;
	}

	public double getCentroYBBox() {
		return centroYBBox;
	}

	public void setCentroYBBox(final double centroYBBox) {
		this.centroYBBox = centroYBBox;
	}

	/**
	 * Recebe uma lista de pontos de um objeto gráfico e então calcula o valores de sua BBox.
	 *
	 * @param pontos os pontos do objeto gráfico.
	 */
	public void calcularBBox(final List<Ponto> pontos) {
		xMaxBBox = pontos.get(0).getX();
		xMinBBox = pontos.get(0).getX();
		yMaxBBox = pontos.get(0).getY();
		yMinBBox = pontos.get(0).getY();

		for (int i = 1, size = pontos.size(); i < size; i++) {
			Ponto ponto = pontos.get(i);
			double x = ponto.getX();
			double y = ponto.getY();

			if (x > xMaxBBox) {
				xMaxBBox = x;
			} else if (x < xMinBBox) {
				xMinBBox = x;
			}

			if (y > yMaxBBox) {
				yMaxBBox = y;
			} else if (y < yMinBBox) {
				yMinBBox = y;
			}
		}

		// calcula centro da BBox
		centroXBBox = (xMaxBBox + xMinBBox) / 2;
		centroYBBox = (yMaxBBox + yMinBBox) / 2;
	}

	/**
	 * Verifica se os pontos passados por parâmetro estão dentro da BBOx ou não
	 *
	 * @param x - X do Ponto
	 * @param y - Y do Ponto
	 * @return -está dentro da bbox
	 */
	public boolean pontoInterno(final int x, final int y) {
		return ((x < xMaxBBox) && (x > xMinBBox) && (y < yMaxBBox) && (y > yMinBBox));
	}
}
