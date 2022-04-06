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

    /**
     * Depth first search. Returns a path from the starting node to the end node. In the case of
     * a network flow, we'll use this to get the augmented path from s to t.
     * @param start
     */
    private Set<Integer> DFS(int start) {
        Set<Integer> unvisited = this.adjList.keySet();
        Set<Integer> visited = new HashSet<Integer>();
        Set<Integer> path = new HashSet<Integer>();

        DFSHelper(start, unvisited, visited, path);
        return path;
    }

    private void DFSHelper(int n, Set<Integer> unvisited, Set<Integer> visited, Set<Integer> path) {
        List<Integer> neighbors = adjList.get(n);

        for (int neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                DFSHelper(n, unvisited, visited, path);
            }
        }

        path.add(n);
        visited.add(n); // mark current node as visited
    }

    /**
     * Given a path consisting of nodes, get the minimum flow capacity
     * from the edges that connect the nodes
     */
    private int getMinCapacity(Set<Integer> path) {

        // convert path to array of nodes for ease
        Integer[] nodes = new Integer[path.size()];
        nodes = path.toArray(nodes);

        // find and store the edge for each node
        int minCapacity = Integer.MAX_VALUE;
        for (int i = 0; i < nodes.length - 1; i++) {
            Edge e = getEdge(nodes[i], nodes[i + 1]);
            if (e.capacity < minCapacity) {
                minCapacity = e.capacity;
            }
        }

        return minCapacity;
    }

    /**
     * Gets the edge that connects the source and destination nodes
     * @param source the starting node
     * @param destination the end node
     * @return the edge coming out of n, or null if no edge connects the two
     */
    private Edge getEdge(int source, int destination) {
        for (Edge e : edges) {
            if (e.source == source && e.destination == destination) {
                return e;
            }
        }

        return null;
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