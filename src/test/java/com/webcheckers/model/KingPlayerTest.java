package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the king piece
 */
@Tag("Model-tier")
public class KingPlayerTest
{

    /**
     * tests that promote turns a single piece into a king
     */
    @Test
    public void testPromote()
    {
        Piece temp = new Piece(Piece.PieceEnum.SINGLE, Piece.ColorEnum.RED);
        temp.promote();
        assertTrue(temp.getType() == Piece.PieceEnum.KING);
    }


    /**
     * tests that the single red piece is actually promoted
     * when it reaches end of board
     */
    @Test
    public void testPromoteRedPieceAtEnd()
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
                /* 5 */ "  @  *  @  r  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  w  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                 /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Move singleMove = new Move(new Position(1,1),
                new Position(0,2));

        ArrayList<Move> moves = new ArrayList<>();
        moves.add(singleMove);

        MoveApplyer.applyMove(moves, board);
        assertSame(board.getTile(singleMove.getEndPosition()).getPiece().getType(),
                Piece.PieceEnum.KING);

    }


    /**
     * tests that the single white piece is actually
     * promoted when it reaches end of board
     */
    @Test
    public void testPromoteWhitePieceAtEnd()
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

        Move singleMove = new Move(new Position(6,4),
                new Position(7,3));

        ArrayList<Move> moves = new ArrayList<>();
        moves.add(singleMove);

        MoveApplyer.applyMove(moves, board);
        assertSame(board.getTile(singleMove.getEndPosition()).getPiece().getType(),
                Piece.PieceEnum.KING);

    }


    /**
     *  ensures kings can move how it is allowed to
     */
    @Test
    public void testMove()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  W  @  *  @  w  @  " +
                /* 1 */ "  @  *  @  r  @  *  @  r  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  W  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  r  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Position start = new Position(5,3);
        List<Position> positions =
                MoveValidator.getValidMovePositions(board, start);
        assertTrue(positions.contains(new Position(7,5)));
        assertTrue(positions.contains(new Position(6,2)));
        assertTrue(positions.contains(new Position(4,4)));
        assertTrue(positions.contains(new Position(4,2)));
    }


    /**
     * tests that a king can jump legally
     */
    @Test
    public void testApplyJumpMoveNormal()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  W  @  *  @  w  @  " +
                /* 1 */ "  @  *  @  r  @  *  @  r  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  r  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  W  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Move jumpMove = new Move(new Position(0,2),
                new Position(2,4));

        List<Move> moves = new ArrayList<>();
        moves.add(jumpMove);

        MoveApplyer.applyMove(moves, board);

        assertFalse(board.isOccupied(new Position(1,3)));

        assertFalse(board.isOccupied(new Position(0,2)));
        assertTrue(board.isOccupied(new Position(2,4)));

    }


    /**
     * tests king specific jump move
     */
    @Test
    public void testJumpMoveKing()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  W  @  *  @  w  @  " +
                /* 1 */ "  @  *  @  r  @  *  @  r  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  r  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  W  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Move jumpMove = new Move(new Position(5,3),
                new Position(3,1));

        List<Move> moves = new ArrayList<>();
        moves.add(jumpMove);

        MoveApplyer.applyMove(moves, board);

        assertFalse(board.isOccupied(new Position(4,2)));

        assertFalse(board.isOccupied(new Position(5,3)));
        assertTrue(board.isOccupied(new Position(3,1)));
    }

}
