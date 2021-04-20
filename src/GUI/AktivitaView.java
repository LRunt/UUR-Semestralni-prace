package GUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		Label datumLB = new Label(aktivita.getDatum().format(formatter));
		nazevDatum.getChildren().addAll(nazevLB, datumLB);
		obrazek.getChildren().addAll(ikona, nazevDatum);
		
		return obrazek;
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
