package cg.base;

public class Ponto {

	private double	x;
	private double	y;
	private double	z;
	private double	w;

	public Ponto(final double x, final double y, final double z, final double w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Ponto(final double x, final double y) {
		this.x = x;
		this.y = y;
		z = 0;
		w = 0;
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(final double z) {
		this.z = z;
	}

	public double getW() {
		return w;
	}

	public void setW(final double w) {
		this.w = w;
	}

}
