package minesweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Cell {

    private int x;
    private int y;
    private int size;
    private Color color;
    private Rectangle cell;
    private StackPane cellPane;
    private boolean revealed, marked;
    private ImageView marker;
    private static final Image markerImg = new Image(Main.class.getResourceAsStream("img/mark.png"));

    public Cell(int col, int row, int width) {
        this.x = col * width;
        this.y = row * width;
        this.size = width;
        this.revealed = false;
        this.marked = false;
        this.color = Color.WHITE;
        this.setupCell();
    }

    private void setupCell() {
        this.cell = new Rectangle(this.size, this.size);
        this.cell.setFill(this.color);
        this.cell.setStroke(Color.BLACK);
        this.cellPane = new StackPane();
        cellPane.setMinSize(0, 0);
        cellPane.setMaxSize(this.size, this.size);
        this.cellPane.getChildren().add(this.cell);
        this.cellPane.relocate(this.x, this.y);
    }

    public abstract void revealCell();

    public void changeMarker(){
        if(marked){
            getCellPane().getChildren().remove(getMarkerImage());
            marked = false;
        } else {
            getCellPane().getChildren().add(getMarkerImage());
            marked = true;
        }
    };



    public ImageView getMarkerImage(){
        if(marker == null){
            marker = new ImageView(markerImg);
            marker.setPreserveRatio(true);
            marker.setFitHeight(size /2);
        }
        return marker;
    }

    public boolean isRevealed() {
        return this.revealed;
    }

    public boolean isMarked(){
        return marked;
    }

    public void setReveal(Boolean state) {
        revealed = state;
    }

    public int getSize() {
        return size;
    }

    public Rectangle getCell() {
        return cell;
    }

    public StackPane getCellPane() {
        return this.cellPane;
    }
}
