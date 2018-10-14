package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.lang.Iterable;

import com.webcheckers.model.*;


/**
 * @author Jeffery Russell 10-14-18
 */
public class BoardView implements Iterable<Row>
{

    private List<Row> board;

    /**
     * Creates a new Board
     */
    public BoardView()
    {
        this.board = new ArrayList<Row>(8);
        for(int i = 0; i < 8; i++)
        {
            board.add(new Row(i));
        }
    }


    // /**
    //  * returns the inverted display for the black player.
    //  */
    // public List<Row> getInverted()
    // {
    //     ArrayList<Row> temp = new ArrayList<Row>();
    //     for(int i = 7; i <= 0; i--)
    //     {
    //         temp.add(board.get(i));
    //     }
    //     return temp;
    // }

    public Iterator<Row> iterator() 
    {
        return board.iterator();
    }
}
