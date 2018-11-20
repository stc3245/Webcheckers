package com.webcheckers.appl;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents a player within the webcheckers game
 * 
 * @author: Perry Deng, Bryce Murphy
 * @author Sean Coyne, Jeffery Russell 10-10-18
 */
public class Player 
{
    /** Stores name of player */
    private String name;
    // stores previous game history
    private Stack<Game> pastGames = new Stack<>();

    /**
     * constructor for Player class
     */
    public Player(String username)
    {
        this.name = username;
    }


    /**
     * getter for name variable
     * return: name
     */
    public String getName()
    {
        return name;
    }

    public void addToPastGames(Game game) {
        pastGames.add(game);
    }

    public Game getLastGame() {
        return !pastGames.empty() ? pastGames.peek() : null;
    }

    public String getLastResult() {
        Piece.ColorEnum me = (getLastGame().getRedPlayer() == this) ? Piece.ColorEnum.RED : Piece.ColorEnum.WHITE;
        if (getLastGame().getGameState() == Game.GameState.RedResigned || getLastGame().getGameState() == Game.GameState.RedLost) {
            return me == Piece.ColorEnum.RED ? "lost" : "won";
        } else if (getLastGame().getGameState() == Game.GameState.WhiteResigned || getLastGame().getGameState() == Game.GameState.WhiteLost) {
            return me == Piece.ColorEnum.WHITE ? "lost" : "won";
        }
        return "error";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}