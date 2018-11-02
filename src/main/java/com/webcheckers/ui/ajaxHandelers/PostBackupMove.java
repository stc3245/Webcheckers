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
    /** Object used to fetch the players game */
    private PlayerLobby lobby;

    /** Object  used to convert objects to json strings */
    private final Gson gson;


    /**
     * Instantiates the post handeler with
     *
     * @param lobby
     */
    public PostBackupMove(PlayerLobby lobby)
    {
        this.lobby = lobby;
        this.gson = new Gson();
    }


    /**
     * Reverts all the players moves that they made in the turn
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
        game.backupMoves();

        Message msg = new Message(Message.MessageEnum.info, "Move Reverted");

        return gson.toJson(msg);
    }
}
