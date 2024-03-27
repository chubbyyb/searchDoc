import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class app {

    public void checkWordInFile(String word, String[] selectedFiles)
    {
        // print word and selected files
        System.out.println("Word: " + word);
        System.out.println("Selected files: ");
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
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
