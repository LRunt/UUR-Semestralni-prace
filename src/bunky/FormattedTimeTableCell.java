package bunky;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class FormattedTimeTableCell<S, T> extends TableCell<S, T> {
	
	private final Label renderLB = new Label();
	private final HBox editHB = new HBox();
	
	public FormattedTimeTableCell() {
		setGraphic(renderLB);
	}
}
