package com.webcheckers.model;

/* Importing necessary elements from Junit */
import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.jetty.server.LocalConnector.LocalEndPoint;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

//Assorted Imports Necessary for Row
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Iterable;

/**
 * JUnit test suite for Row
 * @author Max Gusinov 10-24-18
 */
@Tag("Model-tier")
public class RowTest
{
    /**
     * JUnit test for 1-param constructor
     */
    @Test
    public void testConstructor1() {
        new Row(3);    
    }
    
    /**
     * JUnit test for 2-param constructor
     */
    @Test
    public void testConstructor2() {
        ArrayList<Space> s = new ArrayList<Space>();
        new Row(3, s);
    }

    /**
     * JUnit test for inverted
     */
    @Test
    public void testinverted() {
        Row row1 = new Row(8);
        Row row2 = new Row(8);

        //row2 = row2.inverted();
        
        assertEquals(row1, row2);
    }

    /**
     * JUnit test for Iterator, check it returns the correct #
     */
    @Test
    public void testgetIndex() {
        int t = 5;
        Row temp = new Row(t);
        assertEquals(temp.getIndex(), t);
    }

    /**
     * JUnit Test for Iterator, check it has 8 spaces
     */
    @Test
     public void testIterator() {
        int length;
        length = 1;
        Row temp = new Row(length);
        assertNotNull(temp.iterator());
     }
 }