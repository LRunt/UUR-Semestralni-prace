package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import GUI.Main;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * 
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public class DataModel {

	public ListProperty<Aktivita> aktivity = new SimpleListProperty<>(FXCollections.observableArrayList());
	public ListProperty<Zavod> zavody = new SimpleListProperty<>(FXCollections.observableArrayList());
	public ListProperty<Aktivita> zobrazeni = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public void initializeModel() {
		try {
			List<String> seznamRadek = Files.readAllLines(Paths.get("data\\data.csv"));
			for(String radka : seznamRadek) {
				String atributy[] = radka.split(";");
				aktivity.add(new Aktivita(atributy[4],Double.parseDouble(atributy[1]), LocalTime.parse(atributy[2]), TypAktivity.getAktivita(atributy[3]), LocalDate.parse(atributy[0]), Integer.parseInt(atributy[5]), Double.parseDouble(atributy[6]), Integer.parseInt(atributy[7]), Integer.parseInt(atributy[8])));
			}
			seznamRadek.removeAll(seznamRadek);
			seznamRadek = Files.readAllLines(Paths.get("data\\dataZ.csv"));
			for(String radka : seznamRadek) {
				String atributy[] = radka.split(";");
				zavody.add(new Zavod(LocalDate.parse(atributy[0]), atributy[1], Integer.parseInt(atributy[2])));
			}
			aktivity.stream().forEach(a -> zobrazeni.add(a));
		}
		catch (Exception e) {
			System.out.println("Chyba pri nacitani dat ze souboru" + e.getMessage());
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
			
			PrintWriter pwZ = new PrintWriter(
							  new BufferedWriter(
							  new FileWriter(
							  new File("data\\dataZ.csv"))));
			for(Zavod z : zavody) {
				pwZ.println(z.toString());
			}
			pwZ.close();
		}
		catch (Exception e) {
			System.out.println("Chyba pri ukladani dat do souboru" + e.getMessage());
		}
	}
	
	public void sortData() {
		Collections.sort(aktivity, (a,b) -> -1 * a.getDatum().compareTo(b.getDatum()));
	}
	
	public LocalDate getMinDate() {
		return aktivity.get(aktivity.size() - 1).getDatum();
	}
	
	public LocalDate getMaxDate() {
		return aktivity.get(0).getDatum();
	}
	
	public String[] getYears() {
		sortData();
		String[] roky = new String[(getMaxDate().getYear() - getMinDate().getYear()) + 1];
		int rok;
		for(int i = 0; i < roky.length; i++) {
			rok = getMinDate().getYear() + i;
			roky[i] = rok + "";
		}
		return roky;
	}
	
	public boolean[][] getMesice(){
		long pocet;
		String[] roky = getYears();	
		boolean[][] pole = new boolean[roky.length][12]; 
		for(int i = 0; i < roky.length; i++) {
			final int p = i;
			for(int j = 0; j < 12; j++) {
				final int o = j + 1;
				pocet = aktivity.stream().filter(a -> a.getDatum().getYear() == Integer.parseInt(roky[p]))
								.filter(a -> a.getDatum().getMonthValue() == o).count();
				if(pocet == 0) {
					pole[i][j] = false;
				} else {
					pole[i][j] = true;
				}
			}
		}
		return pole;
	}
}
