package utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Trida pro vyskakovani dialogovych oken 
 * @author Lukas Runt
 * @version 1.0 (2021-04-09)
 */
public class Message {
	private Alert alertError = new Alert(AlertType.ERROR);
	private Alert alertInfo = new Alert(AlertType.INFORMATION); 
	private Alert alertVyber = new Alert(AlertType.CONFIRMATION);
	
	/**
	 * Error dialog 
	 */
	public void showErrorDialog(String zprava) {
		alertError.setTitle("Error");
		alertError.setHeaderText(null);
		alertError.setContentText(zprava);
		alertError.showAndWait();
	}
	
	/**
	 * Information dialog
	 */
	public void showInfoDialog(String zprava) {
		alertInfo.setTitle("Info");
		alertInfo.setHeaderText(null);
		alertInfo.setContentText(zprava);
		alertInfo.showAndWait();
	}
	
	public boolean showVyberDialog(String zprava) {
		alertVyber.setTitle("Smazani");
		alertVyber.setHeaderText(null);
		alertVyber.setContentText(zprava);
		Optional<ButtonType> result = alertVyber.showAndWait();
		if(result.get() == ButtonType.CANCEL) {
			return false;
		}
		else {
			return true;
		}
	}
}
