package com.webcheckers.model.bot;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;

import java.util.List;


/**
 * Class which defines the behavior of a checkers bot
 *
 * @author Jeffery Russell, Perry D
 */
public abstract class GameAgent
{
    public GameAgent()
    {
    }

    /**
     * Returns the bot's preferred move to make
     *
     * @param board current checkers board
     * @param currentColor color of current player
     * @return
     */
    public abstract List<Move> nextMove(BoardView board, Piece.ColorEnum currentColor);
}
