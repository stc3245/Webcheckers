package com.webcheckers.model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Junit test for the move validator class
 *
 * @author Jeffery Russell 10-30-18
 */
@Tag("Model-tier")
public class MoveValidatorTest
{

    /** Copy pasta used to generate a checkers board */
    String boardString =
            /** '@'= white tile '*' = empty black tile */
            /** r = red, w = white, caps means king    */
            /**         White side of board      */
            /**         0  1  2  3  4  5  6  7   */
            /** 0 */ "  *  @  *  @  *  @  *  @  " +
            /** 1 */ "  @  *  @  *  @  *  @  *  " +
            /** 2 */ "  *  @  *  @  *  @  *  @  " +
            /** 3 */ "  @  *  @  *  @  *  @  *  " +
            /** 4 */ "  *  @  *  @  *  @  *  @  " +
            /** 5 */ "  @  *  @  *  @  *  @  *  " +
            /** 6 */ "  *  @  *  @  *  @  *  @  " +
            /** 7 */ "  @  *  @  *  @  *  @  *  ";
            /**         Red side of board       */


    /**
     * Tests to see if there is an opponent's
     * piece in a particular {@link Position}
     */
    @Test
    public void testHasOpponent()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  w  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  r  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  w  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Position p_red = new Position(3,3);
        Position p_white = new Position(1,7);
        Position p_empty = new Position(0,0);

        assertFalse(MoveValidator.hasOpponent(board, Piece.ColorEnum.RED, p_empty));

        assertTrue(MoveValidator.hasOpponent(board, Piece.ColorEnum.WHITE, p_red));
        assertFalse(MoveValidator.hasOpponent(board, Piece.ColorEnum.RED, p_red));

        assertFalse(MoveValidator.hasOpponent(board, Piece.ColorEnum.WHITE, p_white));
        assertTrue(MoveValidator.hasOpponent(board, Piece.ColorEnum.RED, p_white));
    }


    /**
     * Tests to make sure that you are
     * returning the correct list of jump
     * moves from the getJumpMoves function
     */
    @Test
    public void testGetJumpMoves()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  w  @  *  @  w  @  " +
                /* 1 */ "  @  *  @  r  @  *  @  r  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  w  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  r  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Position start = new Position(5,3);
        List<Position> positions =
                MoveValidator.getValidMovePositions(board, start);
        assertTrue(positions.contains(new Position(7,5)));
        assertTrue(positions.contains(new Position(6,2)));

        start = new Position(0,2);
        positions =
                MoveValidator.getValidMovePositions(board, start);
        assertTrue(positions.contains(new Position(2,4)));
        assertTrue(positions.contains(new Position(1,1)));
    }


    /**
     * Tests to make sure that you are not allowed
     * to jump your own piece
     */
    @Test
    public void testCantJumpSelf()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  r  @  r  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Position start = new Position(3,3);
        List<Position> positions =
                MoveValidator.getValidMovePositions(board, start);
        assert(positions.isEmpty());

        start = new Position(2,2);
        positions = MoveValidator.getValidMovePositions(board, start);
        assertFalse(positions.contains(new Position(0,0)));
        assertTrue(positions.contains(new Position(1,3)));
    }


    /**
     * Tests to see if the getValidMoves function
     * returns a list of all valid normal moves.
     */
    @Test
    public void testGetValidNormalMoves()
    {
        String boardString =
            /* '@'= white tile '*' = empty black tile */
            /* r = red, w = white, caps means king    */
            /*         White side of board      */
            /*         0  1  2  3  4  5  6  7   */
            /* 0 */ "  *  @  *  @  *  @  *  @  " +
            /* 1 */ "  @  *  @  *  @  *  @  *  " +
            /* 2 */ "  *  @  *  @  *  @  *  @  " +
            /* 3 */ "  @  *  @  *  @  *  @  *  " +
            /* 4 */ "  *  @  *  @  *  @  *  @  " +
            /* 5 */ "  @  *  @  r  @  *  @  *  " +
            /* 6 */ "  *  @  *  @  *  @  *  @  " +
            /* 7 */ "  @  *  @  *  @  *  @  *  ";
            /*         Red side of board       */

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
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  w  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Move jumpMove = new Move(new Position(3,3), new Position(1,5));

        MoveValidator.MoveStatus status = MoveValidator.validateMove(board, jumpMove);
        assertEquals(status, MoveValidator.MoveStatus.VALID);

        Move testRequireJumpMove = new Move(new Position(1,1), new Position(0,0));
        status = MoveValidator.validateMove(board, testRequireJumpMove);
        assertEquals(status, MoveValidator.MoveStatus.JUMP_REQUIRED);

        Move whiteJump = new Move(new Position(2,4), new Position(4,2));
        status = MoveValidator.validateMove(board, whiteJump);
        assertEquals(status, MoveValidator.MoveStatus.VALID);
    }


    /**
     * Tests to see if a invalid move is not
     * accepted by the ValidateMove function
     */
    @Test
    public void testInvalidMove()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Move randomMove = new Move(new Position(3,3),
                new Position(1,5));

        MoveValidator.MoveStatus status = MoveValidator.validateMove(board, randomMove);

        assertNotEquals(status, MoveValidator.MoveStatus.VALID);

        Move moveBackwardsRed = new Move(new Position(3,3),
                new Position(4,4));
        status = MoveValidator.validateMove(board, moveBackwardsRed);

        assertNotEquals(status, MoveValidator.MoveStatus.VALID);

        Move moveBackwardsWhite = new Move(new Position(3,3),
                new Position(4,4));
        status = MoveValidator.validateMove(board, moveBackwardsWhite);

        assertNotEquals(status, MoveValidator.MoveStatus.VALID);
    }


    /**
     * Tests to see if a position is valid for a checkers
     * game, it registers as an valid position by
     * the onBoard function
     */
    @Test
    public void testOnBoard()
    {
        assertTrue(MoveValidator.onBoard(new Position(0, 0)));
        assertTrue(MoveValidator.onBoard(new Position(5, 7)));
        assertTrue(MoveValidator.onBoard(new Position(7, 7)));
    }


    /**
     * Tests to see if a position is valid for a checkers
     * game, it registers as an valid position by
     * the onBoard function
     */
    @Test
    public void testOffBoard()
    {
        assertFalse(MoveValidator.onBoard(new Position(-1, 0)));
        assertFalse(MoveValidator.onBoard(new Position(0, -2)));
        assertFalse(MoveValidator.onBoard(new Position(8, 8)));
        assertFalse(MoveValidator.onBoard(new Position(8, 0)));
    }

}
