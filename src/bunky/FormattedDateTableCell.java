/**
 * 
 */
package bunky;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;

/**
 * Vlastni bunka tabulky,
 * pro cteni label, pro editovani datePicker
 * @author Lukas Runt
 * @version 1.0 (2021-04-14)
 */
public class FormattedDateTableCell<S, T> extends TableCell<S, LocalDate> {
	
	private final Label renderLB = new Label();
	private final DatePicker editDP = new DatePicker();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	public FormattedDateTableCell() {
		setGraphic(renderLB);
		
		editDP.setOnKeyReleased(event -> {
			if(event.getCode().equals(KeyCode.ESCAPE)) {
				cancelEdit();
			} else if(event.getCode().equals(KeyCode.ENTER)) {
				LocalDate newValue = editDP.getValue();
				
				commitEdit(newValue);
			}
		});
	}
	
	@Override
	protected void updateItem(LocalDate item, boolean empty) {
		super.updateItem(item, empty);
		editDP.valueProperty().bindBidirectional(itemProperty());
		
		if(empty) {
			editDP.setValue(null);
			renderLB.setText("");
		} 
		else if (item == null) {
			if (isEditing()) {
				editDP.setValue(null);
			}
			else {
				renderLB.setText("Nenastaveno");
			}
		} else {
			if(isEditing()) {
				editDP.setValue(item);
			}
			else {
				renderLB.setText(formatter.format(item));
			}
		}
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		
		setGraphic(editDP);
	}
	
	@Override
	public void commitEdit(LocalDate newValue) {
		super.commitEdit(newValue);
		
		setGraphic(renderLB);
	}
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		
		setGraphic(renderLB);
	}
	
}
