package cg.base;

/**
 * Classe responsável pela manipulação da câmera do espaço gráfico.
 */
public class Camera {
	
	private double xMinOrtho2D;
	private double xMaxOrtho2D;
	private double yMinOrtho2D;
	private double yMaxOrtho2D;
	
	public Camera(double xMinOrtho2D, double xMaxOrtho2D, double yMinOrtho2D,
			double yMaxOrtho2D) {
		super();
		this.xMinOrtho2D = xMinOrtho2D;
		this.xMaxOrtho2D = xMaxOrtho2D;
		this.yMinOrtho2D = yMinOrtho2D;
		this.yMaxOrtho2D = yMaxOrtho2D;
	}
	
	public double getxMinOrtho2D() {
		return xMinOrtho2D;
	}

	public void setxMinOrtho2D(double xMinOrtho2D) {
		this.xMinOrtho2D = xMinOrtho2D;
	}

	public double getxMaxOrtho2D() {
		return xMaxOrtho2D;
	}

	public void setxMaxOrtho2D(double xMaxOrtho2D) {
		this.xMaxOrtho2D = xMaxOrtho2D;
	}

	public double getyMinOrtho2D() {
		return yMinOrtho2D;
	}

	public void setyMinOrtho2D(double yMinOrtho2D) {
		this.yMinOrtho2D = yMinOrtho2D;
	}

	public double getyMaxOrtho2D() {
		return yMaxOrtho2D;
	}

	public void setyMaxOrtho2D(double yMaxOrtho2D) {
		this.yMaxOrtho2D = yMaxOrtho2D;
	}

	public void pan() {
		//TODO
	}
	
	public void zomm() {
		//TODO
	}

}
