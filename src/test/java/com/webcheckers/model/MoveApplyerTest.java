package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;


/**
 * Tester class for the move applyer class
 *
 * @author Jeffery Russell 10-30-18
 */
@Tag("Model-tier")
public class MoveApplyerTest
{
    /**
     * Makes sure that you can apply a simple
     * move correctly to a board view
     */
    @Test
    public void applyNormalMoveCorrectly()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  w  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  w  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  r  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  w  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Move singleMove = new Move(new Position(3,3),
                new Position(2,4));

        List<Move> moves = new ArrayList<>();
        moves.add(singleMove);

        MoveApplyer.applyMove(moves, board);
        assertTrue(board.isOccupied(new Position(2,4)));
        assertFalse(board.isOccupied(new Position(3,3)));

        singleMove = new Move(new Position(0,0),
                new Position(1,1));
        moves.clear();
        moves.add(singleMove);

        MoveApplyer.applyMove(moves, board);
        assertTrue(board.isOccupied(new Position(1,1)));
        assertFalse(board.isOccupied(new Position(0,0)));
    }


    /**
     * Makes sure that you can apply a jump
     * move correctly
     */
    @Test
    public void testApplyJumpMove()
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
        Move jumpMove = new Move(new Position(3,3),
                new Position(1,5));

        List<Move> moves = new LinkedList<>();
        moves.add(jumpMove);

        MoveApplyer.applyMove(moves, board);

        assertFalse(board.isOccupied(new Position(2,4)));

        assertFalse(board.isOccupied(new Position(3,3)));
        assertTrue(board.isOccupied(new Position(1,5)));
    }


    /**
     * Tests to see if the client is forced to make the full
     * multi-jump instead of just the first jump.
     */
    @Test
    public void forceFullMultiJump()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  w  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  r  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Move move = new Move(new Position(0,0),
                new Position(2,2));

        List<Move> moves = new ArrayList<>();
        moves.add(move);

        MoveValidator.MoveStatus stat = MoveApplyer.applyMove(moves, board);

        assertEquals(stat, MoveValidator.MoveStatus.JUMP_REQUIRED);
    }
}
