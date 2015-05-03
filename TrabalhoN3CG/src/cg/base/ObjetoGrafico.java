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
	private boolean selecionado;

	public ObjetoGrafico(final Cor cor) {
		this.cor = new Cor(cor.getR(), cor.getG(), cor.getB());
		primitivaGrafica = GL.GL_LINE_STRIP; // por defaut na criação do objeto será usado GL_LINE_STRIP, alterar apenas na finalização da criação do objeto
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
	
	public boolean isSelecionado() {
		return selecionado;
	}
	
	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public void setObjetosFilhos(final List<ObjetoGrafico> objetosFilhos) {
		this.objetosFilhos = objetosFilhos;
	}

	/**
	 * Adiciona um objeto gráfico filho.
	 *
	 * @param objeto
	 *            o obejto gráfico a ser adicionados aos filhos.
	 */
	public void addObjetoGraficoFilho(final ObjetoGrafico objeto) {
		if (objeto != null) {
			objetosFilhos.add(objeto);
		}
	}

	/**
	 * Remove um objeto gráfico filho.
	 *
	 * @param i
	 *            a posição do objeto gráfico filho a ser removido.
	 * @return o objeto gráfico removido.
	 */
	public ObjetoGrafico removerObjetoGraficoFilho(final int i) {
		return objetosFilhos.remove(i);
	}

	/**
	 * Desenha o objeto gráfico, assim como seus filhos, no espaço gráfico do mundo.
	 *
	 * @param gl
	 *            a instância para desenhar no mundo.
	 */
	public void desenhar(final GL gl) {
		gl.glPushMatrix();
		gl.glMultMatrixd(transformacao.getMatriz(), 0);
		gl.glColor3d(cor.getR(), cor.getG(), cor.getB());
		gl.glBegin(primitivaGrafica);
		for (Ponto ponto : pontos) {
			gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
		}
		gl.glEnd();

		for (ObjetoGrafico objetoGrafico : objetosFilhos) {
			objetoGrafico.desenhar(gl);
		}
		gl.glPopMatrix();
		
		if (selecionado) {
			gl.glPointSize(Mundo.MARGEMSELECAOPONTO);
			
			gl.glBegin(GL.GL_POINTS);
			for (Ponto ponto : pontos) {
				gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
			}
			gl.glEnd();
			
			bBox.desenharBBox(gl);
		}
	}

	/**
	 * Remove um ponto da lista de ponto que representam o objeto gráfico.
	 *
	 * @param ponto
	 *            o ponto a ser removido
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
		result = (prime * result) + ((cor == null) ? 0 : cor.hashCode());
		result = (prime * result) + ((pontos == null) ? 0 : pontos.hashCode());
		result = (prime * result) + primitivaGrafica;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ObjetoGrafico other = (ObjetoGrafico) obj;
		if (cor == null) {
			if (other.cor != null) {
				return false;
			}
		} else
			if (!cor.equals(other.cor)) {
				return false;
			}
		if (pontos == null) {
			if (other.pontos != null) {
				return false;
			}
		} else
			if (!pontos.equals(other.pontos)) {
				return false;
			}
		if (primitivaGrafica != other.primitivaGrafica) {
			return false;
		}
		return true;
	}

	/**
	 * Verifica se selecionou o objeto Grafico ou algum filho
	 *
	 * @param x
	 *            - Ponto X clicado
	 * @param y
	 *            - Ponto Y clicado
	 * @return Objeto Grafico selecionado, podendo ser um filho ou this
	 */
	public ObjetoGrafico verificarSelecao(final int x, final int y) {
		ObjetoGrafico obj = null;
		int i = 0;
		while ((obj == null) && (i < objetosFilhos.size())) {
			obj = objetosFilhos.get(i).verificarSelecao(x, y);
			i++;
		}
		if (obj == null) {
			if (bBox.pontoInterno(x, y) && (scanLine(x, y))) {
				return this;
			}
		}
		return obj;
	}

	private boolean scanLine(final int x, final int y) {
		int paridade = 0;
		double value;
		int j;
		for(int i = 0; i < (pontos.size()); i++){
			if (i == (pontos.size() - 1)){
				j = 0;
			}else{
				j = i + 1;
			}
			value = (y-pontos.get(i).getY()) / (pontos.get(j).getY() - pontos.get(i).getY());
			if((value > 0) && (value < 1)){
				value = pontos.get(i).getX() + ((pontos.get(j).getX()-pontos.get(i).getX())*value);
				if(value > x){
					paridade++;
				}
			}
		}
		return !((paridade % 2) == 0);
	}

	/**
	 * @param remover
	 *            - objeto a ser removido
	 * @return - se removeu ou não o objeto
	 */
	public boolean removerObjetoGraficoFilho(final ObjetoGrafico remover) {
		//TODO nunca deleta o filho
		boolean result = false;
		int i = 0;
		while (!result && (i < objetosFilhos.size())) {
			result = objetosFilhos.get(i).removerObjetoGraficoFilho(remover);
			i++;
		}
		if (result) {
			objetosFilhos.remove(i - 1);
		}
		return result;
	}

	/**
	 * Realiza a translação de um objeto gráfico de acordo os valores para mover em x, y e z.
	 *
	 * @param x o valor para mover em x.
	 * @param y o valor para mover em y.
	 * @param z o valor para mover em z.
	 */
	public void transladarObjeto(final double x, final double y, final double z) {
		Transformacao matrizTranslate = new Transformacao();
		matrizTranslate.transladar(x, y, z);
		transformacao = matrizTranslate.transformarMatrix(transformacao);
	}

	/**
	 * Realiza a alteração na escala do objeto gráfico.
	 *
	 * @param escala a escala a ser aplicada no objeto gráfico.
	 */
	public void escalarObjeto(final double escala) {
		Ponto pontoCentral = new Ponto(bBox.getCentroXBBox(), bBox.getCentroYBBox());
		pontoCentral.inverterSinal();
		Transformacao matrizGlobal = new Transformacao();
		matrizGlobal.atribuirIdentidade();

		Transformacao matrizTmpTranslacao = new Transformacao();
		matrizTmpTranslacao.transladar(pontoCentral.getX(),pontoCentral.getY(),pontoCentral.getZ());
		matrizGlobal = matrizTmpTranslacao.transformarMatrix(matrizGlobal);

		Transformacao matrizTmpEscala = new Transformacao();
		matrizTmpEscala.escalar(escala, escala, 1.0);
		matrizGlobal = matrizTmpEscala.transformarMatrix(matrizGlobal);

		pontoCentral.inverterSinal();
		Transformacao matrizTmpTranslacaoInversa = new Transformacao();
		matrizTmpTranslacaoInversa.transladar(pontoCentral.getX(),pontoCentral.getY(),pontoCentral.getZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformarMatrix(matrizGlobal);

		transformacao = transformacao.transformarMatrix(matrizGlobal);
	}

	/**
	 * Realiza a rotação de um obejto gráfico de acordo com um ângulo.
	 *
	 * @param angulo o ângulo para a rotação do objeto gráfico.
	 */
	public void rotacionarObjeto(final double angulo) {
		Ponto pontoCentral = new Ponto(bBox.getCentroXBBox(), bBox.getCentroYBBox());
		pontoCentral.inverterSinal();
		Transformacao matrizGlobal = new Transformacao();
		matrizGlobal.atribuirIdentidade();

		Transformacao matrizTmpTranslacao = new Transformacao();
		matrizTmpTranslacao.transladar(pontoCentral.getX(),pontoCentral.getY(),pontoCentral.getZ());
		matrizGlobal = matrizTmpTranslacao.transformarMatrix(matrizGlobal);

		Transformacao matrizTmpRotacao = new Transformacao();
		matrizTmpRotacao.rotacionarZ(angulo);
		matrizGlobal = matrizTmpRotacao.transformarMatrix(matrizGlobal);

		pontoCentral.inverterSinal();
		Transformacao matrizTmpTranslacaoInversa = new Transformacao();
		matrizTmpTranslacaoInversa.transladar(pontoCentral.getX(),pontoCentral.getY(),pontoCentral.getZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformarMatrix(matrizGlobal);

		transformacao = transformacao.transformarMatrix(matrizGlobal);
	}
	
	/**
	 * Limpa as transformações do objeto selecionado e dos seus filhos
	 */
	public void limparTransformações() {
		transformacao.atribuirIdentidade();
		for (ObjetoGrafico objetoGrafico : objetosFilhos) {
			objetoGrafico.limparTransformações();
		}
	}
}
