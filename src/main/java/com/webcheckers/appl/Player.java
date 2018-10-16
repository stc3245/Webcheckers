package com.webcheckers.appl;

import com.webcheckers.model.*;

/**
 * Represents a player within the webcheckers game
 * 
 * author: Perry Deng, Bryce Murphy
 * @author Sean Coyne, Jeffery Russell 10-10-18
 */
public class Player 
{
    /** Stores name of player */
    private String name;

    /** Stores active game of the user */
    private Game game;

    /**
     * constructor for Player class
     */
    public Player(String username) 
    {
        name = username;
        this.game = null;
    }

    /**
     * getter for name variable
     * return: name
     */
    public String getName()
    {
        return name;
    }

    /**
     * sets the current game
     * 
     * @param g
     */
    public void setGame(Game g)
    {
        this.game = g;
    }


    /**
     * Returns the current game the player is in
     * @return
     */
    public Game getGame()
    {
        return this.game;
    }
    

    /**
     * Returns if the player is currently in a game or not.
     */
    public boolean inGame()
    {
        return this.game != null;
    }

    /**
     * getter for PlayerBoard
     * return: an either inverted or normal PlayerBoard
     */
    public BoardView getPlayersBoard()
    {
        if(this.game.getWhitePlayer() != this)
        {
            return game.getBoard();
        }
        return game.getBoard().getInverted();
    }

}