package Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Aktivita {
	/** nazev aktivity */
	private StringProperty nazev = new SimpleStringProperty();
	/** urazena vzdalenost*/ 
	private DoubleProperty vzdalenost = new SimpleDoubleProperty();
	/** cas aktivity v sekundach */
	private IntegerProperty cas = new SimpleIntegerProperty();
	/** typ sportu */
	private ObjectProperty<TypAktivity> typ = new SimpleObjectProperty<>();
	
	/**
	 * Konstruktor
	 * @param nazev
	 * @param vzdalenost
	 * @param cas
	 * @param typ
	 */
	public Aktivita(String nazev, double vzdalenost, int cas, TypAktivity typ) {
		setNazev(nazev);
		setVzdalenost(vzdalenost);
		setCas(cas);
		setTyp(typ);
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
}
