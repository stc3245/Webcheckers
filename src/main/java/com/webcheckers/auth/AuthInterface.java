package com.webcheckers.auth;

import com.webcheckers.appl.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * interface for logging in players
 * one instance for every player
 */
public class AuthInterface {
    public enum message {
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
    public message signIn(String username, String password, String clientUID, Player player){
        // sanity check
        if (password == null){
            password = "";
        }
        if (clientUID == null){
            clientUID = "";
        }

        try{
            player.copiesValuesFrom(AuthData.signIn(username, password, clientUID));
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
     * method for signing players off
     * @param username string of the username
     * @return LogInMessage from login attempt
     */
    public message signOff(String username, String password, String clientUID, Player player){
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
                    return message.ALREADY_SIGNEDOFF;
                case WRONG_CREDENTIALS:
                    return message.WRONG_CREDENTIALS;
                default:
                    return message.UNKNOWN_ERROR;
            }
        }

        return message.SUCCESS;
    }

    /**
     * method for signing users up
     * @param username string of the username
     * @param password string of the username
     * @return LogInMessage from login attempt
     */
    public message signUp(String username,String password){
        // sanity check
        if (password == null){
            password = "";
        }
        try{
            AuthData.signUp(username, password);
        }catch (AuthException e){
            switch (AuthException.ExceptionMessage.valueOf(e.getMessage())) {
                case USERNAME_TAKEN:
                    return message.USERNAME_TAKEN;
                default:
                    return message.UNKNOWN_ERROR;
            }
        }
        return message.SUCCESS;
    }

    public message authenticate(String username, String password, String clientUID){
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

    public static Collection<String> getOnlinePlayers(){
        return  AuthData.getSignedInUsers();
    }
}
