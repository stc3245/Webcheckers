package com.webcheckers.model;

import java.util.ArrayList;

public class Row extends ArrayList<Space> {

    //Private Instance Variables
    private int index;

    /**
     * constructor for row
     *
     * @param index - index of the row on the board
     */
    public Row(int index){
       if (index % 2 == 0) {
           for (int i = 0; i < 8; i++) {
               if (i % 2 == 0) {
                   add(new Space(i, null, ColorEnum.WHITE));
               } else {
                   add(new Space(i, new Piece(), ColorEnum.RED));
               }
           }
       }
       else{
           for (int i = 0; i < 8; i++) {
               if (i % 2 == 0) {
                   add(new Space(i, new Piece(), ColorEnum.RED));
               } else {
                   add(new Space(i, null, ColorEnum.WHITE));
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

}
