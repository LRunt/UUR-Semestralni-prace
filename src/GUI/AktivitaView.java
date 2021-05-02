package GUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
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

	public void showDialog(Aktivita aktivita) {
		this.aktivita = aktivita;
		this.setTitle(aktivita.getNazev());
		this.setWidth(500);
		this.setHeight(300);
		
		this.setScene(new Scene(getRoot()));
		
		this.show();
	}

	private Parent getRoot() {
		BorderPane rootBorderPane = new BorderPane();
		
		try {
			rootBorderPane.setCenter(getGUI());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return rootBorderPane;
	}

	private Node getGUI() throws FileNotFoundException {
		HBox obrazek = new HBox();
		InputStream stream = new FileInputStream(getCesta(aktivita.getTyp()));
		Image image = new Image(stream);
		ImageView ikona = new ImageView();
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
		avgRychlostC.textProperty().bind(aktivita.prumernaRychlostProperty().asString());;
		Label avgRychlostLB = new Label("Avg. rychlost");
		avgRychlost.getChildren().addAll(avgRychlostC, avgRychlostLB);
		pravo.add(avgRychlost, 1, 2);
		
		if(aktivita.getPrumernyTep() != 0) {
			VBox avgTep = new VBox();
			Label avgTepC = new Label();
			Label avgTepLB = new Label("Avg. tep");
			avgTep.getChildren().addAll(avgTepC, avgTepLB);
			pravo.add(avgTep, 2, 2);
		}
		
		
		HBox celek = new HBox();
		celek.getChildren().addAll(obrazek, pravo);
		celek.setSpacing(20);
		
		return celek;
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
