package GUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.Observable;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Aktivita;
import model.TypAktivity;

/**
 * Uzivatelske rozhrani vizualizace aktivity
 * @author Lukas Runt
 * @version 1.0 (2021-04-19)
 */
public class AktivitaView extends Stage{
	
	private Aktivita aktivita;
	public static String titulek;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private Image image;
	private ImageView ikona;
	private TextField nazevTF;
	private ComboBox<TypAktivity> typCB;
	private DatePicker datumDP;
	private TextField casTF;
	private TextField vzdalenostTF;
	private TextField prevyseniTF;
	private TextArea poznamkyTA;
	BorderPane rootBorderPane;

	public void showDialog(Aktivita aktivita) {
		this.aktivita = aktivita;
		this.setTitle(aktivita.getNazev());
		this.setWidth(500);
		this.setHeight(300);
		
		this.setScene(new Scene(getRoot()));
		
		this.show();
	}

	private Parent getRoot() {
		rootBorderPane = new BorderPane();
		
		rootBorderPane.setCenter(getGUI());
		rootBorderPane.setBottom(getOvladani1());
		
		return rootBorderPane;
	}

	private Node getOvladani1() {
		HBox tlacitka = new HBox();
		
		Button upravBT = new Button("Uprav");
		upravBT.setOnAction(e -> uprav(e));
		upravBT.setPrefWidth(80);
		
		Button zavriBT = new Button("Zavri");
		zavriBT.setOnAction(e -> close());
		zavriBT.setPrefWidth(80);
		
		tlacitka.getChildren().addAll(upravBT, zavriBT);
		tlacitka.setAlignment(Pos.CENTER_RIGHT);
		tlacitka.setPadding(new Insets(5));
		tlacitka.setSpacing(5);
		
		return tlacitka;
	}
	
	private Node getOvladani2() {
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
	
	private Node getGUI() {
		HBox obrazek = new HBox();
		InputStream stream = null;
		try {
			stream = new FileInputStream(getCesta(aktivita.getTyp()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		image = new Image(stream);
		ikona = new ImageView();
		aktivita.typProperty().addListener(a -> {
			try {
				zmenObrazek(a);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		ikona.setImage(image);
		ikona.setFitWidth(50);
		ikona.setFitHeight(50);
		VBox nazevDatum = new VBox();
		Label nazevLB = new Label(aktivita.getNazev());
		Font nazevFN = new Font(20);
		nazevLB.setFont(nazevFN);
		Label datumLB = new Label(aktivita.getDatum().format(formatter));
		nazevDatum.getChildren().addAll(nazevLB, datumLB);
		obrazek.getChildren().addAll(ikona, nazevDatum);
		obrazek.setSpacing(10);
		
		GridPane pravo = new GridPane();
		
		pravo.setHgap(10);
		pravo.setVgap(10);
		Font cisla = new Font(15);
		
		VBox vzdalenost = new VBox();
		Label vzdalenostC = new Label();
		vzdalenostC.textProperty().bind(aktivita.vzdalenostProperty().asString());
		vzdalenostC.setFont(cisla);
		Label vzdalenostLB = new Label("Vzdalenost");
		vzdalenost.getChildren().addAll(vzdalenostC, vzdalenostLB);
		pravo.add(vzdalenost, 1, 1);
		
		VBox cas = new VBox();
		Label casC = new Label();
		casC.textProperty().bind(aktivita.casProperty().asString());
		casC.setFont(cisla);
		Label casLB = new Label("Cas");
		cas.getChildren().addAll(casC, casLB);
		pravo.add(cas, 2, 1);
		
		VBox avgRychlost = new VBox();
		Label avgRychlostC = new Label();
		avgRychlostC.setFont(cisla);
		avgRychlostC.textProperty().bind(aktivita.prumernaRychlostProperty().asString());
		Label avgRychlostLB = new Label("Avg. rychlost");
		avgRychlost.getChildren().addAll(avgRychlostC, avgRychlostLB);
		pravo.add(avgRychlost, 1, 2);
		
		if(aktivita.getPrumernyTep() != 0) {
			VBox avgTep = new VBox();
			Label avgTepC = new Label();
			avgTepC.setFont(cisla);
			avgTepC.textProperty().bind(aktivita.prumernyTepProperty().asString());
			Label avgTepLB = new Label("Avg. tep");
			avgTep.getChildren().addAll(avgTepC, avgTepLB);
			pravo.add(avgTep, 2, 2);
		}
		

		VBox prevyseni = new VBox();
		Label prevyseniC = new Label();
		prevyseniC.setFont(cisla);
		prevyseniC.textProperty().bind(aktivita.prevyseniPropety().asString());
		//prevyseniC.textProperty().bind(aktivita.prumernaRychlostProperty().asString());
		Label prevyseniLB = new Label("Prevyseni");
		prevyseni.getChildren().addAll(prevyseniC, prevyseniLB);
		pravo.add(prevyseni, 3, 1);
		
		if(aktivita.getKalorie() != 0) {
			VBox kalorie = new VBox();
			Label kalorieC = new Label();
			kalorieC.setFont(cisla);
			kalorieC.textProperty().bind(aktivita.kalorieProperty().asString());
			Label kalorieLB = new Label("Kalorie");
			kalorie.getChildren().addAll(kalorieC, kalorieLB);
			pravo.add(kalorie, 3, 2);
		}
		
		if(aktivita.getMaxTep() != 0) {
			VBox maxTep = new VBox();
			Label maxTepC = new Label();
			maxTepC.setFont(cisla);
			maxTepC.textProperty().bind(aktivita.maxTepProperty().asString());
			Label maxTepLB = new Label("Max. tep");
			maxTep.getChildren().addAll(maxTepC, maxTepLB);
			pravo.add(maxTep, 2, 3);
		}
		
		if(aktivita.getMaxRychlost() != 0) {
			VBox maxRychlost = new VBox();
			Label maxRychlostC = new Label();
			maxRychlostC.setFont(cisla);
			maxRychlostC.textProperty().bind(aktivita.maxRychlostProperty().asString());
			Label maxRychlostLB = new Label("Max. rychlost");
			maxRychlost.getChildren().addAll(maxRychlostC, maxRychlostLB);
			pravo.add(maxRychlost, 1, 3);
		}
		
		/*Button upravBT = new Button("Uprav");
		pravo.add(upravBT, 2, 4);
		upravBT.setOnAction(e -> uprav(e));
		
		Button zavriBT = new Button("Zavri");
		pravo.add(zavriBT, 3, 4);
		zavriBT.setOnAction(e -> close());
		//pravo.setGridLinesVisible(true);*/
		
		
		HBox gui = new HBox();
		gui.getChildren().addAll(obrazek, pravo);
		gui.setSpacing(20);
		
		return gui;
	}
	
	private void uprav(ActionEvent e) {
		rootBorderPane.setCenter(getUpraveni());
		rootBorderPane.setBottom(getOvladani2());
	}

	private Node getUpraveni() {
		GridPane celek = new GridPane();
		
		celek.setHgap(10);
		celek.setVgap(10);
		
		VBox nazev = new VBox();
		Label nazevLB = new Label("Nazev aktivity");
		nazevTF = new TextField();
		nazevTF.setText(aktivita.getNazev());
		nazev.getChildren().addAll(nazevLB, nazevTF);
		celek.add(nazev, 1, 1);
		
		VBox typ = new VBox();
		Label typLB = new Label("Typ aktivity");
		typCB = new ComboBox<TypAktivity>();
		typCB.getItems().setAll(TypAktivity.values());
		typCB.setValue(aktivita.getTyp());
		typ.getChildren().addAll(typLB, typCB);
		celek.add(typ, 2, 1);
		
		VBox datum = new VBox();
		Label datumLB = new Label("Datum");
		datumDP = new DatePicker();
		datumDP.setValue(aktivita.getDatum());
		datum.getChildren().addAll(datumLB, datumDP);
		celek.add(datum, 3, 1);
		
		VBox cas = new VBox();
		Label casLB = new Label("Cas");
		casTF = new TextField();
		casTF.setText(aktivita.getCas().toString());
		cas.getChildren().addAll(casLB, casTF);
		celek.add(cas, 1, 2);
		
		VBox vzdalenost = new VBox();
		Label vzdalenostLB = new Label("Vzdalenost");
		vzdalenostTF = new TextField();
		vzdalenostTF.setText(aktivita.getVzdalenost() + "");
		vzdalenost.getChildren().addAll(vzdalenostLB, vzdalenostTF);
		celek.add(vzdalenost, 2, 2);
		
		VBox prevyseni = new VBox();
		Label prevyseniLB = new Label("Prevyseni");
		prevyseniTF = new TextField();
		prevyseniTF.setText(aktivita.getPrevyseni() + "");
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
		
		return celek;
	}

	private void uloz(ActionEvent e) {
		aktivita.setNazev(nazevTF.getText());
		aktivita.setTyp(typCB.getValue());
		aktivita.setDatum(datumDP.getValue());
		aktivita.setCas(LocalTime.parse(casTF.getText()));
		aktivita.setVzdalenost(Double.parseDouble(vzdalenostTF.getText()));
		aktivita.setPrevyseni(Double.parseDouble(prevyseniTF.getText()));
		
		rootBorderPane.setCenter(getGUI());
		rootBorderPane.setBottom(getOvladani1());
	}

	private void zmenObrazek(Observable a) throws FileNotFoundException {
		InputStream stream = new FileInputStream(getCesta(aktivita.getTyp()));
		image = new Image(stream);
		ikona.setImage(image);
	}

	private String getCesta(TypAktivity typ) {
		if(typ == TypAktivity.CYKLISTIKA) {
			return "data\\kolo.png";
		}
		if(typ == TypAktivity.BEH) {
			return "data\\running.png";
		}
		if(typ == TypAktivity.CHUZE) {
			return "data\\adventurer.png";
		}
		return "data\\activity.png";
	}
	
}
