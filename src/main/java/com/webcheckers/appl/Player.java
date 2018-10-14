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

    private boolean signedIn;

    /** Stores active game of the user */
    private Game game;

    public Player(String username) 
    {
        name = username;
        signedIn = true;
    }

    public String getName()
    {
        return name;
    }

    public boolean signedIn() 
    {
        return signedIn;
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

}