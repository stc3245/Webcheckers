package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.appl.*;
import com.webcheckers.model.*;

/**
 * Class dealing with entering a player in a game 
 * 
 * @author Jeffery Russell 10-13-18
 */
public class PostStartGameRoute implements Route
{
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;

    static final String OPPONENT_ATTR = "opponentName";

    public PostStartGameRoute(final GameCenter gameCenter,
        final TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
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

    PlayerServices playerS = httpSession.attribute(WebServer.PLAYER_KEY);

    Player player = playerS.currentPlayer();

    // other player is not in another game
    if(!gameCenter.playerInGame(opponentName))
    {
        //start a new game
        gameCenter.startGame(player, gameCenter.getPlayer(opponentName));
        response.redirect(WebServer.GAME_URL);
        halt();
    }
    else //invalid user selected
    {
        response.redirect(WebServer.HOME_URL);
        playerS.setErrorMsg("You can't play with that player!"); //display an error message on the home page by changing welcome message
        halt();
    }
    return null;
  }

}