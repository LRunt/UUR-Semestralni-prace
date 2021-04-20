package GUI;
import java.io.File;
import java.time.LocalDate;
import bunky.FormattedDateTableCell;
import bunky.FormattedDoubleTableCell;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Aktivita;
import model.DataModel;
import model.TypAktivity;
import utils.CasStringConverter;
import utils.Ctenar;
import utils.FormattedButtonTableCell;
import utils.Message;

/**
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public class Main extends Application{
	private static final String OBSAH_TITULKU = "Strava - Semestralni prace - Lukas Runt - A20B0226P";
	private TableView<Aktivita> tabulka;
	private ManualniVstup okno = new ManualniVstup();
	private AktivitaView zobrazeniAktivity = new AktivitaView();
	public static DataModel model = new DataModel();
	public static Message zprava = new Message();
	private Stage soubor;
	private Ctenar ctenar = new Ctenar();
	
	/**
	 * Inicializace po spusteni
	 */
	public void init() {
		System.out.println("Inicializace");
		model.initializeModel();
	}
	
	/**
	 * Pred vypnutim aplikace se ulozi data
	 */
	@Override
	public void stop() throws Exception {
		model.saveData();
		super.stop();
	}

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
		
		primaryStage.getIcons().add(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Lidl-Logo.svg/1024px-Lidl-Logo.svg.png"));
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(600);
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
		Label aboutLB = new Label("Info");
		aboutLB.setOnMouseClicked(e -> zobrazInfo(e));
		Menu about = new Menu("", aboutLB);
		
		menu.getMenus().addAll(soubor, statistiky, zavod, about);
		return menu;
	}
	
	/**
	 * Metoda zobrazi info o programu a jeho autorovi s kontaktem, pro nahlaseni pripadnych chyb
	 * @param e kliknuti mysi
	 */
	private void zobrazInfo(MouseEvent e) {
		zprava.showInfoDialog("Program treninkovy denik\n\nAutor:\tLukas Runt\n\nVerze:\t1.0\n\nKontakt:\tlrunt@students.zcu.cz\n\nProgram je zatim v alfa verzi. \nPokud objevite chybu budu rad kdyz me kontaktujete.\nPokusim se ji opravit.");
	}

	/**
	 * Metoda pro nacitani souboru tcx
	 * @param e event
	 */
	private void nactiNovaData(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("Name","*.tcx"));
		File file = chooser.showOpenDialog(soubor);
		if (file != null) {
			ctenar.read(file);
		}
		else {
			zprava.showErrorDialog("Soubor je null");
		}
	}

	private Node getTreeView() {
		
		return null;
	}
	
	private Node getTabulka() {
		tabulka = new TableView<Aktivita>(model.aktivity.get());
		tabulka.setEditable(true);
		
		TableColumn<Aktivita, LocalDate> datumColumn = new TableColumn<>("Datum");
		datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
		datumColumn.setCellFactory(cellData -> new FormattedDateTableCell<>());
		
		TableColumn<Aktivita, String> nazevColumn = new TableColumn<>("Nazev");
		nazevColumn.setCellValueFactory(new PropertyValueFactory<Aktivita, String>("nazev"));
		nazevColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		TableColumn<Aktivita, Double> vzdalenostColumn = new TableColumn<>("Vzdalenost");
		vzdalenostColumn.setCellValueFactory(new PropertyValueFactory<>("vzdalenost"));
		vzdalenostColumn.setCellFactory(cellData -> new FormattedDoubleTableCell<>());
		
		TableColumn<Aktivita, TypAktivity> typColumn = new TableColumn<>("Typ aktivity");
		typColumn.setCellValueFactory(new PropertyValueFactory<>("typ"));
		typColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TypAktivity.values()));
		
		TableColumn<Aktivita, Integer> casColumn = new TableColumn<>("Cas");
		casColumn.setCellValueFactory(new PropertyValueFactory<>("cas"));
		casColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CasStringConverter()));
		//---------------------------prumerna-rychlost-----------------------------------------
		TableColumn<Aktivita, Double> rychlostColumn = new TableColumn<>("Prumerna rychlost");
		rychlostColumn.setCellValueFactory(cellData -> cellData.getValue().prumernaRychlostProperty());
		
		ContextMenu cm = new ContextMenu();
		MenuItem zobrazMI = new MenuItem("Zobraz");
		MenuItem smazMI = new MenuItem("Smaz");
		cm.getItems().add(zobrazMI);
		zobrazMI.setOnAction(e -> zobraz(e));
		cm.getItems().add(smazMI);
		smazMI.setOnAction(e -> smaz(e));
			
		tabulka.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					cm.show(tabulka, event.getScreenX(), event.getScreenY());
				}
			};
		});
	
		/*
		TableColumn<Aktivita, Integer> zobrazColumn = new TableColumn<>("Zobraz");
		zobrazColumn.setCellValueFactory(new PropertyValueFactory<>("Tlacitko"));
		zobrazColumn.setCellFactory(cellData -> new FormattedButtonTableCell<>());*/
		
		tabulka.getColumns().addAll(datumColumn, typColumn, casColumn, vzdalenostColumn, rychlostColumn);
		tabulka.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		return tabulka;
	}

	private void zobraz(ActionEvent e) {
		int index = tabulka.getSelectionModel().getSelectedIndex();
		if(index < 0) {
			zprava.showErrorDialog("Neni vybran prvek k zobrazeni.");
		}
		else {
			zobrazeniAktivity.showDialog(model.aktivity.get(index));
		}
	}

	private void smaz(ActionEvent e) {
		int index = tabulka.getSelectionModel().getSelectedIndex();
		if(index < 0) {
			zprava.showErrorDialog("Neni vybran prvek ke smazani!");
		}
		else {
			if(zprava.showVyberDialog("Opravdu chcete smazat tuto aktivitu?")){
				model.aktivity.remove(index);
			}
			tabulka.getSelectionModel().clearSelection();
		}
	}
}
