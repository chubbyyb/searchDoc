package com.doculyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.text.similarity.CosineSimilarity;

public class SimilarityCheck {

    public boolean check(String[] bookPaths) {
        // String[] bookPaths = {"C:\\Users\\keith\\Documents\\oop
        // 2\\doculyzer\\sample\\new_carthage.txt", "C:\\Users\\keith\\Documents\\oop
        // 2\\doculyzer\\sample\\rome.txt"};
        boolean similar = false;

        try {
            List<Map<CharSequence, Integer>> bookContents = new ArrayList<>();

            // Read the content of each book
            for (String path : bookPaths) {
                Map<CharSequence, Integer> wordFrequencyMap = createWordFrequencyMap(path);
                bookContents.add(wordFrequencyMap);
            }

            // Calculate similarity between books
            double similarityThreshold = 0.75; // Define your threshold
            boolean sameTopic = checkSameTopic(bookContents, similarityThreshold);

            if (sameTopic) {
                similar = true;
            } else {
                similar = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return similar;
    }

    // Read text from file and create a word frequency map
    private static Map<CharSequence, Integer> createWordFrequencyMap(String filePath) throws IOException {
        Map<CharSequence, Integer> wordFrequencyMap = new HashMap<>();
        // stop words list from stopwords-en.txt
        // System.out.println(Paths.get("stopwords-en.txt").toAbsolutePath());
        Set<String> stopWords = new HashSet<>(
                Files.readAllLines(Paths.get("stopwords-en.txt")));
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Remove non-alphabetic characters and convert to lowercase
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    // Skip stop words - unused
                    if (stopWords.contains(word)) {
                        // print stop words
                        // System.out.println("Stop word: " + word);
                        continue;
                    }
                    wordFrequencyMap.merge(word, 1, Integer::sum);
                }
            }
        }

        return wordFrequencyMap;
    }

    // Check if books are about the same topic based on similarity threshold
    private static boolean checkSameTopic(List<Map<CharSequence, Integer>> wordFrequencyMaps, double threshold) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();

        for (int i = 0; i < wordFrequencyMaps.size() - 1; i++) {
            for (int j = i + 1; j < wordFrequencyMaps.size(); j++) {
                double similarityScore = cosineSimilarity.cosineSimilarity(wordFrequencyMaps.get(i),
                        wordFrequencyMaps.get(j));
                System.out.println("Similarity Score between document " + (i + 1) + " and document " + (j + 1) + ": "
                        + similarityScore);
                if (similarityScore < threshold) {
                    return false;
                }
            }
        }
        return true;
    }
}
