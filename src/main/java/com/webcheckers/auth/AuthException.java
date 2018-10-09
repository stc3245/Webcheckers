package com.webcheckers.auth;

import java.security.PrivilegedActionException;

public class AuthException extends Exception {
    enum ExceptionMessage{
        WRONG_CREDENTIALS, ALREADY_SIGNEDIN, ALREADY_SIGNEDOFF, USERNAME_TAKEN, INVALID_CHARACTER;
    }

    AuthException (AuthException.ExceptionMessage exceptionMessage){
        super(exceptionMessage.toString());
    }
}
