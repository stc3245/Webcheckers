package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

import com.webcheckers.model.*;


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
        List<Row> temp = new ArrayList<Row>();
        for(int i = 7; i <= 0; i--)
        {
            temp.add(this.get(i));
        }
        return temp;
    }
}
