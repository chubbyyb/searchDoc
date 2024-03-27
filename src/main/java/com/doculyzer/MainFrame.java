package com.doculyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.HashMap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



public class MainFrame extends JFrame {
    private JButton searchButton; // Declare searchButton as a field
    private ArrayList<String> selectedFiles = new ArrayList<>(); // List of selected files
    public String dirPath;
    private JPanel chartPanel;
    private JLabel strongestCount;
    private JLabel strongestMatch;
    private JPanel percentagePanel;
    app app = new app(); // Create an instance of the app class
    
    public void initialize() {
        this.setTitle("Document Analyzer");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);

        // Title
        JLabel titleLabel = new JLabel("Document Analyzer");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        // Add title panel to main frame
        this.add(titlePanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // Change layout to BorderLayout
        //mainPanel.setBackground(Color.gray);
        this.add(mainPanel, BorderLayout.CENTER);

        // Main Grid
        JPanel mainGrid = new JPanel();
        //mainGrid.setBackground(Color.gray);
        mainGrid.setLayout(new GridLayout(2,2));
        mainPanel.add(mainGrid, BorderLayout.CENTER);

        // Text box panel
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical arrangement
        JScrollPane scrollPane = new JScrollPane(textBoxPanel); // Add textBoxPanel to a JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.WEST); // Add to the left of main panel

        
        JPanel strongestPanel = new JPanel();
        strongestPanel.setLayout(new GridLayout(2, 2));
        //strongestPanel.setBackground(Color.gray);

        strongestMatch = new JLabel("File: ");
        strongestMatch.setFont(new Font("Arial", Font.BOLD, 13));
        strongestPanel.add(strongestMatch);

        strongestCount = new JLabel("Count: ");
        strongestCount.setFont(new Font("Arial", Font.BOLD, 13));
        strongestPanel.add(strongestCount);

        // add random buton
        percentagePanel = new JPanel();
        percentagePanel.setLayout(new BorderLayout());
        strongestPanel.add(percentagePanel);
        mainGrid.add(strongestPanel);
        
        // Chart panel
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());

        mainGrid.add(chartPanel);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());;
        mainGrid.add(searchPanel);

        // Add flow layout to bottom of search panel
        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout());
        searchPanel.add(flowPanel, BorderLayout.SOUTH);

        // add vertical panel to the LEFT of the search panel
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new GridLayout(2, 1));
        searchPanel.add(verticalPanel, BorderLayout.WEST);

        // add vertical panel to the right of the search panel
        JPanel verticalPanel2 = new JPanel();
        verticalPanel2.setLayout(new GridLayout(2, 1));
        searchPanel.add(verticalPanel2, BorderLayout.EAST);

        // add button to the vertical panel
        JButton regexBtn = new JButton("Regex Help");
        regexBtn.setPreferredSize(new Dimension(100, 30));
        // on click, send to website
        regexBtn.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://regex101.com/"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        verticalPanel2.add(regexBtn, BorderLayout.SOUTH);



        // Search Text box
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 30));
        flowPanel.add(textField); // Add without specifying any constraints

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setEnabled(false); // Initially disable the search button
        // Add listener that prints the text in the text box
        searchButton.addActionListener(e -> {
            if (textField.getText().isEmpty()) { // Check if text box is empty
                JOptionPane.showMessageDialog(this, "Please enter a search term", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("Searching for: " + textField.getText());
            // call checkWordInFile method from app class
            HashMap<String, Integer> occurences = app.checkWordInFile(textField.getText(), selectedFiles.toArray(new String[0]));
            // print the occurences
            for (String file : occurences.keySet()) {
                System.out.println(file + ": " + occurences.get(file));
            }
            createChart(occurences);
            createStats(occurences);
        });
        flowPanel.add(searchButton); // Add without specifying any constraints

        // add checkbox label on top of the text box
        JCheckBox caseMatchBtn = new JCheckBox("Case Match");
        // set to true
        caseMatchBtn.setSelected(true);
        app.setCaseMatch(true);

        caseMatchBtn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                app.setCaseMatch(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        verticalPanel.add(caseMatchBtn);

        // add whole word match checkbox under the checkbox label
        JCheckBox wholeWordMatchBtn = new JCheckBox("Whole Word Match");
        // set to true
        wholeWordMatchBtn.setSelected(true);
        app.setWholeWordMatch(true);
        wholeWordMatchBtn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                app.setWholeWordMatch(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        verticalPanel.add(wholeWordMatchBtn);
        

        mainGrid.add(new JPanel());

        // dirLabel
        JLabel dirLabel = new JLabel("Select a folder to analyze:");
        dirLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textBoxPanel.add(dirLabel);

        // Add select button to main panel
        JButton selectButton = new JButton("Select File");
        selectButton.setFont(new Font("Arial", Font.BOLD, 18));
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        selectButton.setPreferredSize(new Dimension(150, 50));

        // Add listener that lets you choose folders
        selectButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only allow directories
            fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.showOpenDialog(null);
            if (fileChooser.getSelectedFile() != null) {
                searchButton.setEnabled(false);
                dirLabel.setText(fileChooser.getSelectedFile().getName()); // Set label to the selected folder name
                dirPath = fileChooser.getSelectedFile().getAbsolutePath(); // Set dirPath to the selected folder path

                // for every text file in the folder
                String files[] = fileChooser.getSelectedFile().list();
                ArrayList<String> txtFiles = new ArrayList<>(); // List of text files
                for (String file : files) {
                    if (file.endsWith(".txt")) {
                        txtFiles.add(file);
                    }
                }

                // Remove existing checkboxes before adding new ones
                textBoxPanel.removeAll();

                // empty the selectedFiles list
                selectedFiles.clear();
                // Add all text files to a list with checkboxes
                for (String txtFile : txtFiles) {
                    JCheckBox checkBox = new JCheckBox(txtFile);
                    checkBox.addItemListener(new ItemListener() { // Add item listener to each checkbox
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) { // Add to selectedFiles if selected
                                System.out.println("Selected: " + txtFile);
                                selectedFiles.add(dirPath + '\\' + txtFile);
                            } else if (e.getStateChange() == ItemEvent.DESELECTED) { // Remove from selectedFiles if deselected
                                System.out.println("Deselected: " + txtFile);
                                selectedFiles.remove(dirPath + '\\' + txtFile);
                            }
                            System.out.println(selectedFiles);
                            // Enable search button only if there are selected files
                            searchButton.setEnabled(!selectedFiles.isEmpty());
                        }
                    });
                    textBoxPanel.add(checkBox);
                }

                // Repaint the panel to reflect the changes
                textBoxPanel.revalidate();
                textBoxPanel.repaint();
            }
        });

        mainPanel.add(selectButton, BorderLayout.SOUTH); // Add button to SOUTH position

        this.setVisible(true);
    }

    // take in HashMap<String, Integer> fileOccurrences and create a bar chart
    private void createChart(HashMap<String, Integer> fileOccurrences)
    {
        // Destroy old chart if exists
        chartPanel.removeAll();

        // get the highest value
        int highest = 0;
        String strongest = "";

        // Creating a DefaultCategoryDataset for the bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String file : fileOccurrences.keySet()) {
            int count = fileOccurrences.get(file);
            String fileName = file.substring(file.lastIndexOf("\\") + 1);
            // Strip extension from file name
            fileName = fileName.substring(0, fileName.lastIndexOf("."));

            // Check if the current count is the highest
            if (count > highest) {
                highest = count;
                strongest = fileName;
            }
            dataset.addValue(count, "Appearances", fileName);
        }
        
        // Creating the bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Appearences",      // chart title
                "Category",       // domain axis label
                "Value",          // range axis label
                dataset);         // data

        // Creating a ChartPanel to hold the chart
        ChartPanel chartPanelComponent = new ChartPanel(chart);
        chartPanel.add(chartPanelComponent, BorderLayout.CENTER);

        // Update the strongest match label
        strongestMatch.setText("File: " + strongest);
        strongestCount.setText("Count: " + highest);

        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private void createStats(HashMap<String, Integer> fileOccurrences)
    {
        // clear the panel
        percentagePanel.removeAll();

        int total = 0;
        for (String file : fileOccurrences.keySet()) {
            total += fileOccurrences.get(file);
        }

        // add pie chart to percentage panel
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String file : fileOccurrences.keySet()) {
            String fn = file.substring(file.lastIndexOf("\\") + 1);
            fn = fn.substring(0, fn.lastIndexOf("."));
            dataset.setValue(fn, fileOccurrences.get(file) * 100 / total);
        }

        JFreeChart chart = ChartFactory.createPieChart("File Occurrences", dataset);
        ChartPanel chartPanelComponent = new ChartPanel(chart);
        percentagePanel.add(chartPanelComponent);
        percentagePanel.revalidate();
        percentagePanel.repaint();

    }
}
