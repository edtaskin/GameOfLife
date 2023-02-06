package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Conway's Game of Life");
			Parent root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
			Scene startScene = new Scene(root);
			startScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(startScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
