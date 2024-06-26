package com.doculyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCheck {
    public boolean caseMatch = false;
    public boolean wholeWordMatch = false;
    private HashMap<String, Integer> totalWords = new HashMap<>();
    private HashMap<String, Integer> fileOccurrences = new HashMap<>();

    public void setCaseMatch(boolean caseMatch) {
        System.out.println("Setting case match to " + caseMatch);
        this.caseMatch = caseMatch;
    }

    public void setWholeWordMatch(boolean wholeWordMatch) {
        System.out.println("Setting whole word match to " + wholeWordMatch);
        this.wholeWordMatch = wholeWordMatch;
    }

    public HashMap<String, Integer> checkWordInFile(String wordPattern, String[] selectedFiles) { // wordPattern is the word to search for
        
        totalWords.clear(); // clear the totalWords dictionary
        fileOccurrences.clear(); // clear the fileOccurrences dictionary

        if (!caseMatch) {
            wordPattern = "(?i)" + wordPattern; // case insensitive
        }

        if (wholeWordMatch) {
            wordPattern = "\\b" + wordPattern + "\\b"; // whole word match
        }

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

                    // Count the total words in the file
                    if (totalWords.containsKey(file)) {
                        totalWords.put(file, totalWords.get(file) + line.split(" ").length);
                    } else {
                        totalWords.put(file, line.split(" ").length);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileOccurrences;
    }
}
