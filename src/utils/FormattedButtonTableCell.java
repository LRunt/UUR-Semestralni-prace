/**
 * 
 */
package utils;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 * @author Lukas Runt
 * @version 1.0 (2021-04-11)
 */
public class FormattedButtonTableCell<S, T> extends TableCell<S, String> {
	private final Button zobrazBT = new Button("Zobraz");
	
	public FormattedButtonTableCell() {
		
		setGraphic(zobrazBT);
	}
	
	
	@Override
	public void startEdit() {
		// TODO Auto-generated method stub
		super.startEdit();
	}
	
	@Override
	public void cancelEdit() {
		// TODO Auto-generated method stub
		super.cancelEdit();
	}
	
	@Override
	public void commitEdit(String newValue) {
		// TODO Auto-generated method stub
		super.commitEdit(newValue);
	}
}
