package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;

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

  static final String TITLE = "WebCheckers Game!";
  static final String VIEW_NAME = "home.ftl";

  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetGameRoute(final Player player, final TemplateEngine templateEngine) {
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
  public Object handle(Request request, Response response) {
    final Session httpSession = request.session();
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    Player player = httpSession.attribute(WebServer.PLAYER_KEY);

    Game game = player.get

    vm.put(CURRENTPLAYER, )


    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }

}