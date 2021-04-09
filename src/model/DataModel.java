package model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * 
 * @author Lukas Runt
 * @version 1.0 (2021-04-08)
 */
public class DataModel {

	public ListProperty<Aktivita> aktivity = new SimpleListProperty<>(FXCollections.observableArrayList());
}
