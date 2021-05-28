package GUI;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

import bunky.FormattedDateTableCell;
import bunky.FormattedDoubleTableCell;
import bunky.FormattedPositionTableCell;
import bunky.FormattedTimeTableCell;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Aktivita;
import model.DataModel;
import model.TypAktivity;
import model.Zavod;
import utils.Ctenar;
import utils.Message;

/**
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public class Main extends Application{
	private static final String OBSAH_TITULKU = "Semestralni prace - Lukas Runt - A20B0226P";
	private TableView<Aktivita> tabulka;
	private TableView<Zavod> tabulkaZ;
	private static TreeView<String> treeView;
	private GuiManual okno = new GuiManual();
	private AktivitaView zobrazeniAktivity = new AktivitaView();
	public static DataModel model = new DataModel();
	public static Message zprava = new Message();
	private Stage soubor;
	private Ctenar ctenar = new Ctenar();
	private Stage myStage = new Stage();
	private DatePicker datumDP;
	private TextField nazevTF;
	private TextField umisteniTF;
	private String[] zkratkyMesicu = {"led", "uno", "bre", "dub", "kve", "cvn", "cvc", "srp", "zar", "rij", "lis", "pro"};
	
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
		System.out.println("Saving data");
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
		this.myStage = primaryStage; 
		myStage.setTitle(OBSAH_TITULKU);
		
		//myStage.getIcons().add(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Lidl-Logo.svg/1024px-Lidl-Logo.svg.png"));
		myStage.setMinHeight(400);
		myStage.setMinWidth(600);
		//myStage.setHeight(600);
		//myStage.setWidth(1000);
		myStage.setScene(getScene());
		
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {Platform.exit();});
	}

	private Scene getScene() {
		Scene scene = new Scene(getRoot());
		return scene;
	}

	private Parent getRoot() {
		BorderPane rootBorderPane = new BorderPane();
		
		rootBorderPane.setTop(getMenu());
		rootBorderPane.setCenter(getSplitPane());
		
		rootBorderPane.setPrefSize(myStage.getWidth() - 15, myStage.getHeight() - 38);
		
		return rootBorderPane;
	}

	private Node getSplitPane() {
		SplitPane splitPane = new SplitPane();
		
		splitPane.getItems().addAll(getTreeView(), getTabulka());
		//splitPane.setPadding(new Insets(5));
		
		return splitPane;
	}

	private Node getMenu() {
		MenuBar menu = new MenuBar();
		
		Menu soubor = new Menu("Nova aktivita");
		MenuItem manual = new MenuItem("Manualne");
		manual.setOnAction(e -> {
			try {
				okno.showDialog();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		MenuItem file = new MenuItem("Souborem");
		file.setOnAction(e -> nactiNovaData(e));
		soubor.getItems().addAll(manual, file);
		
		Label homeLB = new Label("Home");
		homeLB.setOnMouseClicked(e -> prepniNaDomObrazovku(e));
		Menu home = new Menu("", homeLB);
		
		Label statistikyLB = new Label("Statistiky");
		statistikyLB.setOnMouseClicked(e -> prepniNaStatistiky(e));
		Menu statistiky = new Menu("", statistikyLB);
		
		Label zavodLB = new Label("Zavody");
		zavodLB.setOnMouseClicked(e -> prepniNaZavod(e));
		Menu zavod = new Menu("", zavodLB);
		Label aboutLB = new Label("Info");
		aboutLB.setOnMouseClicked(e -> zobrazInfo(e));
		Menu about = new Menu("", aboutLB);
		
		menu.getMenus().addAll(soubor, home, statistiky, zavod, about);
		return menu;
	}

	/**
	 * Prepinani na statistiky
	 * @param e kliknuti na menuItem - statistiky
	 */
	private void prepniNaStatistiky(MouseEvent e) {
		myStage.setScene(statisikyScene());
	}

	/**
	 * Prepinani na domovskou obrazovku
	 * @param e kliknuti na menuItem - home
	 */
	private void prepniNaDomObrazovku(MouseEvent e) {
		myStage.setScene(getScene());
	}

	/**
	 * Prarinani na tabulku se zavody
	 * @param e kliknuti na menuItem - zavody
	 */
	private void prepniNaZavod(MouseEvent e) {
		myStage.setScene(zavodScene());
	}
	
	/**
	 * Scena statistik
	 * @return scena satatistik
	 */
	private Scene statisikyScene() {
		Scene scene = new Scene(getStatistikyRoot());
		return scene;
	}
	
	/**
	 * Scena se zavody
	 * @return scena zavodu
	 */
	private Scene zavodScene() {
		Scene scene = new Scene(getZavodRoot());
		return scene;
	}
	
	/**
	 * BorderPane statistik
	 * @return borderPane statistik
	 */
	private Parent getStatistikyRoot() {
		BorderPane rootBorderPane = new BorderPane();
		
		rootBorderPane.setTop(getMenu());
		rootBorderPane.setCenter(getGraf());
		
		rootBorderPane.setPrefSize(myStage.getWidth() - 15, myStage.getHeight() - 38);
		
		return rootBorderPane;
	}
	
	/**
	 * Metoda se stara o vytvoreni grafu
	 * @return graf
	 */
	private Node getGraf() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Mesice");
		yAxis.setLabel("Kilometry");
		LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		lineChart.setTitle("Statistiky");
		
		String[] roky = model.getYears();
		for(int i = 0; i < roky.length; i++) {
			final int p = i;
			Series<String, Number> series = new XYChart.Series<>();
			series.setName(roky[i]);
			for(int j = 0; j < 12; j++) {
				final int m = j + 1; 
				Double sum = model.aktivity.stream().filter(a -> a.getDatum().getYear() == Integer.parseInt(roky[p]))
						  .filter(a -> a.getDatum().getMonthValue() == m)
						  .map(a -> a.getVzdalenost())
						  .reduce(0.0, Double::sum);
				series.getData().add(new XYChart.Data<String, Number>(zkratkyMesicu[j], sum));
			}
			lineChart.getData().add(series);
		}
		return lineChart;
	}

	/**
	 * BorderPane zavodu
	 * @return borderPane zavodu
	 */
	private Parent getZavodRoot() {
		BorderPane rootBorderPane = new BorderPane();
		
		rootBorderPane.setTop(getMenu());
		rootBorderPane.setCenter(getTabulka2());
		rootBorderPane.setBottom(getOvladani());
		
		rootBorderPane.setPrefSize(myStage.getWidth() - 15, myStage.getHeight() - 38);
		
		return rootBorderPane;
	}

	/**
	 * Ovladani zavoud, pridavani zavodu
	 * @return ovladani
	 */
	private Node getOvladani() {
		GridPane ovladani = new GridPane();
		ovladani.setHgap(10);
		ovladani.setVgap(10);
		
		VBox nazev = new VBox();
		Label nazevLB = new Label("Nazev");
		nazevTF = new TextField(); 
		nazev.getChildren().addAll(nazevLB, nazevTF);
		ovladani.add(nazev, 2, 1);
		
		VBox datum = new VBox();
		Label datumLB = new Label("Datum");
		datumDP = new DatePicker();
		datum.getChildren().addAll(datumLB, datumDP);
		ovladani.add(datum, 1, 1);
		
		VBox umisteni = new VBox();
		Label umisteniLB = new Label("Umisteni");
		umisteniTF = new TextField();
		umisteni.getChildren().addAll(umisteniLB, umisteniTF);
		ovladani.add(umisteni, 3, 1);
		
		Button pridejBT = new Button("Pridej");
		pridejBT.setOnAction(e -> pridejZavod(e));
		pridejBT.setPrefWidth(100);
		ovladani.add(pridejBT, 4, 1);
		
		ovladani.setPadding(new Insets(5));
		ovladani.setAlignment(Pos.CENTER);
		return ovladani;
	}

	/**
	 * Pridani dzavodu do tabulky
	 * @param e kliknuti na tlacitko pridej
	 */
	private void pridejZavod(ActionEvent e) {
		if(datumDP.getValue() == null || nazevTF.getText() == null || umisteniTF.getText() == null) {
			zprava.showErrorDialog("Nejsou vyplneny vsechny udaje pro vytvoreni!");
			return;
		}
		if(umisteniTF.getText().matches(".*[a-z].*") ||  umisteniTF.getText().matches(".*[A-Z].*") || umisteniTF.getText().matches(".*\\p{Punct}.*")){
			if(umisteniTF.getText().contains("-")) {} else {
				zprava.showErrorDialog("Umisteni musi byt cislo!\nPro prazdne pole  = 0\nPro DNF = -1\nPro DSQ = -2");
				return;
			}
		}
		model.zavody.add(new Zavod(datumDP.getValue(), nazevTF.getText(), Integer.parseInt(umisteniTF.getText().trim())));
		datumDP.setValue(null);
		nazevTF.setText(null);
		umisteniTF.setText(null);
	}

	/**
	 * Tabulka zavodu
	 * @return tabulka
	 */
	@SuppressWarnings("unchecked")
	private Node getTabulka2() {
		tabulkaZ = new TableView<Zavod>(model.zavody.get());
		tabulkaZ.setEditable(true);
		//tabulkaZ.setPadding(new Insets(5));
		
		TableColumn<Zavod, String> nazevColumn = new TableColumn<>("Nazev");
		nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
		nazevColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		TableColumn<Zavod, LocalDate> datumColumn = new TableColumn<>("Datum");
		datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
		datumColumn.setCellFactory(cellData -> new FormattedDateTableCell<>());
		
		TableColumn<Zavod, Integer> umisteniColumn = new TableColumn<>("Umisteni");
		umisteniColumn.setCellValueFactory(new PropertyValueFactory<>("umisteni"));
		umisteniColumn.setCellFactory(cellData -> new FormattedPositionTableCell<>(".") );
		
		tabulkaZ.getColumns().addAll(datumColumn, nazevColumn, umisteniColumn);
		tabulkaZ.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		ContextMenu cm = new ContextMenu();
		MenuItem smazMI = new MenuItem("Smaz");
		cm.getItems().add(smazMI);
		smazMI.setOnAction(e -> smaz(e, model.zavody, 2));
		
		tabulkaZ.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					cm.show(tabulkaZ, event.getScreenX(), event.getScreenY());
				}
			};
		});
		
		return tabulkaZ;
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
			//zprava.showErrorDialog("Soubor je null");
			System.out.println("Nebyl vybran soubor.");
		}
	}

	private Node getTreeView() {
		treeView = new TreeView<String>();
		
		treeView.setMaxWidth(150);
		treeView.setPrefWidth(100);
		
		createStartItems();
		
		treeView.getSelectionModel().selectedItemProperty().addListener(this::updateFields);
		model.aktivity.addListener(this::updateFields);
		
		return treeView;
	}
	
	private void updateFields(Observable observable) {
		String item = " ";
		if (treeView.getSelectionModel().getSelectedItem() != null) {
			item = treeView.getSelectionModel().getSelectedItem().getValue().toString();
		}
		model.zobrazeni.clear();
		if(item.equals("All") || item.equals(" ")) {
			model.aktivity.stream().forEach(a -> model.zobrazeni.add(a));
		} else if(item.matches(".*[0-9].*")){
			String zbytecnaPromnenaHehe = item;
			model.aktivity.stream().filter(a -> (a.getDatum().getYear() + "").equals(zbytecnaPromnenaHehe))
			.forEach(a -> model.zobrazeni.add(a));
		} else {
			int mesic = getMesic(item);
			String parent = treeView.getSelectionModel().getSelectedItem().getParent().getValue().toString();
			model.aktivity.stream().filter(a -> a.getDatum().getYear() == Integer.parseInt(parent))
								   .filter(a -> a.getDatum().getMonthValue() == mesic)
								   .forEach(a -> model.zobrazeni.add(a));
		}
	}
	
	/**
	 * Prirazeni cisla mesici
	 * @param mesic mesic v roce
	 * @return cislo mesice
	 */
	private static int getMesic(String mesic) {
		switch (mesic) {
			case "Leden":
				return 1;
			case "Unor":
				return 2;
			case "Brezen":
				return 3;
			case "Duben":
				return 4;
			case "Kveten":
				return 5;
			case "Cerven":
				return 6;
			case "Cervenec":
				return 7;
			case "Srpen":
				return 8;
			case "Zari":
				return 9;
			case "Rijen":
				return 10;
			case "Listopad":
				return 11;
			case "Prosinec":
				return 12;
		}
		return 0;
	}
	
	public static void createStartItems() {
		TreeItem<String> root = new TreeItem<>("All");
		
		String[] roky = model.getYears();
		boolean[][] mesice = model.getMesice();
		for(int i = roky.length - 1; i >= 0; i--) {
			TreeItem<String> datum = new TreeItem<>(roky[i]);
			
			if(mesice[i][0]) {
				TreeItem<String> leden = new TreeItem<>("Leden");
				datum.getChildren().add(leden);
			}
			if(mesice[i][1]) {
				TreeItem<String> unor = new TreeItem<>("Unor");
				datum.getChildren().add(unor);
			}
			if(mesice[i][2]) {
				TreeItem<String> brezen = new TreeItem<>("Brezen");
				datum.getChildren().add(brezen);
			}
			if(mesice[i][3]) {
				TreeItem<String> duben = new TreeItem<>("Duben");
				datum.getChildren().add(duben);
			}
			if(mesice[i][4]) {
				TreeItem<String> kveten = new TreeItem<>("Kveten");
				datum.getChildren().add(kveten);
			}
			if(mesice[i][5]) {
				TreeItem<String> cerven = new TreeItem<>("Cerven");
				datum.getChildren().add(cerven);
			}
			if(mesice[i][6]) {
				TreeItem<String> cervenec = new TreeItem<>("Cervenec");
				datum.getChildren().add(cervenec);
			}
			if(mesice[i][7]) {
				TreeItem<String> srpen = new TreeItem<>("Srpen");
				datum.getChildren().add(srpen);
			}
			if(mesice[i][8]) {
				TreeItem<String> zari = new TreeItem<>("Zari");
				datum.getChildren().add(zari);
			}
			if(mesice[i][9]) {
				TreeItem<String> rijen = new TreeItem<>("Rijen");
				datum.getChildren().add(rijen);
			}
			if(mesice[i][10]) {
				TreeItem<String> listopad = new TreeItem<>("Listopad");
				datum.getChildren().add(listopad);
			}
			if(mesice[i][11]) {
				TreeItem<String> prosinec = new TreeItem<>("Prosinec");
				datum.getChildren().add(prosinec);
			}
			//datum.getChildren().addAll(leden, unor, brezen, duben, kveten, cerven, cervenec, srpen, zari, rijen, listopad, prosinec);
			
			root.getChildren().add(datum);
		}
		treeView.setRoot(root);
	}

	@SuppressWarnings("unchecked")
	private Node getTabulka() {
		tabulka = new TableView<Aktivita>(model.zobrazeni.get());
		tabulka.setEditable(true);
		
		TableColumn<Aktivita, LocalDate> datumColumn = new TableColumn<>("Datum");
		datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
		datumColumn.setCellFactory(cellData -> new FormattedDateTableCell<>());
		
		TableColumn<Aktivita, String> nazevColumn = new TableColumn<>("Nazev");
		nazevColumn.setCellValueFactory(new PropertyValueFactory<Aktivita, String>("nazev"));
		nazevColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		TableColumn<Aktivita, Double> vzdalenostColumn = new TableColumn<>("Vzdalenost");
		vzdalenostColumn.setCellValueFactory(new PropertyValueFactory<>("vzdalenost"));
		vzdalenostColumn.setCellFactory(cellData -> new FormattedDoubleTableCell<>(" km"));
		
		TableColumn<Aktivita, TypAktivity> typColumn = new TableColumn<>("Typ aktivity");
		typColumn.setCellValueFactory(new PropertyValueFactory<>("typ"));
		typColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TypAktivity.values()));
		
		TableColumn<Aktivita, LocalTime> casColumn = new TableColumn<>("Cas");
		casColumn.setCellValueFactory(new PropertyValueFactory<>("cas"));
		casColumn.setCellFactory(cellData -> new FormattedTimeTableCell<>());
		//---------------------------prumerna-rychlost-----------------------------------------
		TableColumn<Aktivita, Double> rychlostColumn = new TableColumn<>("Prum. rychlost");
		rychlostColumn.setCellValueFactory(cellData -> cellData.getValue().prumernaRychlostProperty());
		rychlostColumn.setCellFactory(cellData -> new FormattedDoubleTableCell<>(" km/h"));
		rychlostColumn.setEditable(false);
		
		ContextMenu cm = new ContextMenu();
		MenuItem zobrazMI = new MenuItem("Zobraz");
		MenuItem smazMI = new MenuItem("Smaz");
		cm.getItems().add(zobrazMI);
		zobrazMI.setOnAction(e -> zobraz(e));
		cm.getItems().add(smazMI);
		smazMI.setOnAction(e -> smaz(e, model.aktivity, 1));
			
		tabulka.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					cm.show(tabulka, event.getScreenX(), event.getScreenY());
				}
			};
		});
		
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
			Aktivita zobrazeni = tabulka.getSelectionModel().getSelectedItem();
			zobrazeniAktivity.showDialog(zobrazeni);
		}
	}

	private void smaz(ActionEvent e, ListProperty<?> list, int volba) {
		int index;
		if(volba == 1) {
			index = tabulka.getSelectionModel().getSelectedIndex();
		} else {
			index = tabulkaZ.getSelectionModel().getSelectedIndex();
		}
		
		if(index < 0) {
			zprava.showErrorDialog("Neni vybran prvek ke smazani!");
		}
		else {
			if(zprava.showVyberDialog("Opravdu chcete smazat tuto aktivitu?")){
				if(volba == 1) {
					Aktivita vybrana = tabulka.getSelectionModel().getSelectedItem();
					try {
						list.stream().filter(a -> a == vybrana).forEach(a -> list.remove(a)); 
					}
					catch(Exception ex) {
						createStartItems(); 
					}
				} else {
					list.remove(index);
				}
			}
			tabulka.getSelectionModel().clearSelection();
		}
	}
}
