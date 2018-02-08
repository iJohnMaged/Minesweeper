package minesweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class NumCell extends Cell {

	private int value;
	private static final Map<Integer, Color> colorMapping;

	static {
	    colorMapping = new HashMap<>();
	    colorMapping.put(1, Color.web("#0000FF"));
	    colorMapping.put(2, Color.web("#008000"));
	    colorMapping.put(3, Color.web("#FF0000"));
	    colorMapping.put(4, Color.web("#000080"));
	    colorMapping.put(5, Color.web("#800000"));
	    colorMapping.put(6, Color.web("#008080"));
	    colorMapping.put(7, Color.web("#000000"));
	    colorMapping.put(8, Color.web("#808080"));
    }

	public NumCell(int col, int row, int width, int value) {
		super(col, row, width);
		this.value = value;
	}


	public void revealCell() {
		if (isRevealed())
			return;

		// for when revealing the whole table.
		if (isMarked()){
            changeMarker();
        }


		if (value > 0) {
            Text t = new Text(Integer.toString(value));
			t.setFont(new Font(getSize() / 1.5));
			t.setFill(colorMapping.get(value));
			getCellPane().getChildren().add(t);
		} else {
			getCell().setFill(Color.ANTIQUEWHITE);
		}
		setReveal(true);
	}


	public int getVal() {
		return value;
	}

}
