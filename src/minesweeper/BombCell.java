package minesweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombCell extends Cell {

	private static final Image bomb_image = new Image(Main.class.getResourceAsStream("img/bomb.png"));
	private final ImageView bomb;

	public BombCell(int col, int row, int width) {
		super(col, row, width);
		bomb = new ImageView(bomb_image);
		bomb.setPreserveRatio(true);
		bomb.setSmooth(true);
		bomb.setFitHeight(width / 2);
	}

	public void revealCell() {
		if (isRevealed())
			return;
		getCellPane().getChildren().add(bomb);
		setReveal(true);
	}

}
