package com.webcheckers.model;

/**
 * Represents a message within the webcheckers game
 *
 * @author Sean Coyne 10-10-18
 * @author Jeffery Russell 10-22-18
 */

public class Message
{
    /** The message being sent */
    private String text;

    /** The message type */
    private MessageEnum type;


    /**
     * Creates a new message class
     * @param type type of message
     * @param message string of the message
     */
    public Message(MessageEnum type, String message)
    {
        this.text = message;
        this.type = type;
    }


    /**
     * Get the text of the message
     *
     * @return String - text of message
     */
    public String getText()
    {
        return text;
    }


    /**
     * Get the type of message, either an informational
     * or error message.
     *
     * @return MessageEnum - type of message
     */
    public MessageEnum getType()
    {
        return type;
    }


    /**
     * Simple enum for storing the type of a message
     *
     */
    public enum MessageEnum
    {
        error,
        info
    }
}
