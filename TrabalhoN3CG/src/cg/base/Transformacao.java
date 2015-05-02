package cg.base;

/**
 * Respons�vel por definir a matriz de transforma��o global de um objeto gr�fico.
 */
public class Transformacao {
	
	private static final double DEG_TO_RAD = 0.017453292519943295769236907684886;
	private double[] matriz;
	
	public Transformacao() {
		//criar matriz identidade como padr�o
		matriz = new double[]{
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
	}
	
	/**
	 * Atribui a matriz a de transforma��o identidade.
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
	 * Atribui valores para realiza��o de transla��o.
	 * 
	 * @param x o valor de x para a transla��o.
	 * @param y o valor de y para a transla��o.
	 * @param z o valor de z para a transla��o.
	 */
	public void transladar(double x, double y, double z) {
		atribuirIdentidade();
	    matriz[12] = x;
	    matriz[13] = y;
	    matriz[14] = z;
	}
	
	/**
	 * Atribui valores para realiza��o de escala.
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
	 * Atribui valores para rota��o fixa no eixo z.
	 * 
	 * @param angulo o �ngulo da rota��o a ser aplicada.
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
	 * Realiza a multiplica��o de matrizes de transforma��o.
	 * 
	 * @param t a matriz de transforma��o a ser multiplicada
	 * @return o resultado da multiplica��o das matrizes de transforma��o
	 */
	public Transformacao transformarMatrix(Transformacao t) {
		Transformacao matrizTrans = new Transformacao();
	    for (int i=0; i < 16; ++i)
        matrizTrans.matriz[i] =
              matriz[i%4]    *t.matriz[i/4*4]  +matriz[(i%4)+4] *t.matriz[i/4*4+1]
            + matriz[(i%4)+8]*t.matriz[i/4*4+2]+matriz[(i%4)+12]*t.matriz[i/4*4+3];
		return matrizTrans;
	}

}
