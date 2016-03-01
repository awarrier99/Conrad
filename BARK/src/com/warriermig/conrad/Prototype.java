package com.warriermig.conrad;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Prototype {       
                                     
    public static void main(String[] args) throws Exception {
        System.out.println("hello");
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        
        String buf = new String("");
        Scanner fin = new Scanner(new File("res\\engdict.txt")).useDelimiter(",\\s*");
        ArrayList<String> words = new ArrayList<String>();
        String[] keywords = {"", "", "", ""};
        
        while (fin.hasNext()) {
        	buf = fin.next();
        	words.add(buf);
        }
        fin.close();
        
        for (int x = 0; x < 3; x++) {
        	System.out.println();
        }
        
        
        
        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);

        recognizer.startRecognition(true);
        SpeechResult result = recognizer.getResult();
        System.out.println(result.getHypothesis());
        recognizer.stopRecognition();
    }
}