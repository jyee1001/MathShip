package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelpSceneController implements Initializable {

    @FXML
    private ImageView spaceShip1;
    //private Button rightArrowButton;
    //private Button leftArrowButton;
    //private Button spacebarButton;

    private Stage stage;
	private Scene scene;
	private Parent root; 

    public void toPlayMenuScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/PlayMenu.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

    //@Override
    public void initialize(URL location, ResourceBundle resources) {

        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(new Double[]{
            50.0, 40.0,
            120.0, 40.0, 
            50.0, 40.0, 
            -50.0, 40.0, 
            50.0, 40.0 
        });
        
        
        //Left Right Key translation
        PathTransition translate = new PathTransition();
        translate.setNode(spaceShip1);
        translate.setDuration(Duration.seconds(5));
        translate.setPath(polyline);
        //translate.setAutoReverse(true);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.play();


        //Shooting Animation
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(spaceShip1);
        rotate.setByAngle(180);
        rotate.setAxis(Rotate.Y_AXIS);
        //rotate.play();
        
        
    }

      
}
