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
  public static final String CURRENTSTATE = "currentState";

  public static final String TITLE = "Game!";
  public static final String VIEW_NAME = "game.ftl";

  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private final TemplateEngine templateEngine;

  /** Lobby which keeps track of players and their game status */
  public PlayerLobby lobby;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetGameRoute(final TemplateEngine templateEngine, PlayerLobby lobby)
  {

    this.lobby = lobby;
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

    Player player = httpSession.attribute(GetHomeRoute.PLAYERSERVICES_KEY);
    if(player == null)
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }

    if(!lobby.inGame(player.getName()))
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }

    Game game = lobby.getGame(player.getName());

    Game.GameState arg = game.getGameState();

    // gets piece count for active player
    if (game.getPieceCount(game.getActiveColor()) == 0) {
      switch (game.getActiveColor()) {
        case RED:
          arg = Game.GameState.RedLost;
          break;
        case WHITE:
          arg = Game.GameState.WhiteLost;
          break;
      }
      game.setGameState(arg);
    }
    
    vm.put(CURRENTPLAYER, player);

    vm.put(VIEWMODE, game.getViewMode());
    vm.put(BOARD, game.getPlayersBoard(player));

    vm.put(REDPLAYER, game.getRedPlayer());

    vm.put(WHITEPLAYER, game.getWhitePlayer());

    vm.put(ACTIVECOLOR, game.getActiveColor());

    vm.put(CURRENTSTATE, game.getGameState());

    // if game isn't in progress, redirect home
    if (game.getGameState() != Game.GameState.GameInProgress) {
      lobby.endGame(player, lobby.getPlayer(request.queryParams("opponentName")));
      switch (game.getActiveColor()) {
        case RED:
          System.out.println("in Red");
          if (game.getGameState() == Game.GameState.RedLost || game.getGameState() == Game.GameState.RedResigned) {

          } else if (game.getGameState() == Game.GameState.WhiteLost || game.getGameState() == Game.GameState.WhiteResigned) {

          }
          break;
        case WHITE:
          if (game.getGameState() == Game.GameState.WhiteLost || game.getGameState() == Game.GameState.WhiteResigned) {

          } else if (game.getGameState() == Game.GameState.RedLost || game.getGameState() == Game.GameState.RedResigned) {

          }
          break;
      }
      response.redirect(WebServer.HOME_URL);
      halt();
    }

    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }

}