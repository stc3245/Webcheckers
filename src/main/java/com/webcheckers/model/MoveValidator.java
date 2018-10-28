package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to apply the rules of checkers to validate
 * a move
 *
 * @author Jeffery Russell 10-27-18
 */
public class MoveValidator
{
    public enum MoveStatus{VALID, INVALID, JUMP_REQUIRED}


    private static List<Position> getNormalMoves(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();

        Space startSpace = board.getTile(startPos.getRow(), startPos.getCell());

        int modifier = startSpace.getPiece().getColor() == Piece.ColorEnum.RED ?
                -1: 1;

        Position p1 = new Position(startPos.getRow() + modifier, startPos.getCell() + 1);
        Position p2 = new Position(startPos.getRow() + modifier, startPos.getCell() - 1);

        if(onBoard(p1) && !board.isOccupied(p1))
        {
            validPositions.add(p1);
        }
        if(onBoard(p2) && !board.isOccupied(p2))
        {
            validPositions.add(p2);
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
        int modifier = yourColor== Piece.ColorEnum.RED ?
                -1: 1;
        Position p1 = new Position(startPos.getRow() + (2* modifier), startPos.getCell() + 2);
        Position p2 = new Position(startPos.getRow() + (2* modifier), startPos.getCell() - 2);


        Position between1 = new Position(startPos.getRow() + modifier, startPos.getCell() + 1);
        Position between2 = new Position(startPos.getRow() + modifier, startPos.getCell() - 1);

        if(onBoard(p1) && !board.isOccupied(p1) && hasOpponent(board, yourColor, between1))
        {
            validPositions.add(p1);
        }

        if(onBoard(p2) && !board.isOccupied(p2) && hasOpponent(board, yourColor, between2))
        {
            validPositions.add(p2);
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
