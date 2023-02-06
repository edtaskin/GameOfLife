package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StartSceneController implements Initializable{
	@FXML
	private Label titleLabel;
	@FXML
	private Button startButton, optionalSettingsButton;
	@FXML
	private ToggleButton toggleFadeModeButton;
	@FXML
	private TextField gridSizeTextField;
	@FXML
	private HBox optionalHBox1, optionalHBox2;
	
	private static int x_size, y_size;
	
	private static GameController gameController = new GameController();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		titleLabel.setStyle("-fx-font-family: 'VT323'");
		
		optionalHBox1.setVisible(false);
		optionalHBox2.setVisible(false);
		
		optionalSettingsButton.setOnAction(e -> {
			optionalHBox1.setVisible(!optionalHBox1.isVisible());
			optionalHBox2.setVisible(!optionalHBox2.isVisible());
		});
		
		
		
		startButton.setOnAction(e1 -> {
			String[] gridSizeArr = gridSizeTextField.getText().split("x");
			try {
				x_size = Integer.valueOf(gridSizeArr[0]);
				y_size = Integer.valueOf(gridSizeArr[1]);
				
				if (x_size <= 0 || y_size <= 0) {
					throw new NumberFormatException();
				}
			}catch (ArrayIndexOutOfBoundsException | NumberFormatException ex){
				//Default size will be used in case of ineligible input
				x_size = -1;
				y_size = -1;
			}
			gameController.setGrid((Stage) titleLabel.getScene().getWindow(), x_size, y_size);
		});
		
		toggleFadeModeButton.setOnAction(e2 -> {
			GameController.fadeMode = !GameController.fadeMode;
		});
	}
	
	
	
	
}
