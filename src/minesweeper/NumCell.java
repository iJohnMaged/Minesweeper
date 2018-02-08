package minesweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NumCell extends Cell {

	private int value;

	public NumCell(int col, int row, int width, int value) {
		super(col, row, width);
		this.value = value;
//		if (value == 0) {
//			getCell().setFill(Color.LIGHTCORAL);
//		}
	}

	public void revealCell() {
		if (isRevealed())
			return;
		if (this.value > 0) {
			Text t = new Text(Integer.toString(this.value));
			t.setFont(new Font(getWidth() / 1.5));
			t.setFill(Color.BLUE);
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
