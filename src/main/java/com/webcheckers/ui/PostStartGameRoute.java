package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.appl.*;

import static spark.Spark.halt;

/**
 * Class dealing with entering a player in a game 
 * 
 * @author Jeffery Russell 10-13-18
 */
public class PostStartGameRoute implements Route
{
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    static final String OPPONENT_ATTR = "opponentName";

    public PostStartGameRoute(final PlayerLobby playerLobby,
        final TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
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
    final Session httpSession = request.session();

    final String opponentName = request.queryParams(this.OPPONENT_ATTR);

    Player player = httpSession.attribute(GetHomeRoute.PLAYERSERVICES_KEY);

    //cant start a game when you are not logged in
    if(player == null)
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }

    // other player is not in another game
    if(!playerLobby.inGame(opponentName) &&
         !player.getName().equals(opponentName))
    {
        //start a new game
        playerLobby.startGame(player, playerLobby.getPlayer(opponentName));
        response.redirect(WebServer.GAME_URL);
        halt();
    }
    else //invalid user selected
    {
        response.redirect(WebServer.HOME_URL);

        //code smell
        //playerS.setStartGameError("You can't play with that player!"); //display an error message on the home page by changing welcome message
        halt();
    }
    return null;
  }

}