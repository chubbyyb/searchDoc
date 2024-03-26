import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public void initialize() {
        this.setTitle("Document Analyzer");
        this.setSize(500, 500);
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

        // Add select button at bottom of main panel
        JButton selectButton = new JButton("Select File");
        selectButton.setFont(new Font("Arial", Font.BOLD, 18));
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        selectButton.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        // Add listner to button
        selectButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println("Selected file path: " + filePath);
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
