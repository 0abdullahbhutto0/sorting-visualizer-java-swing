import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SortingVisualizer extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BAR_WIDTH = 4;
    private static final int ARRAY_SIZE = WIDTH / BAR_WIDTH;
    private int[] array = new int[ARRAY_SIZE];
    private final JButton bubbleSortButton = new JButton("Bubble Sort");
    private final JButton selectionSortButton = new JButton("Selection Sort");
    private final JButton insertionSortButton = new JButton("Insertion Sort");
    private final JButton quickSortButton = new JButton("Quick Sort");
    private final JButton mergeSortButton = new JButton("Merge Sort");
    private final JButton heapSortButton = new JButton("Heap Sort");
    private final JButton unsortButton = new JButton("Un-sort");
    private final JButton mainMenu = new JButton("Main Menu");
    private boolean isSorting = false;

    private JSlider speedSlider;
    private JButton pauseButton;
    private boolean isPaused = false;
    private int delay = 10; // Default delay

    public SortingVisualizer() {
        setPreferredSize(new Dimension(WIDTH, 790));
        setBackground(new Color(30, 30, 30)); // Dark background color
        initArray();
        setupGUI();
    }

    private void initArray() {
        Random rand = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = rand.nextInt(HEIGHT); // Random height for each bar
        }
    }

    private void setupGUI() {
        setLayout(new BorderLayout());

        // Button Panel with GridLayout to ensure buttons fit
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 5, 10, 10));
        buttonPanel.setBackground(new Color(40, 40, 40));

        speedSlider = new JSlider(0, 50, delay);
        speedSlider.addChangeListener(e -> delay = speedSlider.getValue());

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);

        bubbleSortButton.addActionListener(this);
        selectionSortButton.addActionListener(this);
        insertionSortButton.addActionListener(this);
        quickSortButton.addActionListener(this);
        mergeSortButton.addActionListener(this);
        heapSortButton.addActionListener(this);
        unsortButton.addActionListener(e -> unsortArray());
        mainMenu.addActionListener(this);

        // Styling the buttons
        styleButton(bubbleSortButton);
        styleButton(selectionSortButton);
        styleButton(insertionSortButton);
        styleButton(quickSortButton);
        styleButton(mergeSortButton);
        styleButton(heapSortButton);
        styleButton(unsortButton);
        styleButton(pauseButton);
        styleButton(mainMenu);
        speedSlider.setBackground(new Color(50, 50, 50));
        speedSlider.setForeground(Color.WHITE);

        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(selectionSortButton);
        buttonPanel.add(insertionSortButton);
        buttonPanel.add(quickSortButton);
        buttonPanel.add(mergeSortButton);
        buttonPanel.add(heapSortButton);
        buttonPanel.add(unsortButton);
        buttonPanel.add(speedSlider);
        buttonPanel.add(pauseButton);
        buttonPanel.add(mainMenu);
        add(buttonPanel, BorderLayout.SOUTH);



        // Initial Paint
        repaint();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50)); // Dark button background
        button.setFocusPainted(false); // Remove focus border
        button.setPreferredSize(new Dimension(140, 40)); // Button size
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (array != null) {
            g.setColor(new Color(0, 150, 255)); // Blue color for bars
            for (int i = 0; i < ARRAY_SIZE; i++) {
                g.fillRect(i * BAR_WIDTH, HEIGHT - array[i], BAR_WIDTH, array[i]);

                g.setColor(Color.BLACK); // Set color for outline
                g.drawRect(i * BAR_WIDTH, HEIGHT - array[i], BAR_WIDTH, array[i]); // Draw the outline

                g.setColor(new Color(0, 150, 255)); //Resetting the color of the bar to blue
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == pauseButton) {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Resume" : "Pause");
            return; // Important: Return to avoid starting a new sort
        }
        if(e.getSource()==mainMenu){
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
            new SplashScreen();
            return;
        }

        if (isSorting) return; // Prevent multiple sorts at once
        isSorting = true;

        // Run the sorting algorithms in background using SwingWorker which will create a separate thread to handle sorting
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
        } else if (e.getSource() == quickSortButton) {
            new SortWorker("Quick Sort") {
                @Override
                protected Void doInBackground() {
                    quickSort(0, ARRAY_SIZE - 1);
                    return null;
                }
            }.execute();
        } else if (e.getSource() == mergeSortButton) {
            new SortWorker("Merge Sort") {
                @Override
                protected Void doInBackground() {
                    mergeSort(0, ARRAY_SIZE - 1);
                    return null;
                }
            }.execute();
        } else if (e.getSource() == heapSortButton) {
            new SortWorker("Heap Sort") {
                @Override
                protected Void doInBackground() {
                    heapSort();
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
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            for (int j = 0; j < ARRAY_SIZE - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    try {
                        Thread.sleep(delay);
                        while (isPaused) {
                            Thread.sleep(100); // Check pause status periodically
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    SwingUtilities.invokeLater(this::repaint);
                }
            }
        }
    }

    private void selectionSort() {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < ARRAY_SIZE; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
            try {
                Thread.sleep(delay);
                while (isPaused) {
                    Thread.sleep(100); // Check pause status periodically
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::repaint);
        }
    }

    private void insertionSort() {
        for (int i = 1; i < ARRAY_SIZE; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && key < array[j]) {
                array[j + 1] = array[j];
                j--;
                try {
                    Thread.sleep(delay);
                    while (isPaused) {
                        Thread.sleep(100); // Check pause status periodically
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(this::repaint);
            }
            array[j + 1] = key;
            try {
                Thread.sleep(delay);
                while (isPaused) {
                    Thread.sleep(100); // Check pause status periodically
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::repaint);
        }
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                // Repaint after each swap!
                try {
                    Thread.sleep(delay);
                    while (isPaused) {
                        Thread.sleep(100); // Check pause status periodically
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(this::repaint);
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        try {
            Thread.sleep(delay);
            while (isPaused) {
                Thread.sleep(100); // Check pause status periodically
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        SwingUtilities.invokeLater(this::repaint);
        return i + 1;
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
            try {
                Thread.sleep(delay);
                while (isPaused) {
                    Thread.sleep(100); // Check pause status periodically
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::repaint);
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
            try {
                Thread.sleep(delay);
                while (isPaused) {
                    Thread.sleep(100); // Check pause status periodically
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::repaint);
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
            try {
                Thread.sleep(delay);
                while (isPaused) {
                    Thread.sleep(100); // Check pause status periodically
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::repaint);
        }
    }

    private void heapSort() {
        buildMaxHeap();
        for (int i = ARRAY_SIZE - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            maxHeapify(0, i);
            try {
                Thread.sleep(delay);
                while (isPaused) {
                    Thread.sleep(100); // Check pause status periodically
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(this::repaint);
        }
    }

    private void buildMaxHeap() {
        for (int i = ARRAY_SIZE / 2 - 1; i >= 0; i--) {
            maxHeapify(i, ARRAY_SIZE);
        }
    }

    private void maxHeapify(int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;
            maxHeapify(largest, n);
        }
    }

    private void unsortArray() {
        if (!isSorting) {
            initArray();
            repaint();
        }
    }

}
