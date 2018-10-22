package com.webcheckers.appl;

import java.util.logging.Logger;



/**
 * The object to coordinate the state of the Web Application.
 * There should be one instance for every client.
 *
 * This class is an example of the GRASP Controller principle.
 *
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 */
public class PlayerServices
{
    private static final Logger LOG = Logger.getLogger(PlayerServices.class.getName());

    private Player player;
    private String startGameError;

    /**
     * Constructor for PlayerServices class
     */
    public PlayerServices()
    {
        LOG.config("PlayerService is initialized.");
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

    public void setPlayer(Player p)
    {
        this.player = p;
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
    public Player currentPlayer()
    {
        return player;
    }
}
