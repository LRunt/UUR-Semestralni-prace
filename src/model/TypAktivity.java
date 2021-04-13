package model;
/**
 * Enum typu sportu, ktere se daji delat
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public enum TypAktivity {
	CYKLISTIKA("Cyklistika"),
	BEH("Beh"),
	CHUZE("Chuze"),
	PLAVANI("Plavani"),
	POSILOVNA("Posilovani"),
	//Nahore druhy sportu ktere se budou asi pouzivat nejvice, dale druhy podle abecedy
	BEZKY("Bezkareni"),
	BRUSLE("Brusleni"),
	LYZE("Lyzovani"),
	STRETCHING("Protahovani"),
	AKTIVITA("Sportovni aktivita");//defaultni kdyby se nasel druh sportu, ktery neni definovan
	
	private String enumValue;
	
	TypAktivity(String value){
		this.enumValue = value;
	}
	
	public String toString() {
		return enumValue; 
	}
	
	public static TypAktivity getAktivita(String aktivita) {
		if(aktivita.equals("Cyklistika") || aktivita.equals("Biking")) {
			return TypAktivity.CYKLISTIKA; 
		}
		if(aktivita.equals("Beh") || aktivita.equals("Running")) {
			return TypAktivity.BEH; 
		}
		if(aktivita.equals("Chuze")) {
			return TypAktivity.CHUZE; 
		}
		if(aktivita.equals("Plavani")) {
			return TypAktivity.PLAVANI; 
		}
		if(aktivita.equals("Posilovani")) {
			return TypAktivity.POSILOVNA; 
		}
		if(aktivita.equals("Bezkareni")) {
			return TypAktivity.BEZKY; 
		}
		if(aktivita.equals("Brusleni")) {
			return TypAktivity.BRUSLE; 
		}
		if(aktivita.equals("Lyzovani")) {
			return TypAktivity.LYZE; 
		}
		if(aktivita.equals("Protahovani")) {
			return TypAktivity.STRETCHING; 
		}
		return TypAktivity.AKTIVITA;
	}
}
