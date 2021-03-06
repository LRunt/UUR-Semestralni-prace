package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import GUI.Main;
import model.Aktivita;
import model.TypAktivity;

/**
 * Kod z PPA1 - Pro cteni xml souboru
 * @author Lukas Runt
 * @version 2.3 (2021-04-09)
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
		double casP;
		LocalTime cas = LocalTime.now();
		double vzdalenost = 0;
		double maxRychlost = 0;
		int kalorie = 0;
		int prumernyTep = 0;
		int maxTep = 0;
		ArrayList<Integer> kadence = new ArrayList<Integer>();
		ArrayList<Double> prevyseniAL = new ArrayList<Double>();
		double prevyseni = 0;
		LocalDate datum = null;
		String pomocnik;
		try {
			sc = new Scanner(file);
			while(sc.hasNext()) {
				nactiDalsiRadku();
				if(radka.contains("Activity Sport")) {
					typ = getAtribut("Activity Sport");
				}
				if(radka.contains("TotalTimeSeconds")) {
					casP = Double.parseDouble(getHodnota());
					int hodina = (int)(casP / 3600);
					int minuta = (int)((casP - 3600 * hodina)/60);
					int sekunda = (int)(casP - 3600 * hodina - 60 * minuta);
					String casStr = String.format("%02d:%02d:%02d", hodina, minuta, sekunda);
					cas = LocalTime.parse(casStr);
				}
				if(radka.contains("DistanceMeters")) {
					vzdalenost = Double.parseDouble(getHodnota())/1000;
				}
				if(radka.contains("StartTime")) {
					pomocnik = getAtribut("StartTime");
					String[] podretezec = pomocnik.split("T");
					datum = LocalDate.parse(podretezec[0]);
				}
				if(radka.contains("MaximumSpeed")) {
					maxRychlost = Double.parseDouble(getHodnota()) * 3.6; //v txc by mela byt rychlost v metrech za sekundu
				}
				if(radka.contains("Calories")) {
					kalorie = Integer.parseInt(getHodnota());
				}
				if(radka.contains("AverageHeartRateBpm") && prumernyTep == 0) {
					nactiDalsiRadku();
					prumernyTep = Integer.parseInt(getHodnota());
				}
				if(radka.contains("MaximumHeartRateBpm") && maxTep == 0) {
					nactiDalsiRadku();
					maxTep = Integer.parseInt(getHodnota());
				}
				if(radka.contains("Cadence")) {
					kadence.add(Integer.parseInt(getHodnota()));
				}
				if(radka.contains("AltitudeMeters")) {
					prevyseniAL.add(Double.parseDouble(getHodnota()));
				}
			}
			//double kadenceAvg = kadence.stream().reduce(0, Integer::sum) / (double)kadence.size();
			if(prevyseniAL.size() > 0) {
				prevyseni = zjistiPrevseni(prevyseniAL);
			}
			GUI.Main.model.aktivity.add(new Aktivita("Morning Ride", vzdalenost, cas, TypAktivity.getAktivita(typ), datum, kalorie, maxRychlost, prumernyTep, maxTep, prevyseni));
			Main.createStartItems();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Main.zprava.showErrorDialog("Soubor" + file + "nebyl nalezen");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Main.zprava.showErrorDialog("Spatny format souboru.\nData budou nejspise v jedne radce.\n");
		}
	}

	/**
	 * Metoda vypocita prevyseni 
	 * @param prevyseni pole s hodnotami prevyseni
	 * @return hodnotu celkoveho prevyseni
	 */
	private double zjistiPrevseni(ArrayList<Double> prevyseni) {
		double celkem = 0;
		for(int i = 0; i < prevyseni.size() - 1; i++) {
			if(prevyseni.get(i) < prevyseni.get(i+1)) {
				celkem += prevyseni.get(i + 1) - prevyseni.get(i);
			}
		}
		return celkem;
	}
}
