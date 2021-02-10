package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu {

    @FXML 
    Button btnVolumen;
    @FXML
    AudioClip audio = new AudioClip("file:sonido/audio.mp3");
    @FXML
    Button btnJugar;
    
   
    public void initialize() {
    	audio.play();
    	URL url = getClass().getResource("/complementos/altavoz.png");
    	Image imagenAltavoz = new Image(url.toString(),70,80,false,true);
    	btnVolumen.setGraphic((new ImageView(imagenAltavoz)));
    }
    
    @FXML
    public void volumen(ActionEvent e) {
    	if(audio.isPlaying()) {
    		audio.stop();
    		URL url = getClass().getResource("/complementos/altavozoff.png");
        	Image imagenAltavoz = new Image(url.toString(),70,80,false,true);
        	btnVolumen.setGraphic((new ImageView(imagenAltavoz)));
    	}else {
    		audio.play();
    		URL url = getClass().getResource("/complementos/altavoz.png");
        	Image imagenAltavoz = new Image(url.toString(),70,80,false,true);
        	btnVolumen.setGraphic((new ImageView(imagenAltavoz)));
    	}
    	
    }

    
    
    public void mostrarCartas(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide();
    	try {
			Stage primaryStage= new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MostrarCartas.fxml"));
			Scene scene = new Scene(root,1300,830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void jugar(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide();
    	try {
    		audio.stop();
			Stage primaryStage= new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Partida.fxml"));
			Scene scene = new Scene(root,1300,830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void botonGuia(ActionEvent event) {
    ((Node)event.getSource()).getScene().getWindow().hide();
	try {
		Stage primaryStage= new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Ayuda.fxml"));
		Scene scene = new Scene(root,1300,830);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
}
}