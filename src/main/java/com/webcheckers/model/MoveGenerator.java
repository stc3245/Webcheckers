package com.webcheckers.model;


import java.util.ArrayList;
import java.util.List;

/**
 * File intended to make the operations of the AI
 * players easier by wrapping the get move functions
 * in the {@link MoveValidator} by getting all the
 * available positions on the board and then converting
 * the jump positions available into Moves rather than
 * positions.
 *
 * @author Jeffery Russell 11-21-18
 */
public class MoveGenerator
{
    /**
     * Returns a list of all the jump moves available to a player
     *
     * @param board current checkers board
     * @param currentColor current player's color
     * @return all jump moves available
     */
    public static List<Move> getAllJumpMoves(BoardView board, Piece.ColorEnum currentColor)
    {
        List<Move> jumpMoves = new ArrayList<>();
        for(Position start: board.getAllActivePositions())
        {
            if(board.getPiece(start).getColor() == currentColor)
            {
                List<Position> endJumpPositions = MoveValidator.getJumpMoves(board, start);

                endJumpPositions.forEach(p-> jumpMoves.add(new Move(start, p)));
            }
        }
        return jumpMoves;
    }


    /**
     * Returns a list of all the normal moves available for a player to
     * make
     * @param board current checkers board
     * @param currentColor current player's color
     * @return all normal moves
     */
    public static List<Move> getAllNormalMoves(BoardView board, Piece.ColorEnum currentColor)
    {
        List<Move> normalMoves = new ArrayList<>();
        for(Position start: board.getAllActivePositions())
        {
            if(board.getPiece(start).getColor() == currentColor)
            {
                List<Position> endJumpPositions = MoveValidator.getNormalMoves(board, start);

                endJumpPositions.forEach(p-> normalMoves.add(new Move(start, p)));
            }
        }
        return normalMoves;
    }
}
