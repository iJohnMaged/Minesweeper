package minesweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombCell extends Cell {

	private static final ImageView bomb = new ImageView(new Image(Main.class.getResourceAsStream("img/bomb.png")));

	public BombCell(int col, int row, int width) {
		super(col, row, width);
		bomb.setPreserveRatio(true);
		bomb.setFitHeight(width / 2);
//		getCell().setFill(Color.RED);
	}

	public void revealCell() {
		if (isRevealed())
			return;
		getCellPane().getChildren().add(bomb);
		setReveal(true);
	}

}
