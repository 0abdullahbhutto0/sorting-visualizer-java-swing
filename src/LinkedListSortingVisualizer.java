import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LinkedListSortingVisualizer extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NODE_SIZE = 40;
    private static final int LIST_SIZE = WIDTH / NODE_SIZE;
    private Node head = null;
    private boolean isSorting = false;
    private final JButton bubbleSortButton = new JButton("Bubble Sort");
    private final JButton selectionSortButton = new JButton("Selection Sort");
    private final JButton insertionSortButton = new JButton("Insertion Sort");
    private final JButton unsortButton = new JButton("Unsort");
    private final JButton mainMenu = new JButton("Main Menu");
    private JSlider speedSlider;
    private JButton pauseButton;
    private boolean isPaused = false;
    private int delay = 10; // Default delay
    private int[] colorArray = new int[LIST_SIZE]; // Array to track colors of the nodes

    public LinkedListSortingVisualizer() {
        setPreferredSize(new Dimension(950, 790));
        setBackground(new Color(30, 30, 30));
        initList();
        setupGUI();
        setVisible(true);
    }

    private void initList() {
        Random rand = new Random();
        head = null;
        // Initialize the linked list with random values
        for (int i = 0; i < LIST_SIZE; i++) {
            Node newNode = new Node(rand.nextInt(HEIGHT), null);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
            colorArray[i] = 0; // Default color (Unsorted)
        }
    }

    private void setupGUI() {
        setLayout(new BorderLayout());

        // GridLayout for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBackground(new Color(40, 40, 40));

        speedSlider = new JSlider(10, 100, delay);
        speedSlider.addChangeListener(e -> delay = speedSlider.getValue());

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);

        bubbleSortButton.addActionListener(this);
        selectionSortButton.addActionListener(this);
        insertionSortButton.addActionListener(this);
        unsortButton.addActionListener(e -> unsortList());
        mainMenu.addActionListener(this);


        styleButton(bubbleSortButton);
        styleButton(selectionSortButton);
        styleButton(insertionSortButton);
        styleButton(unsortButton);
        styleButton(pauseButton);
        styleButton(mainMenu);
        speedSlider.setBackground(new Color(50, 50, 50));
        speedSlider.setForeground(Color.WHITE);

        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(selectionSortButton);
        buttonPanel.add(insertionSortButton);
        buttonPanel.add(unsortButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(mainMenu);
        buttonPanel.add(speedSlider);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initial Paint
        repaint();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Node temp = head;
        int i = 0;
        while (temp != null) {
            // Set color based on the node's state (using colorArray)
            if (colorArray[i] == 0) {
                g.setColor(new Color(0, 150, 255)); // Blue for unsorted
            } else if (colorArray[i] == 1) {
                g.setColor(Color.YELLOW); // Yellow when comparing
            } else if (colorArray[i] == 2) {
                g.setColor(Color.RED); // Red when swapped
            } else if (colorArray[i] == 3) {
                g.setColor(Color.GREEN); // Green when sorted
            }
            // Draw the node as a circle
            /*X-co-ordinates denote where the node will be rendered on the x axis, i.e i=0, NODE SIZE=40=> first node will start at 0 pixels
            * Y-co-ordinates denote where the node will be rendered on the y axis, i,e HEIGHT=600, value=50, NODESIZE/20=> 530, hence the node will be rendered
            * on the bottom of the screen at 530 pixels.*/
            g.fillOval(i * NODE_SIZE, HEIGHT - temp.value - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);

            // Set the text color to white for visibility
            g.setColor(Color.BLACK);
            // Draw the value of the node in the center of the circle
            String value = String.valueOf(temp.value);
            FontMetrics metrics = g.getFontMetrics();
            int x = i * NODE_SIZE + (NODE_SIZE - metrics.stringWidth(value)) / 2;
            int y = HEIGHT - temp.value - NODE_SIZE / 2 + (NODE_SIZE + metrics.getHeight()) / 2;
            g.drawString(value, x, y);

            // Draw the next pointer line
            if (temp.next != null) {
                int currentX = i * NODE_SIZE + NODE_SIZE / 2;
                int currentY = HEIGHT - temp.value;
                int nextX = (i + 1) * NODE_SIZE + NODE_SIZE / 2;
                int nextY = HEIGHT - temp.next.value;
                g.setColor(Color.WHITE);
                g.drawLine(currentX, currentY, nextX, nextY); // Line from current node to next node
            }

            temp = temp.next;
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pauseButton) {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Resume" : "Pause");
            return; // Important: Return to avoid starting a new sort
        }
        if (e.getSource() == mainMenu) {
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
            new SplashScreen();
            return;
        }

        if (isSorting) return; // Prevent multiple sorts at once
        isSorting = true;

        // Run the sorting algorithms in background using SwingWorker
        if (e.getSource() == bubbleSortButton) {
            new SortWorker("Bubble Sort") {
                @Override
                protected Void doInBackground() {
                    bubbleSort();
                    return null;
                }
            }.execute();
        } else if (e.getSource() == selectionSortButton) {
            new SortWorker("Selection Sort") {
                @Override
                protected Void doInBackground() {
                    selectionSort();
                    return null;
                }
            }.execute();
        } else if (e.getSource() == insertionSortButton) {
            new SortWorker("Insertion Sort") {
                @Override
                protected Void doInBackground() {
                    insertionSort();
                    return null;
                }
            }.execute();
        }
    }

    // SwingWorker for handling sorting in background
    private abstract class SortWorker extends SwingWorker<Void, Void> {
        private final String sortType;

        SortWorker(String sortType) {
            this.sortType = sortType;
        }

        @Override
        protected void done() {
            isSorting = false; // Reset for next sort
        }
    }

    // Sorting Algorithms
    private void bubbleSort() {
        Node temp = head;
        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current != null && current.next != null) {
                // Set colors for comparison and swap
                colorArray[getNodeIndex(current)] = 1; // Yellow for comparison
                colorArray[getNodeIndex(current.next)] = 1;
                try { Thread.sleep(delay);
                    while (isPaused) {
                        Thread.sleep(100); // Check pause status periodically
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(this::repaint);

                if (current.value > current.next.value) {
                    // Swap nodes
                    int tempValue = current.value;
                    current.value = current.next.value;
                    current.next.value = tempValue;
                    swapped = true;

                    // Set colors for swap
                    colorArray[getNodeIndex(current)] = 2; // Red for swap
                    colorArray[getNodeIndex(current.next)] = 2;
                    try { Thread.sleep(delay);
                        while (isPaused) {
                            Thread.sleep(100); // Check pause status periodically
                        }
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                SwingUtilities.invokeLater(this::repaint);

                // Reset comparison colors
                colorArray[getNodeIndex(current)] = 0;
                colorArray[getNodeIndex(current.next)] = 0;
                current = current.next;
            }
        } while (swapped);

        // Set all nodes to green when sorted
        temp = head;
        while (temp != null) {
            colorArray[getNodeIndex(temp)] = 3; // Green for sorted
            temp = temp.next;
        }
        repaint();
    }

    private void selectionSort() {
        Node current = head;
        while (current != null) {
            Node minNode = current;
            Node tempNode = current.next;
            while (tempNode != null) {
                // Set colors for comparison
                colorArray[getNodeIndex(current)] = 1;
                colorArray[getNodeIndex(tempNode)] = 1;
                try { Thread.sleep(delay);
                    while (isPaused) {
                        Thread.sleep(100); // Check pause status periodically
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(this::repaint);

                if (tempNode.value < minNode.value) {
                    minNode = tempNode;
                }

                // Reset comparison colors
                colorArray[getNodeIndex(current)] = 0;
                colorArray[getNodeIndex(tempNode)] = 0;
                tempNode = tempNode.next;
            }

            if (minNode != current) {
                // Swap values
                int tempValue = current.value;
                current.value = minNode.value;
                minNode.value = tempValue;

                // Set colors for swap
                colorArray[getNodeIndex(current)] = 2; // Red for swap
                colorArray[getNodeIndex(minNode)] = 2;
                try { Thread.sleep(delay);
                    while (isPaused) {
                        Thread.sleep(100); // Check pause status periodically
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            SwingUtilities.invokeLater(this::repaint);

            current = current.next;
        }

        // Set all nodes to green when sorted
        Node temp = head;
        while (temp != null) {
            colorArray[getNodeIndex(temp)] = 3; // Green for sorted
            temp = temp.next;
        }
        repaint();
    }

    private void insertionSort() {
        Node current = head;
        while (current != null) {
            Node nextNode = current.next;
            while (nextNode != null) {
                // Set colors for comparison
                colorArray[getNodeIndex(current)] = 1;
                colorArray[getNodeIndex(nextNode)] = 1;
                try { Thread.sleep(delay);
                    while (isPaused) {
                        Thread.sleep(100); // Check pause status periodically
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(this::repaint);

                if (current.value > nextNode.value) {
                    // Swap values
                    int tempValue = current.value;
                    current.value = nextNode.value;
                    nextNode.value = tempValue;

                    // Set colors for swap
                    colorArray[getNodeIndex(current)] = 2; // Red for swap
                    colorArray[getNodeIndex(nextNode)] = 2;
                    try { Thread.sleep(delay);
                        while (isPaused) {
                            Thread.sleep(100); // Check pause status periodically
                        }
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    SwingUtilities.invokeLater(this::repaint);
                }

                // Reset comparison colors
                colorArray[getNodeIndex(current)] = 0;
                colorArray[getNodeIndex(nextNode)] = 0;
                nextNode = nextNode.next;
            }
            current = current.next;
        }

        // Set all nodes to green when sorted
        Node temp = head;
        while (temp != null) {
            colorArray[getNodeIndex(temp)] = 3; // Green for sorted
            temp = temp.next;
        }
        repaint();
    }

    private void unsortList() {
        // Reset the list and colors
        initList();
        repaint();
    }

    // LinkedList Node class
    private static class Node {
        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    // Helper method to find node index
    private int getNodeIndex(Node node) {
        int index = 0;
        Node temp = head;
        while (temp != null) {
            if (temp == node) return index;
            temp = temp.next;
            index++;
        }
        return -1; // Node not found
    }

}
