package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import GUI.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Aktivita;
import model.TypAktivity;

public class ManualVstupControl implements Initializable{
	
	@FXML
	private Button zrusBT;
	
	@FXML
	private Button ulozBT;
	
	@FXML
	private TextField nazevTF;
	
	@FXML
	private TextField casTF;
	
	@FXML
	private TextField vzdalenostTF;
	
	@FXML
	private TextField prevyseniTF;
	
	@FXML
	private DatePicker datumDP;
	
	@FXML
	private ComboBox<TypAktivity> typCB;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		typCB.getItems().setAll(TypAktivity.values());
		typCB.setOnAction(e -> visibilita(e));
		casTF.setText("00:00:00");
		nazevTF.setText("Aktivita");
	}
	
	private void visibilita(ActionEvent e) {
		TypAktivity vyber = typCB.getValue();
		if(vyber.equals(TypAktivity.POSILOVNA) || vyber.equals(TypAktivity.STRETCHING) || vyber.equals(TypAktivity.AKTIVITA)) {
			vzdalenostTF.setEditable(false);
			vzdalenostTF.setText("");
			prevyseniTF.setEditable(false);
			prevyseniTF.setText("");
		} 
		if(vyber.equals(TypAktivity.CYKLISTIKA) || vyber.equals(TypAktivity.BEH) || vyber.equals(TypAktivity.BEZKY) || vyber.equals(TypAktivity.CHUZE) || vyber.equals(TypAktivity.PLAVANI) || vyber.equals(TypAktivity.BRUSLE)) {
			vzdalenostTF.setEditable(true);
			prevyseniTF.setEditable(true);
		}
	}
	
	/**
	 * Ukladani aktivity
	 */
	public void uloz() {
		LocalTime cas = null;
		String vzdalenostSTR = vzdalenostTF.getText().replace(',', '.');
		String prevyseniSTR = prevyseniTF.getText().replace(',', '.');
		double vzdalenost = 0;
		double prevyseni = 0;
		try {
			if(casTF.getText().equals("00:00:00")) {
				Main.zprava.showErrorDialog("Cas nemuze byt 0");
				return;
			} else {
				cas = LocalTime.parse(casTF.getText());
			}
		} catch(Exception ex) {
			Main.zprava.showErrorDialog("Spatny format casu!");
			zvyrazni(casTF);
			return;
		}
		if (datumDP.getValue() == null) {
			Main.zprava.showErrorDialog("Datum neni zadan!");
			zvyrazni(datumDP);
			return;
		}
		if (datumDP.getValue().isAfter(LocalDate.now())) {
			Main.zprava.showErrorDialog("Aktivita jeste nemohla probehnout!");
			zvyrazni(datumDP);
			return;
		}
		if (typCB.getValue() == null){
			Main.zprava.showErrorDialog("Neni vyplneny typ aktivity!");
			zvyrazni(typCB);
			return;
		}
		try {
			vzdalenost = Double.parseDouble(vzdalenostSTR);
		} catch(IllegalArgumentException ex) {
			if(vzdalenostTF.getText().equals("") && vzdalenostTF.isEditable() == false) {
				vzdalenost = 0;
			} else {
				Main.zprava.showErrorDialog("Spatne zadana vzdalenost");
				return;
			}
		}
		try {
			prevyseni = Double.parseDouble(prevyseniSTR);
		}catch(IllegalArgumentException ex) {
			if(prevyseniTF.getText().equals("") && prevyseniTF.isEditable() == false) {
				prevyseni = 0;
			} else {
				Main.zprava.showErrorDialog("Spatne zadane prevyseni");
				return;
			}
		}
		if(prevyseni < 0) {
			Main.zprava.showErrorDialog("Prevyseni nesmi byt zaporne");
			return;
		}
		if(vzdalenost < 0) {
			Main.zprava.showErrorDialog("Vzdalenost nesmi byt zaporna");
			return;
		}
		try {
			Main.model.aktivity.add(new Aktivita(nazevTF.getText(), vzdalenost, cas, typCB.getValue(), datumDP.getValue(), prevyseni));
			Main.createStartItems();
			zavri();
		} catch (Exception ex) {
			Main.zprava.showErrorDialog("Spatne vstupni hodnoty");
			return;
		}
	}
	
	public void zvyrazni(Node komponenta) {
		komponenta.setStyle("-fx-border-color: black; -fx-backgroung-color: darksalmon;");
	}

	/**
	 * Zavreni okna (zruseni vkladani)
	 */
	public void zavri() {
		Stage stage = (Stage) zrusBT.getScene().getWindow();
		stage.close();
	}
}
