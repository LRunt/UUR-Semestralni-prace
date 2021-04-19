/**
 * 
 */
package bunky;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.converter.DoubleStringConverter;
import utils.Message;

/**
 * Vlastni bunka tabulky, pro cisla
 * @author Lukas Runt
 * @version 1.0 (2021-04-14)
 */
public class FormattedDoubleTableCell<S, T> extends TableCell<S, Double> {
	
	private final Label renderLB = new Label();
	private final TextField editTF = new TextField();
	private final TextFormatter<Double> formatovac;
	private Double maxValue = Double.MAX_VALUE;
	private Double minValue = 0.0;
	private Message dialog = new Message();
	
	public FormattedDoubleTableCell() {
		setGraphic(renderLB);
		
		formatovac = new TextFormatter<Double>(new DoubleStringConverter());
		editTF.setTextFormatter(formatovac);
		formatovac.valueProperty().bindBidirectional(itemProperty());
		
		editTF.setOnKeyReleased(event -> {
			if(event.getCode().equals(KeyCode.ESCAPE)) {
				cancelEdit();
			} else
				if(event.getCode().equals(KeyCode.ENTER)){
				double newValue = formatovac.getValue();
					
					if (newValue > this.maxValue || newValue < this.minValue) {
						dialog.showErrorDialog("Cislo musi byt kladne.");
						cancelEdit();
					}
					else {
						commitEdit(newValue);
					}
				}
		});
	}
	
	@Override
	protected void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);
		
		if (empty) {
			setText("");
			renderLB.setText("");
			editTF.setText("");
		} else {
			if (item == null) {
				if (isEditing()) {
					editTF.setText("");
				} else {
					renderLB.setText("Nenastaveno");
				}
			} else {
				if(isEditing()) {
					editTF.setText(item.toString());
				} else {
					renderLB.setText(item.toString());
				}
			}
		}
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		
		setGraphic(editTF);
	}
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		
		setGraphic(renderLB);
	}
	
	@Override
	public void commitEdit(Double newValue) {
		super.commitEdit(newValue);
		
		setGraphic(renderLB);
	}
	
}
