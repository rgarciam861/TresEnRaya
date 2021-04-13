package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modelo.GestionDatos;
import modelo.Tipo;
import utiles.RespuestaColocacion;
import vista.UI;

public class ParaUI extends UI {
	private ActionListener acctionListener;
	private Controlador control;
	private boolean jugar;

	public ParaUI() {
		super();
		this.preparameEsaBotonera();
		control = new Controlador();
		this.jugar = true;
		this.btnReiniciar.setVisible(false);
	}

	private void preparameEsaBotonera() {
		// Si dos acciones están vinculadas secuencialmente
		// deben estar en su propio metodo
		this.crearActionListenerParaBotones();
		this.addEventosALaBotonera();
	}

	private void reiniciarJuego() {
		btnReiniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] botones = botonera.getComponents();
				for (Component component : botones) {
					((JButton) component).setText(Tipo.blanco.getNombre());
				}
				control.reinciarJuego();
				jugar=true;
				lblMensaje.setText("");
				btnReiniciar.setVisible(false);

			}
		});
	}

	private void crearActionListenerParaBotones() {
		this.acctionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// objeto componente que dispara el evento
				JButton boton = (JButton) e.getSource();
				String[] split = boton.getName().split(":");
//				System.out.println("posicion x" + split[0]);
//				System.out.println("posicion y" + split[1]);
				// llegare aqui cuando alguien hay pulsado un boton
//				de la botonera

				RespuestaColocacion respuestaColocacion = control.realizarJugada(boton.getName());
				RespuestaColocacion TresEnRaya = control.isTresEnRaya();
				if (jugar) {
					
					if (respuestaColocacion.isRespuesta()) {
						// si estoy aqui es porque ha habido un cambio
						// por lo tanto debo mostrarlo
						boton.setText(respuestaColocacion.getTipo().getNombre());
					}
					lblMensaje.setText(respuestaColocacion.getMensaje());
					if (TresEnRaya.isRespuesta()) {
						lblMensaje.setText(TresEnRaya.getMensaje());
						btnReiniciar.setVisible(true);
						jugar=false;
						reiniciarJuego();
						
					}
				}
			}

		};
	}

	private void addEventosALaBotonera() {
		Component[] bartolo = this.botonera.getComponents();
		// os presento, otra vez, a la estructura foreach
		for (Component component : bartolo) {
			((JButton) component).addActionListener(this.acctionListener);
		}
	}

}
