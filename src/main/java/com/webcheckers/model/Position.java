package com.webcheckers.model;

/**
 * Represents a position within the webcheckers game
 *
 * @author Sean Coyne 10-21-18
 */
public class Position
{

    //Private instance variables

    /** Row of the piece */
    private int row;

    /** col of the piece */
    private int cell;

    /**
     * gets the row of the position
     *
     * @return int - row number
     */
    public int getRow()
    {
        return row;
    }

    /**
     * gets the cell of the position
     *
     * @return int - cell number
     */
    public int getCell()
    {
        return cell;
    }

}
