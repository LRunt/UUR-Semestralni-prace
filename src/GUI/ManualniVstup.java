package GUI;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
	private TextField casTF;
	private TextField vzdalenostTF;
	private Label vzdalenostLB;
	private DatePicker datumDP;
	private TextField prevyseniTF;
	private Label prevyseniLB;
	private TextArea poznamkyTA;
	private Paint cervena = Paint.valueOf("fc0356");
	private Paint bila = Paint.valueOf("ffffff");
	private BackgroundFill spatne = new BackgroundFill(cervena, CornerRadii.EMPTY, Insets.EMPTY);
	
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
		rootBorderPane.setBottom(getOvladani());
		
		return rootBorderPane;
	}

	public Node getOvladani() {
		HBox tlacitka = new HBox();
			
		Button ulozBT = new Button("Uloz");
		ulozBT.setOnAction(e -> uloz(e));
		ulozBT.setPrefWidth(80);
			
		Button zavriBT = new Button("Zavri");
		zavriBT.setOnAction(e -> close());
		zavriBT.setPrefWidth(80);
			
		tlacitka.getChildren().addAll(ulozBT, zavriBT);
		tlacitka.setAlignment(Pos.CENTER_RIGHT);
		tlacitka.setPadding(new Insets(5));
		tlacitka.setSpacing(5);
			
		return tlacitka;
	}

	public Node getGUI() {
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
		typCB.setOnAction(e -> visibilita(e));
		typ.getChildren().addAll(typLB, typCB);
		celek.add(typ, 2, 1);
		
		VBox datum = new VBox();
		Label datumLB = new Label("Datum");
		datumDP = new DatePicker();
		datum.getChildren().addAll(datumLB, datumDP);
		celek.add(datum, 3, 1);
		
		VBox cas = new VBox();
		Label casLB = new Label("Cas");
		casTF = new TextField("01:00:00");
		cas.getChildren().addAll(casLB, casTF);
		celek.add(cas, 1, 2);
		
		VBox vzdalenost = new VBox();
		vzdalenostLB = new Label("Vzdalenost");
		vzdalenostTF = new TextField();
		vzdalenost.getChildren().addAll(vzdalenostLB, vzdalenostTF);
		celek.add(vzdalenost, 2, 2);
		
		VBox prevyseni = new VBox();
		prevyseniLB = new Label("Prevyseni");
		prevyseniTF = new TextField();
		prevyseniTF.setMaxWidth(150);
		prevyseni.getChildren().addAll(prevyseniLB, prevyseniTF);
		celek.add(prevyseni, 3, 2);
		
		VBox poznamky = new VBox();
		Label poznamkyLB = new Label("Poznamky");
		poznamkyTA = new TextArea();
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

	private void visibilita(ActionEvent e) {
		TypAktivity vyber = typCB.getValue();
		if(vyber.equals(TypAktivity.POSILOVNA) || vyber.equals(TypAktivity.STRETCHING) || vyber.equals(TypAktivity.AKTIVITA)) {
			vzdalenostTF.setVisible(false);
			vzdalenostLB.setVisible(false);
			prevyseniTF.setVisible(false);
			prevyseniLB.setVisible(false);
		} 
		if(vyber.equals(TypAktivity.CYKLISTIKA) || vyber.equals(TypAktivity.BEH) || vyber.equals(TypAktivity.BEZKY) || vyber.equals(TypAktivity.CHUZE) || vyber.equals(TypAktivity.PLAVANI) || vyber.equals(TypAktivity.BRUSLE)) {
			vzdalenostTF.setVisible(true);
			vzdalenostLB.setVisible(true);
			prevyseniTF.setVisible(true);
			prevyseniLB.setVisible(true);
		}
	}

	private void uloz(ActionEvent e) {
		LocalDate date;
		LocalTime cas = null;
		double vzdalenost = 0;
		try {
			cas = LocalTime.parse(casTF.getText());
		} catch(Exception ex) {
			casTF.setBackground(new Background(spatne));
			casTF.setStyle("-fx-border-color: black;");
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
			vzdalenost = Double.parseDouble(vzdalenostTF.getText());
		} catch(IllegalArgumentException ex) {
			if(vzdalenostTF.getText().equals("") && vzdalenostTF.isVisible() == false) {
				vzdalenost = 0;
			} else {
				Main.zprava.showErrorDialog("Spatne zadana vzdalenost");
				return;
			}
		}
		Main.model.aktivity.add(new Aktivita(nazevTF.getText(), vzdalenost, cas, typCB.getValue(), datumDP.getValue()));
		Main.createStartItems();
		this.close();
	}
}
