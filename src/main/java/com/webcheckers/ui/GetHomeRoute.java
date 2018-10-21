package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author Jeffery Russell 10-12-18
 */
public class GetHomeRoute implements Route {

  static final String TITLE_ATTR = "title";
  static final String TITLE = "Welcome to WebCheckers!";
  static final String VIEW_NAME = "home.ftl";

  static final String SIGN_IN_ATTR = "signedIn";
  static final String WELCOME_MSG_ATTR = "welcomeMessage";
  static final String USER_NUM_ATTR = "currentUserNum";
  static final String USER_NUM = "current number of signed in users: %d";
  static final String WELCOME_MSG = "Welcome, %s!";
  static final String ERROR_MSG = "errorMsg"; //msg for when someone tries to play wt someone already in the game
  static final String PLAYER_LIST = "users";

  // Key in the session attribute map for the player who started the session
  static final String PLAYERSERVICES_KEY = "playerServices";
  static final String TIMEOUT_SESSION_KEY = "timeoutWatchdog";

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final PlayerLobby playerLobby;

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetHomeRoute is initialized.");
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
    vm.put("title", "Welcome!");
    vm.put(SIGN_IN_ATTR, false);

    // retrieve the game object
    final Session session = request.session();
    PlayerServices playerServices = session.attribute(GetHomeRoute.PLAYERSERVICES_KEY);

    // logic for if a current player is signed in
    if (playerServices!= null)
    {
      if(playerServices.signedIn()) 
      {
        if(playerServices.currentPlayer().inGame())
        {
          response.redirect(WebServer.GAME_URL);
          halt();
          return null;
        }
        else
        {
          vm.put(SIGN_IN_ATTR, true);
          vm.put(WELCOME_MSG_ATTR, String.format(WELCOME_MSG, playerServices.currentPlayer().getName()));
          vm.put(PLAYER_LIST, playerLobby.getOnlinePlayers());
          vm.put(USER_NUM_ATTR, String.format(USER_NUM, playerLobby.getOnlinePlayers().size()));
        }
        
      }
      else 
      {
        vm.put(SIGN_IN_ATTR, false);
        vm.put(USER_NUM_ATTR, String.format(USER_NUM, playerLobby.getOnlinePlayers().size()));
      }
      
    } 
    else
    {
      playerServices = playerLobby.newPlayerServices();
      session.attribute(GetHomeRoute.PLAYERSERVICES_KEY, playerServices);
      vm.put(SIGN_IN_ATTR, false);
      vm.put(USER_NUM_ATTR, String.format(USER_NUM, playerLobby.getOnlinePlayers().size()));
    }

    vm.put(ERROR_MSG, playerServices.getStartGameError());

    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }

}