package com.webcheckers.model;

import java.util.List;

/**
 * Class used to apply a queue of moves to a
 * checkers board
 *
 * @author Jeffery Russell 10-28-18
 * @author Bryce Murphy 10-30-18
 */
public class MoveApplyer
{

    /**
     * Returns the position between two positions. If no such position
     * exists, null is returned instead.
     * @param p1 position 1
     * @param p2 position 2
     * @return position between p1 and p2
     */
    private static Position getPositionBetween(Position p1, Position p2)
    {
        int rowDiff = p1.getRow() - p2.getRow();
        int colDiff = p1.getCell() - p2.getCell();

        if(Math.abs(rowDiff) != 1 && Math.abs(colDiff) != 1)
        {
            return new Position(p2.getRow() + (rowDiff/2),
                    p2.getCell() + (colDiff/2));
        }
        return null;
    }


    /**
     * Applies a single move to the board.
     *
     * @param move move to apply
     * @param board board to apply move on
     */
    public static void applySingleMove(Move move, BoardView board)
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

        // promotes the piece to king if they reach the end of board
        if (finishSpace.getPiece().getType() == Piece.PieceEnum.SINGLE) {
            switch (finishSpace.getPiece().getColor()) {
                case RED:
                    if (move.getEndPosition().getRow() == 0) {
                        finishSpace.getPiece().promote();
                    }
                    break;
                case WHITE:
                    if (move.getEndPosition().getRow() == 7) {
                        finishSpace.getPiece().promote();
                    }
                    break;
            }
        }

    }


    /**
     * Returns all moves in the queue to the {@link BoardView}
     *
     * @param moves collection of moves
     * @param board checkers board to apply moves to
     * @return Status to return to the client
     */
    public static MoveValidator.MoveStatus applyMove(List<Move> moves, BoardView board)
    {
        if(moves.isEmpty())
            return MoveValidator.MoveStatus.INVALID;

        //Ensures that player does not stop half way through double jump move
        //ie the player cannot stop part way through a multiple jump.
        BoardView boardCopy = board.makeCopy();
        moves.forEach(m->applySingleMove(m, boardCopy));
        Position finalTile = moves.get(moves.size() -1).getEndPosition();
        if(MoveValidator.isJumpMove(board, moves.get(0)) &&
                !MoveValidator.getJumpMoves(boardCopy, finalTile).isEmpty())
            return MoveValidator.MoveStatus.JUMP_REQUIRED;

        for(Move m: moves)
        {
            applySingleMove(m, board);
        }

        return MoveValidator.MoveStatus.VALID;
    }

}
