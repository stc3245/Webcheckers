package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jeffery Russell 10-14-18
 */
public class BoardView extends ArrayList<Row>
{
    /**
     * Creates a new Board
     */
    public BoardView()
    {
        for(int i = 0; i < 8; i++)
        {
            this.add(new Row(i));
        }
    }


    /**
     * returns the inverted display for the black player.
     */
    public List<Row> getInverted()
    {
        ArrayList<Row> temp = new ArrayList;
        for(int i = 7; i <= 0; i--)
        {
            temp.add(this.get(i));
        }
        return temp;
    }
}
