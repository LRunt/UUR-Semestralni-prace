package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Aktivita;

public class GuiAktivita extends Stage{
	
	public void showDialog() throws Exception {
		Pane viewPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource("AktivitkaView.fxml"));
		
		this.setScene(new Scene(viewPane));
		this.setMinHeight(400);
		this.setMinWidth(600);
		this.setTitle("Zobrazeni aktivity");
		
		this.getScene().getStylesheets().add("basicStyle.css");
		
		this.show();
	}
	
}
