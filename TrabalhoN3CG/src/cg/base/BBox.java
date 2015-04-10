package cg.base;

public class BBox {
	
	private double xMinBBox;
	private double xMaxBBox;
	private double yMinBBox;
	private double yMaxBBox;
	
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
	
	public void calcularBBox() {
		//TODO
	}
	
}
