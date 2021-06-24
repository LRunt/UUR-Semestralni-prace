package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import GUI.Main;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Aktivita;
import model.TypAktivity;
/**
 * @author Lukas Runt
 * @version 1.0 (2020-05-30)
 */
public class AktivitaViewControl implements Initializable{
	private Aktivita aktivita;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private final DateTimeFormatter formatterCasu = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@FXML
	private Button zavriBT;
	
	@FXML
	private Button upravBT;
	
	@FXML
	private Label datumLB;
	
	@FXML
	private Label nazevLB;
	
	@FXML
	private Label vzdalenostC;
	
	@FXML
	private Label casC;
	
	@FXML
	private Label avgRychlostC;
	
	@FXML
	private Label avgTepC;
	
	@FXML
	private Label maxTepC;
	
	@FXML
	private Label maxRychlostC;
	
	@FXML
	private Label prevyseniC;
	
	@FXML
	private Label kalorieC;
	
	@FXML
	private ImageView image;
	
	@FXML
	private GridPane grid;
	
	@FXML
	private Label randomLB1;
	
	@FXML
	private Label tmin2;
	
	@FXML
	private Label tmin1;
	
	@FXML
	private Label kmh1;
	
	@FXML
	private Label avgTepLB;
	
	@FXML
	private Label kalorieLB;
	
	@FXML
	private Label kcal;
	
	@FXML
	private Label maxTepLB;
	
	@FXML
	private Label maxRychlostLB;
	
	@FXML
	private Label randomLB2;
	
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
	
	@FXML
	private Button zrusBT;
	
	@FXML
	private Button ulozBT;
	
	/**
	 * Inicializacre okna aby se ozbrazily spravne hodnoty
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aktivita = Main.vybranaAktivita;
		if(randomLB1 != null) {
			randomLB1.setVisible(false);
			randomLB2.setVisible(false);
			nazevLB.textProperty().bind(aktivita.nazevProperty());
			datumLB.setText(aktivita.getDatum().format(formatter));
			aktivita.datumProperty().addListener(a -> zmenaDatumu(a));
			vzdalenostC.textProperty().bind(aktivita.vzdalenostProperty().asString());
			casC.setText(aktivita.getCas().format(formatterCasu));
			aktivita.casProperty().addListener(a -> zmenaCasu());
			avgRychlostC.textProperty().bind(aktivita.prumernaRychlostProperty().asString());
			avgTepC.textProperty().bind(aktivita.prumernyTepProperty().asString());
			maxRychlostC.textProperty().bind(aktivita.maxRychlostProperty().asString());
			maxTepC.textProperty().bind(aktivita.maxTepProperty().asString());
			prevyseniC.textProperty().bind(aktivita.prevyseniPropety().asString());
			kalorieC.textProperty().bind(aktivita.kalorieProperty().asString());
			if(aktivita.getPrumernyTep() == 0) {
				avgTepC.setVisible(false);
				tmin1.setVisible(false);
				avgTepLB.setVisible(false);
				
			}
			if(aktivita.getMaxRychlost() == 0) {
				maxRychlostC.setVisible(false);
				maxRychlostLB.setVisible(false);
				kmh1.setVisible(false);
			}
			if(aktivita.getMaxTep() == 0) {
				maxTepC.setVisible(false);
				maxTepLB.setVisible(false);
				tmin2.setVisible(false);
			}
			if(aktivita.getKalorie() == 0) {
				kalorieC.setVisible(false);
				kalorieLB.setVisible(false);
				kcal.setVisible(false);
			}
			InputStream stream = null;
			try {
				stream = new FileInputStream(getCesta(aktivita.getTyp()));
			} catch(Exception ex){
				ex.printStackTrace();
			}
			Image ikona = new Image(stream);
			image.setImage(ikona);
			image.setFitWidth(100);
			image.setFitHeight(100);
			aktivita.typProperty().addListener(a -> {
				try {
					zmenObrazek(a);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
		} else {
			casTF.setText(aktivita.getCas().toString());
			nazevTF.setText(aktivita.getNazev());
			typCB.setValue(aktivita.getTyp());
			datumDP.setValue(aktivita.getDatum());
			typCB.getItems().setAll(TypAktivity.values());
			if(typCB.getValue() != TypAktivity.POSILOVNA || typCB.getValue() != TypAktivity.STRETCHING) {
				vzdalenostTF.setText(aktivita.getVzdalenost() + "");
				prevyseniTF.setText(aktivita.getPrevyseni() + "");
			}
			visibilita();
			typCB.setOnAction(e -> visibilita());
		}	
	}
	
	/**
	 * Obsluhuje textfieldy, jestli maji byt dostupne nebo ne
	 */
	private void visibilita() {
		TypAktivity vyber = typCB.getValue();
		if(vyber.equals(TypAktivity.POSILOVNA) || vyber.equals(TypAktivity.STRETCHING) || vyber.equals(TypAktivity.AKTIVITA)) {
			vzdalenostTF.setEditable(false);
			vzdalenostTF.setText("");
			prevyseniTF.setEditable(false);
			prevyseniTF.setText("");
		} 
		if(vyber.equals(TypAktivity.PLAVANI)) {
			prevyseniTF.setEditable(false);
			prevyseniTF.setText("");
			vzdalenostTF.setEditable(true);
		}
		if(vyber.equals(TypAktivity.CYKLISTIKA) || vyber.equals(TypAktivity.BEH) || vyber.equals(TypAktivity.BEZKY) || vyber.equals(TypAktivity.CHUZE) || vyber.equals(TypAktivity.BRUSLE)) {
			vzdalenostTF.setEditable(true);
			prevyseniTF.setEditable(true);
		}
	}
	
	/**
	 * Meni obrazek ve vzhledu aktivity
	 * @param a 
	 * @throws FileNotFoundException
	 */
	private void zmenObrazek(Observable a) throws FileNotFoundException{
			InputStream stream = new FileInputStream(getCesta(aktivita.getTyp()));
			Image ikona = new Image(stream);
			image.setImage(ikona);
	}

	/**
	 * Zmena casu, kvuli formatu mi nesel pouzit binding
	 */
	private void zmenaCasu() {
		casC.setText(aktivita.getCas().format(formatterCasu));
	}

	/**
	 * Zmena dutumu, kvuli formatu nesel pouzit binding
	 * @param a
	 */
	private void zmenaDatumu(Observable a) {
		datumLB.setText(aktivita.getDatum().format(formatter));
	}

	/**
	 * Zaviraci tlacitko
	 */
	public void zavri() {
		Stage stage;
		if(zrusBT == null) {
			stage = (Stage) zavriBT.getScene().getWindow();
		}else {
			stage = (Stage) zrusBT.getScene().getWindow();
		}
		stage.close();
	}
	
	/**
	 * Zmeni zvhled onka na zadavaci okno
	 * @throws Exception
	 */
	public void uprav() throws Exception {
		Pane viewPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource("ManualInput2.fxml"));
		Stage stage = (Stage) zavriBT.getScene().getWindow();
		stage.setScene(new Scene(viewPane));
		stage.setMinHeight(210);
		stage.setMinWidth(600);
		stage.setWidth(600);
		stage.setHeight(220);
		
		stage.getScene().getStylesheets().add("/vzhled/basicStyle.css");
	}
	
	/**
	 * Ulozeni hodnot
	 * @throws Exception
	 */
	public void uloz() throws Exception {
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
			return;
		}
		if (datumDP.getValue() == null) {
			Main.zprava.showErrorDialog("Datum neni zadan!");
			return;
		}
		if (datumDP.getValue().isAfter(LocalDate.now())) {
			Main.zprava.showErrorDialog("Aktivita jeste nemohla probehnout!");
			return;
		}
		if (typCB.getValue() == null){
			Main.zprava.showErrorDialog("Neni vyplneny typ aktivity!");
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
		} catch(Exception ex) {
			if(prevyseniTF.getText().equals("") || prevyseniTF.isEditable() == false) {
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
		aktivita.setCas(cas);
		aktivita.setNazev(nazevTF.getText());
		aktivita.setVzdalenost(vzdalenost);
		aktivita.setDatum(datumDP.getValue());
		aktivita.setTyp(typCB.getValue());
		aktivita.setPrevyseni(prevyseni);
		Main.createStartItems();
		
		Pane viewPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource("AktivitkaView.fxml"));
		Stage stage = (Stage) ulozBT.getScene().getWindow();
		stage.setScene(new Scene(viewPane));
		stage.setMinHeight(400);
		stage.setMinWidth(600);
		stage.setWidth(600);
		stage.setHeight(450);
		
		stage.getScene().getStylesheets().add("/vzhled/basicStyle.css");
		
	}
	
	/**
	 * Zjisteni cesty k obrazkum
	 * @param typ typ aktivity
	 * @return ceta obrazku
	 */
	private String getCesta(TypAktivity typ) {
		if(typ == TypAktivity.CYKLISTIKA) {
			return "kolo.png";
		}
		if(typ == TypAktivity.BEH) {
			return "running.png";
		}
		if(typ == TypAktivity.CHUZE) {
			return "adventurer.png";
		}
		return "activity.png";
	}
	
}
