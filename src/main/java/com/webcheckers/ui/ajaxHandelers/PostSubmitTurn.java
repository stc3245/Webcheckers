package com.webcheckers.ui.ajaxHandelers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    /** Object used to fetch the players game */
    private PlayerLobby lobby;

    /** Object  used to convert objects to json strings */
    private final Gson gson;


    /**
     * Creates a new object to process post submit turn with
     * a player lobby so that it can fetch the game of the player.
     *
     * @param lobby player lobby
     */
    public PostSubmitTurn(PlayerLobby lobby)
    {
        this.lobby = lobby;
        this.gson = new GsonBuilder().create();
    }


    /**
     * Applies the players moves and returns a {@link Message}
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response)
    {
        Player player = request.session()
                .attribute(GetHomeRoute.PLAYERSERVICES_KEY);
        if(player == null || !lobby.inGame(player.getName()))
        {
            return null;
        }

        Game game = lobby.getGame(player.getName());

        game.applyMoves();


        return gson.toJson(new Message(Message.MessageEnum.info, ""));
    }
}
