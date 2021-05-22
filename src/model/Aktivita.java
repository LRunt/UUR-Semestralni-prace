package model;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.MySimpleDoubleProperty;
import utils.SimpleIntProperty;

public class Aktivita {
	/** nazev aktivity */
	private final StringProperty nazev = new SimpleStringProperty();
	/** urazena vzdalenost*/ 
	private final DoubleProperty vzdalenost = new MySimpleDoubleProperty();
	/** cas aktivity v sekundach */
	private final ObjectProperty<LocalTime> cas = new SimpleObjectProperty<>();
	/** typ sportu */
	private final ObjectProperty<TypAktivity> typ = new SimpleObjectProperty<>();
	/** datum **/
	private final ObjectProperty<LocalDate> datum = new SimpleObjectProperty<>();
	/** poznamka k treninku*/
	//private String poznamka;
	/** Spalene kalorie */
	private final IntegerProperty kalorie =  new SimpleIntProperty();
	/** Maximalni rychlost*/
	private final DoubleProperty maxRychlost = new MySimpleDoubleProperty();
	/** Prumerna tepovka */
	private final IntegerProperty prumernyTep = new SimpleIntProperty();
	/** Maximalni tepovka */
	private final IntegerProperty maxTep = new SimpleIntProperty();
	/** Prevyseni */
	private final DoubleProperty prevyseni = new MySimpleDoubleProperty();
	/** Poznamky */
	private final StringProperty poznamky = new SimpleStringProperty();
	/** Prumerna rychlost - vypocitana z vzdalenosti a casu */
	private final ObjectBinding<Double> prumernaRychost = new ObjectBinding<Double>() {
		{
			bind(vzdalenost, cas);
		}
		@Override
		protected Double computeValue() {
			if ((vzdalenost != null) && (cas != null)) {
				return round(getVzdalenost()/(double)(getCas().getHour() + getCas().getMinute()/60.0 + getCas().getSecond()/3600.0), 2);
			}
			else {
				return null;
			}
		};
	};
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
	
	public Aktivita(String nazev, LocalTime cas, TypAktivity typ, LocalDate datum) {
		setNazev(nazev);
		setCas(cas);
		setTyp(typ);
		datum.format(formatter);
		setDatum(datum);
	}
	
	
	/**
	 * Konstruktor
	 * @param nazev
	 * @param vzdalenost
	 * @param cas
	 * @param typ
	 */
	public Aktivita(String nazev, double vzdalenost, LocalTime cas, TypAktivity typ, LocalDate datum, String poznamky) {
		this(nazev, cas, typ, datum);
		setVzdalenost(vzdalenost);
	}
	
	public Aktivita(String nazev, double vzdalenost, LocalTime cas, TypAktivity typ, LocalDate datum, int kalorie, double maxRychlost, int prumernyTep, int maxTep, double prevyseni, String poznamky) {
		this(nazev, vzdalenost, cas, typ, datum, poznamky);
		setKalorie(kalorie);
		setMaxRychlost(round(maxRychlost, 2));
		setMaxTep(maxTep);
		setPrumernyTep(prumernyTep);
		setPrevyseni(round(prevyseni, 2));
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
		double zaokrouhleno = round(novaVzdalenost, 1);
		vzdalenost.set(zaokrouhleno);
	}
	
	public double getVzdalenost() {
		return vzdalenost.get();
	}
	
	public DoubleProperty vzdalenostProperty() {
		return vzdalenost;
	}
	//------------------------------cas-------------------------------------------
	public void setCas(LocalTime novyCas) {
		cas.set(novyCas);
	}
	
	public LocalTime getCas() {
		return cas.get();
	}
	
	public ObjectProperty<LocalTime> casProperty() {
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
	
	public ObjectBinding<Double> prumernaRychlostProperty() {
		return prumernaRychost;
	}
	//-----------------------------kalorie-------------------------------------------
	public void setKalorie(int noveKalorie) {
		kalorie.set(noveKalorie);
	}
	
	public int getKalorie() {
		return kalorie.get();
	}
	
	public IntegerProperty kalorieProperty() {
		return kalorie;
	}
	//-----------------------------maximalniRychlost----------------------------------
	public void setMaxRychlost(double novaMaxRychlost) {
		maxRychlost.set(novaMaxRychlost);
	}
	
	public double getMaxRychlost() {
		return maxRychlost.get();
	}
	
	public DoubleProperty maxRychlostProperty() {
		return maxRychlost;
	}
	//-----------------------------prumernaTepovka------------------------------------
	public void setPrumernyTep(int novyAvgTep) {
		prumernyTep.set(novyAvgTep);
	}
	
	public int getPrumernyTep() {
		return prumernyTep.get();
	}
	
	public IntegerProperty prumernyTepProperty() {
		return prumernyTep;
	}
	//-----------------------------maximalniTepovka-----------------------------------
	public void setMaxTep(int novyMaximalniTep) {
		maxTep.set(novyMaximalniTep);
	}
	
	public int getMaxTep() {
		return maxTep.get();
	}
	
	public IntegerProperty maxTepProperty() {
		return maxTep;
	}
	//-----------------------------prevyseni------------------------------------------
	public void setPrevyseni(double novePrevyseni) {
		prevyseni.set(novePrevyseni);
	}
	
	public double getPrevyseni() {
		return prevyseni.get();
	}
	
	public DoubleProperty prevyseniPropety() {
		return prevyseni;
	}
	//-----------------------------poznamky-------------------------------------------
	public void setPoznamky(String novePoznamky) {
		poznamky.set(novePoznamky);
	}
	
	public String getPoznamky() {
		return poznamky.get();
	}
	
	public StringProperty poznamkyProperty() {
		return poznamky;
	}
	//-----------------------------toString-------------------------------------------
	public String toString() {
		return String.format(Locale.US, "%s;%f;%02d:%02d:%02d;%s;%s;%d;%f;%d;%d;%f", getDatum(), getVzdalenost(), getCas().getHour(), getCas().getMinute(), getCas().getSecond(), getTyp(), getNazev(), getKalorie(), getMaxRychlost(), getPrumernyTep(), getMaxTep(), getPrevyseni());
	}
	//-----------------------------utils----------------------------------------------
	/**
	 * Metoda zaokrouli desetinna cila na pozadovany pocet mist
	 * @param value hodnota desettineho cisla
	 * @param mista pocet desetinnych mist
	 * @return zaokrouhlene desetinne misto
	 */
	public double round(double value, int mista) { //metoda opsana ze stack overflow, proc vymyslet neco noveho :-), metoda z tridy math mi nevyhovovala
		if (mista < 0) {
			throw new IllegalArgumentException("Cislo nemuze byt zaokrouhleno na negativni cisla");
		}
		
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(mista, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
