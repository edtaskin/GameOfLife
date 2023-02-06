package application;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameController {
	private static final int def_x_size = 70, def_y_size = 50;
	public static Boolean fadeMode = false;

	public void setGrid(Stage stage, int x_size, int y_size) {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: grey");
		if (x_size == -1 || y_size == -1) {
			x_size = def_x_size;
			y_size = def_y_size;
		}
		Pane pane = Grid.initializeGrid(x_size, y_size);
		root.setCenter(pane);
		
		VBox rightVBox = new VBox();
		Button backButton = new Button("BACK");
		Button controlsButton = new Button("Controls");
		rightVBox.getChildren().addAll(backButton, controlsButton);
		root.setRight(rightVBox);
		
		//VBox controlsVBox = new VBox();
		Label controlsLabel = new Label("Hold right arrow key to simulate forward.\nRelease right arrow key to stop the simulation.\nPress F key to toggle fade mode.");
		controlsLabel.setStyle("-fx-font-size: 19; -fx-text-fill: yellow");
		
		
		Popup popup = new Popup();
		popup.getContent().add(controlsLabel);

		
		controlsButton.setOnAction(e -> {
			if (!popup.isShowing()) {
				popup.show(stage);
			}else {
				popup.hide();
			}
		});
		
		Rectangle2D screenBoundaries = Screen.getPrimary().getBounds();
		backButton.setOnAction(e1 -> {
			try {
				Scene startScene = new Scene(FXMLLoader.load(getClass().getResource("StartScene.fxml")));
				startScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(startScene);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		
		Scene scene = new Scene(root, screenBoundaries.getWidth(), screenBoundaries.getHeight());
		stage.setScene(scene);
		stage.setFullScreen(true);
		
		
		scene.addEventFilter(KeyEvent.KEY_PRESSED, e2 -> {
			if (e2.getCode() == KeyCode.RIGHT) { 
				try {
					TimeUnit.MILLISECONDS.sleep(1);
					simulateForward();
				} catch (InterruptedException ex1) {
					ex1.printStackTrace();
				}
			}
			else if (e2.getCode() == KeyCode.F) {
				fadeMode = !fadeMode;
			}
		});
	}
	
	
	public void simulateForward() {
		Grid.updateAllNeighbourCount();
		for (int i = 0; i < Grid.x_size; i++) {
			for (int j = 0; j < Grid.y_size; j++) {
				Cell currentCell = Grid.cellsGrid[i][j];
				//Check 1 & 2: Underpopulation and Overpopulation
				if (currentCell.neighbourCount < 2 || currentCell.neighbourCount > 3) {
					currentCell.nextState = Cell.DEAD;
				}
				//Check 3: Reproduction 
				else if (currentCell.state == Cell.DEAD && currentCell.neighbourCount == 3) {
					currentCell.nextState = Cell.ALIVE;
				}
				//Check 4: Stable condition
				else {
					currentCell.nextState = currentCell.state;
				}
				
			}
		}
		Grid.moveToNextState();
	}
	
}
