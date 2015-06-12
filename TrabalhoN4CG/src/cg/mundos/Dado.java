package cg.mundos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import cg.business.LudoBusiness;

public class Dado implements GLEventListener, MouseListener{

	private final LudoBusiness ludo;

	public Dado(final LudoBusiness ludo) {
		this.ludo = ludo;
		ludo.setDado(this);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (ludo.podeRolarDado()){
			int valor = rolarDado();
			ludo.dadoRolado(valor);
		}
	}

	private int rolarDado() {
		// TODO Animação do dado rolando, retorna o valor do dado
		return 0;
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(final GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayChanged(final GLAutoDrawable arg0, final boolean arg1, final boolean arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(final GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(final GLAutoDrawable arg0, final int arg1, final int arg2, final int arg3, final int arg4) {
		// TODO Auto-generated method stub
	}

}
