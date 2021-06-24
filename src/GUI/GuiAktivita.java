package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Vzhled aktivity
 * @author Lukas Runt
 * @version 1.0 (2021-05-26)
 */

public class GuiAktivita extends Stage{
	
	public void showDialog() throws Exception {
		Pane viewPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource("AktivitkaView.fxml"));
		
		this.setScene(new Scene(viewPane));
		this.setMinHeight(400);
		this.setMinWidth(600);
		this.setTitle("Zobrazeni aktivity");
		
		this.getScene().getStylesheets().add("/vzhled/basicStyle.css");
		
		this.show();
	}
	
}
