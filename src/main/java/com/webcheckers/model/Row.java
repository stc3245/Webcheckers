package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.lang.Iterable;


/**
 * @author Jeffery Russell, Shean 10-14-18
 */
public class Row implements Iterable<Space>
{

    //Private Instance Variables
    private int index;

    private List<Space> row;

    /**
     * constructor for row
     *
     * @param index - index of the row on the board
     */
    public Row(int index)
    {
       Piece.ColorEnum selectedPlayerColor;
       this.row = new ArrayList<>(8);

       this.index = index;

       if(index >= 3)
       {
           selectedPlayerColor = Piece.ColorEnum.RED;
       }
       else
       {
           selectedPlayerColor = Piece.ColorEnum.WHITE;
       }

        Piece tempPiece;
        if(index != 3 && index != 4)
        {
            tempPiece =  new Piece(BoardView.PieceEnum.SINGLE, selectedPlayerColor);
        }
        else
        {
            tempPiece = null;
        }


       for(int i = 0; i < 8; i++)
       {
           Piece.ColorEnum tileColor = ((index + i) %2 == 0) ? Piece.ColorEnum.WHITE : Piece.ColorEnum.RED;
           if(tileColor == Piece.ColorEnum.RED)
           {
                row.add(new Space(i, tempPiece, tileColor));
           }
           else
           {
               row.add(new Space(i, null, tileColor));
           }
           
       }
    }

    /**
     * overloader constructor for Row class
     * @param index index of the row
     * @param row row information
     */
    public Row(int index, List<Space> row)
    {
        this.index = index;
        this.row = row;
    }

    public Row inverted()
    {
        List<Space> row = new ArrayList<Space>(8);
        for(int i = 7; i>=0; i--)
        {
            row.add(this.row.get(i));
        }
        return new Row(this.getIndex(), row);
    }

    /**
     * gets index of the row within the board
     *
     * @return int - index of row
     */
    public int getIndex(){
        return index;
    }

    /**
     * get iterator
     * @return the iterator for the row
     */
    public Iterator<Space> iterator() 
    {
        return row.iterator();
    }

    /**
     * overriden equals function
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Row)) {
            return false;
        }
        Row t = (Row) obj;
        return t.index == this.index;
    }
}
