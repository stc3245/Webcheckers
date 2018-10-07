package com.webcheckers.auth;

/**
 * interface for logging in players
 */
public class AuthInterface {
    public enum message {
        SUCCESS, WRONG_CREDENTIALS, ALREADY_SIGNEDIN, ALREADY_SIGNEDOFF, UNKNOWN_ERROR;
    }

    /**
     * synchronized method for signing players in
     * @param username string of the username
     * @return LogInMessage from login attempt
     */
    public static synchronized message signIn(String username, String password, String clientUID){
        // sanity check
        if (password == null){
            password = "";
        }
        if (clientUID == null){
            clientUID = "";
        }

        try{
            AuthData.signIn(username, password, clientUID);
        }catch (AuthException e){
            switch (AuthException.ExceptionMessage.valueOf(e.getMessage())){
                case ALREADY_SIGNEDIN:
                    return message.ALREADY_SIGNEDIN;
                case WRONG_CREDENTIALS:
                    return message.WRONG_CREDENTIALS;
                default:
                    return message.UNKNOWN_ERROR;
            }
        }

        return message.SUCCESS;
    }

    /**
     * synchronized method for signing players off
     * @param username string of the username
     * @return LogInMessage from login attempt
     */
    public static synchronized message signOff(String username, String password, String clientUID){
        // sanity check
        if (password == null){
            password = "";
        }
        if (clientUID == null){
            clientUID = "";
        }

        try{
            AuthData.signOff(username, password, clientUID);
        }catch (AuthException e){
            switch (AuthException.ExceptionMessage.valueOf(e.getMessage())){
                case ALREADY_SIGNEDIN:
                    return message.ALREADY_SIGNEDOFF;
                case WRONG_CREDENTIALS:
                    return message.WRONG_CREDENTIALS;
                default:
                    return message.UNKNOWN_ERROR;
            }
        }

        return message.SUCCESS;
    }
}
