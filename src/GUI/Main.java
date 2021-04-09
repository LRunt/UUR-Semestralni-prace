package GUI;
import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.Aktivita;
import model.DataModel;
import model.TypAktivity;
import utils.Ctenar;
import utils.Message;

/**
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public class Main extends Application{
	private static final String OBSAH_TITULKU = "Semestralni prace - Lukas Runt - A20B0226P";
	private TableView<Aktivita> tabulka;
	private ManualniVstup okno = new ManualniVstup();
	public static DataModel model = new DataModel();
	private Message zprava = new Message();
	private Stage soubor;
	private Ctenar ctenar = new Ctenar();

	/**
	 * Vstupni bod programu
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(OBSAH_TITULKU);
		
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(500);
		primaryStage.setScene(getScene());
		
		primaryStage.show();
	}

	private Scene getScene() {
		Scene scene = new Scene(getRoot());
		return scene;
	}

	private Parent getRoot() {
		BorderPane rootBorderPane = new BorderPane();
		
		rootBorderPane.setTop(getMenu());
		rootBorderPane.setCenter(getTabulka());
		rootBorderPane.setLeft(getTreeView());
		
		return rootBorderPane;
	}

	private Node getMenu() {
		MenuBar menu = new MenuBar();
		
		Menu soubor = new Menu("Nova aktivita");
		MenuItem manual = new MenuItem("Manualne");
		manual.setOnAction(e -> okno.showDialog());
		MenuItem file = new MenuItem("Souborem");
		file.setOnAction(e -> nactiNovaData(e));
		soubor.getItems().addAll(manual, file);
		
		Menu statistiky = new Menu("Statistika");
		
		Menu zavod = new Menu("Zavody");
		
		Menu help = new Menu("O programu");
		
		menu.getMenus().addAll(soubor, statistiky, zavod, help);
		return menu;
	}
	
	private void nactiNovaData(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		
		File file = chooser.showOpenDialog(soubor);
		
		if (file != null) {
			readSoubor(file);
		}
		else {
		}
	}

	private Node getTreeView() {
		
		return null;
	}
	
	private Node getTabulka() {
		tabulka = new TableView<Aktivita>(model.aktivity.get());
		tabulka.setEditable(false);
		
		TableColumn<Aktivita, String> nazevColumn = new TableColumn<>("Nazev");
		nazevColumn.setCellValueFactory(new PropertyValueFactory<Aktivita, String>("nazev"));
		nazevColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		TableColumn<Aktivita, Double> vzdalenostColumn = new TableColumn<>("Vzdalenost");
		vzdalenostColumn.setCellValueFactory(new PropertyValueFactory<>("vzdalenost"));
		vzdalenostColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		TableColumn<Aktivita, TypAktivity> typColumn = new TableColumn<>("Typ aktivity");
		typColumn.setCellValueFactory(new PropertyValueFactory<>("typ"));
		typColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TypAktivity.values()));
		
		tabulka.getColumns().addAll(nazevColumn, vzdalenostColumn, typColumn);
		tabulka.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		return tabulka;
	}
	
	public void readSoubor(File file) {
		if(file.getPath().contains("tcx")) {
			ctenar.read(file);
		}
		else {
			zprava.showErrorDialog("Zadan nespravny typ souboru.\nVlozte prosim soubor .tcx\n\nVlozen soubor: " + file);
		}
	}
}
