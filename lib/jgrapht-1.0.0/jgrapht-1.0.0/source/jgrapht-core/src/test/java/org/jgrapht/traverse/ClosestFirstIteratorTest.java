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
package org.jgrapht.traverse;

import org.jgrapht.*;
import org.jgrapht.graph.*;

/**
 * Tests for ClosestFirstIterator.
 *
 * @author John V. Sichi
 * @since Sep 3, 2003
 */
public class ClosestFirstIteratorTest
    extends AbstractGraphIteratorTest
{
    // ~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testRadius()
    {
        result = new StringBuffer();

        DirectedGraph<String, DefaultEdge> graph = createDirectedGraph();

        // NOTE: pick 301 as the radius because it discriminates
        // the boundary case edge between v7 and v9
        AbstractGraphIterator<String, ?> iterator = new ClosestFirstIterator<>(graph, "1", 301);

        while (iterator.hasNext()) {
            result.append(iterator.next());

            if (iterator.hasNext()) {
                result.append(',');
            }
        }

        assertEquals("1,2,3,5,6,7", result.toString());
    }

    /**
     * .
     */
    public void testNoStart()
    {
        result = new StringBuffer();

        DirectedGraph<String, DefaultEdge> graph = createDirectedGraph();

        AbstractGraphIterator<String, ?> iterator = new ClosestFirstIterator<>(graph);

        while (iterator.hasNext()) {
            result.append(iterator.next());

            if (iterator.hasNext()) {
                result.append(',');
            }
        }

        assertEquals("1,2,3,5,6,7,9,4,8,orphan", result.toString());
    }

    // NOTE: the edge weights make the result deterministic
    @Override
    String getExpectedStr1()
    {
        return "1,2,3,5,6,7,9,4,8";
    }

    @Override
    String getExpectedStr2()
    {
        return getExpectedStr1() + ",orphan";
    }

    @Override
    AbstractGraphIterator<String, DefaultEdge> createIterator(
        DirectedGraph<String, DefaultEdge> g, String vertex)
    {
        AbstractGraphIterator<String, DefaultEdge> i = new ClosestFirstIterator<>(g, vertex);
        i.setCrossComponentTraversal(true);

        return i;
    }
}

// End ClosestFirstIteratorTest.java
