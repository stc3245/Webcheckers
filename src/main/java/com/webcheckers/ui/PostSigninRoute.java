package com.webcheckers.ui;

import com.webcheckers.authModel.SigninServices;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * The UI Controller for handling Post /signin
 *
 * @author <a href='mailto: xxd9704@rit.edu'>Perry Deng</a>
 */
public class PostSigninRoute implements Route {
    //
    // Constants
    //
    private final SigninServices signinServices;
    private final TemplateEngine templateEngine;

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
    static final String ERROR_USERNAME_TAKEN = "This username has been taken. ";
    static final String ERROR_INVALID_CHARACTERS = "Invalid characters detected. ";

    /**
     * constructor
     * @param signinServices an instance of SigninServices, handles sign in logic
     * @param templateEngine an instance of TemplateEngine
     */
    PostSigninRoute(SigninServices signinServices, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.signinServices = signinServices;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
