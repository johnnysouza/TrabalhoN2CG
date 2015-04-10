package cg.base;

import java.util.List;

public class ObjetoGrafico {
	
	private Cor cor;
	private int primitivaGrafica;
	private List<Ponto> pontos;
	private Transformacao transformacao;
	private BBox bBox;
	private List<ObjetoGrafico> objetosFilhos;
	
	
	public Cor getCor() {
		return cor;
	}
	public void setCor(Cor cor) {
		this.cor = cor;
	}
	public int getPrimitivaGrafica() {
		return primitivaGrafica;
	}
	public void setPrimitivaGrafica(int primitivaGrafica) {
		this.primitivaGrafica = primitivaGrafica;
	}
	public List<Ponto> getPontos() {
		return pontos;
	}
	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}
	public Transformacao getTransformacao() {
		return transformacao;
	}
	public void setTransformacao(Transformacao transformacao) {
		this.transformacao = transformacao;
	}
	public BBox getbBox() {
		return bBox;
	}
	public void setbBox(BBox bBox) {
		this.bBox = bBox;
	}
	public List<ObjetoGrafico> getObjetosFilhos() {
		return objetosFilhos;
	}
	public void setObjetosFilhos(List<ObjetoGrafico> objetosFilhos) {
		this.objetosFilhos = objetosFilhos;
	}
	
	public void addObjetoGrafico(ObjetoGrafico objeto) {
		if (objeto != null) {
			objetosFilhos.add(objeto);
		}
	}
	
	public ObjetoGrafico removerObjetoGrafico(int i) {
		return objetosFilhos.remove(i);
	}
	
	public void desenhar() {
		//TODO
	}

}
