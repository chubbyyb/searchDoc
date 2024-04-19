# Doculyzer: A Text Similarity Checker
![image](https://github.com/chubbyyb/searchDoc/assets/79348344/426cc47c-3a95-4d84-989c-9004837895e4)

Doculyzer is a Java-based text analysis tool. It checks the similarity between two text files using cosine similarity based on a specified similarity threshold. It also checks which files has the most of a common word using a hashmap.

## Package Overview
The main package for this project is com.doculyzer. This package contains the;
- **SimilarityCheck class**, which is responsible for reading the text from files, creating word frequency maps, and checking the similarity between documents.
- **MainFrame class**, which is responsible for user input and the GUI
- **Wordcheck class**, which is responsible for ranking words based on occurences
- **app class**, which starts the application.

## Class Overview
SimilarityCheck
The SimilarityCheck class contains the following methods:

- `check(String[] bookPaths)`: This method takes an array of file paths as input and returns a boolean value indicating whether the documents are similar or not.

- `createWordFrequencyMap(String filePath)`: This method reads the text from a file and creates a word frequency map.

- `checkSameTopic(List<Map<CharSequence, Integer>> wordFrequencyMaps, double threshold)`: This method checks if the documents are about the same topic based on the similarity threshold.

Wordcheck
- `setCaseMatch(boolean caseMatch)`: Sets case sensitivity for word matching.
- `setWholeWordMatch(boolean wholeWordMatch)`: Sets whole word matching option.
- `checkWordInFile(String wordPattern, String[] selectedFiles)`: Searches for word pattern in selected files, returns file occurrences.

Mainframe
- `initialize()`: Initializes the main frame and sets up its components for the Document Analyzer application.
- `createChart(HashMap<String, Integer> fileOccurrences)`: Generates a bar chart based on file occurrences.
- `createStats(HashMap<String, Integer> fileOccurrences)`: Generates a pie chart displaying percentage of occurrences for each file.

## Usage

1. git clone
2. run app.java

Here's an example of how to use the similarity check using code:
```java
Copy code
SimilarityCheck similarityCheck = new SimilarityCheck();
String[] bookPaths = {"C:\\Users\\keith\\Documents\\oop 2\\doculyzer\\sample\\new_carthage.txt", "C:\\Users\\keith\\Documents\\oop 2\\doculyzer\\sample\\rome.txt"};
boolean similar = similarityCheck.check(bookPaths);
System.out.println("Are the documents similar? " + similar);
```

## Dependencies
Add the following dependency to your pom.xml file:

```xml
    <dependencies>
        <dependency>
        <groupId>org.jfree</groupId>
        <artifactId>jfreechart</artifactId>
        <version>1.5.3</version>
        </dependency>

        <dependency>
        <groupId>edu.stanford.nlp</groupId>
        <artifactId>stanford-corenlp</artifactId>
        <version>4.4.0</version>
        </dependency>
        
        <dependency>
        <groupId>edu.stanford.nlp</groupId>
        <artifactId>stanford-corenlp</artifactId>
        <version>4.4.0</version>
        <classifier>models</classifier>
        </dependency>

        <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-text</artifactId>
        <version>1.11.0</version>
        </dependency>

        <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.9.1</version>
        </dependency>

    </dependencies>
```

## License
Doculyzer is released under the MIT License. See the LICENSE file for details.

## Contributing
Stopwords were provided by https://github.com/stopwords-iso/stopwords-en under the MIT license
