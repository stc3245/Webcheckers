package com.webcheckers.model;

import java.util.Objects;

/**
 * Represents a piece within the webcheckers game
 *
 * @author Sean Coyne 10-10-18
 */

public class Piece 
{

    //Private instance variables
    private BoardView.PieceEnum type;
    private ColorEnum color;

    public Piece(BoardView.PieceEnum type, ColorEnum color)
    {
        this.type = type;
        this.color = color;
    }


    /**
     * Get the type of this piece.
     *
     * @return PieceEnum - type of piece
     */
    public BoardView.PieceEnum getType(){
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


    /**
     * Simple enum for storing color of player
     *
     */
    public enum ColorEnum
    {
        RED,
        WHITE
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return type == piece.type &&
                color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}
