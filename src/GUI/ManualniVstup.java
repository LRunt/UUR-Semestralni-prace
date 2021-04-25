package GUI;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Aktivita;
import model.TypAktivity;

/**
 * @author Lukas Runt 
 * @version 1.0 (2021-04-08)
 */
public class ManualniVstup extends Stage{
	private TextField nazevTF;
	private ComboBox<TypAktivity> typCB;
	private Spinner<Integer> hodinaSP;
	private Spinner<Integer> minutaSP;
	private Spinner<Integer> sekundaSP;
	private TextField vzdalenostTF;
	private DatePicker datumDP;
	private TextField prevyseniTF;
	
	public void showDialog() {
		this.setTitle("Nova aktivita - Lukas Runt");
		this.setMinWidth(500);
		this.setWidth(650);
		this.setMaxHeight(400);
		this.setMinHeight(300);
		this.setMaxWidth(700);
		
		this.setScene(new Scene(getRoot()));
		
		this.show();
	}

	private Parent getRoot() {
		BorderPane rootBorderPane = new BorderPane();
		
		rootBorderPane.setCenter(getGUI());
		
		return rootBorderPane;
	}

	private Node getGUI() {
		GridPane celek = new GridPane();
		
		celek.setHgap(10);
		celek.setVgap(10);
		
		VBox nazev = new VBox();
		Label nazevLB = new Label("Nazev aktivity");
		nazevTF = new TextField();
		nazevTF.setText("Aktivita");
		nazev.getChildren().addAll(nazevLB, nazevTF);
		celek.add(nazev, 1, 1);
		
		VBox typ = new VBox();
		Label typLB = new Label("Typ aktivity");
		typCB = new ComboBox<TypAktivity>();
		typCB.getItems().setAll(TypAktivity.values());
		typ.getChildren().addAll(typLB, typCB);
		celek.add(typ, 2, 1);
		
		VBox datum = new VBox();
		Label datumLB = new Label("Datum");
		datumDP = new DatePicker();
		datum.getChildren().addAll(datumLB, datumDP);
		celek.add(datum, 3, 1);
		
		VBox cas = new VBox();
		Label casLB = new Label("Cas");
		HBox casHB = new HBox();
		hodinaSP = new Spinner<Integer>(0, 59, 1);
		hodinaSP.setPrefWidth(60);
		hodinaSP.setEditable(true);
		minutaSP = new Spinner<Integer>(0, 59, 0);
		minutaSP.setPrefWidth(60);
		minutaSP.setEditable(true);
		sekundaSP = new Spinner<Integer>(0, 59, 0);
		sekundaSP.setEditable(true);
		sekundaSP.setPrefWidth(60);
		casHB.getChildren().addAll(hodinaSP, minutaSP, sekundaSP);
		cas.getChildren().addAll(casLB, casHB);
		celek.add(cas, 1, 2);
		
		VBox vzdalenost = new VBox();
		Label vzadalenostLB = new Label("Vzdalenost");
		vzdalenostTF = new TextField();
		vzdalenost.getChildren().addAll(vzadalenostLB, vzdalenostTF);
		celek.add(vzdalenost, 2, 2);
		
		VBox prevyseni = new VBox();
		Label prevyseniLB = new Label("Prevyseni");
		prevyseniTF = new TextField();
		prevyseniTF.setMaxWidth(150);
		prevyseni.getChildren().addAll(prevyseniLB, prevyseniTF);
		celek.add(prevyseni, 3, 2);
		
		VBox poznamky = new VBox();
		Label poznamkyLB = new Label("Poznamky");
		TextArea poznamkyTA = new TextArea();
		poznamkyTA.setPrefHeight(100);
		poznamkyTA.setPrefWidth(200);
		poznamky.getChildren().addAll(poznamkyLB, poznamkyTA);
		celek.add(poznamky, 1, 3);
		
		HBox tlacitka = new HBox();
		Button ulozBT = new Button("Uloz");
		ulozBT.setOnAction(e -> uloz(e));
		ulozBT.setPrefWidth(100);
		Button zrusBT = new Button("Zrus");
		zrusBT.setOnAction(e -> this.close());
		zrusBT.setPrefWidth(100);
		tlacitka.getChildren().addAll(ulozBT, zrusBT);
		tlacitka.setSpacing(5);
		tlacitka.setAlignment(Pos.BOTTOM_RIGHT);
		celek.add(tlacitka, 3, 3);
		
		return celek;
	}

	private void uloz(ActionEvent e) {
		String casSTR = String.format("%02d:%02d:%02d", hodinaSP.getValue(), minutaSP.getValue(), sekundaSP.getValue());
		//int cas = hodinaSP.getValue() * 60 * 60 + minutaSP.getValue() * 60 + sekundaSP.getValue();
		LocalTime cas = LocalTime.parse(casSTR);
		if (datumDP.getValue() == null) {
			Main.zprava.showErrorDialog("Neni vyplneny datum!");
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
		if (vzdalenostTF.getText().matches(".*[a-z].*")||  vzdalenostTF.getText().matches(".*[A-Z].*") || vzdalenostTF.getText().matches(".*\\p{Punct}.*")) {
			Main.zprava.showErrorDialog("V textoven policku pro rychlost je znak ktery neni cislo!");
			return;
		}
		Main.model.aktivity.add(new Aktivita(nazevTF.getText(), Double.parseDouble(vzdalenostTF.getText()), cas, typCB.getValue(), datumDP.getValue()));
		this.close();
	}
}
