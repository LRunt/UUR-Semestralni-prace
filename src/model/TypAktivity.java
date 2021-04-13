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
		if(aktivita.equals("Cyklistika")) {
			return TypAktivity.CYKLISTIKA; 
		}
		return TypAktivity.AKTIVITA;
	}
}
