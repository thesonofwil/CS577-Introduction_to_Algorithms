
import java.util.ArrayList;
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
 * Undirected graph implementation. Note part of this code is from my own Graph implementation
 * from CS400.
 */
public class DFS {

    private static Graph[] graphs;

    // public void construct_graph(String filename) {
    //     try {
    //         File input = new File(filename);
    //         Scanner fileReader = new Scanner(input); // New scanner to read input

    //         // Parse file and add edges
    //         int numInstances = fileReader.nextInt();
    //         for (int instance = 0; instance < numInstances; instance++) {
    //             int numNodes = fileReader.nextInt();
    //             for (int node = 0; node < numNodes; node++) {
    //                 String line = fileReader.nextLine();
    //                 String[] vertices = line.split(" ");
    //                 if (vertices.length > 1) {
    //                     for (String v : vertices) {
    //                         graph.addEdge(vertices[0], v);
    //                 }
    //             }
    //         }
    //     } catch (FileNotFoundException e) {
    //         System.err.println("Error: file not found");
    //         return;
    //     }
    // }
    // }

    private static void parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of components
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numNodes = input.nextInt(); // Get number of nodes per component
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

    private static void DFS(Graph graph) {
        List<String> visited;
        Stack<String> s;
    }

    public static void main(String[] args) {
        parse_input();

        for (Graph graph : graphs) {
            DFS(graph);
        }
    }
}
