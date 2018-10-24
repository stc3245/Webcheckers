package com.webcheckers.model;

/* Importing necessary elements from Junit */
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.BoardView.PieceEnum;
import com.webcheckers.model.Piece.ColorEnum;

import org.eclipse.jetty.server.LocalConnector.LocalEndPoint;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for Space
 * @author Max Gusinov 10-24-18
 */

 public class SpaceTest {

    /**
     * JUnit test for getCellIdx
     */
    @Test
    public void testgetCellIdx() {
        int ind = 3;
        Space tSpace = new Space(ind, null, ColorEnum.RED);
        assertEquals(ind, tSpace.getCellIdx());
    }

    /**
     * JUnit test for isValid
     */
    @Test
    public void testisValid() {
        Space tSpace = new Space(5, null, ColorEnum.RED);
        assertEquals(true, tSpace.isValid());
    }

    /**
     * JUnit test for getPiece
     */
    @Test
    public void testgetPiece() {
        Piece p = new Piece(PieceEnum.SINGLE, ColorEnum.RED);
        Space tSpace = new Space(4, p, ColorEnum.RED);

        assertEquals(p, tSpace.getPiece());
    }
    //still need to check setPiece
 }