package com.webcheckers.model;

/**
 * Represents a message within the webcheckers game
 *
 * @author Sean Coyne 10-10-18
 */

public class Message {

    //Private instance variables
    private String text;
    private MessageEnum type;

    /**
     * Get the text of the message
     *
     * @return String - text of message
     */
    public String getText(){
        return text;
    }

    /**
     * Get the type of message, either an informational
     * or error message.
     *
     * @return MessageEnum - type of message
     */
    public MessageEnum getType(){
        return type;
    }
}
