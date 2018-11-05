package com.webcheckers.model;

import java.util.Objects;

/**
 * Represents a piece within the webcheckers game
 *
 * @author Sean Coyne 10-10-18
 * @author Bryce Murphy 10-30-18
 */
public class Piece 
{
    /** Type of checkers piece */
    private PieceEnum type;

    /** Color of the piece */
    private ColorEnum color;


    /**
     * Simple constructor which takes a type and
     * color of the checkers piece.
     *
     * @param type
     * @param color
     */
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
    public PieceEnum getType()
    {
        return type;
    }

    /**
     * Get the color of this piece.
     *
     * @return ColorEnum - color of piece
     */
    public ColorEnum getColor()
    {
        return color;
    }

    // promote the player to king
    public void promote() {
        this.type = PieceEnum.KING;
    }

    public Piece makeCopy() {
        return new Piece(this.type, this.color);
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

    /**
     * Enum for storing different types of checkers pieces
     *
     * @author Sean Coyne 10-10-18
     */
    public enum PieceEnum {
        SINGLE,
        KING
    }

    @Override
    public boolean equals(Object o)
    {
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
