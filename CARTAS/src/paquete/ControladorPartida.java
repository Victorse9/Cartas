package paquete;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import javafx.stage.Stage;

public class ControladorPartida {

	@FXML
	private RadioButton r1, r2, r3, r4, r5, r6, r7, r8;
	@FXML
	private Button btnAtacar, btnPasarTurno;
	@FXML
	private ToggleGroup grupo1, grupo2;
	@FXML
	private ImageView carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8, marco1, marco2, marco3, marco4,
			marco5, marco6, marco7, marco8;
	@FXML
	private Label carta1Ataque, carta1Vida, carta2Ataque, carta2Vida, carta3Ataque, carta3Vida, carta4Ataque,
			carta4Vida, carta5Ataque, carta5Vida, carta6Ataque, carta6Vida, carta7Ataque, carta7Vida, carta8Ataque,
			carta8Vida, lblSituacion;

	private Carta oCarta1, oCarta2, oCarta3, oCarta4, oCarta5, oCarta6, oCarta7, oCarta8;
	private int vida1, vida2, vida3, vida4, vida5, vida6, vida7, vida8, ataque1, ataque2, ataque3, ataque4, ataque5,
			ataque6, ataque7, ataque8;
	@FXML
	private TextArea txtAreaHistorial;
	private boolean miTurno = true;

	/**
	 * Botón atacar
	 * 
	 * @param e
	 * @throws InterruptedException
	 */
	@FXML
	public void atacar(ActionEvent e) throws InterruptedException {
		// Ataque del jugador
		ataqueCartas(compruebaSeleccion1(), compruebaSeleccion2());
		miTurno = false;
		btnAtacar.setDisable(true);

		switch (compruebaDerrota()) {
		case "VICTORIA":
			lblSituacion.setText("VICTORIA");
			break;
		case "DERROTA":
			lblSituacion.setText("DERROTA");
			break;
		case "SEGUIMOS":
			btnPasarTurno.setDisable(false);
			lblSituacion.setText("YA HAS ATACADO. PULSA PASAR TURNO");
			break;
		}
	}

	@FXML
	public void pasarTurno(ActionEvent e) throws InterruptedException {
		Thread hilo = new Thread();
		try {
			hilo.sleep(3000);
			hilo.join();
			int[] cartasIA = seleccionCartaIA();
			ataqueCartas(cartasIA[0], cartasIA[1]);
			miTurno = true;
			btnPasarTurno.setDisable(true);
			lblSituacion.setText("¡ES TU TURNO, ACABA CON ÉL!");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw e1;
		}
		
		switch (compruebaDerrota()) {
		case "VICTORIA":
			lblSituacion.setText("VICTORIA");
			break;
		case "DERROTA":
			lblSituacion.setText("DERROTA");
			break;
		case "SEGUIMOS":
			break;
		}

	}

	@FXML
	public void initialize() throws Exception {
		lblSituacion.setText("ES TU TURNO. PIENSA BIEN TU PRIMER ATAQUE");
		try {
			cargaCartas("ED, EDD Y EDDY", "MIKE WAZOWSKY", "OSO YOGUI", "RANDALL", "GARROSH", "MAIEV", "VARIAN",
					"SYLVANAS");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

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
	 * Abandonar partida
	 * 
	 * @param e
	 */
	@FXML
	public void btnVolver(ActionEvent e) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Abandonar");
		alert.setContentText("¿Deseas abandonar partida?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
				((Node) e.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
				Scene scene = new Scene(root, 1300, 830);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
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
			System.out.println(fin);
		} else if (vida1 <= 0 && vida2 <= 0 && vida3 <= 0 && vida4 <= 0) {
			fin = "VICTORIA";
			System.out.println(fin);
		}
		return fin;
	}

	/**
	 * Ataque del jugador
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
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
					carta5.setDisable(true);
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
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
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
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
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
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida5) == false) {
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
					carta1.setDisable(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
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
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
					carta6.setDisable(true);
					carta6Vida.setText(vidaCero(vida6) + "");
					txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " ha muerto.");
				}
				// IA CARTA 3
			} else if (seleccionGrupo1 == 3) {
				vida3 = vida3 - ataque6;
				carta3Vida.setText(vida3 + "");
				vida6 = vida6 - ataque3;
				carta5Vida.setText(vida6 + "");
				// Historial partida
				txtAreaHistorial.appendText("\n >" + oCarta6.getNombre() + " inflingió " + oCarta6.getAtaque()
						+ "p. de daño a: " + oCarta3.getNombre() + ".");
				txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " inflingió " + oCarta3.getAtaque()
						+ "p. de daño a: " + oCarta6.getNombre() + ".");
				// Comprueba si siguien vivos
				if (sigueVivo(vida3) == false) {
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
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
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida6) == false) {
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
					carta1.setDisable(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
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
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
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
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
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
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida7) == false) {
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
					carta1.setDisable(true);
					carta1Vida.setText(vidaCero(vida1) + "");
					txtAreaHistorial.appendText("\n >" + oCarta1.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
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
					carta2.setDisable(true);
					carta2Vida.setText(vidaCero(vida2) + "");
					txtAreaHistorial.appendText("\n >" + oCarta2.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
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
					carta3.setDisable(true);
					carta3Vida.setText(vidaCero(vida3) + "");
					txtAreaHistorial.appendText("\n >" + oCarta3.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
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
					carta4.setDisable(true);
					carta4Vida.setText(vidaCero(vida4) + "");
					txtAreaHistorial.appendText("\n >" + oCarta4.getNombre() + " ha muerto.");
				}
				if (sigueVivo(vida8) == false) {
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

	public int[] seleccionCartaIA() {
		int seleccionIA1, seleccionIA2;
		seleccionIA1 = 1 + (int) (Math.random() * 4);
		seleccionIA2 = 1 + (int) (Math.random() * 4);

		// Selecciona una carta del grupo 1 viva
		switch (seleccionIA1) {
		case 1:
			if (sigueVivo(vida1) == false) {
				seleccionIA1 = 1 + (int) (Math.random() * 4);
			}
			break;
		case 2:
			if (sigueVivo(vida2) == false) {
				seleccionIA1 = 1 + (int) (Math.random() * 4);
			}
			break;
		case 3:
			if (sigueVivo(vida3) == false) {
				seleccionIA1 = 1 + (int) (Math.random() * 4);
			}
			break;
		case 4:
			if (sigueVivo(vida4) == false) {
				seleccionIA1 = 1 + (int) (Math.random() * 4);
			}
			break;
		}

		// Selecciona una carta del grupo 2 viva
		switch (seleccionIA2) {
		case 1:
			if (sigueVivo(vida5) == false) {
				seleccionIA2 = 1 + (int) (Math.random() * 4);
			}
			break;
		case 2:
			if (sigueVivo(vida6) == false) {
				seleccionIA2 = 1 + (int) (Math.random() * 4);
			}
			break;
		case 3:
			if (sigueVivo(vida7) == false) {
				seleccionIA2 = 1 + (int) (Math.random() * 4);
			}
			break;
		case 4:
			if (sigueVivo(vida8) == false) {
				seleccionIA2 = 1 + (int) (Math.random() * 4);
			}
			break;
		}

		int[] seleccionadas = { seleccionIA1, seleccionIA2 };
		return seleccionadas;
	}

}
