package com.webcheckers.model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Junit test for the move validator class
 *
 * @author Jeffery Russell 10-30-18
 */
public class MoveValidatorTest
{

    String boardString =
            /**         0  1  2  3  4  5  6  7*/
            /** 0 */ "  *  *  *  *  *  *  *  *  " +
            /** 1 */ "  *  *  *  *  *  *  *  *  " +
            /** 2 */ "  *  *  *  *  *  *  *  *  " +
            /** 3 */ "  *  *  *  *  *  *  *  *  " +
            /** 4 */ "  *  *  *  *  *  *  *  *  " +
            /** 5 */ "  *  *  *  *  *  *  *  *  " +
            /** 6 */ "  *  *  *  *  *  *  *  *  " +
            /** 7 */ "  *  *  *  *  *  *  *  *  ";
    /**
     * Tests to see if there is an opponent's
     * piece in a particular {@link Position}
     */
    @Test
    public void testHasOpponent()
    {

    }


    /**
     * Tests to make sure that you are
     * returning the correct list of jump
     * moves from the getJumpMoves function
     */
    @Test
    public void testGetJumpMoves()
    {

    }


    /**
     * Tests to make sure that you are not allowed
     * to jump your own piece
     */
    @Test
    public void testCantJumpSelf()
    {

    }


    /**
     * Tests to see if the getValidMoves function
     * returns a list of all valid moves.
     */
    @Test
    public void testGetValidMoves()
    {
        String boardString =
            /**         White side of board      */
            /**         0  1  2  3  4  5  6  7   */
            /** 0 */ "  *  @  *  @  *  @  *  @  " +
            /** 1 */ "  @  *  @  *  @  *  @  *  " +
            /** 2 */ "  *  @  *  @  *  @  *  @  " +
            /** 3 */ "  @  *  @  *  @  *  @  *  " +
            /** 4 */ "  *  @  *  @  *  @  *  @  " +
            /** 5 */ "  @  *  @  r  @  *  @  *  " +
            /** 6 */ "  *  @  *  @  *  @  *  @  " +
            /** 7 */ "  @  *  @  *  @  *  @  *  ";
            /**         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Position start = new Position(5,3);

        List<Position> positions =
                MoveValidator.getValidMovePositions(board, start);

        assertTrue(positions.contains(new Position(4,2)));

        assertTrue(positions.contains(new Position(4,4)));
    }


    /**
     * Tests to see if a valid move is accepted by
     * the validateMove function
     */
    @Test
    public void testValidateMove()
    {

    }


    /**
     * Tests to see if a invalid move is not
     * accepted by the ValidateMove function
     */
    @Test
    public void testInvalidMove()
    {

    }


    /**
     * Tests to see if a position is valid for a checkers
     * game, it registers as an valid position by
     * the onBoard function
     */
    @Test
    public void testOnBoard()
    {
        assertTrue(true);
    }


    /**
     * Tests to see if a position is valid for a checkers
     * game, it registers as an valid position by
     * the onBoard function
     */
    @Test
    public void testOffBoard()
    {

    }

}
