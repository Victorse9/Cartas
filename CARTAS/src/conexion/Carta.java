package conexion;

public class Carta {
	private String nombre;
	private int ataque;
	private int vida;
	private String calidad;
	private String fotoURL;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

	public String getFotoURL() {
		return fotoURL;
	}

	public void setFotoURL(String fotoURL) {
		this.fotoURL = fotoURL;
	}

	@Override
	public String toString() {
		return "Carta [nombre=" + nombre + ", ataque=" + ataque + ", vida=" + vida + ", calidad=" + calidad
				+ ", fotoURL=" + fotoURL + "]";
	}

}

