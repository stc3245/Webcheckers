package com.webcheckers.ui;

import com.webcheckers.appl.*;


import spark.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;


/**
 * The UI Controller for handling Post /signin
 *
 * @author <a href='mailto: xxd9704@rit.edu'>Perry Deng</a>
 * @author <a href='mailto: bm5890@rit.edu'>Bryce Murphy</a>
 */
public class PostSignInRoute implements Route {
    //
    // Constants
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    // username input value
    private static final String NAME_PARAM = "username";

    // Values used in the view-model map for rendering the signin view after entering credentials.
    private static final String VIEW_NAME = "signin.ftl";

    private static final String ERROR_MESSAGE_ATTR = "errorMessage";
    private static final String SIGN_IN_ERROR_ATTR = "signInError";
    static final String DISPLYED_MESSAGE_ATTR = "displayedMessage";
    static final String SIGNIN_AUTHORIZED = "User has successfully sign in. ";
    static final String SIGNIN_UNAUTHORIZED = "User has failed to sign in. ";
    static final String SUCCESSFUL_SIGNUP = "A new user has been registered. ";
    static final String UNSUCCESSFUL_SIGNUP = "Registration failed. ";

    static final String ERROR_MESSAGE = "errorMessage";
    static final String ERROR_DEFAULT = "An unknown error has occurred. ";
    static final String ERROR_WRONG_CREDENTIALS = "Credentials are incorrect. ";
    static final String ERROR_ALREADY_LOGGEDIN = "The user is already logged in. ";
    private static final String ERROR_USERNAME_TAKEN = "This username has been taken. ";
    private static final String ERROR_INVALID_CHARACTERS = "Invalid characters detected. ";

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    /**
     * constructor
     * @param playerLobby the one and only instance of playerLobby
     * @param templateEngine an instance of TemplateEngine
     */
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response)
     {

        final Session httpSession = request.session();
        final Session session = request.session();
        final String username = request.queryParams(NAME_PARAM);

        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);

        ModelAndView mv = null;

        Player player = request.session().attribute(GetHomeRoute.PLAYERSERVICES_KEY);

        // check for active session
        if(player == null)
        {
            if(!(playerLobby.usernameTaken(username)))
            {
                if(PlayerLobby.containsInvalidCharacters(username))
                {
                    mv = error(vm, (ERROR_INVALID_CHARACTERS));
                }
                else
                {
                    //playerServices.setPlayer(playerLobby.createPlayer(username));
                    Player p = playerLobby.createPlayer(username);

                    session.attribute(GetHomeRoute.PLAYERSERVICES_KEY, p);

                    response.redirect(WebServer.HOME_URL);
                    halt();
                    return null;
                }
            }
            else
            {
                mv = error(vm, (ERROR_USERNAME_TAKEN));
            }
        }
        else
        {
            //already logged in
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        return templateEngine.render(mv);

    }

    /**
     * error handler for the ModelAndView
     */
    private ModelAndView error(final Map<String, Object> vm, final String message) {
        vm.put(GetHomeRoute.SIGN_IN_ATTR, false);
        vm.put(SIGN_IN_ERROR_ATTR, true);
        System.out.println(message);

        vm.put(ERROR_MESSAGE, message);
        vm.put(GetHomeRoute.ERROR_MSG, "");
        return new ModelAndView(vm, VIEW_NAME);
    }

}