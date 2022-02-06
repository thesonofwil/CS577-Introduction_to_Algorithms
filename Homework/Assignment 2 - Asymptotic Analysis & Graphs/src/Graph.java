import java.util.ArrayList;
import java.util.List;

/**
 * Filename:   Graph.java
 * Project:    Assignment 1
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   02/9/22
 *
 * Undirected graph implementation. Note part of this code is from my own Graph implementation
 * from CS400.
 */

public class Graph {

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