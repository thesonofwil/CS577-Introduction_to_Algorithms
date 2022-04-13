import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Filename:   MaxFlow.java
 * Project:    Assignment 10 - More Network Flow
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   04/13/22
 *
 * Determines what the maximum matching value is in a bipartite graph, as well as if the graph
 * has a perfect match.
 * 
 * TODO: the biggest outostanding challenge is implementing the residual graph.
 * This could potentially be achieved through having two graph instances, but it
 * turns out that is quite difficult to do with nested classes.
 */
public class BipartiteMaxMatching {

    private int numNodesA;
    private int numNodesB;
    private int numEdges;
    private Set<Integer> nodeSetA;
    private Set<Integer> nodeSetB;
    // private Map<Integer, List<Integer>> adjListA; // k = node, v = nodes k points to
    // private Map<Integer, List<Integer>> adjListB;
    private int[][] adjMatrix;

    private BipartiteMaxMatching(int numNodesA, int numNodesB, int numEdges) {
        this.numNodesA = numNodesA;
        this.numNodesB = numNodesB;
        this.numEdges = numEdges;
        this.nodeSetA = new HashSet<Integer>();
        this.nodeSetB = new HashSet<Integer>();
        // this.adjListA = new HashMap<>(); // Edges originating from A
        // this.adjListB = new HashMap<>(); // Edges originating from B
        this.adjMatrix = new int[numNodesA][numNodesB];

    }

    /**
     * Representation of a directed edge with a flow capacity
     */
    private class Edge {
        private int source;
        private int destination;

        private Edge(int source, int destination, int capacity) {
            this.source = source;
            this.destination = destination;
        }
    }

    /**
     * Parse stdin and create instances of MaxFlow and edges
     */
    private static BipartiteMaxMatching[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        BipartiteMaxMatching instances[] = new BipartiteMaxMatching[numInstances];

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numNodesA = input.nextInt(); // Get number of nodes in set A
            int numNodesB = input.nextInt(); // Get number of nodes in set B
            int numEdges = input.nextInt();
            instances[numInstance] = new BipartiteMaxMatching(numNodesA, numNodesB, numEdges); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line

            // Add edges and connect from nodes in A to B
            while (numEdges > 0) {
                int nodeA = input.nextInt();
                int nodeB = input.nextInt();

                instances[numInstance].addEdge(nodeA, nodeB);
                numEdges--;
            }            
        }
        input.close();
        return instances;
    }

    /**
     * Store the edge that connects a node in set A to a node in set B
     * 
     * @param nodeA the source node in A
     * @param nodeB the target node in B
     */
    private void addEdge(int nodeA, int nodeB) {
        this.adjMatrix[nodeA - 1][nodeB - 1] = 1;
    }

    // /**
    //  * Depth first search. Returns a path from the starting node to the end node. In the case of
    //  * a network flow, we'll use this to get the augmented path from s to t.
    //  * 
    //  * @param start
    //  */
    // private Set<Integer> DFS(int start) {
    //     Set<Integer> unvisited = this.adjList.keySet();
    //     Set<Integer> visited = new HashSet<Integer>();
    //     Set<Integer> path = new HashSet<Integer>();

    //     DFSHelper(start, unvisited, visited, path);
    //     return path;
    // }

    // private void DFSHelper(int n, Set<Integer> unvisited, Set<Integer> visited, Set<Integer> path) {
    //     List<Integer> neighbors = adjList.get(n);

    //     for (int neighbor : neighbors) {
    //         Edge e = getEdge(n, neighbor);
    //         if (!visited.contains(neighbor) && e.capacity > 0) {
    //             DFSHelper(n, unvisited, visited, path);
    //         }
    //     }

    //     path.add(n);
    //     visited.add(n); // mark current node as visited
    // }

    // /**
    //  * Given a path consisting of nodes, get the minimum flow capacity
    //  * from the edges that connect the nodes.
    //  * 
    //  * @param pathNodes the set containing the nodes of the given path
    //  * @param path the set of edges of the path. Initially empty
    //  * @return the minimum capacity amongst the edges, as well as the set of edges found during
    //  * the path returned as reference
    //  */
    // private int getMinCapacity(Set<Integer> pathNodes, Set<Edge> path) {

    //     // convert path to array of nodes for ease
    //     Integer[] nodes = new Integer[pathNodes.size()];
    //     nodes = pathNodes.toArray(nodes);

    //     // find and store the edge for each node
    //     int minCapacity = Integer.MAX_VALUE;
    //     for (int i = 0; i < nodes.length - 1; i++) {
    //         Edge e = getEdge(nodes[i], nodes[i + 1]);
    //         path.add(e);
    //         if (e.capacity < minCapacity) {
    //             minCapacity = e.capacity;
    //         }
    //     }

    //     return minCapacity;
    // }

    private int maxMatching() {
        int match[] = new int[this.numNodesB];

        for (int i = 0; i < this.numNodesB; i++) {
            match[i] = -1;
        }

        int count = 0;
        for (int i = 0; i < this.numNodesA; i++) {
            int visited[] = new int[this.numNodesB];
            
            if (isPerfectMatch(visited, match, i)) {
                count++;
            }
        }

        return count;
    }

    private boolean isPerfectMatch(int visited[], int match[], int i) {
        for (int j = 0; j < this.numNodesB; j++) {
            if (this.adjMatrix[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;

                if (match[j] < 0 || isPerfectMatch(visited, match, match[j])) {
                    match[j] = i;
                    return true;
                }
            }
        }

        return false;
    }

    private char isPerfectMatch() {
        for (int i = 0; i < this.numNodesA; i++) {
            for (int j = 0; j < this.numNodesB; j++) {
                if (this.adjMatrix[i][j] == 0) {
                    return 'N';
                }
            }
        }

        return 'Y';
    }
    // /**
    //  * Checks if a bipartite graph has perfect matching. There is a perfect match if for every node,
    //  * there is at least one edge incident to it
    //  * @return
    //  */
    // private boolean isPerfectMatch() {

    //     // Check if each node has a non-null entry in map
    //     for (int nodeA : this.nodeSetA) {
    //         if (!this.adjListA.containsKey(nodeA)) {
    //             return false;
    //         }
    //     }

    //     for (int nodeB : this.nodeSetB) {
    //         if (!this.adjListB.containsKey(nodeB)) {
    //             return false;
    //         }
    //     }

    //     return true;
    // }

    public static void main(String[] args) {
        try {
            BipartiteMaxMatching[] instances = parse_input();
            for (BipartiteMaxMatching m : instances) {
                System.out.println(m.maxMatching() + " " + m.isPerfectMatch());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}