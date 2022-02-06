
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
 * Depth-first search implementation. This program takes in user input to create the graph(s) and
 * then prints out the DFS traversal of it. Note part of this code is from my own Graph 
 * implementation from CS400.
 */
public class DFS {

    public static class Graph {

        private List<Vertex> vertices;
        private List<String> verticesData;
    
        /*
         * Default no-argument constructor
         */
        public Graph() {
            vertices = new ArrayList<Vertex>();
            verticesData = new ArrayList<String>();
        }
    
        /**
         * Private inner vertex class
         *
         * @author Wilson Tjoeng
         *
         */
        private class Vertex {
            String data;
            List<String> adjacencies; // neighbors of the vertex
    
            /**
             * Vertex constructor 
             *
             * @param s String data to hold
             */
            Vertex (String s) {
                this.data = s;
                adjacencies = new ArrayList<String>();
            }
        }
    
        /**
         * Add new vertex to the graph.
         *
         * If vertex is null or already exists,
         * method ends without adding a vertex or 
         * throwing an exception.
         *
         * Valid argument conditions:
         * 1. vertex is non-null
         * 2. vertex is not already in the graph 
         */
        public void addVertex(String vertex) {
            Vertex v = new Vertex(vertex);
            vertices.add(v);
            verticesData.add(vertex);
        }
    
        /**
         * Updates the adjacency list of vertex1 by adding vertex2 to it.
         *
         */
        public void addEdge(String vertex1, String vertex2) {
    
            if (vertex1 == null || vertex2 == null) {
                return;
            }
    
            // Get vertex nodes
            Vertex v1 = getVertex(vertex1);
            v1.adjacencies.add(vertex2);
        }
    
        /**
         * Returns a List that contains all the vertices
         *
         */
        public List<String> getAllVertices() {
            return verticesData;
        }
    
        /**
         * Gets the first vertex in the graph, which should be lexographically the least
         * 
         * @return the first vertex
         */
        public String getFirstVertex() {
            Vertex v = vertices.get(0);
            return v.data;
        }
    
        /**
         * Get the adjacency list of a vertex 
         * 
         * @param vertex to get list for 
         * @return the adjacency list of vertex
         */
        public List<String> getAdjacencyList(String vertex) {
            Vertex v = getVertex(vertex);
            return v.adjacencies;
        }
    
        /////---------------- Private Helper Methods ----------------\\\\\
    
        /**
         * Gets the vertex node from graph given string
         *
         * @param data string to search for in vertex set
         * @return vertex object with the given string
         */
        private Vertex getVertex(String data) {
            for (Vertex v : vertices) {
                if (v.data.equals(data)) {
                    return v;
                }
            }
    
            return null;
        }
    }

    private static Graph[] graphs; // array of graphs 

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

    /**
     * To iterate through all nodes in a graph, even if disconnected, we'll need to implement
     * topological ordering. 
     * 
     * @param graph the graph to iterate through
     */
    private static void topOrder(Graph graph) {
        SortedSet<String> visited = new TreeSet<>(); // keep track of visited nodes
        List<String> vertices = graph.getAllVertices(); 
        StringBuilder result = new StringBuilder(); // store results here

        for (String vertex : vertices) {
            depthFirstSearch(graph, visited, vertex, result); // Start DFS on first node
        }
        String output = result.toString(); 
        System.out.print(output.substring(0, output.length() - 1)); // Get rid of trailing space
    }

    /**
     * Iterative depth-first search. Prints DFS order of graph.
     * 
     * @param graph the graph to search over
     * @param visited list of nodes already visited
     * @param vertex the central node we are traversing from
     * @param result DFS result stored in string builder
     */
    private static void depthFirstSearch(Graph graph, SortedSet<String> visited, String vertex, 
    StringBuilder result) {
        Stack<String> s = new Stack<String>();

        if (!visited.contains(vertex)) {
            s.push(vertex); // Push first node onto stack
            visited.add(vertex);
        } else { // We've already visited this node
            return;
        }
        
        while(!s.isEmpty()) {
            String u = s.pop();
            result.append(u + " ");
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
            System.out.print('\n');
        }
    }
}
