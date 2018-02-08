package minesweeper;

enum Difficulty {

    EASY(8, 10), MEDIUM(16, 40), HARD(30, 99);

    private final int numberOfCells;
    private final int numberOfBombs;

    Difficulty(int numberOfCells, int numberOfBombs) {
        this.numberOfCells = numberOfCells;
        this.numberOfBombs = numberOfBombs;
    }


    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }
}