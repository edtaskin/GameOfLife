package application;

import javafx.scene.shape.Rectangle;

public class Cell {
	public static final Boolean ALIVE = true;
	public static final Boolean DEAD = false;
	
	public Boolean state;
	public Boolean nextState;
	public Rectangle cell;
	public int neighbourCount;
	
	Cell(Rectangle rec){
		state = DEAD;
		nextState = DEAD;
		cell = rec;
		neighbourCount = 0;
	}
	
	@Override
	public String toString() {
		return String.format("state: %s, next state: %s, neighbourCount: %d", state == ALIVE ? "ALIVE" : "DEAD",  nextState == ALIVE ? "ALIVE" : "DEAD", neighbourCount);
	}
	
}
