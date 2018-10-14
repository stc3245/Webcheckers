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
import com.webcheckers.model.*;

/**
 * Class dealing with validating a move
 * 
 * @author Jeffery Russell 10-14-18
 */
public class PostValidateMove implements Route
{
    private final TemplateEngine templateEngine;

    public PostValidateMove(final TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
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

    PlayerServices playerS = httpSession.attribute(WebServer.PLAYER_KEY);
    Player player = playerS.currentPlayer();

    

    response.redirect(WebServer.GAME_URL);
    return null;
  }

}