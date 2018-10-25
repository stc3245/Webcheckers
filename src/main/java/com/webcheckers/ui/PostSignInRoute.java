package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.auth.AuthException;

import com.webcheckers.ui.*;

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
    private final GameCenter gameCenter;
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
     * @param gameCenter the one and only instance of gameCenter
     * @param templateEngine an instance of TemplateEngine
     */
    PostSignInRoute(GameCenter gameCenter, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {

        final String username = request.queryParams(NAME_PARAM);

        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);

        ModelAndView mv;

        final PlayerServices playerServices = request.session().attribute(WebServer.PLAYER_KEY);

        // check for active session
        if(playerServices != null) {
            // attempts to sign in
            if (playerServices.signIn(username)) {
                mv = success(playerServices, vm);
            } else {

                System.out.println(playerServices.getErrorMsg());
                mv = error(vm, playerServices.getErrorMsg().toString());
            }
        }else{
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

        if(message.equals(AuthException.ExceptionMessage.ALREADY_SIGNEDIN.toString()))
        {
            vm.put(ERROR_MESSAGE, ERROR_ALREADY_LOGGEDIN );
        }
        else if(message.equals(AuthException.ExceptionMessage.INVALID_CHARACTER.toString()))
        {
            vm.put(ERROR_MESSAGE, ERROR_INVALID_CHARACTERS);
        }
        else
        {
            vm.put(ERROR_MESSAGE, ERROR_USERNAME_TAKEN);
        }
        vm.put(GetHomeRoute.ERROR_MSG, "");
        return new ModelAndView(vm, VIEW_NAME);
    }

    private ModelAndView success(PlayerServices playerServices, final Map<String, Object> vm) {
        // puts text on initial redirect to home page. further calls are dealt with in GetHomeRoute on refresh
        String name = playerServices.currentPlayer().getName();
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);
        vm.put(GetHomeRoute.WELCOME_MSG_ATTR, String.format(GetHomeRoute.WELCOME_MSG, name));
        vm.put(GetHomeRoute.PLAYER_LIST, gameCenter.getOnlinePlayers());
        vm.put(GetHomeRoute.ERROR_MSG, "");
        return new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
    }

}