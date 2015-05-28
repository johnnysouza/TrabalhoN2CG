package cg.base;

import java.util.List;

import javax.media.opengl.GL;

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
	 * Verifica se o ponto passado por parâmetro está dentro da BBox ou não.
	 * @param ponto ponto a ser verificado
	 * @param transformacao transformação que o ponto sofreu, será considerada na validação
	 * @return - está dentro da bbox.
	 */
	public boolean pontoInterno(final Ponto ponto, final Transformacao transformacao) {
		double x = ponto.getX();
		double y = ponto.getY();

		Ponto xMinyMax = new Ponto(xMinBBox, yMaxBBox);
		Ponto xMinyMaxTransformado = transformacao.transformarPonto(xMinyMax);
		double xMinBBoxTransformado = xMinyMaxTransformado.getX();
		double yMaxBBoxTransformado = xMinyMaxTransformado.getY();

		Ponto xMaxyMin = new Ponto(xMaxBBox, yMinBBox);
		Ponto xMaxyMinTransformado = transformacao.transformarPonto(xMaxyMin);
		double xMaxBBoxTransformado = xMaxyMinTransformado.getX();
		double yMinBBoxTransformado = xMaxyMinTransformado.getY();

		return ((x < xMaxBBoxTransformado) && (x > xMinBBoxTransformado) && (y < yMaxBBoxTransformado) && (y > yMinBBoxTransformado));
	}

	/**
	 * Desenha a BBox.
	 *
	 * @param gl a instância para desenhar no mundo.
	 */
	public void desenharBBox(final GL gl) {
		gl.glColor3d(0.0, 0.0, 0.0);
		gl.glBegin(GL.GL_LINE_LOOP);

		gl.glVertex3d(xMinBBox, yMaxBBox, 0);
		gl.glVertex3d(xMaxBBox, yMaxBBox, 0);
		gl.glVertex3d(xMaxBBox, yMinBBox, 0);
		gl.glVertex3d(xMinBBox, yMinBBox, 0);

		gl.glEnd();
	}
}
