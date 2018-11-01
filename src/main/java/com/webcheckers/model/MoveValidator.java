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
    public enum MoveStatus{VALID, INVALID, JUMP_REQUIRED}


    private static List<Position> getNormalMoves(BoardView board, Position startPos)
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

    private static boolean hasOpponent(BoardView board, Piece.ColorEnum yourColor, Position inquestion)
    {
        if(board.isOccupied(inquestion))
        {
            Space space = board.getTile(inquestion);
            return (!space.getPiece().getColor().equals(yourColor));
        }
        return false;
    }

    private static boolean onBoard(Position p)
    {
        return p.getCell() < 8 && p.getRow() < 8 &&
                p.getCell() >= 0 && p.getRow() >= 0;
    }

    private static List<Position> getJumpMoves(BoardView board, Position startPos)
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


    private static List<Position> getValidMovePositions(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();

        validPositions.addAll(getJumpMoves(board, startPos));

        validPositions.addAll(getNormalMoves(board, startPos));

        System.out.println(validPositions);

        return validPositions;
    }


    public static MoveStatus validateMove(BoardView board, Move move)
    {
        for(Position p: getValidMovePositions(board, move.getStartPosition()))
        {
            System.out.println(move.getEndPosition() + " : "  + p);
            if(p.equals(move.getEndPosition()))
            {
                return MoveStatus.VALID;
            }
        }
        return MoveStatus.INVALID;
    }


}
