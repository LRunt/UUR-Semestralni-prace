package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.util.converter.LocalDateStringConverter;

/**
 * 
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public class DataModel {

	public ListProperty<Aktivita> aktivity = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public void initializeModel() {
		try {
			List<String> seznamRadek = Files.readAllLines(Paths.get("data\\data.csv"));
			for(String radka : seznamRadek) {
				String atributy[] = radka.split(";");
				aktivity.add(new Aktivita(atributy[4],Double.parseDouble(atributy[1]), Integer.parseInt(atributy[2]), TypAktivity.getAktivita(atributy[3]), LocalDate.parse(atributy[0])));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveData() {
		try {
			PrintWriter pw = new PrintWriter(
							 new BufferedWriter(
							 new FileWriter(
							 new File("data\\data.csv"))));
			for(Aktivita a : aktivity) {
				pw.println(a.toString());
			}
			pw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
