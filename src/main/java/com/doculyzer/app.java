package com.doculyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class app {
    public HashMap<String, Integer> checkWordInFile(String wordPattern, String[] selectedFiles) {
        // make a dictionary of every file and how many times the word appears in it
        HashMap<String, Integer> fileOccurrences = new HashMap<>();

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(wordPattern);

        // for every selected file
        for (String file : selectedFiles) {
            try {
                // Read the file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Create matcher for line
                    Matcher matcher = pattern.matcher(line);
                    // Check if the line contains the word
                    while (matcher.find()) {
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
