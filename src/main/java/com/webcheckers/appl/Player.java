package com.webcheckers.appl;

import com.webcheckers.model.BoardView;

import java.util.Objects;

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