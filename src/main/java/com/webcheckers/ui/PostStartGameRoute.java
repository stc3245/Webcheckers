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

import com.webcheckers.appl.*;

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


    public boolean isValidOpponent(String name)
    {
        return false;
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

    Player player = httpSession.attribute(WebServer.PLAYER_KEY);

    // other player is not in another game
    if(!gameCenter.playerInGame(opponentName))
    {
        //start a new game
        gameCenter.startGame(player, gameCenter.getPlayer(opponentName));
        response.redirect(WebServer.GAME_URL);
    }
    else //invalid user selected
    {
        response.redirect(WebServer.HOME_URL);
    }
    return null;
  }

}