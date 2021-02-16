package paquete;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import conexion.Carta;
import conexion.Consulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorPartida {

	@FXML
	private RadioButton r1, r2, r3, r4, r5, r6, r7, r8;
	@FXML
	private Button btnAtacar, btnPasarTurno;
	@FXML
	private ToggleGroup grupo1, grupo2;
	@FXML
	private ImageView carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8, marco1, marco2, marco3, marco4,
			marco5, marco6, marco7, marco8, altavozOn, altavozOff;
	@FXML
	private Label carta1Ataque, carta1Vida, carta2Ataque, carta2Vida, carta3Ataque, carta3Vida, carta4Ataque,
			carta4Vida, carta5Ataque, carta5Vida, carta6Ataque, carta6Vida, carta7Ataque, carta7Vida, carta8Ataque,
			carta8Vida, lblSituacion, f1, f2, f3, f4, f5, f6, f7, f8, lblEquipoRival;

	private Carta oCarta1, oCarta2, oCarta3, oCarta4, oCarta5, oCarta6, oCarta7, oCarta8;
	private int vida1, vida2, vida3, vida4, vida5, vida6, vida7, vida8, ataque1, ataque2, ataque3, ataque4, ataque5,
			ataque6, ataque7, ataque8;
	@FXML
	private TextArea txtAreaHistorial;
	private boolean miTurno = true;
	private int rival = 1;
	@FXML
	private Stage stageVictoria;
	@FXML
	private AudioClip audioVictoria = new AudioClip("file:sonido/sonido-de-victoria.mp3");
	@FXML
	private AudioClip audioDerrota = new AudioClip("file:sonido/sonido-de-derrota.mp3");
	@FXML
	private AudioClip cancion = new AudioClip("file:sonido/cancion.mp3");
	@FXML
	private AudioClip espada = new AudioClip("file:sonido/espada.mp3");
	@FXML
	private AudioClip fallo = new AudioClip("file:sonido/fallo.mp3");
	@FXML
	private AnchorPane container;
	private String eleccion, nombre1, nombre2, nombre3, nombre4;

	public ControladorPartida(String eleccion) {
		this.eleccion = eleccion;
	}

	@FXML
	public void initialize() throws Exception {

		ControladorEligeMazo elige = new ControladorEligeMazo();

		// Guarda la eleccion del mazo.
		switch (eleccion) {
		case "SELECCIONAR 'GRUPO HEARTHSTONE'":
			nombre1 = "ALAMUERTE";
			nombre2 = "VADIN";
			nombre3 = "CTHUN";
			nombre4 = "BOOM";
			break;

		case "SELECCIONAR 'GRUPO WOW'":
			nombre1 = "GARROSH";
			nombre2 = "MAIEV";
			nombre3 = "VARIAN";
			nombre4 = "SYLVANAS";
			break;

		case "SELECCIONAR 'TERRORISTAS DAM'":
			nombre1 = "MAESO";
			nombre2 = "TONY";
			nombre3 = "ALEX";
			nombre4 = "DAVID";
			break;
		}
		// Permite arrastrar la ventana de la app
		this.onDraggedScene(this.container);
		// Inicia la cancion del modo jugar
		cancion.setVolume(0.4);
		cancion.play();
		// Se genera la pantalla y luego, la vamos llamando cuando sea necesario
		generarPantallaVictoria();
		lblSituacion.setText("ES TU TURNO. PIENSA BIEN TU PRIMER ATAQUE");
		lblEquipoRival.setText("Derrota a: 'La banda de Randall'");
		// Carga el primer rival
		try {
			cargaCartas("ED, EDD Y EDDY", "MIKE WAZOWSKY", "OSO YOGUI", "RANDALL", nombre1, nombre2, nombre3, nombre4);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Botón atacar, para realizar mi ataque
	 * 
	 * @param e
	 * @throws Exception
	 */
	@FXML
	public void atacar(ActionEvent event) throws Exception {
		// Decide si se ataca o falla el ataque
		boolean fallaAtaque = false;
		String mensaje = "";

		// Comprobamos si nuestra carta acierta el ataque o falla
		switch (compruebaSeleccion2()) {
		case 1:
			fallaAtaque = fallaAtaque(oCarta5);
			mensaje = "\n > " + oCarta5.getNombre() + " falló su ataque.";
			break;
		case 2:
			fallaAtaque = fallaAtaque(oCarta6);
			mensaje = "\n > " + oCarta6.getNombre() + " falló su ataque.";
			break;
		case 3:
			fallaAtaque = fallaAtaque(oCarta7);
			mensaje = "\n > " + oCarta7.getNombre() + " falló su ataque.";
			break;
		case 4:
			fallaAtaque = fallaAtaque(oCarta8);
			mensaje = "\n > " + oCarta8.getNombre() + " falló su ataque.";
			break;
		}

		// Si nuestra carta acierta se realiza el ataque
		if (fallaAtaque == false) {
			espada.play();
			// Ataque del jugador
			ataqueCartas(compruebaSeleccion1(), compruebaSeleccion2());
			miTurno = false;
			btnAtacar.setDisable(true);

			switch (compruebaDerrota()) {
			case "VICTORIA":
				habilitaCartas();
				lblSituacion.setText("ES TU TURNO");
				// Sumamos uno a la variable rival para indicarle que nos toca el siguiente
				// rival
				rival = rival + 1;
				// Comprueba el rival que toca
				switch (rival) {
				case 2:
					cargarVictoria();
					lblEquipoRival.setText("Derrota a: 'La Brigada Streamer'");
					try {
						cargaCartas("ELMILLOR", "EL XOKAS", "ORSLOK", "KNEKRO", nombre1, nombre2, nombre3, nombre4);
					} catch (Exception e1) {
						e1.printStackTrace();
						throw e1;
					}
					resetCartas();
					miTurno = true;
					break;
				case 3:
					cargarVictoria();
					lblEquipoRival.setText("Derrota a: 'The Green Doramion'");
					try {
						cargaCartas("WOLFANG", "TENSE", "STAXX", "FLIPIN", nombre1, nombre2, nombre3, nombre4);
					} catch (Exception e1) {
						e1.printStackTrace();
						throw e1;
					}
					resetCartas();
					miTurno = true;
					break;
				default:
					cancion.stop();
					cargarVictoria();
					((Node) event.getSource()).getScene().getWindow().hide();
				}

				break;
			case "DERROTA":
				cancion.stop();
				((Node) event.getSource()).getScene().getWindow().hide();
				cargarDerrota();
				break;
			case "SEGUIMOS":
				btnPasarTurno.setDisable(false);
				lblSituacion.setText("YA HAS ATACADO. PULSA PASAR TURNO");
				deshabilitaCartas();
				break;
			}
			// Si la carta falla el ataque imprimimos el mensaje de fallo
		} else {
			fallo.play();
			txtAreaHistorial.appendText(mensaje);
			miTurno = false;
			deshabilitaCartas();
			r1.setSelected(false);
			r2.setSelected(false);
			r3.setSelected(false);
			r4.setSelected(false);
			r5.setSelected(false);
			r6.setSelected(false);
			r7.setSelected(false);
			r8.setSelected(false);
			marco1.setVisible(false);
			marco2.setVisible(false);
			marco3.setVisible(false);
			marco4.setVisible(false);
			marco5.setVisible(false);
			marco6.setVisible(false);
			marco7.setVisible(false);
			marco8.setVisible(false);
			btnAtacar.setDisable(true);
			btnPasarTurno.setDisable(false);
			lblSituacion.setText("FALLASTE EL ATAQUE. PASA DE TURNO");
		}

	}

	/**
	 * Boton pasar turno, ademas realiza el ataque de la IA
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void pasarTurno(ActionEvent event) throws Exception {

		// Decide si se ataca o falla el ataque
		boolean fallaAtaque = false;
		String mensaje = "";

		// Comprobamos si nuestra carta acierta el ataque o falla
		switch (seleccionCartaIA1()) {
		case 1:
			fallaAtaque = fallaAtaque(oCarta1);
			mensaje = "\n > " + oCarta1.getNombre() + " falló su ataque.";
			break;
		case 2:
			fallaAtaque = fallaAtaque(oCarta2);
			mensaje = "\n > " + oCarta2.getNombre() + " falló su ataque.";
			break;
		case 3:
			fallaAtaque = fallaAtaque(oCarta3);
			mensaje = "\n > " + oCarta3.getNombre() + " falló su ataque.";
			break;
		case 4:
			fallaAtaque = fallaAtaque(oCarta4);
			mensaje = "\n > " + oCarta4.getNombre() + " falló su ataque.";
			break;
		}

		if (fallaAtaque == false) {
			espada.play();
			habilitaCartas();
			Thread hilo = new Thread();
			try {
				hilo.sleep(1000);
				hilo.join();
				int cartaIA1, cartaIA2;

				cartaIA1 = seleccionCartaIA1();
				cartaIA2 = seleccionCartaIA2();

				ataqueCartas(cartaIA1, cartaIA2);
				miTurno = true;
				btnPasarTurno.setDisable(true);
				lblSituacion.setText("¡ES TU TURNO, ACABA CON ÉL!");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				throw e1;
			}

			switch (compruebaDerrota()) {
			case "VICTORIA":
				lblSituacion.setText("ES TU TURNO");
				// Sumamos uno a la variable rival para indicarle que nos toca el siguiente
				// rival
				rival = rival + 1;
				// Comprueba el rival que toca
				switch (rival) {
				case 2:
					cargarVictoria();
					try {
						lblEquipoRival.setText("Derrota a: 'La Brigada Streamer'");
						cargaCartas("ELMILLOR", "EL XOKAS", "ORSLOK", "KNEKRO", "GARROSH", "MAIEV", "VARIAN",
								"SYLVANAS");
					} catch (Exception e1) {
						e1.printStackTrace();
						throw e1;
					}
					resetCartas();
					miTurno = true;
					break;
				case 3:
					cargarVictoria();
					try {
						lblEquipoRival.setText("Derrota a: 'The Green Doramion'");
						cargaCartas("WOLFANG", "TENSE", "STAXX", "FLIPIN", "GARROSH", "MAIEV", "VARIAN", "SYLVANAS");
					} catch (Exception e1) {
						e1.printStackTrace();
						throw e1;
					}
					resetCartas();
					miTurno = true;
					break;
				default:
					cancion.stop();
					cargarVictoria();
					((Node) event.getSource()).getScene().getWindow().hide();
				}
				break;
			case "DERROTA":
				cancion.stop();
				((Node) event.getSource()).getScene().getWindow().hide();
				cargarDerrota();
				break;
			case "SEGUIMOS":
				break;
			}
		} else {
			fallo.play();
			txtAreaHistorial.appendText(mensaje);
			miTurno = true;
			habilitaCartas();
			btnAtacar.setDisable(false);
			btnPasarTurno.setDisable(true);
			lblSituacion.setText("EL ENEMIGO FALLO SU ATAQUE. ¡ES TU TURNO!");
		}

	}

	/**
	 * Metodo que aplica los ataques, tanto el del usuario como el de la IA
	 * 
	 * @param seleccionGrupo1
	 * @param seleccionGrupo2
	 */
	public void ataqueCartas(int seleccionGrupo1, int seleccionGrupo2) {

		switch (seleccionGrupo2) {
		// JUGADOR CARTA 1
		case 1:
			// IA CARTA 1
			if (seleccionGrupo1 == 1) {
				vida1 = vida1 - ataque5;
				carta1Vida.setText(vida1 + "");
				vida5 = vida5 - ataque1;
				carta5Vida.setText(vida5 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " inflingió " + oCarta5.getAtaque()
						+ "p. de daño a: " + oCarta1.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " inflingió " + oCarta1.getAtaque()
						+ "p. de daño a: " + oCarta5.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida1) == false) {
					carta1.setDisable(true);
					f1.setVisible(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
					carta5.setDisable(true);
					f5.setVisible(true);
					carta5Vida.setText(vidaCero(vida5) + "");
					txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " ha muerto.");
				}
				// IA CARTA 2
			} else if (seleccionGrupo1 == 2) {
				vida2 = vida2 - ataque5;
				carta2Vida.setText(vida2 + "");
				vida5 = vida5 - ataque2;
				carta5Vida.setText(vida5 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " inflingió " + oCarta5.getAtaque()
						+ "p. de daño a: " + oCarta2.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " inflingió " + oCarta2.getAtaque()
						+ "p. de daño a: " + oCarta5.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida2) == false) {
					f2.setVisible(true);
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
					f5.setVisible(true);
					carta5.setDisable(true);
					carta5Vida.setText(vidaCero(vida5) + "");
					txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " ha muerto.");
				}
				// IA CARTA 3
			} else if (seleccionGrupo1 == 3) {
				vida3 = vida3 - ataque5;
				carta3Vida.setText(vida3 + "");
				vida5 = vida5 - ataque3;
				carta5Vida.setText(vida5 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " inflingió " + oCarta5.getAtaque()
						+ "p. de daño a: " + oCarta3.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " inflingió " + oCarta3.getAtaque()
						+ "p. de daño a: " + oCarta5.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida3) == false) {
					f3.setVisible(true);
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
					f5.setVisible(true);
					carta5.setDisable(true);
					carta5Vida.setText(vidaCero(vida5) + "");
					txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " ha muerto.");
				}
				// IA CARTA 4
			} else if (seleccionGrupo1 == 4) {
				vida4 = vida4 - ataque5;
				carta4Vida.setText(vida4 + "");
				vida5 = vida5 - ataque4;
				carta5Vida.setText(vida5 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " inflingió " + oCarta5.getAtaque()
						+ "p. de daño a: " + oCarta4.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " inflingió " + oCarta4.getAtaque()
						+ "p. de daño a: " + oCarta5.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida4) == false) {
					f4.setVisible(true);
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
					f5.setVisible(true);
					carta5.setDisable(true);
					carta5Vida.setText(vidaCero(vida5) + "");
					txtAreaHistorial.appendText("\n >" + oCarta5.getNombre() + " ha muerto.");
				}
			}
			break;
		// JUGADOR CARTA 2
		case 2:
			// IA CARTA 1
			if (seleccionGrupo1 == 1) {
				vida1 = vida1 - ataque6;
				carta1Vida.setText(vida1 + "");
				vida6 = vida6 - ataque1;
				carta6Vida.setText(vida6 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " inflingió " + oCarta6.getAtaque()
						+ "p. de daño a: " + oCarta1.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " inflingió " + oCarta1.getAtaque()
						+ "p. de daño a: " + oCarta6.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida1) == false) {
					f1.setVisible(true);
					carta1.setDisable(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
					f6.setVisible(true);
					carta6.setDisable(true);
					carta6Vida.setText(vidaCero(vida6) + "");
					txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " ha muerto.");
				}
				// IA CARTA 2
			} else if (seleccionGrupo1 == 2) {
				vida2 = vida2 - ataque6;
				carta2Vida.setText(vida2 + "");
				vida6 = vida6 - ataque2;
				carta6Vida.setText(vida6 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " inflingió " + oCarta6.getAtaque()
						+ "p. de daño a: " + oCarta2.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " inflingió " + oCarta2.getAtaque()
						+ "p. de daño a: " + oCarta6.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida2) == false) {
					f2.setVisible(true);
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
					f6.setVisible(true);
					carta6.setDisable(true);
					carta6Vida.setText(vidaCero(vida6) + "");
					txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " ha muerto.");
				}
				// IA CARTA 3
			} else if (seleccionGrupo1 == 3) {
				vida3 = vida3 - ataque6;
				carta3Vida.setText(vida3 + "");
				vida6 = vida6 - ataque3;
				carta6Vida.setText(vida6 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " inflingió " + oCarta6.getAtaque()
						+ "p. de daño a: " + oCarta3.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " inflingió " + oCarta3.getAtaque()
						+ "p. de daño a: " + oCarta6.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida3) == false) {
					f3.setVisible(true);
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
					f6.setVisible(true);
					carta6.setDisable(true);
					carta6Vida.setText(vidaCero(vida6) + "");
					txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " ha muerto.");
				}
				// IA CARTA 4
			} else if (seleccionGrupo1 == 4) {
				vida4 = vida4 - ataque6;
				carta4Vida.setText(vida4 + "");
				vida6 = vida6 - ataque4;
				carta6Vida.setText(vida6 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " inflingió " + oCarta6.getAtaque()
						+ "p. de daño a: " + oCarta4.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " inflingió " + oCarta4.getAtaque()
						+ "p. de daño a: " + oCarta6.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida4) == false) {
					f4.setVisible(true);
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
					f6.setVisible(true);
					carta6.setDisable(true);
					carta6Vida.setText(vidaCero(vida6) + "");
					txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " ha muerto.");
				}
			}
			break;
		// JUGADOR CARTA 3
		case 3:
			// IA CARTA 1
			if (seleccionGrupo1 == 1) {
				vida1 = vida1 - ataque7;
				carta1Vida.setText(vida1 + "");
				vida7 = vida7 - ataque1;
				carta7Vida.setText(vida7 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " inflingió " + oCarta7.getAtaque()
						+ "p. de daño a: " + oCarta1.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " inflingió " + oCarta1.getAtaque()
						+ "p. de daño a: " + oCarta7.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida1) == false) {
					f1.setVisible(true);
					carta1.setDisable(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
					f7.setVisible(true);
					carta7.setDisable(true);
					carta7Vida.setText(vidaCero(vida7) + "");
					txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " ha muerto.");
				}
				// IA CARTA 2
			} else if (seleccionGrupo1 == 2) {
				vida2 = vida2 - ataque7;
				carta2Vida.setText(vida2 + "");
				vida7 = vida7 - ataque2;
				carta7Vida.setText(vida7 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " inflingió " + oCarta7.getAtaque()
						+ "p. de daño a: " + oCarta2.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " inflingió " + oCarta2.getAtaque()
						+ "p. de daño a: " + oCarta7.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida2) == false) {
					f2.setVisible(true);
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
					f7.setVisible(true);
					carta7.setDisable(true);
					carta7Vida.setText(vidaCero(vida7) + "");
					txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " ha muerto.");
				}
				// IA CARTA 3
			} else if (seleccionGrupo1 == 3) {
				vida3 = vida3 - ataque7;
				carta3Vida.setText(vida3 + "");
				vida7 = vida7 - ataque3;
				carta7Vida.setText(vida7 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " inflingió " + oCarta7.getAtaque()
						+ "p. de daño a: " + oCarta3.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " inflingió " + oCarta3.getAtaque()
						+ "p. de daño a: " + oCarta7.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida3) == false) {
					f3.setVisible(true);
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
					f7.setVisible(true);
					carta7.setDisable(true);
					carta7Vida.setText(vidaCero(vida7) + "");
					txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " ha muerto.");
				}
				// IA CARTA 4
			} else if (seleccionGrupo1 == 4) {
				vida4 = vida4 - ataque7;
				carta4Vida.setText(vida4 + "");
				vida7 = vida7 - ataque4;
				carta7Vida.setText(vida7 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " inflingió " + oCarta7.getAtaque()
						+ "p. de daño a: " + oCarta4.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " inflingió " + oCarta4.getAtaque()
						+ "p. de daño a: " + oCarta7.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida4) == false) {
					f4.setVisible(true);
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
					f7.setVisible(true);
					carta7.setDisable(true);
					carta7Vida.setText(vidaCero(vida7) + "");
					txtAreaHistorial.appendText("\n >" + oCarta7.getNombre() + " ha muerto.");
				}
			}
			break;
		// JUGADOR CARTA 4
		case 4:
			// IA CARTA 1
			if (seleccionGrupo1 == 1) {
				vida1 = vida1 - ataque8;
				carta1Vida.setText(vida1 + "");
				vida8 = vida8 - ataque1;
				carta8Vida.setText(vida8 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " inflingió " + oCarta8.getAtaque()
						+ "p. de daño a: " + oCarta1.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " inflingió " + oCarta1.getAtaque()
						+ "p. de daño a: " + oCarta8.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida1) == false) {
					f1.setVisible(true);
					carta1.setDisable(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
					f8.setVisible(true);
					carta8.setDisable(true);
					carta8Vida.setText(vidaCero(vida8) + "");
					txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " ha muerto.");
				}
				// IA CARTA 2
			} else if (seleccionGrupo1 == 2) {
				vida2 = vida2 - ataque8;
				carta2Vida.setText(vida2 + "");
				vida8 = vida8 - ataque2;
				carta8Vida.setText(vida8 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " inflingió " + oCarta8.getAtaque()
						+ "p. de daño a: " + oCarta2.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " inflingió " + oCarta2.getAtaque()
						+ "p. de daño a: " + oCarta8.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida2) == false) {
					f2.setVisible(true);
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
					f8.setVisible(true);
					carta8.setDisable(true);
					carta8Vida.setText(vidaCero(vida8) + "");
					txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " ha muerto.");
				}
				// IA CARTA 3
			} else if (seleccionGrupo1 == 3) {
				vida3 = vida3 - ataque8;
				carta3Vida.setText(vida3 + "");
				vida8 = vida8 - ataque3;
				carta8Vida.setText(vida8 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " inflingió " + oCarta8.getAtaque()
						+ "p. de daño a: " + oCarta3.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " inflingió " + oCarta3.getAtaque()
						+ "p. de daño a: " + oCarta8.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida3) == false) {
					f3.setVisible(true);
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
					f8.setVisible(true);
					carta8.setDisable(true);
					carta8Vida.setText(vidaCero(vida8) + "");
					txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " ha muerto.");
				}
				// IA CARTA 4
			} else if (seleccionGrupo1 == 4) {
				vida4 = vida4 - ataque8;
				carta4Vida.setText(vida4 + "");
				vida8 = vida8 - ataque4;
				carta8Vida.setText(vida8 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " inflingió " + oCarta8.getAtaque()
						+ "p. de daño a: " + oCarta4.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " inflingió " + oCarta4.getAtaque()
						+ "p. de daño a: " + oCarta8.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida4) == false) {
					f4.setVisible(true);
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
					f8.setVisible(true);
					carta8.setDisable(true);
					carta8Vida.setText(vidaCero(vida8) + "");
					txtAreaHistorial.appendText("\n >" + oCarta8.getNombre() + " ha muerto.");
				}
			}
			break;
		default:
			System.out.println("Ocurrió algún problema");
		}
		// Quitar seleccion de los radio button y marco
		r1.setSelected(false);
		r2.setSelected(false);
		r3.setSelected(false);
		r4.setSelected(false);
		r5.setSelected(false);
		r6.setSelected(false);
		r7.setSelected(false);
		r8.setSelected(false);
		marco1.setVisible(false);
		marco2.setVisible(false);
		marco3.setVisible(false);
		marco4.setVisible(false);
		marco5.setVisible(false);
		marco6.setVisible(false);
		marco7.setVisible(false);
		marco8.setVisible(false);
		btnAtacar.setDisable(true);
	}

	/**
	 * Carga las cartas de la bbdd
	 * 
	 * @param nombre1
	 * @param nombre2
	 * @param nombre3
	 * @param nombre4
	 * @param nombre5
	 * @param nombre6
	 * @param nombre7
	 * @param nombre8
	 * @throws Exception
	 */
	public void cargaCartas(String nombre1, String nombre2, String nombre3, String nombre4, String nombre5,
			String nombre6, String nombre7, String nombre8) throws Exception {
		Consulta consulta = new Consulta();
		try {
			// Guardo los datos para aplicar luego los daños
			oCarta1 = new Carta();
			oCarta2 = new Carta();
			oCarta3 = new Carta();
			oCarta4 = new Carta();
			oCarta5 = new Carta();
			oCarta6 = new Carta();
			oCarta7 = new Carta();
			oCarta8 = new Carta();

			// Cartas grupo 1
			oCarta1 = consulta.devuelveCarta(nombre1);
			oCarta2 = consulta.devuelveCarta(nombre2);
			oCarta3 = consulta.devuelveCarta(nombre3);
			oCarta4 = consulta.devuelveCarta(nombre4);
			// Cartas Grupo 2
			oCarta5 = consulta.devuelveCarta(nombre5);
			oCarta6 = consulta.devuelveCarta(nombre6);
			oCarta7 = consulta.devuelveCarta(nombre7);
			oCarta8 = consulta.devuelveCarta(nombre8);
			// Imagen
			carta1.setImage(new Image(oCarta1.getFotoURL()));
			carta2.setImage(new Image(oCarta2.getFotoURL()));
			carta3.setImage(new Image(oCarta3.getFotoURL()));
			carta4.setImage(new Image(oCarta4.getFotoURL()));
			carta5.setImage(new Image(oCarta5.getFotoURL()));
			carta6.setImage(new Image(oCarta6.getFotoURL()));
			carta7.setImage(new Image(oCarta7.getFotoURL()));
			carta8.setImage(new Image(oCarta8.getFotoURL()));
			// Muestra el ataque y vida en los label
			carta1Ataque.setText(oCarta1.getAtaque() + "");
			carta1Vida.setText(oCarta1.getVida() + "");
			carta2Ataque.setText(oCarta2.getAtaque() + "");
			carta2Vida.setText(oCarta2.getVida() + "");
			carta3Ataque.setText(oCarta3.getAtaque() + "");
			carta3Vida.setText(oCarta3.getVida() + "");
			carta4Ataque.setText(oCarta4.getAtaque() + "");
			carta4Vida.setText(oCarta4.getVida() + "");
			carta5Ataque.setText(oCarta5.getAtaque() + "");
			carta5Vida.setText(oCarta5.getVida() + "");
			carta6Ataque.setText(oCarta6.getAtaque() + "");
			carta6Vida.setText(oCarta6.getVida() + "");
			carta7Ataque.setText(oCarta7.getAtaque() + "");
			carta7Vida.setText(oCarta7.getVida() + "");
			carta8Ataque.setText(oCarta8.getAtaque() + "");
			carta8Vida.setText(oCarta8.getVida() + "");
			// Guardo el ataque
			ataque1 = oCarta1.getAtaque();
			ataque2 = oCarta2.getAtaque();
			ataque3 = oCarta3.getAtaque();
			ataque4 = oCarta4.getAtaque();
			ataque5 = oCarta5.getAtaque();
			ataque6 = oCarta6.getAtaque();
			ataque7 = oCarta7.getAtaque();
			ataque8 = oCarta8.getAtaque();
			// Guardo la vida
			vida1 = oCarta1.getVida();
			vida2 = oCarta2.getVida();
			vida3 = oCarta3.getVida();
			vida4 = oCarta4.getVida();
			vida5 = oCarta5.getVida();
			vida6 = oCarta6.getVida();
			vida7 = oCarta7.getVida();
			vida8 = oCarta8.getVida();

		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Selecciona la carta
	 * 
	 * @param event
	 */
	@FXML
	public void clickCarta(MouseEvent event) {
		// COMPRUEBA EN QUE CARTA SE HIZO CLICK PARA MARCAR EL RADIO BUTTON
		// Carta 1
		if (event.getSource().equals(carta1) || event.getSource().equals(marco1)) {
			if (r1.isSelected()) {
				r1.setSelected(false);
				marco1.setVisible(false);
			} else {
				r1.setSelected(true);
				marco1.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco2.setVisible(false);
				marco3.setVisible(false);
				marco4.setVisible(false);
			}
			// Carta 2
		} else if (event.getSource().equals(carta2) || event.getSource().equals(marco2)) {
			if (r2.isSelected()) {
				r2.setSelected(false);
				marco2.setVisible(false);
			} else {
				r2.setSelected(true);
				marco2.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco1.setVisible(false);
				marco3.setVisible(false);
				marco4.setVisible(false);
			}
			// Carta 3
		} else if (event.getSource().equals(carta3) || event.getSource().equals(marco3)) {
			if (r3.isSelected()) {
				r3.setSelected(false);
				marco3.setVisible(false);
			} else {
				r3.setSelected(true);
				marco3.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco2.setVisible(false);
				marco1.setVisible(false);
				marco4.setVisible(false);
			}
			// Carta 4
		} else if (event.getSource().equals(carta4) || event.getSource().equals(marco4)) {
			if (r4.isSelected()) {
				r4.setSelected(false);
				marco4.setVisible(false);
			} else {
				r4.setSelected(true);
				marco4.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco2.setVisible(false);
				marco1.setVisible(false);
				marco3.setVisible(false);
			}
			// Carta 5
		} else if (event.getSource().equals(carta5) || event.getSource().equals(marco5)) {
			if (r5.isSelected()) {
				r5.setSelected(false);
				marco5.setVisible(false);
			} else {
				r5.setSelected(true);
				marco5.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco6.setVisible(false);
				marco7.setVisible(false);
				marco8.setVisible(false);
			}
			// Carta 6
		} else if (event.getSource().equals(carta6) || event.getSource().equals(marco6)) {
			if (r6.isSelected()) {
				r6.setSelected(false);
				marco6.setVisible(false);
			} else {
				r6.setSelected(true);
				marco6.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco5.setVisible(false);
				marco7.setVisible(false);
				marco8.setVisible(false);
			}
			// Carta 7
		} else if (event.getSource().equals(carta7) || event.getSource().equals(marco7)) {
			if (r7.isSelected()) {
				r7.setSelected(false);
				marco7.setVisible(false);
			} else {
				r7.setSelected(true);
				marco7.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco5.setVisible(false);
				marco6.setVisible(false);
				marco8.setVisible(false);
			}
			// Carta 8
		} else if (event.getSource().equals(carta8) || event.getSource().equals(marco8)) {
			if (r8.isSelected()) {
				r8.setSelected(false);
				marco8.setVisible(false);
			} else {
				r8.setSelected(true);
				marco8.setVisible(true);
				// Quitamos visibilidad al resto de marcos
				marco5.setVisible(false);
				marco6.setVisible(false);
				marco7.setVisible(false);
			}
		}
		deshabilitaAtaque();
	}

	/**
	 * Desactiva el boton de atacar si no estan selecionadas las cartas
	 */
	public void deshabilitaAtaque() {
		if (miTurno == false) {
			btnAtacar.setDisable(true);
			btnPasarTurno.setDisable(false);
		} else {
			if ((r1.isSelected() | r2.isSelected() | r3.isSelected() | r4.isSelected()
					&& (r5.isSelected() | r6.isSelected() | r7.isSelected() | r8.isSelected()))) {
				btnAtacar.setDisable(false);
			} else {
				btnAtacar.setDisable(true);
			}
		}
	}

	/**
	 * Ir menu
	 * 
	 * @param e
	 */
	@FXML
	public void btnVolver(MouseEvent e) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Abandonar");
		alert.setContentText("¿Deseas abandonar partida?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			cancion.stop();
			try {
				((Node) e.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
				Scene scene = new Scene(root, 1300, 830);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.getIcons().add(new Image("/complementos/logo.png"));
				primaryStage.show();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}

	/**
	 * Comprueba la seleccion primera
	 * 
	 * @return
	 */
	public int compruebaSeleccion1() {
		if (grupo1.getSelectedToggle().equals(r1)) {
			return 1;
		} else if (grupo1.getSelectedToggle().equals(r2)) {
			return 2;
		} else if (grupo1.getSelectedToggle().equals(r3)) {
			return 3;
		} else if (grupo1.getSelectedToggle().equals(r4)) {
			return 4;
		} else
			return 0;
	}

	/**
	 * Comprueba la seleccion segunda
	 * 
	 * @return
	 */
	public int compruebaSeleccion2() {
		if (grupo2.getSelectedToggle().equals(r5)) {
			return 1;
		} else if (grupo2.getSelectedToggle().equals(r6)) {
			return 2;
		} else if (grupo2.getSelectedToggle().equals(r7)) {
			return 3;
		} else if (grupo2.getSelectedToggle().equals(r8)) {
			return 4;
		} else
			return 0;
	}

	/**
	 * Comprueba si la carta está con vida
	 * 
	 * @param vida
	 * @return
	 */
	public boolean sigueVivo(int vida) {
		if (vida <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Establece la vida en 0 quitando nº negativos
	 * 
	 * @param vidaCero
	 * @return
	 */
	public int vidaCero(int vidaCero) {
		if (vidaCero < 0) {
			vidaCero = 0;
		}
		return vidaCero;
	}

	/**
	 * Comprueba la derrota de la máquina
	 * 
	 * @return
	 */
	public String compruebaDerrota() {
		String fin = "SEGUIMOS";
		if (vida5 <= 0 && vida6 <= 0 && vida7 <= 0 && vida8 <= 0) {
			fin = "DERROTA";
		} else if (vida1 <= 0 && vida2 <= 0 && vida3 <= 0 && vida4 <= 0) {
			fin = "VICTORIA";
		}
		return fin;
	}

	/**
	 * Seleccion primera carta IA
	 * 
	 * @return
	 */
	public int seleccionCartaIA1() {
		int seleccionIA = -1;
		boolean fin = false;

		while (fin == false) {
			seleccionIA = 1 + (int) (Math.random() * 4);
			// Selecciona una carta del grupo 1 viva
			switch (seleccionIA) {
			case 1:
				if (sigueVivo(vida1) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			case 2:
				if (sigueVivo(vida2) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			case 3:
				if (sigueVivo(vida3) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			case 4:
				if (sigueVivo(vida4) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			}
		}

		return seleccionIA;
	}

	/**
	 * Seleccion segunda carta IA
	 * 
	 * @return
	 */
	public int seleccionCartaIA2() {
		int seleccionIA = -1;
		boolean fin = false;

		while (fin == false) {
			seleccionIA = 1 + (int) (Math.random() * 4);
			// Selecciona una carta del grupo 1 viva
			switch (seleccionIA) {
			case 1:
				if (sigueVivo(vida5) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			case 2:
				if (sigueVivo(vida6) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			case 3:
				if (sigueVivo(vida7) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			case 4:
				if (sigueVivo(vida8) == false) {
					fin = false;
				} else {
					fin = true;
				}
				break;
			}
		}

		return seleccionIA;
	}

	/**
	 * Habilita todas las cartas y quita imagenes como el marco y la imagen de
	 * muerte
	 */
	public void resetCartas() {

		// cartas
		carta1.setDisable(false);
		carta2.setDisable(false);
		carta3.setDisable(false);
		carta4.setDisable(false);
		carta5.setDisable(false);
		carta6.setDisable(false);
		carta7.setDisable(false);
		carta8.setDisable(false);
		// marco
		marco1.setVisible(false);
		marco2.setVisible(false);
		marco3.setVisible(false);
		marco4.setVisible(false);
		marco5.setVisible(false);
		marco6.setVisible(false);
		marco7.setVisible(false);
		marco8.setVisible(false);
		// F
		f1.setVisible(false);
		f2.setVisible(false);
		f3.setVisible(false);
		f4.setVisible(false);
		f5.setVisible(false);
		f6.setVisible(false);
		f7.setVisible(false);
		f8.setVisible(false);

		txtAreaHistorial.setText("");
	}

	/**
	 * Deshabilita las cartas
	 */
	public void deshabilitaCartas() {
		// cartas
		carta1.setDisable(true);
		carta2.setDisable(true);
		carta3.setDisable(true);
		carta4.setDisable(true);
		carta5.setDisable(true);
		carta6.setDisable(true);
		carta7.setDisable(true);
		carta8.setDisable(true);
	}

	/**
	 * Habilita las cartas
	 */
	public void habilitaCartas() {
		// cartas
		carta1.setDisable(false);
		carta2.setDisable(false);
		carta3.setDisable(false);
		carta4.setDisable(false);
		carta5.setDisable(false);
		carta6.setDisable(false);
		carta7.setDisable(false);
		carta8.setDisable(false);
	}

	/**
	 * Carga la pantalla de Victoria
	 */
	public void cargarVictoria() {
		audioVictoria.play();
		stageVictoria.show();
	}

	/**
	 * Genera el Stage Victoria
	 */
	public void generarPantallaVictoria() {
		try {
			stageVictoria = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Victoria.fxml"));
			Scene scene = new Scene(root, 1300, 830);
			stageVictoria.setScene(scene);
			stageVictoria.setResizable(false);
			stageVictoria.getIcons().add(new Image("/complementos/logo.png"));
			stageVictoria.initStyle(StageStyle.UNDECORATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga la pantalla de Derrota
	 */
	public void cargarDerrota() {
		audioDerrota.play();
		cancion.stop();
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Derrota.fxml"));
			Scene scene = new Scene(root, 1300, 830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("/complementos/logo.png"));
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la app
	 * 
	 * @param e
	 */
	@FXML
	public void cerrarApp(MouseEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Cerrar CutreStone");
		alert.setContentText("¿Deseas salir de CutreStone?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
				System.exit(0);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}

	@FXML
	public void mute(MouseEvent e) {
		if (cancion.isPlaying()) {
			altavozOn.setVisible(false);
			altavozOff.setVisible(true);
			cancion.stop();
		} else if (!cancion.isPlaying()) {
			altavozOn.setVisible(true);
			altavozOff.setVisible(false);
			cancion.play();
		}
	}

	/**
	 * Metodo para probabilidad de fallo de ataque
	 * 
	 * @param carta
	 * @return
	 */
	public boolean fallaAtaque(Carta carta) {
		boolean fallo = false;

		String calidad = carta.getCalidad();
		int probabilidadAcierto;

		switch (calidad) {
		// Legendarias siempre aciertan
		case "LEGENDARIA":
			fallo = false;
			break;
		case "COMUN":
			probabilidadAcierto = 1 + (int) (Math.random() * 10);
			if (probabilidadAcierto >= 8) {
				fallo = true;
			} else {
				fallo = false;
			}
			break;
		}

		return fallo;
	}

	/**
	 * Permite arrastrar la ventana
	 * 
	 * @param panelFather
	 */
	public void onDraggedScene(AnchorPane panelFather) {
		AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
		AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

		panelFather.setOnMousePressed(e -> {
			Stage stage = (Stage) panelFather.getScene().getWindow();
			xOffset.set(stage.getX() - e.getScreenX());
			yOffset.set(stage.getY() - e.getScreenY());

		});

		panelFather.setOnMouseDragged(e -> {
			Stage stage = (Stage) panelFather.getScene().getWindow();
			stage.setX(e.getScreenX() + xOffset.get());
			stage.setY(e.getScreenY() + yOffset.get());
			panelFather.setStyle("-fx-cursor: CLOSED_HAND;");
		});

		panelFather.setOnMouseReleased(e -> panelFather.setStyle("-fx-cursor: DEFAULT;"));

	}
}
