package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * GUI manualniho vkladani
 * @author Lukas Runt 
 * @version 1.0 (2021-05-28)
 */
public class GuiManual extends Stage{

	public void showDialog() throws Exception {
		Pane manualPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource("ManualInput.fxml"));
		
		this.setScene(new Scene(manualPane));
		this.setMinHeight(210);
		this.setMinWidth(600);
		this.setTitle("Manualni vstup");
		
		this.getScene().getStylesheets().add("/vzhled/basicStyle.css");
		
		this.show();
	}

}
