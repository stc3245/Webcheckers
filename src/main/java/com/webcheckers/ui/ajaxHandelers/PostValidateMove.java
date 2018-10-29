package com.webcheckers.ui.ajaxHandelers;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.ui.GetHomeRoute;
import spark.Request;
import spark.Response;
import spark.Route;

import com.webcheckers.appl.*;
import com.webcheckers.model.*;


/**
 * ** Description from Swen Website -- for consistency**
 *
 * This action submits a single move for a player to
 * be validated. The server must keep track of each
 * proposed move for a single turn in the user's game
 * state. The response body must be a message that
 * has type info if the move is valid or error if it
 * is invalid. The text of the message must tell
 * the user why a move is invalid.
 *
 * HTTP Response Body:
 * a {@link com.webcheckers.model.Move} data type
 *
 * HTTP Request Body:
 * N/A
 *
 * @author Jeffery Russell 10-14-18
 */
public class PostValidateMove implements Route
{
    /** Object used to fetch the players game */
    private PlayerLobby lobby;


    /** Object  used to convert objects to json strings */
    private final Gson gson;


    /**
     * Initializes the route handeler with the player lobby
     *
     * @param lobby
     */
    public PostValidateMove(PlayerLobby lobby)
    {
        this.lobby = lobby;
        this.gson = new Gson();
    }


    /**
    * Starts a game with another player if that player is available to
    * play a game.
    *
    * @param request
    *   the HTTP request
    * @param response
    *   the HTTP response
    *
    * @return
    *   the rendered HTML for the Home page
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

        final String clientJSON = request.body();
        System.out.println(clientJSON);

        final Move move = gson.fromJson(clientJSON, Move.class);


        return gson.toJson(game.validateMove(move));

    }

}