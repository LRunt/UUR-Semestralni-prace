package bunky;

import java.time.LocalTime;

import GUI.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class FormattedTimeTableCell<S, T> extends TableCell<S, LocalTime> {
	
	private final Label renderLB = new Label();
	private final TextField editTF = new TextField();
	//private final DateTimeFormatter formatovac = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public FormattedTimeTableCell() {
		setGraphic(renderLB);
		
		editTF.setOnKeyReleased(event -> {
			if(event.getCode().equals(KeyCode.ESCAPE)) {
				cancelEdit();
			} else if(event.getCode().equals(KeyCode.ENTER)) {
				try {
					LocalTime newValue = LocalTime.parse(editTF.getText());
					commitEdit(newValue);
				} catch(Exception e) {
					Main.zprava.showErrorDialog("Cas byl zapsan spatne!");
					cancelEdit();
				}
				
			}
		});
	}
	
	@Override
	protected void updateItem(LocalTime item, boolean empty) {
		super.updateItem(item, empty);
		if(empty) {
			editTF.setText(null);
			renderLB.setText("");
		} 
		else if (item == null) {
			if (isEditing()) {
				editTF.setText(null);
			}
			else {
				renderLB.setText("Nenastaveno");
			}
		} else {
			if(isEditing()) {
				editTF.setText(String.format("%02d:%02d:%02d", item.getHour(), item.getMinute(), item.getSecond()));
			}
			else {
				renderLB.setText(String.format("%02d:%02d:%02d", item.getHour(), item.getMinute(), item.getSecond()));
			}
		}
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		editTF.setText(renderLB.getText());
		setGraphic(editTF);
	}
	
	@Override
	public void commitEdit(LocalTime newValue) {
		super.commitEdit(newValue);
		
		setGraphic(renderLB);
	}
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		
		setGraphic(renderLB);
	}
}
