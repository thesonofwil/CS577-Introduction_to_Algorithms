import java.util.Scanner;

/**
 * Filename:   MaxFlow.java
 * Project:    Assignment 10 - More Network Flow
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   04/13/22
 *
 * Determines what the maximum matching value is in a bipartite graph, as well as if the graph
 * has a perfect match. Algorithm based on article by GeeksforGeeks
 * https://www.geeksforgeeks.org/maximum-bipartite-matching/
 */
public class BipartiteMaxMatching {

    private int numNodesA;
    private int numNodesB;
    private int[][] adjMatrix;

    private BipartiteMaxMatching(int numNodesA, int numNodesB, int numEdges) {
        this.numNodesA = numNodesA;
        this.numNodesB = numNodesB;
        this.adjMatrix = new int[numNodesA][numNodesB];
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
     * Store the edge that connects two nodes into an adjacency matrix
     * 
     * @param nodeA the source node in A
     * @param nodeB the target node in B
     */
    private void addEdge(int nodeA, int nodeB) {
        this.adjMatrix[nodeA - 1][nodeB - 1] = 1;
    }

    /**
     * Get the max number of matches and therefore max flow for the graph.
     * 
     * @return the max flow
     */
    private int maxMatching() {
        int matches[] = new int[this.numNodesB]; // keep track of matches

        // Recursively find matches for each node in A
        int count = 0;
        for (int i = 0; i < this.numNodesA; i++) {
            int visited[] = new int[this.numNodesB];

            if (isMatch(visited, matches, i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * DFS function that matches a node in A to a node in B. If true, then there is an augmenting
     * flow path from s to t.
     * 
     * @param visited list of nodes visited passed by reference
     * @param matches list of nodes that have a match passed by reference
     * @param i the index of nodes in set A
     * 
     * @return true if there is a match for node Ai, false if not
     */
    private boolean isMatch(int visited[], int matches[], int i) {
        for (int j = 0; j < this.numNodesB; j++) {
            if (this.adjMatrix[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;

                if (matches[j] == 0 || isMatch(visited, matches, matches[j])) {
                    matches[j] = i;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the graph has a perfect match. A graph has a perfect match if for every node,
     * there is at least one edge incident to it.
     * 
     * @return true if perfect match
     */
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