package com.webcheckers.model.bot;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An agent that chooses move randomly from all moves available
 *
 * @author <a href='mailto:xxd9704@rit.edu'>Perry Deng</a>
 */
public class RandomAgent extends GameAgent
{
    @Override
    public List<Move> nextMove(BoardView board, Piece.ColorEnum currentColor)
    {
        List<Move> endJumpMoves = new ArrayList<>();
        List<Move> endNormalMoves = new ArrayList<>();
        Random randomGenerator = new Random();

        for (Row row : board)
        {
            for (Space space : row)
            {
                if (space.getPiece() != null)
                {

                    Piece piece = space.getPiece();

                    if ((piece.getColor() == currentColor))
                    {
                        Position startPos = new Position(row.getIndex(), space.getCellIdx());

                        List<Position> endJumpPositions = MoveValidator.getJumpMoves(board, startPos);
                        List<Position> endNormalPositions = MoveValidator.getNormalMoves(board, startPos);

                        for (Position endPosition : endJumpPositions)
                        {
                            endJumpMoves.add(new Move(startPos, endPosition));
                        }

                        for (Position endPosition : endNormalPositions)
                        {
                            endNormalMoves.add(new Move(startPos, endPosition));
                        }
                    }
                }
            }
        }

        List<Move> reccMoves = new ArrayList<>();

        if (!endJumpMoves.isEmpty())
        {
            reccMoves.add(endJumpMoves.get(randomGenerator.nextInt(endJumpMoves.size())));
        }
        else
        {
            reccMoves.add(endNormalMoves.get(randomGenerator.nextInt(endNormalMoves.size())));
        }
        return reccMoves;
    }
}
