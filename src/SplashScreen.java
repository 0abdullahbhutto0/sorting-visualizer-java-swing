import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SplashScreen extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(66, 139, 202);
    private static final Color HOVER_COLOR = new Color(51, 122, 183);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public SplashScreen() {
        setTitle("Sorting Visualizer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.DARK_GRAY);

        // Welcome Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Color.DARK_GRAY);
        welcomePanel.setBorder(new EmptyBorder(30, 30, 15, 30));

        JLabel titleLabel = new JLabel("Sorting Visualizer");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Choose your visualization type");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(titleLabel);
        welcomePanel.add(subtitleLabel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 20, 20));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30));

        JButton arrayButton = createStyledButton("Array Visualization", "View and sort arrays in real-time");
        JButton linkedListButton = createStyledButton("Linked List Visualization", "Visualize linked list sorting algorithms");

        arrayButton.addActionListener(e -> {
            JFrame frame = new JFrame("Sorting Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new SortingVisualizer());
            frame.pack();
            frame.setVisible(true);
            dispose();
        });

        linkedListButton.addActionListener(e -> {
            JFrame frame = new JFrame("List Sorting Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new LinkedListSortingVisualizer());
            frame.pack();
            frame.setVisible(true);
            dispose();
        });

        buttonPanel.add(arrayButton);
        buttonPanel.add(linkedListButton);

        add(welcomePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createStyledButton(String title, String description) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(10, 5));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(BUTTON_FONT);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(BUTTON_FONT);

        JLabel descLabel = new JLabel(description);
        descLabel.setForeground(new Color(220, 220, 220));
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        button.add(textPanel, BorderLayout.CENTER);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }
}