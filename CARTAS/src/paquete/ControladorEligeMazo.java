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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorEligeMazo {
	Stage stage;
	private String seleccion;
	@FXML
	private Button btn1, btn2, btn3;
	private Carta oCarta1, oCarta2, oCarta3, oCarta4, oCarta5, oCarta6, oCarta7, oCarta8, oCarta9, oCarta10, oCarta11,
			oCarta12;
	@FXML
	private ImageView carta1A, carta2A, carta3A, carta4A, carta1B, carta2B, carta3B, carta4B, carta1C, carta2C, carta3C,
			carta4C;
	@FXML
	private Label carta1Ataque, carta1Vida, carta2Ataque, carta2Vida, carta3Ataque, carta3Vida, carta4Ataque,
			carta4Vida, ataque1A, vida1A, ataque2A, vida2A, ataque3A, vida3A, ataque4A, vida4A, ataque1B, vida1B,
			ataque2B, vida2B, ataque3B, vida3B, ataque4B, vida4B, ataque1C, vida1C, ataque2C, vida2C, ataque3C, vida3C,
			ataque4C, vida4C;
	@FXML
	AnchorPane container;

	public void irPartida(ActionEvent event) {

		Button eleccion = (Button) ((Node) event.getSource());
		seleccion = eleccion.getText();
		((Node) event.getSource()).getScene().getWindow().hide();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Partida.fxml"));
			ControladorPartida controller = new ControladorPartida(seleccion);
			loader.setController(controller);
			stage = new Stage();
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 1300, 830);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.getIcons().add(new Image("/complementos/logo.png"));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void initialize() throws Exception {
		this.onDraggedScene(this.container);
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
			oCarta9 = new Carta();
			oCarta10 = new Carta();
			oCarta11 = new Carta();
			oCarta12 = new Carta();
			// Cartas A
			oCarta1 = consulta.devuelveCarta("ALAMUERTE");
			oCarta2 = consulta.devuelveCarta("VADIN");
			oCarta3 = consulta.devuelveCarta("CTHUN");
			oCarta4 = consulta.devuelveCarta("BOOM");
			// Grupo B
			oCarta5 = consulta.devuelveCarta("GARROSH");
			oCarta6 = consulta.devuelveCarta("MAIEV");
			oCarta7 = consulta.devuelveCarta("VARIAN");
			oCarta8 = consulta.devuelveCarta("SYLVANAS");
			// Grupo C
			oCarta9 = consulta.devuelveCarta("MAESO");
			oCarta10 = consulta.devuelveCarta("TONY");
			oCarta11 = consulta.devuelveCarta("ALEX");
			oCarta12 = consulta.devuelveCarta("DAVID");
			// Imagen grupo A
			carta1A.setImage(new Image(oCarta1.getFotoURL()));
			carta2A.setImage(new Image(oCarta2.getFotoURL()));
			carta3A.setImage(new Image(oCarta3.getFotoURL()));
			carta4A.setImage(new Image(oCarta4.getFotoURL()));
			// Imagen grupo B
			carta1B.setImage(new Image(oCarta5.getFotoURL()));
			carta2B.setImage(new Image(oCarta6.getFotoURL()));
			carta3B.setImage(new Image(oCarta7.getFotoURL()));
			carta4B.setImage(new Image(oCarta8.getFotoURL()));
			// Imagen grupo C
			carta1C.setImage(new Image(oCarta9.getFotoURL()));
			carta2C.setImage(new Image(oCarta10.getFotoURL()));
			carta3C.setImage(new Image(oCarta11.getFotoURL()));
			carta4C.setImage(new Image(oCarta12.getFotoURL()));

			// GRUPO A
			ataque1A.setText(oCarta1.getAtaque() + "");
			vida1A.setText(oCarta1.getVida() + "");
			ataque2A.setText(oCarta2.getAtaque() + "");
			vida2A.setText(oCarta2.getVida() + "");
			ataque3A.setText(oCarta3.getAtaque() + "");
			vida3A.setText(oCarta3.getVida() + "");
			ataque4A.setText(oCarta4.getAtaque() + "");
			vida4A.setText(oCarta4.getVida() + "");
			// GRUPO B
			ataque1B.setText(oCarta5.getAtaque() + "");
			vida1B.setText(oCarta5.getVida() + "");
			ataque2B.setText(oCarta6.getAtaque() + "");
			vida2B.setText(oCarta6.getVida() + "");
			ataque3B.setText(oCarta7.getAtaque() + "");
			vida3B.setText(oCarta7.getVida() + "");
			ataque4B.setText(oCarta8.getAtaque() + "");
			vida4B.setText(oCarta8.getVida() + "");
			// GRUPO C
			ataque1C.setText(oCarta9.getAtaque() + "");
			vida1C.setText(oCarta9.getVida() + "");
			ataque2C.setText(oCarta10.getAtaque() + "");
			vida2C.setText(oCarta10.getVida() + "");
			ataque3C.setText(oCarta11.getAtaque() + "");
			vida3C.setText(oCarta11.getVida() + "");
			ataque4C.setText(oCarta12.getAtaque() + "");
			vida4C.setText(oCarta12.getVida() + "");

		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			throw e;
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
