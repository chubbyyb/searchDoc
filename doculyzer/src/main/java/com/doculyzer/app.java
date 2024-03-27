package com.doculyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class app {
    public HashMap<String, Integer> checkWordInFile(String word, String[] selectedFiles)
    {
        // make a dictionary of every file and how many times the word appears in it
        HashMap<String, Integer> fileOccurrences = new HashMap<>();

        for (String file : selectedFiles) {
            System.out.println(file);
        }
        
        // for every selected file
        for (String file : selectedFiles) {
            try {
                // Read the file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Check if the line contains the word
                    if (line.contains(word)) {
                        System.out.println("Found in " + file + ": " + line);
                        // If the file is already in the dictionary, increment the count
                        if (fileOccurrences.containsKey(file)) {
                            fileOccurrences.put(file, fileOccurrences.get(file) + 1);
                        } else {
                            // Otherwise, add the file to the dictionary
                            fileOccurrences.put(file, 1);
                    }
                }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileOccurrences;     
    }
    
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.initialize();
    }
}
