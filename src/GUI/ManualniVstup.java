package GUI;
import javafx.event.ActionEvent;
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
	private TextField hodinaTF;
	private TextField minutaTF;
	private TextField sekundaTF;
	private TextField vzdalenostTF;
	
	public void showDialog() {
		this.setTitle("Nova aktivita - Lukas Runt");
		this.setMinWidth(500);
		this.setMinHeight(300);
		
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
		DatePicker datumDP = new DatePicker();
		datum.getChildren().addAll(datumLB, datumDP);
		celek.add(datum, 3, 1);
		
		VBox cas = new VBox();
		Label casLB = new Label("Cas");
		HBox casHB = new HBox();
		hodinaTF = new TextField();
		hodinaTF.setPrefWidth(50);
		minutaTF = new TextField();
		minutaTF.setPrefWidth(50);
		sekundaTF = new TextField();
		sekundaTF.setPrefWidth(50);
		casHB.getChildren().addAll(hodinaTF, minutaTF, sekundaTF);
		cas.getChildren().addAll(casLB, casHB);
		celek.add(cas, 1, 2);
		
		VBox vzdalenost = new VBox();
		Label vzadalenostLB = new Label("Vzdalenost");
		vzdalenostTF = new TextField();
		vzdalenost.getChildren().addAll(vzadalenostLB, vzdalenostTF);
		celek.add(vzdalenost, 2, 2);
		
		VBox kalorie = new VBox();
		Label kalorieLB = new Label("Kalorie");
		TextField kalorieTF = new TextField();
		kalorie.getChildren().addAll(kalorieLB, kalorieTF);
		celek.add(kalorie, 3, 2);
		
		VBox poznamky = new VBox();
		Label poznamkyLB = new Label("Poznamky");
		TextArea poznamkyTA = new TextArea();
		poznamkyTA.setPrefHeight(100);
		poznamkyTA.setPrefWidth(100);
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
		int cas = Integer.parseInt(hodinaTF.getText()) * 60 * 60 + Integer.parseInt(minutaTF.getText()) * 60 + Integer.parseInt(sekundaTF.getText());
		Main.model.aktivity.add(new Aktivita(nazevTF.getText(), Double.parseDouble(vzdalenostTF.getText()), cas, typCB.getValue()));
		this.close();
	}
}
