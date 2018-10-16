package com.webcheckers.model;

/**
 * Represents a piece within the webcheckers game
 *
 * @author Sean Coyne 10-10-18
 */

public class Piece 
{

    //Private instance variables
    private PieceEnum type;
    private ColorEnum color;

    public Piece(PieceEnum type, ColorEnum color)
    {
        this.type = type;
        this.color = color;
    }


    /**
     * Get the type of this piece.
     *
     * @return PieceEnum - type of piece
     */
    public PieceEnum getType(){
        return type;
    }

    /**
     * Get the color of this piece.
     *
     * @return ColorEnum - color of piece
     */
    public ColorEnum getColor() {
        return color;
    }
}
