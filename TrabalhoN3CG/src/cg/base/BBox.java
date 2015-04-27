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
	
	public BBox(double xMinBBox, double xMaxBBox, double yMinBBox,
			double yMaxBBox) {
		this.xMinBBox = xMinBBox;
		this.xMaxBBox = xMaxBBox;
		this.yMinBBox = yMinBBox;
		this.yMaxBBox = yMaxBBox;
	}
	
	public double getxMinBBox() {
		return xMinBBox;
	}

	public void setxMinBBox(double xMinBBox) {
		this.xMinBBox = xMinBBox;
	}

	public double getxMaxBBox() {
		return xMaxBBox;
	}

	public void setxMaxBBox(double xMaxBBox) {
		this.xMaxBBox = xMaxBBox;
	}

	public double getyMinBBox() {
		return yMinBBox;
	}

	public void setyMinBBox(double yMinBBox) {
		this.yMinBBox = yMinBBox;
	}

	public double getyMaxBBox() {
		return yMaxBBox;
	}

	public void setyMaxBBox(double yMaxBBox) {
		this.yMaxBBox = yMaxBBox;
	}
	
	public double getCentroXBBox() {
		return centroXBBox;
	}

	public void setCentroXBBox(double centroXBBox) {
		this.centroXBBox = centroXBBox;
	}

	public double getCentroYBBox() {
		return centroYBBox;
	}

	public void setCentroYBBox(double centroYBBox) {
		this.centroYBBox = centroYBBox;
	}

	/**
	 * Recebe uma lista de pontos de um objeto gráfico e então calcula o valores de sua BBox.
	 * 
	 * @param pontos os pontos do objeto gráfico.
	 */
	public void calcularBBox(List<Ponto> pontos) {
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
	
}
