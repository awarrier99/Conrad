package com.warriermig.conrad;

import javax.swing.JOptionPane;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Prototype implements Runnable {       
	public void run() {
		String buf = new String("");
        Scanner fin = null;
		try {
			fin = new Scanner(new File("res\\engdictedit.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        ArrayList<String> words = new ArrayList<String>();
        String[] keywords = {"", "", "", ""};
        
        while (fin.hasNext()) {
        	buf = fin.nextLine();
        	words.add(buf);
        }
        fin.close();
        
        Random random = new Random();
        for (int x = 0; x < 3; x++) {
        	keywords[x] = words.get(random.nextInt(words.size()));
        }
        
        String keys = "";
        for (String s: keywords) {
        	keys += s + ' ';
        }
        JOptionPane.showMessageDialog(null, "Say the following words: " + keys);
	}
	
    public static void main(String[] args) throws IOException {
    	Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("res\\2986.dic");
        configuration.setLanguageModelPath("res\\2986.lm");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        recognizer.startRecognition(true);
        SpeechResult result;
        (new Thread(new Prototype())).start();
        while ((result = recognizer.getResult()) != null) {
            JOptionPane.showMessageDialog(null, "You said: " + result.getHypothesis());
        }
        recognizer.stopRecognition();
    }
}