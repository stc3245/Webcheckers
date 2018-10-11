package com.webcheckers.model;

/**
 * Represents a player within the webcheckers game
 *
 * @author Sean Coyne, Jeffery Russell 10-10-18
 */

public class Player {

    //Private instance variable
    private String name;


    /** Stores active game of the user */
    private Game game;

    public Player(String name)
    {
        this.name = name;
    }

    /**
     * Gets the name of the player
     *
     * @return String - name
     */
    public String getName(){
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
     * 
     * @return
     */
    public Game getGame()
    {
        return this.game;
    }

}
