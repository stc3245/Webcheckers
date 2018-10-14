package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

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
  static final String PLAYER_LIST = "users";

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetHomeRoute(final PlayerLobby playerLobby, 
  final TemplateEngine templateEngine)
   {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
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

    // logic for if a current player is signed in
    if(playerLobby.currentPlayer() != null) 
    {
      if(playerLobby.signedIn()) 
      {
        vm.put(SIGN_IN_ATTR, true);
        vm.put(WELCOME_MSG_ATTR, String.format(WELCOME_MSG, 
          playerLobby.currentPlayer().getName()));
        vm.put(PLAYER_LIST, playerLobby.getPlayers());
      }
    }
    else 
    {
      vm.put(SIGN_IN_ATTR, false);
      int userNum = 0;
      for (Player p : playerLobby.getPlayers()) 
      {
        userNum++;
      }
      vm.put(USER_NUM_ATTR, String.format(USER_NUM, userNum));
    }

    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }

}