/**
 * 
 */
package utils;

import javafx.util.StringConverter;

/**
 * Trida na prevod vterina na textovy retezec ve formatu [hh:mm:ss]
 * @author Lukas Runt
 * @version 1.0 (2021-04-11)
 */
public class CasStringConverter extends StringConverter<Integer>{
	/** jednotky casu se deli/nasobi 60 */
	private final int PREVOD = 60;
	/** hodiny */
	private Integer hod;
	/** minuty */
	private Integer min;
	/** vteriny */
	private Integer sec;

	/**
	 *Metoda ktera prevede object ve vterinach na format [hh:mm:ss]
	 */
	@Override
	public String toString(Integer object) {
		if(object != null) {
			hod = (int)(object/(PREVOD*PREVOD));
			min = (int)((object-(hod*PREVOD*PREVOD))/PREVOD);
			sec = object - (hod * PREVOD * PREVOD) - (min * PREVOD);
			return String.format("%02d:%02d:%02d", hod, min, sec);
		}
		return null;
	}

	@Override
	public Integer fromString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
