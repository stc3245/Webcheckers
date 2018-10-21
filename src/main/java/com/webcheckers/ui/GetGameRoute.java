package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.*;
import com.webcheckers.appl.*;
import static spark.Spark.halt;

import spark.*;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:jxr8142@rit.edu'>Jeffery Russell</a> 10-9-18
 */
public class GetGameRoute implements Route 
{

  public static final String CURRENTPLAYER = "currentPlayer";
  public static final String VIEWMODE = "viewMode";
  public static final String REDPLAYER = "redPlayer";
  public static final String WHITEPLAYER = "whitePlayer";
  public static final String ACTIVECOLOR = "activeColor";
  public static final String BOARD = "board";

  public static final String TITLE = "Game!";
  public static final String VIEW_NAME = "game.ftl";

  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetGameRoute(final TemplateEngine templateEngine)
  {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
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
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", TITLE);

    PlayerServices playerS = httpSession.attribute(GetHomeRoute.PLAYERSERVICES_KEY);
    if(playerS == null)
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }

    Player player = playerS.currentPlayer();

    if(!player.inGame())
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }

    Game game = player.getGame();

    vm.put(CURRENTPLAYER, player);

    vm.put(VIEWMODE, game.getViewMode());
    vm.put(BOARD, player.getPlayersBoard());


    vm.put(REDPLAYER, game.getRedPlayer());

    vm.put(WHITEPLAYER, game.getWhitePlayer());

    vm.put(ACTIVECOLOR, game.getActiveColor());


    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }

}