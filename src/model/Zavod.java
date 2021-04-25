package model;

import java.time.LocalDate;
import java.util.Locale;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.SimpleIntProperty;

public class Zavod {
	/** Nazev zavodu */
	private final StringProperty nazev = new SimpleStringProperty();
	/** Datum zavodu */
	private final ObjectProperty<LocalDate> datum = new SimpleObjectProperty<>();
	/** Umisteni v zavode, -1 = DNF, 0 = nezname vysledky */
	private final IntegerProperty umisteni = new SimpleIntProperty(-1, Integer.MAX_VALUE);

	public Zavod(LocalDate datum, String nazev, int umisteni) {
		setDatum(datum);
		setNazev(nazev);
		setUmisteni(umisteni);
	}

	//-------------nazev----------------------
	public void setNazev(String novyNazev) {
		nazev.set(novyNazev);
	}
	
	public String getNazev() {
		return nazev.get();
	}
	
	public StringProperty nazevProperty(){
		return nazev;
	}
	
	//-------------datum-----------------------
	public void setDatum(LocalDate novyDatum) {
		datum.set(novyDatum);
	}
	
	public LocalDate getDatum() {
		return datum.get();
	}
	
	public ObjectProperty<LocalDate> datumProperty() {
		return datum;
	}
	
	//------------umisteni---------------------
	public void setUmisteni(int noveUmisteni) {
		umisteni.set(noveUmisteni);
	}
	
	public int getUmisteni() {
		return umisteni.get();
	}
	
	public IntegerProperty umisteniProperty() {
		return umisteni;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.US, "%s;%s;%d", getDatum(), getNazev(), getUmisteni());
	}
}
