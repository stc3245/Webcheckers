package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.appl.*;
import com.webcheckers.model.*;

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

    static final String ERROR_WARNING = "You can't play with that player.";

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

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
    vm.put(GetHomeRoute.SIGN_IN_ATTR, true);

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
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);
        vm.put(GetHomeRoute.WELCOME_MSG_ATTR,
             String.format(GetHomeRoute.WELCOME_MSG, player.getName()));
        vm.put(GetHomeRoute.PLAYER_LIST, playerLobby.getOnlinePlayers());
        vm.put(GetHomeRoute.USER_NUM_ATTR, String.format(GetHomeRoute.USER_NUM,
             playerLobby.getOnlinePlayers().size()));

        vm.put(GetHomeRoute.ERROR_MSG, this.ERROR_WARNING);
    }
    return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME));
  }

}