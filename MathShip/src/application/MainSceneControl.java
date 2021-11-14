package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneControl {
	private Stage stage;
	private Scene scene;
	private Parent root; 
	
	//These methods are used as the output when the user inputs via Button.
	/*Each method loads the specified fxml scene file, creates a new Stage,
	 sets the Scene to the Stage, then displays it changing the view for the user. 
	 */
	
	
	public void toLoginScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/LoginScene.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void toSignUpScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/SignUpScene.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void toPlayMenuScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/PlayMenu.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
