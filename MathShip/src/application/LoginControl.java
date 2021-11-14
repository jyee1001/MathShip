package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginControl {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	TextField usernameField;
	
	@FXML
	TextField passwordField;
	
	@FXML
	Button loginButton;
	
	public void toMainScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/MainScene.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void checkCredentials(){
		
	}
	

}
