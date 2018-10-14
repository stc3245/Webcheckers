package com.webcheckers.appl;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

import com.webcheckers.auth.AuthInterface;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;

/**
 * The object to coordinate the state of the Web Application.
 * Should only have one instance
 *
 * This class is an example of the Pure Fabrication principle.
 *
 * @author <a href='mailto:xxd9704@rit.edu'>Perry Deng</a>
 * @author Jeffery Russell 10-13-18
 */
public class GameCenter 
{
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    private Map<String, PlayerServices> players;

    public GameCenter()
    {
        this.players = new HashMap<String, PlayerServices>();
    }

    /**
     * Get a new {@Linkplain PlayerServices} object instance to provide client-specific services to
     * the client who just connected to this application.
     *
     * @return
     *   A new {@Link PlayerServices}
     */
    public PlayerServices newPlayerServices() 
    {
      LOG.fine("New player services instance created.");
      return new PlayerServices(this);
    }

    /**
     * Create a new Game
     *
     * @return
     *   A new {@link Game}
     */
    public void startGame(Player player1, Player player2) {
      new Game(player1, player2);
    }

    public Collection<String> getOnlinePlayers()
    {
        return AuthInterface.getOnlinePlayers();
    }

    /**
     * Determins if a player is already in a game
     */
    public boolean playerInGame(String player)
    {
      return false;
    }


    /**
     * Returns the specific player who is logged into 
     * with that username.
     */
    public Player getPlayer(String playerName)
    {
      return null;
    }
}
