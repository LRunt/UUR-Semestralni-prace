
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Lukas Runt
 * @version 1.0 (2020-04-08)
 */
public class Main extends Application{
	private static final String OBSAH_TITULKU = "Semestralni prace - Lukas Runt - A20B0226P";
	private TableView<String> tabulka;
	private ManualniVstup okno = new ManualniVstup();

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
		soubor.getItems().addAll(manual, file);
		
		Menu statistiky = new Menu("Statistika");
		
		Menu zavod = new Menu("Zavody");
		
		Menu help = new Menu("O programu");
		
		menu.getMenus().addAll(soubor, statistiky, zavod, help);
		return menu;
	}
	
	private Node getTreeView() {
		
		return null;
	}
	
	private Node getTabulka() {
		tabulka = new TableView<>();
		
		return tabulka;
	}

}
