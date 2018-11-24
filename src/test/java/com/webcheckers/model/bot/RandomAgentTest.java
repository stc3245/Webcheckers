package com.webcheckers.model.bot;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Jeffery Russell 11-21-18
 */
@Tag("Model-tier")
public class RandomAgentTest
{
    /** Component under test */
    private RandomAgent cut;

    /**
     * Initalizes the cut
     */
    @BeforeEach
    public void setup()
    {
        cut = new RandomAgent();
    }


    /**
     * Makes sure that the bot makes a valid jump moves
     * when no jump moves are available
     */
    @Test
    public void makesValidNormalMove()
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
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        List<Move> moveList = cut.nextMove(board, Piece.ColorEnum.WHITE);

        MoveValidator.MoveStatus status = MoveApplyer.applyMove(moveList,board);

        assertTrue(status == MoveValidator.MoveStatus.VALID);
    }


    /**
     * Makes sure that the bot makes a jump move when
     * presented with one
     */
    @Test
    public void makesValidJumpMove()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  w  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  w  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  r  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        List<Move> moveList = cut.nextMove(board, Piece.ColorEnum.WHITE);

        MoveValidator.MoveStatus status = MoveApplyer.applyMove(moveList,board);

        assertTrue(status == MoveValidator.MoveStatus.VALID);
    }


    /**
     * Makes sure that the bot makes a double jump
     * move when it has the opportunity
     */
    @Test
    public void makesValidDoubleJumpMove()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  w  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  w  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
        /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        List<Move> moveList = cut.nextMove(board, Piece.ColorEnum.WHITE);

        MoveValidator.MoveStatus status = MoveApplyer.applyMove(moveList,board);

        assertTrue(status == MoveValidator.MoveStatus.VALID);
    }
}
