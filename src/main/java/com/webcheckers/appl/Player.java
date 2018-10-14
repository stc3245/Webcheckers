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

    public Player(String username) 
    {
        name = username;
    }

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
     * copies values from another Player, used for syncing session data and server data
     * @param player instance to be copied from
     */
    public void copiesValuesFrom(Player player){
        this.name = player.getName();
        this.game = player.getGame();
    }
}