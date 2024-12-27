Objective:
The objective of this project is to create a Sorting Algorithm Visualizer in Java using Swing. The visualizer is designed to help users understand how different sorting algorithms work by visually representing their execution on both arrays and linked lists. The project provides the ability to pause, speed up, and slow down the sorting process for better comprehension.
	
Description:

The Sorting Algorithm Visualizer is a Java-based application that uses the Swing framework for graphical user interface (GUI) design. It implements various sorting algorithms that work on both arrays and linked lists. The visualizer displays the array and linked list elements as bars and nodes, respectively, while dynamically updating their positions during the sorting process. Users can interact with the visualizer by controlling the speed of sorting, pausing, and resuming the process.
The visualizer supports the following algorithms:
•	For Arrays: Bubble Sort, Insertion Sort, Selection Sort, Merge Sort, Quick Sort, and Heap Sort.
•	For Linked Lists: Bubble Sort, Insertion Sort, and Selection Sort.


Concepts Implemented:

Visualization:
•	Each bar of the array is drawn using fillRect and has an outline drawn using drawRect, and each node is drawn using fillOval representing a Node.
•	The bars are visualized with varying heights, corresponding to the values in the array, and the pointers in the array are also visualized using drawLine method of the paintComponent.
•	The paintComponent() method is overridden to refresh the display after each sorting step.

Control Buttons:
•	Buttons for triggering sorting algorithms, controlling speed via JSlider, and pausing/resuming the sorting process.


Threaded Execution:
•	The sorting algorithms are executed using SwingWorker to keep the sorting process from freezing the UI.
•	Sorting steps are visualized as the array is updated, with a Thread.sleep to control the speed of visualization, which can be adjusted using the slider.
