package com.warriermig.conrad;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.animation.*;
import javafx.util.Duration;

public class GUI extends Application {
	private Pane root;
	private Label title;
	private Button startButton;
	private double windowHeight;
	private double windowWidth;
	private DropShadow dropShadow;

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
	// private void installEventHandler(final Node keyNode) {
	// 	// handler for enter key press / release events, other keys are
	// 	// handled by the parent (keyboard) node handler
	// 	final EventHandler<MouseEvent> keyEventHandler =
	// 	new EventHandler<MouseEvent>() {
	// 		public void handle(final MouseEvent e) {
				
	// 		}
	// 	};

	// 	startButton.onMousePressed
	// }
	public void styleButton(){
		dropShadow = new DropShadow();
		dropShadow.setRadius(15.0);
		dropShadow.setOffsetX(0.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.rgb(0, 0, 0, 0.6));
		startButton.setEffect(dropShadow);
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
		styleButton();
		final Animation anim = new Transition(){
			{setCycleDuration(Duration.millis(100));}
			protected void interpolate (double frac){
				final int dist = 3;
				final int radius = 15;
				dropShadow.setOffsetY(frac*dist);
				dropShadow.setRadius(frac*radius);
				// startButton.setStyle("-fx-background-color: "+Color.color(1-frac, 1-frac, 1-frac).toString()+";");
				// startButton.setEffect(dropShadow);
			}
		};
		startButton.setOnMousePressed(e -> {
			dropShadow.setOffsetY(0.0);
			dropShadow.setRadius(0.0);
			// startButton.setStyle("-fx-background-color: #CCCCCC;");
		});
		startButton.setOnMouseReleased(e -> {
			anim.play();
		});
		
		title = new Label("Hello!");
		title.getStyleClass().add("text");
		title.setId("title");
		

		setupRegion();
		// t.setFontSmoothingType(FontSmoothingType.LCD);
		
	}

	@Override
	public void start(Stage primary){
		Scene s = new Scene(root, windowWidth, windowHeight);
		s.getStylesheets().add(getClass().getResource("gui.css").toString()); 
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