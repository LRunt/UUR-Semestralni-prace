package model;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.MySimpleDoubleProperty;
import utils.SimpleIntProperty;

public class Aktivita {
	/** nazev aktivity */
	private StringProperty nazev = new SimpleStringProperty();
	/** urazena vzdalenost*/ 
	private DoubleProperty vzdalenost = new MySimpleDoubleProperty();
	/** cas aktivity v sekundach */
	private IntegerProperty cas = new SimpleIntProperty();
	/** typ sportu */
	private ObjectProperty<TypAktivity> typ = new SimpleObjectProperty<>();
	/** datum **/
	private ObjectProperty<LocalDate> datum = new SimpleObjectProperty<>();
	/** poznamka k treninku*/
	private String poznamka;
	/** Spalene kalorie */
	private IntegerProperty kalorie =  new SimpleIntProperty();
	/** Prevyseni */
	private IntegerProperty prevyseni = new SimpleIntProperty();
	/** */
	private final ReadOnlyDoubleWrapper prumernaRychost = new ReadOnlyDoubleWrapper();
	
	/**
	 * Konstruktor
	 * @param nazev
	 * @param vzdalenost
	 * @param cas
	 * @param typ
	 */
	public Aktivita(String nazev, double vzdalenost, int cas, TypAktivity typ, LocalDate datum) {
		setNazev(nazev);
		setVzdalenost(vzdalenost);
		setCas(cas);
		setTyp(typ);
		setDatum(datum);
		prumernaRychost.set(vzdalenost/(cas/3600));
	}
	
	public Aktivita(String nazev, double vzdalenost, int cas, TypAktivity typ, LocalDate datum, String poznamka) {
		this(nazev, vzdalenost, cas, typ, datum);
		this.poznamka = poznamka;
	}
	
	public Aktivita(String nazev, double vzdalenost, int cas, TypAktivity typ, LocalDate datum, int kalorie, String poznamka) {
		this(nazev, vzdalenost, cas, typ, datum);
		this.poznamka = poznamka;
	}
	
	//------------------------------nazev-----------------------------------------
	/**
	 * Nastavuje novy nazev
	 * @param novyNazev navy nazev aktivity
	 */
	public void setNazev(String novyNazev) {
		nazev.set(novyNazev);
	}
	
	/** 
	 * Vraci nazev aktivity
	 * @return nazev
	 */
	public String getNazev() {
		return nazev.get();
	}
	
	public StringProperty nazevProperty() {
		return nazev;
	}
	//------------------------------vzdalenost------------------------------------
	public void setVzdalenost(double novaVzdalenost) {
		vzdalenost.set(novaVzdalenost);
	}
	
	public double getVzdalenost() {
		return vzdalenost.get();
	}
	
	public DoubleProperty vzdalenostProperty() {
		return vzdalenost;
	}
	//------------------------------cas-------------------------------------------
	public void setCas(int novyCas) {
		cas.set(novyCas);
	}
	
	public int getCas() {
		return cas.get();
	}
	
	public IntegerProperty casProperty() {
		return cas;
	}
	//------------------------------typ-------------------------------------------
	public void setTyp(TypAktivity novyTyp) {
		typ.set(novyTyp);
	}
	
	public TypAktivity getTyp() {
		return typ.get();
	}
	
	public ObjectProperty<TypAktivity> typProperty(){
		return typ;
	}
	//-----------------------------datum-------------------------------------------
	public void setDatum(LocalDate novyDatum) {
		datum.set(novyDatum);
	}
	
	public LocalDate getDatum() {
		return datum.get();
	}
	
	public ObjectProperty<LocalDate> datumProperty(){
		return datum;
	}
	//-----------------------------prumerna rychlost--------------------------------
	public double getPrumernaRychlost() {
		return prumernaRychost.get();
	}
	
	public ReadOnlyDoubleProperty prumernaRychlostProperty() {
		return prumernaRychost;
	}
	//-----------------------------toString----------------------------------------
	public String toString() {
		return String.format("%s; %f; %d, %s", getNazev(), getVzdalenost(), getCas(), getTyp());
	}
}
