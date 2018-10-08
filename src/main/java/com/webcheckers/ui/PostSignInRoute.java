package com.webcheckers.ui;

import com.sun.deploy.util.StringUtils;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

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

    static final String NAME_PARAM = "username";

    // Values used in the view-model map for rendering the signin view after entering credentials.
    static final String VIEW_NAME = "signin.ftl";

    static final String SIGNIN_ATTR = "signinAuthorized";

    static final String SIGNUP_ATTR = "signupAuthorized";

    static final String DISPLYED_MESSAGE_ATTR = "displayedMessage";
    static final String SIGNIN_AUTHORIZED = "User has successfully sign in. ";
    static final String SIGNIN_UNAUTHORIZED = "User has failed to sign in. ";
    static final String SUCCESSFUL_SIGNUP = "A new user has been registered. ";
    static final String UNSUCCESSFUL_SIGNUP = "Registration failed. ";

    static final String ERROR_MESSAGE = "errorMessage";
    static final String ERROR_DEFAULT = "An unknown error has occurred. ";
    static final String ERROR_WRONG_CREDENTIALS = "Credentials are incorrect. ";
    static final String ERROR_ALREADY_LOGGEDIN = "The user is already logged in. ";
    static final String ERROR_USERNAME_TAKEN = "This username has been taken. ";
    static final String ERROR_INVALID_CHARACTERS = "Invalid characters detected. ";

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

        LOG.config("POSTSigninRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        final String username = request.queryParams(NAME_PARAM);

        LOG.config(username);

        PlayerLobby playerLobby = new PlayerLobby();

        // if the username is valid
        if (playerLobby.isValid(username)) {
            if (playerLobby.userExists(username)) {
                LOG.config("Player already exists");
                // navigate back to sign in page with error message
            } else {
                playerLobby.createPlayer(username);
                // navigate to home page with success message
            }
        } else {
            LOG.config("Make a username containing alphanumeric characters");
            // navigate back to sign in page with error message
        }

        return null;
    }
}
