package com.webcheckers.model;


import java.util.Queue;

/**
 * @author Jeffery Russell
 */
public class MoveApplyer
{
    private static void applySingleMove(Move move, BoardView board)
    {
        Space startSpace = board.getTile(move.getStartPosition());

        Space finishSpace = board.getTile(move.getEndPosition());

        finishSpace.setPiece(startSpace.getPiece());
        startSpace.setPiece(null);
    }

    public static boolean jumpMovesAvailable(BoardView boardView, Piece.ColorEnum currentColor)
    {
        return false;
    }


    public static MoveValidator.MoveStatus applyMove(Queue<Move> moves, BoardView board)
    {
        if(moves.isEmpty())
        {
            return MoveValidator.MoveStatus.INVALID;
        }

        Space startSpace = board.getTile(moves.peek().getStartPosition());

        if(jumpMovesAvailable(board, startSpace.getPiece().getColor()))
        {
            return MoveValidator.MoveStatus.JUMP_REQUIRED;
        }

        while(!moves.isEmpty())
        {
            applySingleMove(moves.remove(), board);
        }

        return MoveValidator.MoveStatus.VALID;
    }

}
