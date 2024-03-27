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
import java.util.HashMap;


public class MainFrame extends JFrame {
    private JButton searchButton; // Declare searchButton as a field
    private ArrayList<String> selectedFiles = new ArrayList<>(); // List of selected files
    public String dirPath;
    private JPanel chartPanel;
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
        mainPanel.setBackground(Color.gray);
        this.add(mainPanel, BorderLayout.CENTER);

        // Main Grid
        JPanel mainGrid = new JPanel();
        mainGrid.setBackground(Color.gray);
        mainGrid.setLayout(new GridLayout(2,2));
        mainPanel.add(mainGrid, BorderLayout.CENTER);

        // Text box panel
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical arrangement
        JScrollPane scrollPane = new JScrollPane(textBoxPanel); // Add textBoxPanel to a JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.WEST); // Add to the left of main panel

        mainGrid.add(new JPanel());

        
        // Chart panel
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        mainGrid.add(chartPanel);



        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(Color.gray);
        mainGrid.add(searchPanel);

        // Add flow layout to bottom of search panel
        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout());
        searchPanel.add(flowPanel, BorderLayout.SOUTH);

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
        });
        flowPanel.add(searchButton); // Add without specifying any constraints

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

        // Creating a DefaultCategoryDataset for the bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String file : fileOccurrences.keySet()) {
            dataset.addValue(fileOccurrences.get(file), "Appearances", file);
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
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}