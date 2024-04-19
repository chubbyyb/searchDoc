# Doculyzer: A Text Similarity Checker
Doculyzer is a Java-based tool that checks the similarity between two text documents. It uses cosine similarity to determine if the documents are about the same topic based on a specified similarity threshold.

## Package Overview
The main package for this project is com.doculyzer. This package contains the SimilarityCheck class, which is responsible for reading the text from files, creating word frequency maps, and checking the similarity between documents.

## Class Overview
SimilarityCheck
The SimilarityCheck class contains the following methods:

- `check(String[] bookPaths)`: This method takes an array of file paths as input and returns a boolean value indicating whether the documents are similar or not.

- `createWordFrequencyMap(String filePath)`: This method reads the text from a file and creates a word frequency map.

- `checkSameTopic(List<Map<CharSequence, Integer>> wordFrequencyMaps, double threshold)`: This method checks if the documents are about the same topic based on the similarity threshold.

## Usage
To use the Doculyzer tool, follow these steps:

1. Create a new instance of the SimilarityCheck class.
2. Call the check method, passing an array of file paths as input.
3. The check method will return a boolean value indicating whether the documents are similar or not.

Here's an example of how to use the Doculyzer tool:

```java
Copy code
SimilarityCheck similarityCheck = new SimilarityCheck();
String[] bookPaths = {"C:\\Users\\keith\\Documents\\oop 2\\doculyzer\\sample\\new_carthage.txt", "C:\\Users\\keith\\Documents\\oop 2\\doculyzer\\sample\\rome.txt"};
boolean similar = similarityCheck.check(bookPaths);
System.out.println("Are the documents similar? " + similar);
```

## Dependencies
Doculyzer uses the Apache Commons Text library for calculating cosine similarity. To use this library, add the following dependency to your pom.xml file:

```xml
Copy code
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-text</artifactId>
    <version>1.9</version>
</dependency>
```

## License
Doculyzer is released under the MIT License. See the LICENSE file for details.## Contributing