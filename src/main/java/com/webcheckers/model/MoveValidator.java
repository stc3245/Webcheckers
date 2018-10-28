package com.webcheckers.model;


import com.webcheckers.appl.Player;

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


    public static List<Position> getNormalMoves(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();

        System.out.println(board);
        System.out.println(startPos);
        Space startSpace = board.getTile(startPos.getRow(), startPos.getCell());

        int modifier = startSpace.getPiece().getColor() == Piece.ColorEnum.RED ?
                -1: 1;

        Position p1 = new Position(startPos.getRow() + modifier, startPos.getCell() + 1);
        Position p2 = new Position(startPos.getRow() + modifier, startPos.getCell() - 1);

        if(!board.isOccupied(p1))
        {
            validPositions.add(p1);
        }
        if(!board.isOccupied(p2))
        {
            validPositions.add(p2);
        }

        return validPositions;
    }

    public static List<Position> getJumpMoves(BoardView board, Position startPos)
    {
        List<Position> validPositions = new ArrayList<>();



        return validPositions;
    }


    public static List<Position> getValidMovePositions(BoardView board, Position startPos)
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
                System.out.println("good!");
                return MoveStatus.VALID;
            }
        }
        return MoveStatus.INVALID;
    }


}
