package com.webcheckers.model.bot;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;

public abstract class GameAgent {
    public GameAgent(){
    }

    public abstract Move nextMove(Game game);
}
