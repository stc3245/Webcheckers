package com.webcheckers.auth;

import com.webcheckers.appl.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * interface for logging in players
 * one instance for every player
 */
public class AuthInterface {
    public enum Message {
        SUCCESS, WRONG_CREDENTIALS, ALREADY_SIGNEDIN, ALREADY_SIGNEDOFF, USERNAME_TAKEN, UNKNOWN_ERROR;
    }

    /**
     * factory method for a new auth interface instance
     * @return
     */
    public static AuthInterface getAuthInterfaceInstance(){
        return new AuthInterface();
    }

    /**
     * method for signing players in
     * @param username string of the username
     * @return LogInMessage from login attempt
     */
    public Message signIn(String username)
    {
        try{
            AuthData.signIn(username);
        }catch (AuthException e){
            switch (AuthException.ExceptionMessage.valueOf(e.getMessage())){
                case ALREADY_SIGNEDIN:
                    return Message.ALREADY_SIGNEDIN;
                case WRONG_CREDENTIALS:
                    return Message.WRONG_CREDENTIALS;
                default:
                    return Message.UNKNOWN_ERROR;
            }
        }
        return Message.SUCCESS;
    }

    /**
     * method for signing players off
     * @param username string of the username
     * @return LogInMessage from login attempt
     */
    public Message signOff(String username, String password, String clientUID, Player player){
        // sanity check
        if (password == null){
            password = "";
        }
        if (clientUID == null){
            clientUID = "";
        }

        try{
            AuthData.signOff(username, password, clientUID, player);
        }catch (AuthException e){
            switch (AuthException.ExceptionMessage.valueOf(e.getMessage())){
                case ALREADY_SIGNEDIN:
                    return Message.ALREADY_SIGNEDOFF;
                case WRONG_CREDENTIALS:
                    return Message.WRONG_CREDENTIALS;
                default:
                    return Message.UNKNOWN_ERROR;
            }
        }

        return Message.SUCCESS;
    }

    /**
     * getter for collection of online players
     * return: collection of online players(string)
     */
    public static Collection<String> getOnlinePlayers(){
        return  AuthData.getSignedInUsers();
    }
}
