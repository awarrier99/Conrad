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

//Prototype stuff:
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.warriermig.conrad.Prototype;

public class GUI extends Application {
	private Pane root;
	private Label title;
	private Button startButton;
	private double windowHeight;
	private double windowWidth;
	private DropShadow dropShadow;
	private Label wordsDisp;
	private ArrayList<String> words;
	private String wordString;
	
	private static LiveSpeechRecognizer recognizer;
	private static Configuration configuration;

	public void setupRegion(){
		root.getChildren().addAll(title, wordsDisp, startButton);
	}
	public void centerThings(){
		title.setLayoutX((windowWidth-title.prefWidth(-1))/2);
		title.setLayoutY((windowHeight-title.prefHeight(-1))/2-75);
		wordsDisp.setLayoutX((windowWidth-wordsDisp.prefWidth(-1))/2);
		wordsDisp.setLayoutY((windowHeight-wordsDisp.prefHeight(-1))/2-25);
		startButton.setPrefWidth(60);
		startButton.setPrefHeight(startButton.prefWidth(-1));
		startButton.setLayoutX((windowWidth-startButton.prefWidth(-1))/2);
		startButton.setLayoutY((windowHeight-startButton.prefHeight(-1))/2+50);
		
	}
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
		startButton.setOnAction(e -> {
			wordString = genWords();
			dispWords(wordString);
			Listener l = new Listener(recognizer);
			FutureTask<String> ft1 = new FutureTask<String>(l);
			String val = "";
			ExecutorService executor = Executors.newFixedThreadPool(1);
			
			executor.execute(ft1);
			while (true) {
	            try {
	                if(ft1.isDone()){
	                    //shut down executor service
	                    executor.shutdown();
	                    break;
	                }
	                if(!ft1.isDone()){
	                	val = ft1.get();
	                }
	            } catch (InterruptedException | ExecutionException ex) {
	                ex.printStackTrace();
	            }
	        }
//	        recognizer.startRecognition(true);
			//SpeechResult result;
//			result = recognizer.getResult();
//	        while ((result = recognizer.getResult()) != null) {
//	            System.out.println("You said: " + result.getHypothesis());
//	        }
			System.out.println("You said: " + val);
//	        recognizer.stopRecognition();
			validate(val);
			centerThings();
		});
		
		title = new Label("Hello!");
		wordsDisp = new Label("");
		wordsDisp.getStyleClass().add("text");
		wordsDisp.setId("word");
		title.getStyleClass().add("text");
		title.setId("title");
		

		setupRegion();
		// t.setFontSmoothingType(FontSmoothingType.LCD);
		
	}
	
	public void makeAllWords(){
		String buf = new String("");
        Scanner fin = null;
		try {
			fin = new Scanner(new File("res\\engdictedit.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        words = new ArrayList<String>();
        
        while (fin.hasNext()) {
        	buf = fin.nextLine();
        	words.add(buf);
        }
        fin.close();
	}
	
	public String genWords(){
		String[] keywords = {"", "", ""};
        Random random = new Random();
        for (int x = 0; x < 3; x++) {
        	keywords[x] = words.get(random.nextInt(words.size()));
        }
        
        String keys = keywords[0]+" "+keywords[1]+" "+keywords[2];
        return keys;
	}
	public void dispWords(String s){
		title.setText("Say the following");
		wordsDisp.setText(s);
		centerThings();
	}
	
	public void validate(String s){
		if(s.equals(wordString)){
			pass();
		}else{
			fail(s);
		}
	}
	
	public void pass(){
		root.setStyle("-fx-background-color: #4CAF50;");
		title.setText("Access Granted");
		wordsDisp.setText("");
	}
	
	public void fail(String s){
		root.setStyle("-fx-background-color: #F44336;");
		title.setText("Failed!");
		wordsDisp.setText("");
	}

	@Override
	public void start(Stage primary){
		Scene s = new Scene(root, windowWidth, windowHeight);
		makeAllWords();
		wordString = genWords();
		dispWords(wordString);
		s.getStylesheets().add(getClass().getResource("gui.css").toString()); 
		s.setFill(Color.web("#1A237E"));
		primary.setScene(s);
		primary.setTitle("BARK!");
		primary.show();
		centerThings();
	}

	public static void main(String[] args){
		configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("res\\2986.dic");
        configuration.setLanguageModelPath("res\\2986.lm");
        
        try{
			recognizer = new LiveSpeechRecognizer(configuration);
		}catch(IOException exception){
			System.out.println("Error");
		}

        
		launch(args);
	}
}

class Listener implements Callable<String> {
	private static LiveSpeechRecognizer recognizer;
	public Listener(LiveSpeechRecognizer recognizer){
		this.recognizer = recognizer;
	}
    public String call(){
    	recognizer.startRecognition(true);
		SpeechResult result;
		
		result = recognizer.getResult();
		String returnstr = "";
        while (returnstr == "") {
        	returnstr = result.getHypothesis();
        }
        System.out.println("In the thread it's " + returnstr);
        recognizer.stopRecognition();
        return returnstr;
    }

}

