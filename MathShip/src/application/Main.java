package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/MainScene.fxml")); //loads the MainScene.fxml file which was built in scenebuilder
			Scene scene1 = new Scene(root); //scene1 is now a Scene with the MainScene.fxml content
			primaryStage.setScene(scene1); 
			primaryStage.setResizable(false); //Makes it so that the application cannot be resized
			primaryStage.setTitle("Math Ship"); //Sets the title of the stage to Math Ship
			primaryStage.show(); //shows the stage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args); //fallback in case the javafx application cannot run
	}
}
