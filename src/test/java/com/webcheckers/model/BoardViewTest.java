package com.webcheckers.model;

/**
 * The Junit test suite for the {@link BoardView}
 *
 * @author Sean Coyne 10-24-2018
 */


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;

@Tag("Model-tier")
public class BoardViewTest {


    BoardView boardView;

    /**
     * test to make sure boardview iterator works correctly
     */
    @Test
    public void testGetIterator(){
        boardView = new BoardView();
        Iterator<Row> rowIterator = boardView.iterator();

        Row r = rowIterator.next();
        assertEquals(0, r.getIndex());
        assertEquals(true, rowIterator.hasNext());

    }

    /**
     * tests constructor of boardview
     */
    @Test
    public void testConstructor(){
        boardView = new BoardView();
        assertNotNull(boardView);
        testGetIterator();
    }

    /**
     * tests other constructor of boardview by building
     */
    @Test
    public void testOverloadConstructor(){
        ArrayList rowArrayList = new ArrayList<Row>(8);
        for(int i = 0; i < 8; i++)
        {
            rowArrayList.add(new Row(i));
        }
        boardView = new BoardView(rowArrayList);
        assertNotNull(boardView);
        testGetIterator();

    }

    /**
     * test boardview constructor when passed in null
     */
    @Test
    public void testOverloadConstructorGivenNull() {
        BoardView boardView2 = new BoardView(null);
        assertThrows(NullPointerException.class, () ->boardView2.iterator());
    }

    /**
     * tests to make sure getInverted function works correctly
     */
    @Test
    public void testGetInverted(){
        boardView = new BoardView();
        BoardView boardViewInverted = boardView.getInverted();

        Iterator<Row> rowIterator = boardViewInverted.iterator();

        Row r = rowIterator.next();
        assertEquals(0, r.getIndex());
        assertEquals(true, rowIterator.hasNext());

    }
}
