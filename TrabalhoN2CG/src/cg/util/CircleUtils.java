package cg.util;

public final class CircleUtils {

	/**
	 * Cria valor X para um ponto de um circunferência.
	 * 
	 * @param angulo
	 * @param raio
	 * @return o valor X para um ponto de um circunferência.
	 */
	public static double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	/**
	 * Cria valor Y para um ponto de um circunferência.
	 * 
	 * @param angulo
	 * @param raio
	 * @returno valor Y para um ponto de um circunferência.
	 */
	public static double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}

}
