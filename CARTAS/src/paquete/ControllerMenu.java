package paquete;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControllerMenu {

	@FXML
	private Button btnVolumen;
	@FXML
	private AudioClip audio = new AudioClip("file:sonido/audio.mp3");
	@FXML
	private Button btnJugar;
	@FXML
	private ImageView altavozOn, altavozOff;
	@FXML
	private AnchorPane container;

	@FXML
	public void initialize() {
		audio.play();
		onDraggedScene(container);
	}

	@FXML
	public void volumen(MouseEvent e) {
		if (audio.isPlaying()) {
			audio.stop();
			altavozOn.setVisible(false);
			altavozOff.setVisible(true);
		} else {
			audio.play();
			altavozOn.setVisible(true);
			altavozOff.setVisible(false);
		}

	}

	/**
	 * Boton jugar
	 * 
	 * @param event
	 */
	public void jugar(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = null;
		try {
			audio.stop();
			primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Partida.fxml"));
			Scene scene = new Scene(root, 1300, 830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/complementos/logo.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Guia juego
	 * 
	 * @param event
	 */
	public void botonGuia(ActionEvent event) {
		audio.stop();
		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Ayuda.fxml"));
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