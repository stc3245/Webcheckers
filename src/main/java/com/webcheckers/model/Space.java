package com.webcheckers.model;

import java.util.Objects;

/**
 * Represents a space on the board within
 * the webcheckers game
 *
 * @author Sean Coyne 10-14-18
 */

public class Space {

    //Private Instance Variables
    private int cellIdx;
    private Piece piece;
    private Piece.ColorEnum color;

    /**
     * Constructor for space object
     *
     * @param cellIdx - position of cell in row
     * @param piece - piece if there is one in space or null
     * @param color - color of piece (red or white)
     */
    public Space(int cellIdx, Piece piece, Piece.ColorEnum color)
    {
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.color = color;
    }

    /**
     * gets the id of the cell in the row
     *
     * @return int - id of cell
     */
    public int getCellIdx(){
        return this.cellIdx;
    }

    /**
     * returns true if space is valid location for
     * piece or false if not.
     *
     * @return piece - true or false
     */
    public boolean isValid(){
        if((this.color == Piece.ColorEnum.RED) && (this.piece == null)){
            return true;
        }
        return false;

    }

    /**
     * gets piece that resides on space or
     * null if there is no piece.
     *
     * @return Piece - null or piece
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * sets the piece of the current square
     *
     * @param p - piece moving to square
     */
    public void setPiece(Piece p)
    {
        this.piece = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return cellIdx == space.cellIdx &&
                Objects.equals(piece, space.piece) &&
                color == space.color;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cellIdx, piece, color);
    }
}
