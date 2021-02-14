package paquete;

import java.util.concurrent.atomic.AtomicReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorVictoria {
	@FXML
	private Button btnVictoria, btnVictoriaSalir;
	@FXML
	private Label lblVictoria;
	private int rival = 0;
	@FXML
	private AudioClip doramio = new AudioClip("file:sonido/doramio.mp3");
	@FXML
	private AudioClip xokas = new AudioClip("file:sonido/xokas.mp3");
	@FXML
	private AnchorPane container;

	@FXML
	public void initialize() {
		onDraggedScene(container);
	}

	/**
	 * Boton siguiente rival
	 * 
	 * @param event
	 */
	@FXML
	public void siguienteRival(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		rival = rival + 1;

		if (rival == 1) {
			xokas.play();
		} else if (rival >= 2) {
			doramio.play();
			btnVictoria.setVisible(false);
			btnVictoriaSalir.setVisible(true);
			lblVictoria.setVisible(true);
		}

	}

	@FXML
	public void salir(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
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
