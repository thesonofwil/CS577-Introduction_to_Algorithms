import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

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

    private Graph graph;

    public void construct_graph(String filename) {
        try {
            File input = new File(filename);
            Scanner fileReader = new Scanner(input); // New scanner to read input

            // Parse file and add edges
            int numInstances = fileReader.nextInt();
            for (int instance = 0; instance < numInstance; instance++) {
                int numNodes = fileReader.nextInt();
                for (int node = 0; node < numNodes; node++) {
                    String line = fileReader.nextLine();
                    String[] vertices = line.split(" ");
                    if (vertices.length > 1) {
                        for (String v : vertices) {
                            graph.addEdge(vertices[0], v);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: file not found");
            return;
        }
    }
    }
}
