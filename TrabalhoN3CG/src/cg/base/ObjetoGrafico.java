package cg.base;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

/**
 * Representa um objeto gráfico e seus filhos.
 */
public class ObjetoGrafico {

	private Cor cor;
	private int primitivaGrafica;
	private List<Ponto> pontos;
	private Transformacao transformacao;
	private BBox bBox;
	private List<ObjetoGrafico> objetosFilhos;
	
	public ObjetoGrafico(Cor cor) {
		this.cor = new Cor(cor.getR(), cor.getG(), cor.getB());
		primitivaGrafica = GL.GL_LINE_STRIP; //por defaut na criação do objeto será usado GL_LINE_STRIP, alterar apenas na finalização da criação do objeto
		pontos = new ArrayList<Ponto>();
		transformacao = new Transformacao();
		bBox = new BBox();
		objetosFilhos = new ArrayList<ObjetoGrafico>();
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(final Cor cor) {
		this.cor = cor;
	}

	public int getPrimitivaGrafica() {
		return primitivaGrafica;
	}

	public void setPrimitivaGrafica(final int primitivaGrafica) {
		this.primitivaGrafica = primitivaGrafica;
	}

	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(final List<Ponto> pontos) {
		this.pontos = pontos;
	}

	public void addPonto(final Ponto ponto) {
		pontos.add(ponto);
	}

	public Transformacao getTransformacao() {
		return transformacao;
	}

	public void setTransformacao(final Transformacao transformacao) {
		this.transformacao = transformacao;
	}

	public BBox getbBox() {
		return bBox;
	}

	public void setbBox(final BBox bBox) {
		this.bBox = bBox;
	}

	public List<ObjetoGrafico> getObjetosFilhos() {
		return objetosFilhos;
	}

	public void setObjetosFilhos(final List<ObjetoGrafico> objetosFilhos) {
		this.objetosFilhos = objetosFilhos;
	}

	/**
	 * Adiciona um objeto gráfico filho.
	 * 
	 * @param objeto o obejto gráfico a ser adicionados aos filhos.
	 */
	public void addObjetoGraficoFilho(final ObjetoGrafico objeto) {
		if (objeto != null) {
			objetosFilhos.add(objeto);
		}
	}

	/**
	 * Remove um objeto gráfico filho.
	 * 
	 * @param i a posição do objeto gráfico filho a ser removido.
	 * @return o objeto gráfico removido.
	 */
	public ObjetoGrafico removerObjetoGraficoFilho(final int i) {
		return objetosFilhos.remove(i);
	}

	/**
	 * Desenha o objeto gráfico, assim como seus filhos, no espaço gráfico do mundo.
	 * 
	 * @param gl a instância para desenhar no mundo.
	 */
	public void desenhar(final GL gl) {
		//TODO preparar o método de desenho para as transformação com o grafo de cena
		gl.glColor3d(cor.getR(), cor.getG(), cor.getB());
		gl.glBegin(primitivaGrafica);
		for (Ponto ponto : pontos) {
			gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
		}
		gl.glEnd();

		for (ObjetoGrafico objetoGrafico : objetosFilhos) {
			objetoGrafico.desenhar(gl);
		}
	}

	/**
	 * Remove um ponto da lista de ponto que representam o objeto gráfico.
	 * 
	 * @param ponto o ponto a ser removido
	 */
	public void removePonto(final Ponto ponto) {
		pontos.remove(ponto);
	}

	/**
	 * Chama o calculo da BBox do objeto gráfico.
	 */
	public void calcularBBox() {
		if (bBox == null) {
			bBox = new BBox();
		}
		bBox.calcularBBox(pontos);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((pontos == null) ? 0 : pontos.hashCode());
		result = prime * result + primitivaGrafica;
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
		ObjetoGrafico other = (ObjetoGrafico) obj;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (pontos == null) {
			if (other.pontos != null)
				return false;
		} else if (!pontos.equals(other.pontos))
			return false;
		if (primitivaGrafica != other.primitivaGrafica)
			return false;
		return true;
	}

}
