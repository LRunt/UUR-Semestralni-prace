package utils;

import javafx.beans.property.SimpleIntegerProperty;
/**
 * Upravena ineger property, tak aby se nedala ulozit hodnota mimo limity
 * @author Lukas Runt
 * @version 1.0 (2021-04-09)
 */
public class SimpleIntProperty extends SimpleIntegerProperty{
	private int minValue;
	private int maxValue;
	
	/**
	 * Defaultni konstruktor pro kladna cisla
	 */
	public SimpleIntProperty() {
		this.minValue = 0;
		this.maxValue = Integer.MAX_VALUE;
	}
	
	/**
	 * Konstruktor pro vytvoreni vlastnich hranic
	 * @param minValue minimalni hodnota
	 * @param maxValue maximalni hodnota
	 */
	public SimpleIntProperty(int minValue, int maxValue){
		if(minValue > maxValue) {
			throw new IllegalArgumentException("Minimalni hodnota nemuze byt vetsi nez maximalni.");
		}
		else {
			this.minValue = minValue;
			this.maxValue = maxValue;
		}
	}
	
	@Override
	public void set(int newValue) {
		setInternal(newValue);
	}
	
	@Override
	public void setValue(Number v) {
		if(v == null) {
			throw new NullPointerException("Hodnota nemuze byt NULL");
		}
		else {
			setInternal(v.intValue());
		}
	}
	
	private void setInternal(int v) {
		if (v < minValue || v > maxValue) {
			throw new IllegalArgumentException("Nepovolena hodnota.");
		}
		else {
			super.set(v);
		}
	}
}
