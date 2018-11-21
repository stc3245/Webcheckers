package com.webcheckers.model.bot;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An agent that chooses move randomly from all moves available
 *
 * @author <a href='mailto:xxd9704@rit.edu'>Perry Deng</a>
 * @author Jeffery Russell 11-21-18
 */
public class RandomAgent extends GameAgent
{

    /**
     * A simple random bot which takes the first move available
     * If a jump or double jump move is available it will take
     * that first. If no jump move is available it wil make a
     * normal move.
     *
     * @param board board of the game
     * @param currentColor current player
     * @return move queue to make
     */
    @Override
    public List<Move> nextMove(BoardView board, Piece.ColorEnum currentColor)
    {
        List<Move> endJumpMoves = MoveGenerator.getAllJumpMoves(board, currentColor);
        List<Move> endNormalMoves = MoveGenerator.getAllNormalMoves(board, currentColor);
        Random randomGenerator = new Random();

        List<Move> reccMoves = new ArrayList<>();


        while(!endJumpMoves.isEmpty())
        {
            reccMoves.add(endJumpMoves.get(0));
            Position finalPos = endJumpMoves.get(0).getEndPosition();
            BoardView boardCopy = board.makeCopy();
            reccMoves.forEach(m->MoveApplyer.applySingleMove(m, boardCopy));

            List<Position> moreJumps = MoveValidator.getJumpMoves(boardCopy, finalPos);

            endJumpMoves = new ArrayList<>();
            for(Position p: moreJumps)
            {
                endJumpMoves.add(new Move(finalPos, p));
            }
        }

        if(reccMoves.isEmpty())
        {
            reccMoves.add(endNormalMoves.get(randomGenerator.nextInt(endNormalMoves.size())));
        }
        return reccMoves;
    }
}
