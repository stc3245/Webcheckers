package com.webcheckers.ui.ajaxHandelers;


import spark.Request;
import spark.Response;
import spark.Route;

/**
 * ** Description from Swen Website -- for consistency**
 *
 * This action asks the server to remove the last move
 * that was previously validated. The server must update
 * the player's turn in the user's game state. The response
 * body must be a message that has type info if the action
 * was successful or error if it was unsuccessful. The
 * text of the message must tell the user what the action
 * did.
 *
 * HTTP Response Body:
 * a {@link com.webcheckers.model.Message} data type
 *
 * HTTP Request Body:
 * N/A
 *
 * @author Jeffery Russell 10-20-18
 */
public class PostBackupMove implements Route
{
    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        return null;
    }
}
