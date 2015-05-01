package cg.base;

/**
 * Classe responsável pela manipulação da câmera do espaço gráfico.
 */
public class Camera {
	
	private double xMinOrtho2D;
	private double xMaxOrtho2D;
	private double yMinOrtho2D;
	private double yMaxOrtho2D;
	private int valorZoom;
	
	public Camera(double xMinOrtho2D, double xMaxOrtho2D, double yMinOrtho2D,
			double yMaxOrtho2D) {
		super();
		valorZoom = 100;
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

	/**
	 * Realiza a movimentação da câmera.
	 * 
	 * @param movimento o movimento a ser realizado pela câmera.
	 */
	public void pan(MovimentoCamera movimento) {
		switch(movimento) {
		case MOVER_PARA_CIMA:
			yMaxOrtho2D--;
			yMinOrtho2D--;
			break;
		case MOVER_PARA_BAIXO:
			yMaxOrtho2D++;
			yMinOrtho2D++;
			break;
		case MOVER_PARA_ESQUERDA:
			xMaxOrtho2D++;
			xMinOrtho2D++;
			break;
		case MOVER_PARA_DIREITA:
			xMaxOrtho2D--;
			xMinOrtho2D--;
			break;
		default:
			//Movimentação não suportada	
		}
	}
	
	/**
	 * Altera o zoom da câmera.
	 * 
	 * @param zoom o zoom a ser aplicado na câmera.
	 */
	public void zomm(ZoomCamera zoom) {
		switch(zoom) {
		case MAIS_ZOOM:
			if (valorZoom < 400) {
				valorZoom++;
				xMinOrtho2D++;
				yMinOrtho2D++;
				xMaxOrtho2D--;
				yMaxOrtho2D--;
			}
			break;
		case MENOS_ZOOM:
			if (valorZoom > -200) {
				valorZoom--;
				xMinOrtho2D--;
				yMinOrtho2D--;
				xMaxOrtho2D++;
				yMaxOrtho2D++;
			}
			break;
		default:
			//Operação não suportada
		}
		System.out.println("zoom:" + valorZoom);
	}
	
	public enum MovimentoCamera {
		MOVER_PARA_CIMA, MOVER_PARA_BAIXO, MOVER_PARA_ESQUERDA, MOVER_PARA_DIREITA; 
	}
	
	public enum ZoomCamera {
		MAIS_ZOOM, MENOS_ZOOM;
	}

}
