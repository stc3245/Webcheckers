package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.webcheckers.model.BoardView.PieceEnum.KING;
import static com.webcheckers.model.BoardView.PieceEnum.SINGLE;

/**
 * Class used to apply the rules of checkers to validate
 * a move
 *
 * @author Jeffery Russell 10-27-18
 * @author Bryce Murphy 10-30-18
 */
public class MoveValidator
{
    /** Enum which represents the validation status of a move */
    public enum MoveStatus{VALID, INVALID, JUMP_REQUIRED}


    /**
     * Fetches a list of normal moves a player can make (non-jump moves)
     *
     * @param board checkers board
     * @param startPos start position of the move
     * @return list of normal moves
     */
    public static List<Position> getNormalMoves(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();

        Space startSpace = board.getTile(startPos.getRow(), startPos.getCell());

        int modifier = startSpace.getPiece().getColor() == Piece.ColorEnum.RED ? -1: 1;

        ArrayList<Position> positions = new ArrayList<>();

        positions.add(new Position(startPos.getRow() + modifier, startPos.getCell() + 1));
        positions.add(new Position(startPos.getRow() + modifier, startPos.getCell() - 1));

        if (startSpace.getPiece().getType() == KING) {
            positions.add(new Position(startPos.getRow() - modifier, startPos.getCell() + 1));
            positions.add(new Position(startPos.getRow() - modifier, startPos.getCell() - 1));
        }

        for (Position p: positions) {
            if(onBoard(p) && !board.isOccupied(p)) {
                validPositions.add(p);
            }
        }

        return validPositions;
    }


    /**
     * Checks to see if a piece has the opponent in it
     *
     * @param board checkers board
     * @param yourColor color of player making move
     * @param inquestion the piece you wish to check
     * @return whether or not the piece has an opponent
     */
    public static boolean hasOpponent(BoardView board, Piece.ColorEnum yourColor, Position inquestion)
    {
        if(board.isOccupied(inquestion))
        {
            Space space = board.getTile(inquestion);
            return (!space.getPiece().getColor().equals(yourColor));
        }
        return false;
    }


    /**
     * Checks to see if a piece is on the board or not
     *
     * @param p piece
     * @return whether piece is on the board
     */
    public static boolean onBoard(Position p)
    {
        return p.getCell() < 8 && p.getRow() < 8 &&
                p.getCell() >= 0 && p.getRow() >= 0;
    }


    /**
     * Generates a list of all jump moves available to a piece.
     *
     * @param board checkers board
     * @param startPos start position of the move
     * @return list of jump moves for a piece
     */
    public static List<Position> getJumpMoves(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();

        Space startSpace = board.getTile(startPos.getRow(), startPos.getCell());
        Piece.ColorEnum yourColor = startSpace.getPiece().getColor();
        int modifier = yourColor == Piece.ColorEnum.RED ? -1: 1;

        ArrayList<Position> positions = new ArrayList<>();
        ArrayList<Position> between = new ArrayList<>();

        positions.add(new Position(startPos.getRow() + (2* modifier), startPos.getCell() + 2));
        positions.add(new Position(startPos.getRow() + (2* modifier), startPos.getCell() - 2));

        between.add(new Position(startPos.getRow() + modifier, startPos.getCell() + 1));
        between.add(new Position(startPos.getRow() + modifier, startPos.getCell() - 1));

        if (startSpace.getPiece().getType() == KING) {
            positions.add(new Position(startPos.getRow() - (2* modifier), startPos.getCell() + 2));
            positions.add(new Position(startPos.getRow() - (2* modifier), startPos.getCell() - 2));

            between.add(new Position(startPos.getRow() - modifier, startPos.getCell() + 1));
            between.add(new Position(startPos.getRow() - modifier, startPos.getCell() - 1));
        }

        int pos = 0;
        for (Position p: positions) {
            if(onBoard(p) && !board.isOccupied(p) && hasOpponent(board, yourColor, between.get(pos))) {
                validPositions.add(p);
            }
            pos++;
        }

        return validPositions;
    }


    /**
     * Determines if a particular move is a jump move or not.
     *
     * @param board checkers board
     * @param move move to validate
     * @return whether or not the move is considered a jump move
     */
    public static boolean isJumpMove(BoardView board, Move move)
    {
        List<Position> validJumpMoves = getJumpMoves(board, move.getStartPosition());

        return validJumpMoves.stream()
                .anyMatch(m-> m.equals(move.getEndPosition()));
    }


    /**
     * Scans entire board to determine if a jump is available on the
     * board
     *
     * @param boardView checkers board
     * @param currentColor current players color
     * @return whether a jump move is available or not
     */
    public static boolean jumpMovesAvailable(BoardView boardView, Piece.ColorEnum currentColor)
    {
        for(Row row : boardView) {

            for (Space space : row) {

                if (space.getPiece() != null) {

                    Piece piece = space.getPiece();

                    if ((piece.getColor() == currentColor)
                            && (piece.getType() == BoardView.PieceEnum.SINGLE)) {

                        Position startPosition =
                                new Position(row.getIndex(), space.getCellIdx());

                        List<Position> jumpMoves =
                                MoveValidator.getJumpMoves(boardView, startPosition);

                        if(!jumpMoves.isEmpty()){

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    /**
     * Returns a list of all valid moves for a piece to make
     *
     * @param board checkers board
     * @param startPos start position of the move
     * @return list of all possible valid moves on the current board
     */
    public static List<Position> getValidMovePositions(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();

        validPositions.addAll(getJumpMoves(board, startPos));

        validPositions.addAll(getNormalMoves(board, startPos));

        return validPositions;
    }


    /**
     * Returns a {@link Message} corresponding to the status of the move.
     * If it is valid {@link MoveStatus} valid will be returned. Otherwise
     * other error statuses will be returned corresponding to the reason
     * the move is incorrect
     *
     * @param board
     * @param move
     * @return
     */
    public static MoveStatus validateMove(BoardView board, Move move)
    {
        Piece.ColorEnum currentColor = board.getTile(move.getStartPosition()).getPiece().getColor();
        if(jumpMovesAvailable(board, currentColor))
        {
            if(!isJumpMove(board, move))
            {
                return MoveValidator.MoveStatus.JUMP_REQUIRED;
            }
        }


        for(Position p: getValidMovePositions(board, move.getStartPosition()))
        {
            if(p.equals(move.getEndPosition()))
            {
                return MoveStatus.VALID;
            }
        }
        return MoveStatus.INVALID;
    }


    /**
     * Validates a queue of moves. This allows for the player
     * to make a double jump move.
     *
     * @param board current game board
     * @param move move to make
     * @return
     */
    public static MoveStatus validateMoves(BoardView board, Move move, List<Move> preveous)
    {
        //we must make a copy in case the player decides to backup their
        //last move they made.
        BoardView boardCopy = board.makeCopy();

        MoveApplyer.applyMove(preveous, boardCopy);

        return validateMove(boardCopy, move);
    }
}
