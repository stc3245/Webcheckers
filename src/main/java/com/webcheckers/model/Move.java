package com.webcheckers.model;

/**
 * Represents a move within the webcheckers game
 *
 * @author Sean Coyne 10-21-18
 */


public class Move {

    //Private instance variables
    private Position startPosition;
    private Position endPosition;

    /**
     * gets the start position of the move
     *
     * @return Position - start
     */
    public Position getStart(){
        return startPosition;
    }

    /**
     * gets the end position of the move
     *
     * @return Position - end
     */
    public Position getEndPosition(){
        return endPosition;
    }

}
