package com.webcheckers.ui.ajaxHandelers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import com.webcheckers.ui.GetHomeRoute;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Class which sends a recommended move for the player to make to
 * the client in the form of a {@link com.webcheckers.model.Move}
 *
 * @author Jeffery RUssell 11-9-18
 */
public class PostGetHelp implements Route
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
    public PostGetHelp(PlayerLobby lobby)
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
            return gson.toJson("");
        }

        Game game = lobby.getGame(player.getName());

        Move reccMove = game.getRecommendedMove();

        return gson.toJson(reccMove);
    }
}
