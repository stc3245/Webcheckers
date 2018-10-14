package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

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

    public PlayerServices(GameCenter gameCenter) {
        LOG.config("PlayerService is initialized.");
        this.gameCenter = gameCenter;
        authInstance = AuthInterface.getAuthInterfaceInstance();
        errorMsg = "";
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

    public boolean signIn(String username){
        player = new Player(null);
        AuthInterface.message msg = authInstance.signIn(username, null, null, player);
        if (msg != AuthInterface.message.SUCCESS){
            errorMsg = msg.name();
            msg = authInstance.signUp(username, null);
            if (msg != AuthInterface.message.SUCCESS){
                LOG.config("PlayerService unsuccessfully signed " + username + " up.");
                LOG.config("PlayerService unsuccessfully signed " + username + " in.");
                return false;
            }
            LOG.config("PlayerService successfully signed " + username + " up.");
            msg = authInstance.signIn(username, null, null, player);
            if (msg != AuthInterface.message.SUCCESS){
                LOG.config("PlayerService unsuccessfully signed " + username + " in.");
                return false;
            }
            gameCenter.startSession(this);
            LOG.config("PlayerService successfully signed ." + username + " in.");
            return true;
        }
        gameCenter.startSession(this);
        LOG.config("PlayerService successfully signed ." + username + " in.");
        return true;
    }

    public boolean signOff(){
        String name = player.getName();
        AuthInterface.message msg = authInstance.signOff(name, null, null, player);
        if (msg != AuthInterface.message.SUCCESS){
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
