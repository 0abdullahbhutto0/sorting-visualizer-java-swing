# Sorting Algorithm Visualizer

A Java Swing-based application that provides interactive visualization of various sorting algorithms on both arrays and linked lists.

## 🎯 Objective

This project creates a comprehensive Sorting Algorithm Visualizer designed to help users understand how different sorting algorithms work through visual representation. The visualizer allows users to observe the step-by-step execution of sorting algorithms with interactive controls for enhanced learning.

## 📋 Description

The Sorting Algorithm Visualizer is a Java application built using the Swing framework that demonstrates various sorting algorithms through dynamic visual representations. Arrays are displayed as bars with heights corresponding to their values, while linked lists are shown as connected nodes. The application provides real-time visualization of the sorting process with user-controlled speed settings.

## 🔑 Key Features

Interactive Visualization: Watch sorting algorithms execute step-by-step

Speed Control: Adjust visualization speed using a slider

Pause/Resume: Control the sorting process flow

Dual Data Structure Support: Visualize sorting on both arrays and linked lists

Multiple Algorithms: Choose from various sorting algorithms

## 🔧 Supported Algorithms

## 🟦 Array Sorting Algorithms

Bubble Sort – Compare and swap adjacent elements

Insertion Sort – Insert elements into their correct position

Selection Sort – Select minimum/maximum and place in position

Merge Sort – Divide and conquer approach

Quick Sort – Partition-based sorting

Heap Sort – Binary heap-based sorting

## 🔗 Linked List Sorting Algorithms

Bubble Sort – Adapted for linked list traversal

Insertion Sort – Insert nodes in correct position

Selection Sort – Select and reposition nodes

## 🎨 Visualization Components

Array Bars: Elements drawn using fillRect() with drawRect() outlines

Node Representation: Linked list nodes visualized with fillOval()

Pointer Visualization: Array pointers shown using drawLine()

Dynamic Updates: paintComponent() method overridden for real-time display refresh

## 🖱️ User Interface Controls

Algorithm Selection Buttons: Trigger different sorting algorithms

Speed Control Slider: Adjust visualization speed using JSlider

Pause/Resume Functionality: Control sorting process execution

Interactive Controls: User-friendly interface for algorithm exploration

## ⚙️ Threading and Performance

SwingWorker Implementation: Prevents UI freezing during sorting operations

Threaded Execution: Sorting algorithms run on separate threads

Speed Control: Thread.sleep() duration adjusted via slider input

Responsive UI: Maintains interface responsiveness during visualization

## 🚀 Getting Started

## 📦 Prerequisites

Java Development Kit (JDK) 8 or higher

Java Swing library (included in standard JDK)

## 🧰 Installation

# Clone the repository
git clone https://github.com/yourusername/sorting-visualizer-java-swing.git

# Navigate to the project directory
cd sorting-visualizer-java-swing

# Compile the Java files
javac src/*.java

# Run the application
java -cp src Main

## 🎮 How to Use
Launch the Application: Run the main class to start the visualizer

Choose Data Structure: Select between array or linked list visualization

Select Algorithm: Click on the desired sorting algorithm button

Control Speed: Use the speed slider to adjust visualization pace

Pause/Resume: Use control buttons to pause or resume sorting

Observe: Watch the step-by-step sorting process unfold

## 🎨 Visualization Features
Color-coded Elements: Different colors represent various states (comparing, swapping, sorted)

Real-time Updates: See changes as they happen during the sorting process

Interactive Speed Control: Slow down to understand complex operations or speed up for quick overview

Clear Visual Indicators: Pointers and highlighting show current algorithm focus

## 📚 Educational Value
This visualizer serves as an excellent educational tool for:

Computer Science Students: Understanding algorithm complexity and behavior

Programming Enthusiasts: Learning different sorting approaches

Algorithm Analysis: Comparing performance characteristics visually

Teaching Aid: Instructors can use it to demonstrate sorting concepts

## 🤝 Contributing
Contributions are welcome! Here are ways you can contribute:

Add new sorting algorithms

Improve visualization graphics

Enhance user interface

Add algorithm complexity information

Fix bugs and optimize performance

## 📌 Development Guidelines

# Fork the repository

# Create a feature branch
git checkout -b feature/AmazingFeature

# Commit your changes
git commit -m "Add some AmazingFeature"

# Push to the branch
git push origin feature/AmazingFeature

# Open a Pull Request
