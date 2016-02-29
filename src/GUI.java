import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GUI extends Application {
	private Pane root;
	private Label title;
	private Button startButton;
	private Rectangle r;
	private double windowHeight;
	private double windowWidth;

	public void setupRegion(){
		root.getChildren().addAll(title, startButton);
	}
	public void centerThings(){
		title.setLayoutX((windowWidth-title.prefWidth(-1))/2);
		title.setLayoutY((windowHeight-title.prefHeight(-1))/2-75);
		startButton.setPrefWidth(60);
		startButton.setPrefHeight(startButton.prefWidth(-1));
		startButton.setLayoutX((windowWidth-startButton.prefWidth(-1))/2);
		startButton.setLayoutY((windowHeight-startButton.prefHeight(-1))/2+50);

	}
	@Override
	public void init(){
		windowHeight = 300;
		windowWidth = 500;
		root = new Pane();
		root.setId("background");

		//Image micImage = new Image(getClass().getResourceAsStream("not.png"));
		startButton = new Button();
		startButton.setId("button");

		

		title = new Label("Hello!");
		title.getStyleClass().add("text");
		title.setId("title");
		

		setupRegion();
		// t.setFontSmoothingType(FontSmoothingType.LCD);
		
	}

	@Override
	public void start(Stage primary){
		Scene s = new Scene(root, windowWidth, windowHeight);
		s.getStylesheets().add("gui.css"); 
		s.setFill(Color.web("#1A237E"));
		primary.setScene(s);
		primary.setTitle("BARK!");
		primary.show();
		centerThings();
	}

	public static void main(String[] args){
		launch(args);
	}
}