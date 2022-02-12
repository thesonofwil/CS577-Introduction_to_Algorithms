import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Filename:   DFS.java
 * Project:    Assignment 2 - Asymptotic Analysis & Graphs
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   02/9/22
 *
 * Depth-first search implementation. This program takes in user input to create the graph(s) and
 * then prints out the DFS traversal of it. Note part of this code is from my own Graph 
 * implementation from CS400.
 */
public class Greedy {

    public static class Job {
        private int startTime;
        private int endTime;

        public Job(int start, int end) {
            this.startTime = start;
            this.endTime = endh;
        }
    }

    private static void parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of components
        graphs = new Graph[numInstances]; // Allocate memory to create subgraphs

        // Keep looping according to the number of components
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            graphs[numInstance] = new Graph(); // Initialize graph
            int numNodes = input.nextInt(); // Get number of nodes per component
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Each line represents a vertex and its edges
            while (numNodes > 0) {
                String line = input.nextLine();
                String[] vertices = line.split(" "); // Split line by space
                if (vertices.length > 1) {
                    graphs[numInstance].addVertex(vertices[0]);
                    for (int v = 1; v < vertices.length; v++) {
                        graphs[numInstance].addEdge(vertices[0], vertices[v]);
                    }
                } else { 
                    graphs[numInstance].addVertex(vertices[0]);
                }
                numNodes--;
            }
        }

        input.close();
    }

    public static void main(String[] args) {
        parse_input();

    }
}