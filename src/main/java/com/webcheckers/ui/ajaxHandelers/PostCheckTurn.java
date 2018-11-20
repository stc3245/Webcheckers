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

    /** Object which stores all games */
    private PlayerLobby lobby;

    /** Object  used to convert objects to json strings */
    private final Gson gson;


    /**
     * Initalizes class level objects lobby and
     * gson
     *
     * @param lobby
     */
    public PostCheckTurn(PlayerLobby lobby)
    {
        gson = new Gson();
        this.lobby = lobby;
    }


    /**
     * Uses the game to determine whether the current player
     * is their turn and returns a {@link Message}
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object handle(Request request, Response response)
    {
        Player player = request.session().attribute(GetHomeRoute.PLAYERSERVICES_KEY);
        if(player == null || !lobby.inGame(player.getName()))
        {
            return gson.toJson( new Message(Message.MessageEnum.info, "true"));
        }

        Game game = lobby.getGame(player.getName());

        Message msg;

        if(game.isCurrentPlayer(player))
        {
            msg = new Message(Message.MessageEnum.info, "true");
        }
        else
        {
            msg = new Message(Message.MessageEnum.info, "false");
        }

        return gson.toJson(msg);
    }
}
