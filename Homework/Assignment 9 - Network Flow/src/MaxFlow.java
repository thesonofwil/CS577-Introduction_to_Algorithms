import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Filename:   Knapsack.java
 * Project:    Assignment 8 - More Dynamic Programming
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   03/30/22
 *
 * Dynammic programming algorithm to solve the Knapsack problem. Given a set of items where each
 * item has a value and a weight, and a knapsack with a weight capacity, determine the optimal set
 * of items the knapsack should take and print out its overall value.
 */
public class MaxFlow {

    private int numNodes;
    private int numEdges;
    private Map<Integer, List<Integer>> adjList; // k = node, v = nodes k points to
    private List<Edge> edges;

    private MaxFlow(int numNodes, int numEdges) {
        this.numNodes = numNodes;
        this.numEdges = numEdges;
        this.adjList = new HashMap<>();
        this.edges = new ArrayList<Edge>();
    }

    /**
     * Representation of a directed edge with a flow capacity
     */
    private class Edge {
        private int source;
        private int destination;
        private int capacity;
        private int flow;

        private Edge(int source, int destination, int capacity) {
            this.source = source;
            this.destination = destination;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    /**
     * Parse stdin and create instances of MaxFlow and edges
     */
    private static MaxFlow[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        MaxFlow instances[] = new MaxFlow[numInstances];

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numNodes = input.nextInt(); // Get number of items per intance
            int numEdges = input.nextInt();
            instances[numInstance] = new MaxFlow(numNodes, numEdges); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line

            // Get the weight and value of item and add it to list
            while (numEdges > 0) {
                int sourceNode = input.nextInt();
                int targetNode = input.nextInt();
                int capacity = input.nextInt();

                instances[numInstance].addEdge(sourceNode, targetNode, capacity);
                numEdges--;
            }
        }
        input.close();
        return instances;
    }

    private void addEdge(int source, int target, int capacity) {
        Edge edge = new Edge(source, target, capacity);
        this.edges.add(edge);
        
        if (this.adjList.get(source) == null) { // map doesn't have node
            this.adjList.put(source, new ArrayList<Integer>()); // initialize new values list
            this.adjList.get(source).add(target); // add target as adjacency
        } else {
            this.adjList.get(source).add(target); // update neighbors
        }
    }

    private void DFS(int start) {
        Set<Integer> unvisited = this.adjList.keySet();
        Set<Integer> visited = new HashSet<Integer>();
    }

    private void DFSHelper(int n, Set<Integer> unvisited, Set<Integer> visited) {

    }

    public static void main(String[] args) {
        try {
            MaxFlow[] instances = parse_input();
            for (MaxFlow m : instances) {
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}