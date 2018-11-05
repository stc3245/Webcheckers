package com.webcheckers.model;

/**
 * Represents a move within the webcheckers game
 *
 * @author Sean Coyne 10-21-18
 * @author Jeffery Russell 10-30-18
 */
public class Move
{
    /** Start position of a move */
    private Position start;

    /** End position of a move */
    private Position end;

    /**
     * Simple constructor which takes in a start and
     * finish position for a move.
     *
     * @param startPosition start position of move
     * @param endPosition end position of move
     */
    public Move(Position startPosition, Position endPosition)
    {
        this.start = startPosition;
        this.end = endPosition;
    }


    /**
     * gets the start position of the move
     *
     * @return Position - start
     */
    public Position getStartPosition()
    {
        return start;
    }


    /**
     * gets the end position of the move
     *
     * @return Position - end
     */
    public Position getEndPosition()
    {
        return end;
    }
}
