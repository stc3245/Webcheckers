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
public class RandomAgent extends GameAgent {
    @Override
        public Move nextMove(Game game) {
            List<Move> endJumpMoves = new ArrayList<>();
            List<Move> endNormalMoves = new ArrayList<>();
            Random randomGenerator = new Random();
            BoardView board = game.getBoard();

            for (Row row : board){
                for (Space space : row){
                    if (space.getPiece() != null) {

                        Piece piece = space.getPiece();

                        if ((piece.getColor() == game.getActiveColor())){
                            Position startPos = new Position(row.getIndex(), space.getCellIdx());

                            List<Position> endJumpPositions = MoveValidator.getJumpMoves(board, startPos);
                            List<Position> endNormalPositions = MoveValidator.getNormalMoves(board, startPos);

                            for (Position endPosition : endJumpPositions){
                                endJumpMoves.add(new Move(startPos, endPosition));
                            }

                            for (Position endPosition : endNormalPositions){
                                endNormalMoves.add(new Move(startPos, endPosition));
                            }
                        }
                    }
                }
            }
            if (!endJumpMoves.isEmpty()){
                return endJumpMoves.get(randomGenerator.nextInt(endJumpMoves.size()));
            }
            return endNormalMoves.get(randomGenerator.nextInt(endNormalMoves.size()));
        }
}
