package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

//import application.PlayMenuControl.Rocket;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;


public class PlayMenuControl {
	private Stage stage;
	private Scene scene;
	private Parent root; 
	private Ship player;
	private List<Bullet>bullets;
	private List<Meteor>meteors;
	private static final Random RAND = new Random();
	private static final int WIDTH = 500; //width and height of canvas
	private static final int HEIGHT = 700;
	private static final int PLAYER_SIZE = 50; //this will be basically the hit box of player
	private double mouseX; 
	private static int score;
	private static int inequality;
	public GraphicsContext gc;
	
    static final Image PLAYER_IMG = new Image("https://i.ibb.co/PD1KSQp/Red-Fly-Ship.png");
    static final Image REDMETEOR = new Image("https://i.ibb.co/k63WNzJ/Multiply-Fire.png");
    static final Image BLUEMETEOR = new Image("https://i.ibb.co/5r8zbBF/divideball.png");
    static final Image PINKMETEOR = new Image("https://i.ibb.co/HTsc3zQ/AddBall.png");
    static final Image GREENMETEOR = new Image("https://i.ibb.co/gZLqLGM/Subtract-Ball.png");
    static final Image background = new Image("https://i.ibb.co/tpRfC5H/CommonBG.png"); //need to work on background
    
    static final Image METEOR_IMGS[]= {REDMETEOR, BLUEMETEOR, PINKMETEOR, GREENMETEOR }; //Meteor Images in array
	final int MAX_METEORS = 10,  MAX_SHOTS = MAX_METEORS * 2;
	boolean gameOver = false;
	
	public void startGame(ActionEvent event) throws IOException {
		
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setTitle("Math Ship");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        BorderPane root1 = new BorderPane(canvas);
        Scene mainScene = new Scene(root1, WIDTH, HEIGHT);
        
 
        //Created time line object with Duration set at 10 ms. it will run the Graphics Context
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX()); //gets positions of x of mouse cursor
        canvas.setOnMousePressed(e -> {
			if(bullets.size() < MAX_SHOTS) { //shots cannot exceed twice the amount of enemy objects
				bullets.add(player.shoot()); 
			}
			if(gameOver) { 
				gameOver = false;
				setup();
			}
		});
        
        setup();
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();
 
	}
	
	private void run(GraphicsContext gc) {
	    
		gc.setFill(Color.grayRgb(20));
		//gc.drawImage(background, 0, 0);

		//Temporary. ToDO: NEED TO ADD BACKGROUND
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(15));
		gc.setFill(Color.WHITE);
		gc.fillText("Score: " + score, 60,  20);
		gc.setFont(Font.font(30));
		gc.setFill(Color.YELLOW);
		gc.fillText(inequality + " < 100", 250, 40);
		
		if(gameOver || inequality > 100) { //If player dies or makes the inequality false
			gameOver = true;
			gc.setFont(Font.font(35));
			gc.setFill(Color.RED);
			gc.fillText("Game Over \n Your Score: " + score + "\n\n Click to Play Again", WIDTH / 2, HEIGHT /2.5);
		}
		else {
		player.update();
		player.draw(); //draws the players image
		

		if(mouseX < WIDTH - PLAYER_SIZE) {//this is here so that the player can't move off screen
		player.posX = (int) mouseX;
		}
		
		meteors.stream().peek(Ship::update).peek(Ship::draw).forEach(e -> { //checks if player collides 
			if(player.collide(e) && !player.exploding) {
				player.explode();
			}
		});
		
		for (int i = bullets.size() - 1; i >=0 ; i--) {
			Bullet shot = bullets.get(i);
			if(shot.posY < 0 || shot.toRemove)  { //checks if bullet collides or if it goes off screen
				bullets.remove(i);
				continue;
			}
			shot.update();
			shot.draw();
			
			for (Meteor bomb : meteors) { //METEOR MATH COLLIDING LOGIC PINK(add) RED(MULT) GREEN(SUB) PURPLE(divide)
				if(shot.colide(bomb) && !bomb.exploding && bomb.img == PINKMETEOR) {
					inequality++;
					score++;
					bomb.explode();
					shot.toRemove = true;
				}
				else if(shot.colide(bomb) && !bomb.exploding && bomb.img == BLUEMETEOR) {
					if(inequality/2 >= 1) {
					inequality = inequality/2;
					}
					score++;
					bomb.explode();
					shot.toRemove = true;
				}
				else if(shot.colide(bomb) && !bomb.exploding && bomb.img == REDMETEOR) {
					
					inequality = inequality*2;
					score++;
					bomb.explode();
					shot.toRemove = true;
				}
				else if(shot.colide(bomb) && !bomb.exploding && bomb.img == GREENMETEOR) {
					if(inequality- 1 >= 1) {
						inequality--;
					}
					score++;
					bomb.explode();
					shot.toRemove = true;
				}
			}
		}
		
		
		for (int i = meteors.size() - 1; i >= 0; i--){ //go through meteor list if it is destroyed create a new one  
			if(meteors.get(i).destroyed)  {
				meteors.set(i, newBomb());
			}
		}
		
		
		gameOver = player.destroyed; //if player is destroyed then the game is over

		}
		

	}
	
	private void setup() {
		bullets = new ArrayList <>();
		meteors = new ArrayList<>();
        player = new Ship(250, 650, PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        inequality = 1;
		IntStream.range(0, MAX_METEORS).mapToObj(i -> this.newBomb()).forEach(meteors::add);

	}
	
	public class Ship{ //Ship Player class

		int posX, posY, sizeX, sizeY;
		Image img;
		boolean exploding, destroyed;
		
		public Ship(int posX, int posY, int sizeX, int sizeY, Image image) {
			this.posX = posX;
			this.posY = posY;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			img = image;
		}
		public void update() {
			if(exploding) {
				destroyed = true;
			}
		}
		public void draw() {

				gc.drawImage(img, posX, posY, sizeX, sizeY);
			
		}
		
		public Bullet shoot() {
			return new Bullet(posX + sizeX / 2 - Bullet.size / 2, posY - Bullet.size);
		}
		
		public boolean collide(Ship other) {
			int d = distance(this.posX + sizeX / 2, this.posY + sizeY /2, 
							other.posX + other.sizeX / 2, other.posY + other.sizeY / 2);
			return d < other.sizeX / 2 + this.sizeX / 2 ;
		}
		public void explode() {
			exploding = true;
			
		}
	}

	
	
	
	
	public class Meteor extends Ship {
		
		int SPEED = (score/100)+2; //can change this to adjust difficulty
		
		public Meteor(int posX, int posY, int sizeX, int sizeY, Image image) {
			super(posX, posY, sizeX, sizeY, image);
		}
		
		public void update() {
			super.update();
			if(!exploding && !destroyed) {
				posY += SPEED;
			}
			if(posY > HEIGHT) {
				
				destroyed = true;
			}
		}
	}
	
	public class Bullet{
		
		public boolean toRemove;

		int posX, posY, speed = 10;
		static final int size = 6;
			
		public Bullet(int posX, int posY) {
			this.posX = posX;
			this.posY = posY;
		}

		public void update() {
			posY-=speed;
		}
		

		public void draw() {
			gc.setFill(Color.RED);
			gc.fillOval(posX, posY, size, size);
			
		}
		
		public boolean colide(Ship Rocket) {
			int distance = distance(this.posX + size / 2, this.posY + size / 2, 
					Rocket.posX + Rocket.sizeX / 2, Rocket.posY + Rocket.sizeY / 2);
			return distance  < Rocket.sizeX / 2 + size / 2;
		}

	}
	
	public Meteor newBomb() { //generates random bomb at a random x location
		return new Meteor(50 + RAND.nextInt(WIDTH - 100), 0, 50, 80, METEOR_IMGS[RAND.nextInt(METEOR_IMGS.length)]);
	}
	
	
	int distance(int x1, int y1, int x2, int y2) { //Distance formula
		return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
	

	
	
	public void toScoreboard(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/ScoreboardScene.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void toHelp(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/HelpScene.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void toMainScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("view/MainScene.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

}
