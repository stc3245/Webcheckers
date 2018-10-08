package com.webcheckers.ui;

import com.sun.deploy.util.StringUtils;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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
    private static final String WELCOME_MSG = "Welcome, %s";
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
     * @param playerLobby an instance of SigninServices, handles sign in logic
     * @param templateEngine an instance of TemplateEngine
     */
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {

        final String username = request.queryParams(NAME_PARAM);

        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE_ATTR);
        vm.put(SIGN_IN_ERROR_ATTR, false);

        ModelAndView mv;

        // if the username is valid
        if (playerLobby.isValid(username)) {
            if (playerLobby.userExists(username)) {
                // navigate back to sign in page with error message
                mv = error(vm, ERROR_USERNAME_TAKEN);
            } else {
                // create a new player and return to home page
                playerLobby.createPlayer(username);
                vm.put(GetHomeRoute.WELCOME_MSG, String.format(WELCOME_MSG, username));
                mv = success(vm, WELCOME_MSG);
            }
        } else {
            // navigate back to sign in page with error message
            mv = error(vm, ERROR_INVALID_CHARACTERS);
        }

        return templateEngine.render(mv);

    }

    private ModelAndView error(final Map<String, Object> vm, final String message) {
        vm.put(SIGN_IN_ERROR_ATTR, true);
        vm.put(ERROR_MESSAGE_ATTR, message);
        return new ModelAndView(vm, VIEW_NAME);
    }

    private ModelAndView success(final  Map<String, Object> vm, final String message) {
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);
        vm.put(GetHomeRoute.PLAYER_LIST, playerLobby.getPlayers());
        return new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
    }

}
