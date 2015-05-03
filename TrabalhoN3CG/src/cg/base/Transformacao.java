package cg.base;

import java.util.Arrays;

/**
 * Responsável por definir a matriz de transformação global de um objeto gráfico.
 */
public class Transformacao {
	
	private static final double DEG_TO_RAD = 0.017453292519943295769236907684886;
	private double[] matriz;
	
	public Transformacao() {
		//criar matriz identidade como padrão
		matriz = new double[]{
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
	}
	
	/**
	 * Atribui a matriz a de transformação identidade.
	 */
	public void atribuirIdentidade() {
		for (int i=0; i<16; ++i) {
				matriz[i] = 0.0;
		}
		matriz[0] = matriz[5] = matriz[10] = matriz[15] = 1.0;
	}
	
	public Transformacao(double[] matriz) {
		this.matriz = matriz;
	}

	public double[] getMatriz() {
		return matriz;
	}

	public void setMatriz(double[] matriz) {
		this.matriz = matriz;
	}

	/**
	 * Atribui valores para realização de translação.
	 * 
	 * @param x o valor de x para a translação.
	 * @param y o valor de y para a translação.
	 * @param z o valor de z para a translação.
	 */
	public void transladar(double x, double y, double z) {
		atribuirIdentidade();
	    matriz[12] = x;
	    matriz[13] = y;
	    matriz[14] = z;
	}
	
	/**
	 * Atribui valores para realização de escala.
	 * 
	 * @param x o valor de x para a escala.
	 * @param y o valor de y para a escala.
	 * @param z o valor de z para a escala.
	 */
	public void escalar(double x, double y, double z) {
		atribuirIdentidade();
	    matriz[0] =  x;
	    matriz[5] =  y;
	    matriz[10] = z;
	}
	
	/**
	 * Atribui valores para rotação fixa no eixo z.
	 * 
	 * @param angulo o ângulo da rotação a ser aplicada.
	 */
	public void rotacionarZ(double angulo) {
		atribuirIdentidade();
		double grausRad = DEG_TO_RAD * angulo;
	    matriz[0] =  Math.cos(grausRad);
	    matriz[4] = -Math.sin(grausRad);
	    matriz[1] =  Math.sin(grausRad);
	    matriz[5] =  Math.cos(grausRad);
	}
	
	/**
	 * Realiza a multiplicação de matrizes de transformação.
	 * 
	 * @param t a matriz de transformação a ser multiplicada
	 * @return o resultado da multiplicação das matrizes de transformação
	 */
	public Transformacao transformarMatrix(Transformacao t) {
		Transformacao matrizTrans = new Transformacao();
	    for (int i=0; i < 16; ++i)
        matrizTrans.matriz[i] =
              matriz[i%4]    *t.matriz[i/4*4]  +matriz[(i%4)+4] *t.matriz[i/4*4+1]
            + matriz[(i%4)+8]*t.matriz[i/4*4+2]+matriz[(i%4)+12]*t.matriz[i/4*4+3];
		return matrizTrans;
	}

	/**
	 * Aplica as transformações da matriz no ponto informado.
	 * 
	 * @param ponto o ponto para aplicar as transformações.
	 * @return o ponto com as transformações aplicadas.
	 */
	public Ponto transformarPonto(Ponto ponto) {
		Ponto pointResult = new Ponto(
				matriz[0]*ponto.getX()  + matriz[4]*ponto.getY() + matriz[8]*ponto.getZ()  + matriz[12]*ponto.getW(),
				matriz[1]*ponto.getX()  + matriz[5]*ponto.getY() + matriz[9]*ponto.getZ()  + matriz[13]*ponto.getW(),
				matriz[2]*ponto.getX()  + matriz[6]*ponto.getY() + matriz[10]*ponto.getZ() + matriz[14]*ponto.getW(),
                matriz[3]*ponto.getX()  + matriz[7]*ponto.getY() + matriz[11]*ponto.getZ() + matriz[15]*ponto.getW());
		return pointResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(matriz);
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
		Transformacao other = (Transformacao) obj;
		if (!Arrays.equals(matriz, other.matriz))
			return false;
		return true;
	}

}
