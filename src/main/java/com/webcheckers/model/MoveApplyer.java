package com.webcheckers.model;


import java.util.Queue;

/**
 * @author Jeffery Russell 10-28-18
 */
public class MoveApplyer
{

    private static Position getPositionBetween(Position p1, Position p2)
    {
        int rowDiff = p1.getRow() - p2.getRow();
        int colDiff = p1.getCell() - p2.getCell();

        if(Math.abs(rowDiff) != 1 && Math.abs(colDiff) != 1)
        {
            return new Position(p2.getRow() + (rowDiff/2), p2.getCell() + (colDiff/2));
        }
        return null;
    }

    private static void applySingleMove(Move move, BoardView board)
    {
        Space startSpace = board.getTile(move.getStartPosition());

        Space finishSpace = board.getTile(move.getEndPosition());

        Position between = getPositionBetween(move.getStartPosition(), move.getEndPosition());

        if(between != null) //jump move
        {
            Space middle = board.getTile(between);
            middle.setPiece(null);
        }

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
