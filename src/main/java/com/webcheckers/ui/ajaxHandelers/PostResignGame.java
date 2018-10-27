package com.webcheckers.ui.ajaxHandelers;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.GetHomeRoute;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * ** Description from Swen Website -- for consistency**
 *
 * This action tells the server that this player is resigning
 * the game. If it's this player's turn then the is only
 * enabled in the Empty Turn state. Once the player make a valid
 * move then this button is disabled. The player may backup
 * from the move to go back to the Empty Turn state, which
 * re-enabled the Resign button.
 *
 * The response body must be a message that has type info if
 * the action was successful or error if it was unsuccessful.
 * When successful the client-side code will send the player
 * back to the Home page.
 *
 * HTTP Response Body:
 * a {@link com.webcheckers.model.Message} data type
 *
 * HTTP Request Body:
 * N/A
 *
 * @author Jeffery Russell 10-20-18
 */
public class PostResignGame implements Route
{

    private PlayerLobby lobby;


    public PostResignGame(PlayerLobby lobby)
    {
        this.lobby = lobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        Player player = request.session().attribute(GetHomeRoute.PLAYERSERVICES_KEY);
        if(player == null || !lobby.inGame(player.getName()))
        {
            return null;
        }

        Game game = lobby.getGame(player.getName());

        return null;
    }
}
