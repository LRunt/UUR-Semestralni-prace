package bunky;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.converter.IntegerStringConverter;
import utils.Message;

public class FormattedPositionTableCell<S, T> extends TableCell<S, Integer>{
	
	private final Label renderLB = new Label();
	private final TextField editTF = new TextField();
	private final TextFormatter<Integer> formatovac;
	private Integer maxValue = Integer.MAX_VALUE;
	private Integer minValue = -2;
	private Message dialog = new Message();
	private String units;
	
	public FormattedPositionTableCell(String units) {
		setGraphic(renderLB);
		this.units = units;
		
		formatovac = new TextFormatter<Integer>(new IntegerStringConverter());
		editTF.setTextFormatter(formatovac);
		formatovac.valueProperty().bindBidirectional(itemProperty());
		
		editTF.setOnKeyReleased(event -> {
			if(event.getCode().equals(KeyCode.ESCAPE)) {
				cancelEdit();
			} else
				if(event.getCode().equals(KeyCode.ENTER)){
				int newValue = formatovac.getValue();
					
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
	protected void updateItem(Integer item, boolean empty) {
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
					if(item <= 0) {
						commitEdit(item);
					} else {
						renderLB.setText(item.toString() + units);
					}
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
	public void commitEdit(Integer newValue) {
		super.commitEdit(newValue);
		if(newValue == -2) {
			renderLB.setText("DSQ");
		}
		if(newValue == -1) {
			renderLB.setText("DNF");
		}
		if(newValue == 0) {
			renderLB.setText("");
		}
		setGraphic(renderLB);
	}
}
