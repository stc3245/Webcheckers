package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.Objects;


/**
 * Representation of a checkers row.
 *
 * @author Jeffery Russell, Shean 10-14-18
 */
public class Row implements Iterable<Space>
{

    /** zero-based index of the row on the checkers board */
    private int index;

    /** Spaces on the row */
    private List<Space> row;

    /**
     * Contains logic for constructing the start game
     * row of a checkers board.
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
            tempPiece =  new Piece(Piece.PieceEnum.SINGLE, selectedPlayerColor);
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
                row.add(new Space(i, tempPiece == null ? null : tempPiece.makeCopy(), tileColor));
           }
           else
           {
               row.add(new Space(i, null, tileColor));
           }
           
       }
    }


    /**
     * Gets a space at a particular index
     *
     * @param index
     * @return
     */
    public Space getSpace(int index)
    {
        return this.row.get(index);
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


    /**
     * Returns an inverted row
     *
     * @return row inverted
     */
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
    public int getIndex()
    {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row t = (Row) o;

        for(int i = 0; i < this.row.size(); i++)
        {
            if(!this.getSpace(i).equals(t.getSpace(i)))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, row);
    }


    /**
     * Creates a deep copy of the row
     * @return copy of the row
     */
    public Row makeCopy()
    {
        List<Space> spaces = new ArrayList<>();
        for(Space s: this.row)
        {
            spaces.add(s.makeCopy());
        }
        return new Row(this.index, spaces);
    }
}
