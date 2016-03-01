package com.warriermig.conrad;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Prototype {       
                                     
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        
        String buf = new String("");
        Scanner fin = new Scanner(new File("res\\engdict.txt"));
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
        
        for (String s: keywords) {
        	System.out.println(s);
        }
        
    }
}