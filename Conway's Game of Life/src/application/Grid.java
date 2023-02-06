package application;

import javafx.animation.FillTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Grid {
	public static final int neighbourCount = 8;
	public static Cell[][] cellsGrid;
	public static int x_size, y_size;
	public static Pair[] xyIncrementsArr = new Pair[neighbourCount]; //Array of pairs holding necessary increments to x-y coordinates to reach the neighbour 
	
	public static Pane initializeGrid(int x, int y) {		
		int index = 0;
		for (int x_increment = -1; x_increment < 2; x_increment++) {
			for (int y_increment = -1; y_increment < 2; y_increment++) {
				if (x_increment != 0 || y_increment != 0) {
					xyIncrementsArr[index++] = new Pair(x_increment, y_increment);
				}
			}
		}
		
		x_size = x;
		y_size = y;
		
		Pane pane = new Pane();
		Rectangle[][] grid = new Rectangle[x_size][y_size];
		cellsGrid = new Cell[x_size][y_size];
		
		Rectangle2D screenBoundaries = Screen.getPrimary().getBounds();
		double cellSize = (screenBoundaries.getHeight())/y_size; //TODO
		
		for (int i = 0; i < x_size; i++) {
			for (int j = 0; j < y_size; j++) {
				grid[i][j] = new Rectangle();
				grid[i][j].setX(i * cellSize);
				grid[i][j].setY(j * cellSize);
				grid[i][j].setWidth(cellSize);
				grid[i][j].setHeight(cellSize);
				grid[i][j].setFill(Color.BLACK);
				grid[i][j].setStroke(Color.GRAY);
				final int row = i;
				final int col = j;
				grid[i][j].setOnMouseClicked(event -> {
					grid[row][col].setFill(Color.WHITE);
					cellsGrid[row][col].state = Cell.ALIVE;
				});
				
				cellsGrid[i][j] = new Cell(grid[i][j]);
				pane.getChildren().add(grid[i][j]);
			}
		}
		return pane;
	}

	//Updates the value of neighbourCount field for each cell on the grid
	public static void updateAllNeighbourCount() {
		for (int i = 0; i < Grid.x_size; i++) {
			for (int j = 0; j < Grid.y_size; j++) {
				cellsGrid[i][j].neighbourCount = 0;
				//Check neighbors 1 to 8 starting from top left
				for (Pair pair : xyIncrementsArr) {
					try {
						if (cellsGrid[i + pair.x_increment][j + pair.y_increment].state == Cell.ALIVE) {
							cellsGrid[i][j].neighbourCount++; //Increment neighbour count of current cell, if the neighboring cell is alive
						}
					}catch (ArrayIndexOutOfBoundsException e){
						continue;
					}
				}
			}
		}
	}
	
	
	//Transition to next state and change cell color accordingly
	public static void moveToNextState() {
		for (int i = 0; i < Grid.x_size; i++) {
			for (int j = 0; j < Grid.y_size; j++) {
				Cell currentCell = cellsGrid[i][j];
				if (currentCell.nextState == Cell.ALIVE) {
					currentCell.cell.setFill(Color.WHITE);
				}
				else { 
					if (currentCell.state == Cell.ALIVE && GameController.fadeMode) {
						FillTransition ft = new FillTransition(Duration.millis(2000), currentCell.cell, Color.WHITE, Color.BLACK);
						ft.play();
					}
					else currentCell.cell.setFill(Color.BLACK);
				}
				currentCell.state = currentCell.nextState;
			}
		}
	}
	
}
