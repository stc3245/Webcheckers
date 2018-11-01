package com.webcheckers.model;

/* Importing necessary elements from Junit */
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.BoardView.PieceEnum;
import com.webcheckers.model.Piece.ColorEnum;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for Space
 * @author Max Gusinov 10-24-18
 */
@Tag("Model-tier")
 public class SpaceTest
{
    /**
     * JUnit test for getCellIdx
     */
    @Test
    public void testgetCellIdx()
    {
        int ind = 3;
        Space tSpace = new Space(ind, null, ColorEnum.RED);
        assertEquals(ind, tSpace.getCellIdx());
    }


    /**
     * JUnit test for isValid
     */
    @Test
    public void testisValid()
    {
        Space tSpace = new Space(5, null, ColorEnum.RED);
        assertEquals(true, tSpace.isValid());
    }


    /**
     * JUnit test for getPiece
     */
    @Test
    public void testgetPiece()
    {
        Piece p = new Piece(PieceEnum.SINGLE, ColorEnum.RED);
        Space tSpace = new Space(4, p, ColorEnum.RED);

        assertEquals(p, tSpace.getPiece());
    }


    /**
     * JUnit test for setPiece
     */
    @Test
    public void testSetPiece()
    {
        Piece p = new Piece(PieceEnum.SINGLE, ColorEnum.WHITE);
        Piece q = new Piece(PieceEnum.SINGLE, ColorEnum.RED);
        Space s = new Space(4, p, ColorEnum.WHITE);
        s.setPiece(p);

        assertSame(p, s.getPiece());
    }


    /**
     * Test to see if the hashcode and equals
     * functions work on the space class.
     */
    @Test
    public void TestEqualsAndHash()
    {
        Space cut = new Space(4,
                new Piece(PieceEnum.SINGLE, ColorEnum.RED),
                ColorEnum.RED);

        Space same = new Space(4,
                new Piece(PieceEnum.SINGLE, ColorEnum.RED),
                ColorEnum.RED);

        assertEquals(cut, cut);
        assertEquals(cut, same);
        assertTrue(cut.hashCode() == same.hashCode());

        assertNotEquals(null, cut);
        assertNotEquals(42, cut);

        Space different = new Space(4,
                new Piece(PieceEnum.SINGLE, ColorEnum.WHITE),
                ColorEnum.RED);

        assertNotEquals(different, cut);
        assertNotEquals(different, "Hello World");
    }
 }