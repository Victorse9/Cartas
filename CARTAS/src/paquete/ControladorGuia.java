package paquete;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorGuia {

	@FXML
	private Pane panel1, panel2;
	@FXML
	private AnchorPane container;

	@FXML
	public void initialize() {
		onDraggedScene(container);
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
		alert.setTitle("Volver al menú");
		alert.setContentText("¿Deseas volver al menú?");

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
	 * Ir a la página 2 de la guía
	 * 
	 * @param e
	 */
	@FXML
	public void irPagina2(MouseEvent e) {
		panel1.setVisible(false);
		panel2.setVisible(true);
	}

	/**
	 * Ir a la página 1 de la guía
	 * 
	 * @param e
	 */
	@FXML
	public void irPagina1(MouseEvent e) {
		panel1.setVisible(true);
		panel2.setVisible(false);
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
