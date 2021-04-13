package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import model.Aktivita;
import model.TypAktivity;

/**
 * Kod z PPA1 - Pro cteni xml souboru
 * @author Lukas Runt
 * @version 1.0 (2021-04-09)
 */
public class Ctenar {
	/** Scanner pro cteni ze standartniho vstupu*/
	private Scanner sc;
	/** nacitana radka */
	private String radka;
	
	/**
	 * Krome uchovani reference na tuto instanci konstruktor nic nedela.
	 * @param sc pro cteni ze standarniho vstupu
	 */
	public Ctenar() {
	}
	
	/**
	 * Nacte a ulozi si ze Scanneru dalsi radku.
	 */
	public void nactiDalsiRadku() {
		radka = sc.nextLine();
	}

	/**
	 * Vrati nazev elementu nachazejici se na aktualni radce.
	 * @return nazev elementu
	 */
	public String getElement() {
		if(radka.contains("=")) {
			int zacatek = radka.indexOf("<");
			String odkud = radka.substring(zacatek);
			int konec = odkud.indexOf(" ") + zacatek;
			if(zacatek == -1 || konec == -1) {
				return null;
			}
			else {
				String element = radka.substring(zacatek + 1, konec);
				return element;
			}
		}
		else {
			int zacatek = radka.indexOf("<");
			int konec = radka.indexOf(">");
			if(zacatek == -1 || konec == -1) {
				return null;
			}
			else {
				String element = radka.substring(zacatek + 1, konec);
				return element;
			}
		}
	}
	
	/**
	 * Vrati textovou hodnotu (retezec) elementu na aktualni radce,
	 * tedy text osazeny nezioteviraci a zaviraci znackou.
	 * @return retezec hodnoty
	 */
	public String getHodnota() {
		int zacatek = radka.indexOf(">");
		int konec = radka.lastIndexOf("<");
		if(zacatek == -1 || konec == -1) {
			return null;
		}
		else {
			String hodnota = radka.substring(zacatek + 1, konec);
			return hodnota;
		}
	}
	
	/**
	 * Vreti texovou hodnotu (retezec) atributu nazev na aktualni radce.
	 * Poud se atribut nevyskytuje, metoda vrati null.
	 * @praram nazev
	 * @return retezec atributu
	 */
	public String getAtribut(String nazev) {
		if (radka.contains(nazev)) {
			int odkud = radka.indexOf(nazev + "=");
			if(odkud == -1){
				odkud = 0;
			}
			String presneMisto = radka.substring(odkud);
			int zacatek = presneMisto.indexOf("\"") + odkud;
			String jenAtributACoJeZa = radka.substring(zacatek+1);
			int konec = jenAtributACoJeZa.indexOf("\"") + zacatek + 1;  
			if(zacatek == -1 || konec == -1) {
				return null;
			}
			else {
				String hodnota = radka.substring(zacatek + 1, konec);
				return hodnota;
			}
		}
		return null;
		
	}
	
	/**
	 * Metoda precte souboru a vytvori novou aktivitu
	 * @param file tcx soubor ze ktereho se vytvori aktivita
	 */
	public void read(File file) {
		String typ = "";
		double cas = 0;
		double vzdalenost = 0;
		LocalDate datum = null;
		String pomocnik;
		try {
			sc = new Scanner(file);
			while(sc.hasNext() && (typ.equals("") || cas == 0 || vzdalenost == 0 || datum == null)) {
				nactiDalsiRadku();
				if(radka.contains("Activity Sport")) {
					typ = getAtribut("Activity Sport");
				}
				if(radka.contains("TotalTimeSeconds")) {
					cas = Double.parseDouble(getHodnota());
				}
				if(radka.contains("DistanceMeters")) {
					vzdalenost = Double.parseDouble(getHodnota())/1000;
				}
				if(radka.contains("StartTime")) {
					pomocnik = getAtribut("StartTime");
					String[] podretezec = pomocnik.split("T");
					datum = LocalDate.parse(podretezec[0]);
				}
			}
			GUI.Main.model.aktivity.add(new Aktivita("Morning Ride", vzdalenost, (int)cas, TypAktivity.getAktivita(typ), datum));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
