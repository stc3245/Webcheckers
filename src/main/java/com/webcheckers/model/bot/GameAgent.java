package com.webcheckers.model.bot;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;

import java.util.List;

public abstract class GameAgent
{
    public GameAgent()
    {
    }

    public abstract List<Move> nextMove(BoardView board, Piece.ColorEnum currentColor);
}
