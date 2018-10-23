package com.webcheckers.appl;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.*;
import com.webcheckers.appl.*;

/**
 * The object to coordinate the state of the Web Application.
 * Should only have one instance
 *
 * This class is an example of the Pure Fabrication principle.
 *
 * @author <a href='mailto:xxd9704@rit.edu'>Perry Deng</a>
 * @author Jeffery Russell 10-13-18
 */
public class PlayerLobby
{
    private static final Logger LOG = Logger.getLogger
            (PlayerLobby.class.getName());

    /** Map of all player usernames to their sessions */
    private Map<String, Player> activeSessions;


    public List<Game> activeGames;


    /**
     * Initializes game center's hashmap
     */
    public PlayerLobby()
    {
        this.activeGames = new ArrayList<>();
        this.activeSessions = new HashMap<>();
    }


    /**
     * start a logged in session
     *
     * @param username username
     */
    public Player createPlayer(String username)
    {
        Player p = new Player(username);
        activeSessions.put(username, p);
        return p;
    }


    /**
     * deletes the data of an active session when they log off
     * @param username userame
     */
    public void terminateSession(String username)
    {
        activeSessions.remove(username);
    }


    /**
     * Create a new Game
     *
     * @return
     *   A new {@link Game}
     */
    public Game startGame(Player player1, Player player2)
    {
        Game g =  new Game(player1, player2);
        this.activeGames.add(g);
        return g;
    }


    public Game getGame(String playerName)
    {
        for(Game g: activeGames)
        {
            if(g.playerInGame(playerName))
            {
                return g;
            }
        }
        return null;
    }


    public Collection<String> getOnlinePlayers()
    {
        return this.activeSessions.keySet();
    }


    /**
     * Determins if a player is already in a game
     */
    public boolean inGame(String playerName)
    {
        return this.getGame(playerName) != null;
    }


    /**
     * Returns the specific player who is logged into 
     * with that username.
     */
    public Player getPlayer(String playerName)
    {
        return this.activeSessions.get(playerName);
    }


    /**
     * Checks to see if the username is taken
     *
     * @param username
     * @return
     */
    public boolean usernameTaken(String username)
    {
        return this.activeSessions.containsKey(username);
    }



    public static boolean containsInvalidCharacters(String username)
    {
        return (username.length() == 0 ||
                username.length() !=
                        username.replaceAll(" ", "").length());
    }
}
