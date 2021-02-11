package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class ControladorGuia {
	
	@FXML
	Label label1,label2,label3,label4,label5,lblAtaque,lblVida,ataqueRandall1,vidaRandall2,lblRareza;
	@FXML
	ImageView tinta,flechaAtras,flechaAlante,flechaAlante1,fotoRandall,flechaAtaque,flechaVida,flechaVictor,flechaAtras1;
	@FXML
	Circle vidaRandall1,ataqueRandall2;
	
	public void botonVolver(ActionEvent event) {
	((Node)event.getSource()).getScene().getWindow().hide();
	try {
		Stage primaryStage= new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root,1300,830);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
}
	@FXML
	public void flecha(MouseEvent event) {
		
		label5.setText("- El juego finaliza cuando derrotas las 4 cartas del rival.");
		label1.setText("- Acción por turnos");
		label2.setText("- Puedes seleccionar una carta enemiga y una propia para atacarla por turno.");
		label3.setText("- Cuando atacas infringes tu daño de ataque a su vida y viceversa");
		label4.setText("- Cuando la vida de una carta llega a 0 queda inhabilitada.");
		
		tinta.setVisible(false);
		
		flechaAtras.setVisible(false);
		flechaAtras1.setVisible(true);
		
		flechaAlante.setVisible(false);
		flechaAlante1.setVisible(true);
	}
	
	public void ultimaFlecha(MouseEvent event) {
		
		label5.setVisible(false);
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		flechaAlante1.setVisible(false);
		
		flechaAtras1.setVisible(false);
		flechaAtras.setVisible(true);
		tinta.setVisible(false);
		
		fotoRandall.setVisible(true);
		ataqueRandall1.setVisible(true);
		ataqueRandall2.setVisible(true);
		vidaRandall1.setVisible(true);
		vidaRandall2.setVisible(true);
		lblVida.setVisible(true);
		lblAtaque.setVisible(true);
		flechaAtaque.setVisible(true);
		flechaVida.setVisible(true);
		flechaVictor.setVisible(true);
		lblRareza.setVisible(true);

	}
	
	public void flechaAtras(MouseEvent event) {
		label5.setVisible(true);
		label1.setVisible(true);
		label2.setVisible(true);
		label3.setVisible(true);
		label4.setVisible(true);
		flechaAlante1.setVisible(true);
		
		flechaAtras.setVisible(false);
		flechaAtras1.setVisible(true);
		
		fotoRandall.setVisible(false);
		ataqueRandall1.setVisible(false);
		ataqueRandall2.setVisible(false);
		vidaRandall1.setVisible(false);
		vidaRandall2.setVisible(false);
		lblVida.setVisible(false);
		lblAtaque.setVisible(false);
		flechaAtaque.setVisible(false);
		flechaVida.setVisible(false);
		flechaVictor.setVisible(false);
		lblRareza.setVisible(false);
	}
	
	public void flechaAtrasjejeje(MouseEvent event) {
		label1.setText("- CUTRESTONE es un juego de estrategia de cartas 1 vs 1.");
		label2.setText("- Cada jugador consta de un mazo de 4 cartas");
		label3.setText("- Existen 2 tipos de rarezas de cartas: Legendaria y común.");
		label4.setText("- Las cartas legendarias son más poderosas.");
		label5.setText("- El objetivo es derrotar todas las cartas enemigas.");
		tinta.setVisible(true);
		flechaAtras1.setVisible(false);
	}
	

}
