package com.webcheckers.ui.ajaxHandelers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

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

  private PlayerLobby lobby;


  public PostValidateMove(PlayerLobby lobby)
  {
      this.lobby = lobby;
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
      Player player = request.session().attribute(GetHomeRoute.PLAYERSERVICES_KEY);
      if(player == null || !lobby.inGame(player.getName()))
      {
          return null;
      }

      Game game = lobby.getGame(player.getName());

      return null;
  }

}