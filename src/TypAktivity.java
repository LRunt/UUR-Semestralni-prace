/**
 * Enum typu sportu, ktere se daji delat
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public enum TypAktivity {
	CYKLISTIKA("Cyklistika"),
	BEH("Beh"),
	CHUZE("Chuze"),
	POSILOVNA("Posilovani"),
	PLAVANI("Plavani");
	
	private String enumValue;
	
	TypAktivity(String value){
		this.enumValue = value;
	}
	
	public String toString() {
		return enumValue; 
	}
}
