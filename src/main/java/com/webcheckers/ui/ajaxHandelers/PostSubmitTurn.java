package com.webcheckers.ui.ajaxHandelers;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * ** Description from Swen Website -- for consistency**
 *
 * This action submits the player's turn. The Submit
 * turn button will only be active when the Game View
 * is in the Stable Turn state. The response body must
 * be a message that has type info if server processed
 * the turn or error if an error occurred. The text of
 * the message is ignored.
 *
 * HTTP Response Body:
 * a {@link com.webcheckers.model.Message} data type
 *
 * HTTP Request Body:
 * N/A
 *
 * @author Jeffery Russell 10-20-18
 */
public class PostSubmitTurn implements Route
{
    private PlayerLobby lobby;


    public PostSubmitTurn(PlayerLobby lobby)
    {
        this.lobby = lobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        return null;
    }
}
