package com.webcheckers.appl;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.model.*;
import com.webcheckers.model.bot.GameAgent;
import com.webcheckers.model.bot.MinimaxAgent;
import com.webcheckers.model.bot.RandomAgent;

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
    /** Map of all player usernames to their sessions */
    private Map<String, Player> activeSessions;

    /** Active games on the server */
    private List<Game> activeGames;

    /** names of bots */
    private final String randomAgent = "Easy-Bot";
    private final String minimaxAgent = "LeetBot";
    public final String[] bots = {randomAgent};
    private HashMap<String, Class> botMap;

    /**
     * Initializes game center's hashmap
     */
    public PlayerLobby()
    {
        this.activeGames = new ArrayList<>();
        this.activeSessions = new HashMap<>();

        // bots are players too
        botMap = new HashMap<>();
        for (String bot : bots)
        {
            Player botPlayer = new Player(bot);
            activeSessions.put(bot, botPlayer);
            switchState: switch (bot)
            {
                case randomAgent:
                    botMap.put(bot, RandomAgent.class);
                    break switchState;
                case minimaxAgent:
                    botMap.put(bot, MinimaxAgent.class);
                    break switchState;
                default:
                    break switchState;
            }
        }
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

        // check for bot
        if (botMap.containsKey(player2.getName()))
        {
            GameAgent agent = null;
            try {
                agent = (GameAgent)botMap.get(player2.getName()).getConstructor().newInstance();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            g.setAgent(agent);
        }
        return g;
    }


    /**
     * Returns the player of a specific player
     * null if the game is not in a game
     *
     * @param playerName name of a player
     * @return game of that player
     */
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


    /**
     * Returns a list of online players
     *
     * @return list of players
     */
    public Collection<String> getOnlinePlayers()
    {
        HashSet<String> listOfNames = new HashSet<>(this.activeSessions.keySet());
        listOfNames.removeAll(this.botMap.keySet());

        return listOfNames;
    }


    /**
     * Returns a list of all the mot names
     *
     * @return
     */
    public Collection<String> getBotNames()
    {
        return this.botMap.keySet();
    }


    /**
     * Returns a list of online players that does not have the passed username
     *
     * @return list of players
     */
    public Collection<String> getOtherPlayers(String username)
    {
        HashSet<String> listOfNames = new HashSet<>(this.getOnlinePlayers());
        listOfNames.remove(username);
        return listOfNames;
    }

    /**
     * Determins if a player is already in a game
     */
    public boolean inGame(String playerName) {
        return !botMap.containsKey(playerName) && this.getGame(playerName) != null;
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
     * @param username name of player
     * @return if the username has been taken
     */
    public boolean usernameTaken(String username)
    {
        return this.activeSessions.containsKey(username);
    }


    /**
     * Determines if a username is invalid to use
     *
     * @param username
     * @return whether or not username is valid
     */
    public static boolean containsInvalidCharacters(String username)
    {
        username = username.trim();
        for (char c : username.toCharArray())
        {
            if (!(Character.isLetterOrDigit(c) || c == ' '))
            {
                // found invalid char
                return true;
            }
        }

        return (username.length() == 0);
    }
}
