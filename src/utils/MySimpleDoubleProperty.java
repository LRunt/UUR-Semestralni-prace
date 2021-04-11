package utils;

import javafx.beans.property.SimpleDoubleProperty;

public class MySimpleDoubleProperty extends SimpleDoubleProperty{
	private double minValue;
	private double maxValue;

	/**
	 * Defaultni konstruktor pro kladna cisla 
	 */
	public MySimpleDoubleProperty() {
		this.minValue = 0;
		this.maxValue = Double.MAX_VALUE;
	}
	
	@Override
	public void set(double newValue) {
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
	
	private void setInternal(double v) {
		if (v < minValue || v > maxValue) {
			throw new IllegalArgumentException("Nepovolena hodnota");
		}
		else {
			super.set(v);
		}
	}
	
}
