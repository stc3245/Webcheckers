package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;


import com.webcheckers.auth.*;
import com.webcheckers.appl.Player;

/**
 * The object to coordinate the state of the Web Application.
 * There should be one instance for every client.
 *
 * This class is an example of the GRASP Controller principle.
 *
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 */
public class PlayerServices {

    private static final Logger LOG = Logger.getLogger(PlayerServices.class.getName());

    private GameCenter gameCenter;
    private AuthInterface authInstance;
    private Player player;
    private String errorMsg;
    private String startGameError;

    public PlayerServices(GameCenter gameCenter) {
        LOG.config("PlayerService is initialized.");
        this.gameCenter = gameCenter;
        authInstance = AuthInterface.getAuthInterfaceInstance();
        errorMsg = "";
        startGameError = "";
    }


    public void setStartGameError(String error)
    {
        startGameError = error;
    }

    public String getStartGameError()
    {
        return startGameError;
    }

    /**
     * set error message
     * return: no return
     */
    public void setErrorMsg(String s) {
        this.errorMsg = s;

    }

    /**
     * encapsulation
     * @return the name
     */
    public String playerName(){
        if (signedIn()){
            return player.getName();
        }else{
            return "";
        }
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public boolean signIn(String username)
    {

        if(player != null)
        {
            this.errorMsg = AuthException.ExceptionMessage.ALREADY_SIGNEDIN.toString();
            return false;
        }else if(username.length() == 0 || 
            username.length() != username.replaceAll(" ", "").length())
        {
            this.errorMsg = AuthException.ExceptionMessage.INVALID_CHARACTER.toString();
            return false;
        }

        AuthInterface.Message msg = authInstance.signIn(username);
        if (msg != AuthInterface.Message.SUCCESS){
            errorMsg = msg.name();

            if (msg != AuthInterface.Message.SUCCESS){
                LOG.config("PlayerService unsuccessfully signed " + username + " up.");
                LOG.config("PlayerService unsuccessfully signed " + username + " in.");
                return false;
            }
            LOG.config("PlayerService successfully signed " + username + " up.");

            if (msg != AuthInterface.Message.SUCCESS){
                LOG.config("PlayerService unsuccessfully signed " + username + " in.");
                return false;
            }
        }

        this.player = new Player(username);
        gameCenter.startSession(this);
        AuthData.signUp(username, player);
        LOG.config("PlayerService successfully signed ." + username + " in.");
        return true;
    }

    public boolean signOff(){
        String name = player.getName();
        AuthInterface.Message msg = authInstance.signOff(name, null, null, player);
        if (msg != AuthInterface.Message.SUCCESS){
            errorMsg = msg.name();
            LOG.config("PlayerService unsuccessfully signed ." + name + " off");
            return false;
        }
        gameCenter.terminateSession(name);
        player = null;
        LOG.config("PlayerService successfully signed ." + name + " off");
        return true;
    }

    public boolean signedIn() {
        return player != null;
    }

    public Player currentPlayer(){
        return player;
    }
}
