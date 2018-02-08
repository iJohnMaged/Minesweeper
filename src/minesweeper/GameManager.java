package minesweeper;

import java.security.SecureRandom;
import java.util.Random;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameManager {

    private static Random gen = new SecureRandom();
    private Cell[][] cells;
    private Pane root;
    private boolean gameEnded;
    /*
    * Constant values.
    */

    private final int width;
    private int cols;
    private int res;
    private int rows;
    private int numberOfBombs;

    public GameManager(int w, Difficulty diff) {
        width = w;
        root = new Pane();
        newGame(diff);
    }

    private void resetGame(Difficulty diff) {
        root.getChildren().clear();
        res = width / diff.getNumberOfCells();
        numberOfBombs = diff.getNumberOfBombs();
        cols = width / res;
        rows = width / res;
        gameEnded = false;
        createGameBoard();
    }

    public void newGame(Difficulty diff) {
        resetGame(diff);
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
            }else if (!cell.isMarked()) {
                if(cell instanceof NumCell && ((NumCell) cell).getVal() == 0){
                    revealZeros(col, row);
                } else{
                    cell.revealCell();
                }
                checkWin();
            }
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
        glass.setMinSize(width, width);
        Text t = new Text(msg);
        t.setFont(new Font(55));
        t.setFill(Color.RED);
        t.setStroke(Color.WHITE);
        glass.getChildren().add(t);
        root.getChildren().add(glass);
        gameEnded = true;
    }

    private void revealZeros(int col, int row) {

        if (col < 0 || row < 0 || row >= rows || col >= cols)
            return;

        Cell cell = cells[col][row];

        if (cell.isRevealed())
            return;
        cell.revealCell();

        if (cell instanceof NumCell && ((NumCell) cell).getVal() == 0) {
            revealZeros(col + 1, row);
            revealZeros(col - 1, row);
            revealZeros(col, row + 1);
            revealZeros(col, row - 1);
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

}
