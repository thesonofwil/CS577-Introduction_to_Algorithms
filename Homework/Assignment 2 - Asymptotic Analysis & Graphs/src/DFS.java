
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

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
        graphs = new Graph[numInstances]; // Allocate memory to create subgraphs
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            graphs[numInstance] = new Graph(); // Initialize graph
            int numNodes = input.nextInt(); // Get number of nodes per component
            input.nextLine(); // Read the rest of the line to go to the next line
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

    /**
     * To iterate through all nodes in a graph, even if disconnected, we'll need to implement
     * topological ordering. 
     * 
     * @param graph the graph to iterate through
     */
    private static void topOrder(Graph graph) {
        SortedSet<String> visited = new TreeSet<>(); // keep track of visited nodes
        List<String> vertices = graph.getAllVertices(); 

        for (String vertex : vertices) {
            depthFirstSearch(graph, visited, vertex); // Start DFS on first node
        }
    }

    /**
     * Iterative depth-first search. Prints DFS order of graph.
     * 
     * @param graph the graph to search over
     * @param visited list of nodes already visited
     * @param vertex the central node we are traversing from
     */
    private static void depthFirstSearch(Graph graph, SortedSet<String> visited, String vertex) {
        Stack<String> s = new Stack<String>();

        if (!visited.contains(vertex)) {
            s.push(vertex); // Push first node onto stack
            visited.add(vertex);
        } else { // We've already visited this node
            return;
        }
        
        while(!s.isEmpty()) {
            String u = s.pop();
            System.out.print(u + " ");
            if (!visited.contains(u)) {
                visited.add(u);
            }

            // Add neighbors to stack
            List<String> neighbors = graph.getAdjacencyList(u);
            // Since we want lexographical order and neighbors are sorted, we need to push to 
            // stack backwards
            for (int i = neighbors.size() - 1; i >= 0; i--) { 
                String neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    s.push(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        parse_input();

        for (Graph graph : graphs) {
            topOrder(graph);
            System.out.println();
        }
    }
}
