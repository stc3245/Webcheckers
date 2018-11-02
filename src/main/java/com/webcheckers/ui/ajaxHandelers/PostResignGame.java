package com.webcheckers.ui.ajaxHandelers;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
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
    /** Object used to fetch the players game */
    private PlayerLobby lobby;

    /** Object  used to convert objects to json strings */
    private final Gson gson;


    /**
     *
     * @param lobby
     */
    public PostResignGame(PlayerLobby lobby)
    {
        this.lobby = lobby;
        this.gson = new Gson();
    }


    /**
     *
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response)
    {
        Player player = request.session().attribute(GetHomeRoute.PLAYERSERVICES_KEY);
        if(player == null || !lobby.inGame(player.getName()))
        {
            Message msg = new Message(Message.MessageEnum.error, "Not in a game");
            return gson.toJson(msg);
        }

        Game game = lobby.getGame(player.getName());

        return null;
    }
}
