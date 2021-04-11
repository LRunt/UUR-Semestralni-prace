package utils;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	public void read(File file) {
		String typ = "";
		double cas = 0;
		double vzdalenost = 0;
		try {
			sc = new Scanner(file);
			while(sc.hasNext() && (typ.equals("") || cas == 0 || vzdalenost == 0)) {
				nactiDalsiRadku();
				if(radka.contains("Activity Sport")) {
					typ = getAtribut("Activity Sport");
				}
				if(radka.contains("TotalTimeSeconds")) {
					cas = Double.parseDouble(getHodnota());
				}
				if(radka.contains("DistanceMeters")) {
					vzdalenost = Double.parseDouble(getHodnota());
				}
			}
			GUI.Main.model.aktivity.add(new Aktivita("Morning Ride", vzdalenost, (int)cas, zjistiTyp(typ), null));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda zjisti typ aktivity z tcx souboru
	 * @param typ jmeno aktivity
	 * @return Typ aktivity (z enumu {@code TypAktivity}) 
	 */
	private TypAktivity zjistiTyp(String typ) {
		if(typ.equals("Biking")) {
			return TypAktivity.CYKLISTIKA;
		}
		if(typ.equals("Running")) {
			return TypAktivity.BEH;
		}
		return TypAktivity.AKTIVITA; //defaultni typ, kdyz nikde nebude shoda
	}
}
