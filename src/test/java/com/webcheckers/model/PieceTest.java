package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * Test for the piece class
 *
 * @author Jeffery Russell 11-1-18
 */
@Tag("Model-tier")
public class PieceTest
{

    /**
     * Test for the type getter
     */
    @Test
    public void testGetType()
    {
        Piece p = new Piece(BoardView.PieceEnum.SINGLE, Piece.ColorEnum.RED);
        assertEquals(p.getType(), BoardView.PieceEnum.SINGLE);
    }


    /**
     * Test for the color getter
     */
    @Test
    public void testGetColor()
    {
        Piece p = new Piece(BoardView.PieceEnum.SINGLE, Piece.ColorEnum.RED);
        assertEquals(p.getColor(), Piece.ColorEnum.RED);

        p = new Piece(BoardView.PieceEnum.SINGLE, Piece.ColorEnum.WHITE);
        assertEquals(p.getColor(), Piece.ColorEnum.WHITE);
    }


    /**
     * Test for hash code and equals function in {@link Piece}
     */
    @Test
    public void testHashAndEquals()
    {
        Piece p = new Piece(BoardView.PieceEnum.SINGLE, Piece.ColorEnum.RED);

        assertEquals(p,p);
        assertNotEquals(p, null);
        assertNotEquals(p, "Hello world!");

        Piece same = new Piece(BoardView.PieceEnum.SINGLE, Piece.ColorEnum.RED);

        assertEquals(same, p);
        assertEquals(same.hashCode(), p.hashCode());
    }

}
