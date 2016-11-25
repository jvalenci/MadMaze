/*
 * (C) Copyright 2003-2016, by John V Sichi and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.graph;

import java.util.*;

import org.jgrapht.*;

/**
 * A unit test for the AsDirectedGraph view.
 *
 * @author John V. Sichi
 */
public class AsUndirectedGraphTest
    extends EnhancedTestCase
{
    // ~ Instance fields --------------------------------------------------------

    private DirectedGraph<String, DefaultEdge> directed;
    private DefaultEdge loop;
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    private UndirectedGraph<String, DefaultEdge> undirected;

    // ~ Constructors -----------------------------------------------------------

    /**
     * @see junit.framework.TestCase#TestCase(java.lang.String)
     */
    public AsUndirectedGraphTest(String name)
    {
        super(name);
    }

    // ~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testAddEdge()
    {
        try {
            undirected.addEdge(v3, v4);
            assertFalse();
        } catch (UnsupportedOperationException e) {
            assertTrue();
        }

        assertEquals(
            "([v1, v2, v3, v4], [{v1,v2}, {v2,v3}, {v2,v4}, {v4,v4}])", undirected.toString());
    }

    /**
     * .
     */
    public void testAddVertex()
    {
        String v5 = "v5";

        undirected.addVertex(v5);
        assertEquals(true, undirected.containsVertex(v5));
        assertEquals(true, directed.containsVertex(v5));
    }

    /**
     * .
     */
    public void testDegreeOf()
    {
        assertEquals(1, undirected.degreeOf(v1));
        assertEquals(3, undirected.degreeOf(v2));
        assertEquals(1, undirected.degreeOf(v3));
        assertEquals(3, undirected.degreeOf(v4));
    }

    /**
     * .
     */
    public void testGetAllEdges()
    {
        Set<DefaultEdge> edges = undirected.getAllEdges(v3, v2);
        assertEquals(1, edges.size());
        assertEquals(directed.getEdge(v2, v3), edges.iterator().next());

        edges = undirected.getAllEdges(v4, v4);
        assertEquals(1, edges.size());
        assertEquals(loop, edges.iterator().next());
    }

    /**
     * .
     */
    public void testGetEdge()
    {
        assertEquals(directed.getEdge(v1, v2), undirected.getEdge(v1, v2));
        assertEquals(directed.getEdge(v1, v2), undirected.getEdge(v2, v1));

        assertEquals(directed.getEdge(v4, v4), undirected.getEdge(v4, v4));
    }

    /**
     * .
     */
    public void testRemoveEdge()
    {
        undirected.removeEdge(loop);
        assertEquals(false, undirected.containsEdge(loop));
        assertEquals(false, directed.containsEdge(loop));
    }

    /**
     * .
     */
    public void testRemoveVertex()
    {
        undirected.removeVertex(v4);
        assertEquals(false, undirected.containsVertex(v4));
        assertEquals(false, directed.containsVertex(v4));
    }

    /**
     * .
     */
    @Override
    protected void setUp()
    {
        directed = new DefaultDirectedGraph<>(DefaultEdge.class);
        undirected = new AsUndirectedGraph<>(directed);

        directed.addVertex(v1);
        directed.addVertex(v2);
        directed.addVertex(v3);
        directed.addVertex(v4);
        directed.addEdge(v1, v2);
        directed.addEdge(v2, v3);
        directed.addEdge(v2, v4);
        loop = directed.addEdge(v4, v4);
    }
}

// End AsUndirectedGraphTest.java
