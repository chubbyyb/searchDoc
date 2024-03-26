import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
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

        // Text box panel
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.setLayout(new BorderLayout());
        mainPanel.add(textBoxPanel, BorderLayout.WEST); // Add to the left of main panel

        // Text box
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 30));
        textBoxPanel.add(textField, BorderLayout.SOUTH);

        // dirLabel
        JLabel dirLabel = new JLabel("Select a folder to analyze:");
        dirLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textBoxPanel.add(dirLabel, BorderLayout.NORTH);


        // Add select button to main panel
        JButton selectButton = new JButton("Select File");
        selectButton.setFont(new Font("Arial", Font.BOLD, 18));
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        selectButton.setPreferredSize(new Dimension(150, 50));

        // Add listener that lets you choose folders
        selectButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(null);
            if (fileChooser.getSelectedFile() != null) {
            dirLabel.setText(fileChooser.getSelectedFile().getName());
            }
        });

        mainPanel.add(selectButton, BorderLayout.SOUTH); // Add button to SOUTH position

        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.initialize();
    }
}
