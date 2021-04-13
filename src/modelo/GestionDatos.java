package modelo;

import utiles.RespuestaColocacion;

public class GestionDatos {
	private Tablero tablero;
	private Juego juego;
	private Coordenada antigua = null;

	public GestionDatos() {
		super();
		tablero = new Tablero();
		juego = new Juego();
	}

	public RespuestaColocacion realizarJugada(Coordenada coordenada) {
		RespuestaColocacion respuesta = new RespuestaColocacion();
		// A partir de la septima jugada
		if (juego.isMover()) {
			if (tablero.borrarCasilla(coordenada, this.juego.getTurnoActual())) {
				this.juego.setMover(false);
				antigua = coordenada;
				return new RespuestaColocacion(true, "", tablero.getPosicion(coordenada));
			} else {
				return new RespuestaColocacion(false, "esa casilla no es tuya", tablero.getPosicion(coordenada));
			}
			// luego hay que colocar en un casilla libre contigua
		} else if (juego.isJugadaMovimiento()) {
			return colocarFicha(coordenada, antigua);
		} else {
			return colocarFicha(coordenada);
		}

	}

	private RespuestaColocacion colocarFicha(Coordenada coordenada, Coordenada antigua) {
		if (coordenada.isContigua(antigua)) {
			return colocarFicha(coordenada);
		}
		return new RespuestaColocacion(false, "no es contigua", tablero.getPosicion(coordenada));
	}

	private RespuestaColocacion colocarFicha(Coordenada coordenada) {
		boolean colocada = this.tablero.colocarFicha(coordenada, this.juego.getTurnoActual());
		if (colocada) {
			this.juego.incrementaJugada();
			return new RespuestaColocacion(true, "", tablero.getPosicion(coordenada));
		}
		return new RespuestaColocacion(false, "no esta vacia", tablero.getPosicion(coordenada));
	}

	public String getTipoActualName() {
		return this.juego.getTurnoActualName();
	}

	public String getTipoAnteriorName() {
		return this.juego.getTurnoAnteriorName();
	}

//	public RespuestaColocacion getErrorActualName() {
//		return this.tablero.getErrorActual();
//	}
//	
	public RespuestaColocacion isTresEnRaya() {
		RespuestaColocacion respuesta= new RespuestaColocacion(false, "");
		boolean comprobado=this.tablero.comprobarColumna();
		boolean encontrado=this.tablero.comprobarLinea();
		boolean diagonal=this.tablero.comprobarDiagonal();
		if (comprobado) {
			respuesta= new RespuestaColocacion(true, "TresEnRayaVertical");
			
			
		}else if (encontrado) {
			respuesta= new RespuestaColocacion(true, "TresEnRayaHorizontal");
			
		}else if (diagonal) {
			respuesta= new RespuestaColocacion(true, "TresEnRayaDiagonal");
			
		}
		return respuesta;
		
	}
	public void crearJuego() {
		this.juego=new Juego();
		this.tablero=new Tablero();
		
		}
}
