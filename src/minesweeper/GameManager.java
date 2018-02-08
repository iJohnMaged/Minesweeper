package minesweeper;

import java.security.SecureRandom;
import java.util.Random;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameManager {

    private static Random gen = new SecureRandom();
    private Cell[][] cells;
    private Pane root;
    private boolean gameEnded;
    /*
    * Constant values.
    */

    private int width, height;
    private int cols;
    private int res;
    private int rows;
    private int numberOfBombs;
    private final Stage pStage;

    public GameManager(int w, Difficulty diff, Stage pStage) {
        this.pStage = pStage;
        width = w;
        height = w;
        root = new Pane();
        root.setMinSize(width, height);
        resetGame(diff);
    }

    public void resetGame(Difficulty diff) {
        root.getChildren().clear();
        res = Game.cellSize;
        numberOfBombs = diff.getNumberOfBombs();
        cols = width / res;
        rows = height / res;
        gameEnded = false;
        createGameBoard();
    }

    public void resetGame(int bombs) {
        root.getChildren().clear();
        res = Game.cellSize;
        numberOfBombs = bombs;
        cols = width / res;
        rows = height / res;
        gameEnded = false;
        createGameBoard();
    }

    private void createGameBoard() {
        int[][] NumericalCells = createNumericalBoard();
        cells = new Cell[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (NumericalCells[i][j] != -1) {
                    cells[i][j] = new NumCell(i, j, res, NumericalCells[i][j]);
                } else {
                    cells[i][j] = new BombCell(i, j, res);
                }
                root.getChildren().add(cells[i][j].getCellPane());
            }
        }
        enableMouseClickAll();
    }

    private void enableMouseClickAll() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                enableMouseClick(i, j);
            }
        }
    }

    private void enableMouseClick(int col, int row) {
        Cell cell = cells[col][row];

        cell.getCell().setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY && !cell.isRevealed()){
                cell.changeMarker();
            }else if (!cell.isMarked() && !cell.isRevealed()) {
                if(cell instanceof NumCell && ((NumCell) cell).getVal() == 0){
                    revealZeros(col, row);
                } else{
                    cell.revealCell();
                }
                checkWin();
            }
            pStage.sizeToScene();
        });
    }

    private void checkWin() {

        int revealedCells = 0;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (cells[i][j] instanceof BombCell && cells[i][j].isRevealed()) {
                    gameEnd("You lost!");
                    return;
                } else if (cells[i][j] instanceof NumCell && cells[i][j].isRevealed()) {
                    revealedCells++;
                }
            }
        }

        if (revealedCells == (cols * rows) - numberOfBombs) {
            gameEnd("You won!");
        }
    }

    private void gameEnd(String msg) {
        StackPane glass = new StackPane();
        glass.setStyle("-fx-background-color: rgba(2, 13, 7, 0.32);");
        glass.setMinSize(width, height);
        Text t = new Text(msg);
        t.setFont(new Font(Math.min(55, width/4)));
        t.setFill(Color.RED);
        t.setStroke(Color.WHITE);
        glass.getChildren().add(t);
        root.getChildren().add(glass);
        gameEnded = true;

        // Reveal all tables after the game has ended.
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j].revealCell();
            }
        }
    }

    private void revealZeros(int col, int row) {

        if (col < 0 || row < 0 || row >= rows || col >= cols)
            return;

        Cell cell = cells[col][row];

        if (cell.isRevealed())
            return;
        cell.revealCell();

        if (cell instanceof NumCell && ((NumCell) cell).getVal() == 0) {
            for(int dx = -1; dx<=1; dx++){
                for(int dy=-1;dy<=1;dy++){
                    revealZeros(col+dx, row+dy);
                }
            }
        }
    }

    private int[][] createNumericalBoard() {
        int[][] NumericalCells = new int[cols][rows];
        int bombedCell = 0;

        while (bombedCell < numberOfBombs) {
            int col = gen.nextInt(cols);
            int row = gen.nextInt(rows);
            if (NumericalCells[col][row] != -1) {
                NumericalCells[col][row] = -1;
                bombedCell++;
            }
        }

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                /*
                 * I need to check the 8 surrounding cells here.*/
                if (NumericalCells[i][j] == -1) {
                    continue;
                }
                int sum = 0;
                int rowMin = Math.max(0, j - 1);
                int rowMax = Math.min(j + 1, rows - 1);
                int colMin = Math.max(0, i - 1);
                int colMax = Math.min(cols - 1, i + 1);
                for (int m = colMin; m <= colMax; m++) {
                    for (int n = rowMin; n <= rowMax; n++) {
                        if (NumericalCells[m][n] == -1) {
                            sum++;
                        }
                    }
                }
                NumericalCells[i][j] = sum;
            }
        }

        return NumericalCells;
    }

    public Pane getGameBoard() {
        return this.root;
    }

    public boolean hasGameEnded() {
        return gameEnded;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

}
