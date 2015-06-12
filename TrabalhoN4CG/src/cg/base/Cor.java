package cg.base;

/**
 * Classe responsável por definir a cor de um objeto gráfico.
 */
public class Cor {
	
	public static final Cor AZUL = new Cor(0, 0, 1);
	public static final Cor VERDE = new Cor(0, 1, 0);
	public static final Cor VERMELHO = new Cor(1, 0, 0);
	public static final Cor AMARELO = new Cor(1, 1, 0);
	
	private float R;
	private float G;
	private float B;
	
	public Cor(float r, float g, float b) {
		super();
		R = r;
		G = g;
		B = b;
	}
	
	public float getR() {
		return R;
	}
	public void setR(float r) {
		R = r;
	}
	public float getG() {
		return G;
	}
	public void setG(float g) {
		G = g;
	}
	public float getB() {
		return B;
	}
	public void setB(float b) {
		B = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(B);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(G);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(R);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cor other = (Cor) obj;
		if (Double.doubleToLongBits(B) != Double.doubleToLongBits(other.B))
			return false;
		if (Double.doubleToLongBits(G) != Double.doubleToLongBits(other.G))
			return false;
		if (Double.doubleToLongBits(R) != Double.doubleToLongBits(other.R))
			return false;
		return true;
	}
	
	public float[] toFloatArray() {
		return new float[]{R, G, B};
	}

}
