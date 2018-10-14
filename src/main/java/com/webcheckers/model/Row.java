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
        this.row = new ArrayList<>(8);
       if (index % 2 == 0) {
           for (int i = 0; i < 8; i++) {
               if (i % 2 == 0) {
                   row.add(new Space(i, null, ColorEnum.WHITE));
               } else {
                   row.add(new Space(i, new Piece(PieceEnum.SINGLE, ColorEnum.RED), ColorEnum.RED));
               }
           }
       }
       else{
           for (int i = 0; i < 8; i++) {
               if (i % 2 == 0) {
                   row.add(new Space(i, new Piece(PieceEnum.SINGLE, ColorEnum.RED), ColorEnum.RED));
               } else {
                   row.add(new Space(i, null, ColorEnum.WHITE));
               }
           }
       }
    }

    /**
     * gets index of the row within the board
     *
     * @return int - index of row
     */
    public int getIndex(){
        return index;
    }

    public Iterator<Space> iterator() 
    {
        return row.iterator();
    }

}
