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

    /**
     * constructor for Player class
     */
    public Player(String username) 
    {
        name = username;
    }

    /**
     * getter for name variable
     * return: name
     */
    public String getName()
    {
        return name;
    }

    public boolean equals(Player other)
    {
        return true;
    }
}