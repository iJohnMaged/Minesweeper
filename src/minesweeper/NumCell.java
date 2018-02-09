package minesweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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

	public NumCell(int width, int value) {
		super(width);
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
			t.setFont(Font.font("Arial", FontWeight.BOLD, getSize()/1.5));
			t.setFill(colorMapping.get(value));
			t.setTextAlignment(TextAlignment.CENTER);
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
