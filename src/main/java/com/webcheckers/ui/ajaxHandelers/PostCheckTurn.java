package com.webcheckers.ui.ajaxHandelers;


import spark.Request;
import spark.Response;
import spark.Route;

/**
 * ** Description from Swen Website -- for consistency**
 *
 * This action checks to see if the opponent has submitted their turn.
 * The HTTP response must return message with type of info and the text
 * of the message is either true if it's now this players turn or false
 * if the opponent is still taking their turn.
 *
 * If the opponent resigns the game then this Ajax calls must return an
 * info message with true; and when the Game View is rendered it must
 * inform this player that their opponent resigned the game.
 *
 * HTTP Response Body:
 * a {@link com.webcheckers.model.Message} data type
 *
 * HTTP Request Body:
 * N/A
 *
 * @author Jeffery Russell 10-20-18
 */
public class PostCheckTurn implements Route
{
    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        return null;
    }
}
