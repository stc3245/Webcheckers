package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

import com.webcheckers.auth.AuthData;
import com.webcheckers.auth.AuthInterface;
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

    /**
     * Constructor for PlayerServices class
     */
    public PlayerServices(GameCenter gameCenter) {
        LOG.config("PlayerService is initialized.");
        this.gameCenter = gameCenter;
        authInstance = AuthInterface.getAuthInterfaceInstance();
        errorMsg = "";
        startGameError = "";
    }

    /**
     * setter for game error
     */
    public void setStartGameError(String error)
    {
        startGameError = error;
    }

    /**
     * getter for a startGameError
     */
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

    /**
     * getter for error message
     * return: errorMsg
     */
    public String getErrorMsg(){
        return errorMsg;
    }

    /**
     * class for signing in a specific username
     * returns boolean value dependent on the success of signing in
     */
    public boolean signIn(String username){
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

    /**
     * class for signing off
     * return: a boolean for signing off and its relative success
     */
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

    /**
     * class for checking if a player is signed in
     * return: if a player is signed in or not
     */
    public boolean signedIn() {
        return player != null;
    }

    /**
     * getter for currentPlayer
     * return: the current player struct
     */
    public Player currentPlayer(){
        return player;
    }
}
