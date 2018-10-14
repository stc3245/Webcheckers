package com.webcheckers.appl;

import java.util.Collection;
import java.util.logging.Logger;

import com.webcheckers.auth.AuthInterface;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

/**
 * The object to coordinate the state of the Web Application.
 * Should only have one instance
 *
 * This class is an example of the Pure Fabrication principle.
 *
 * @author <a href='mailto:xxd9704@rit.edu'>Perry Deng</a>
 */
public class GameCenter {
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    /**
     * Get a new {@Linkplain PlayerServices} object instance to provide client-specific services to
     * the client who just connected to this application.
     *
     * @return
     *   A new {@Link PlayerServices}
     */
    public PlayerServices newPlayerServices() {
      LOG.fine("New player services instance created.");
      return new PlayerServices(this);
    }

    /**
     * Create a new Game
     *
     * @return
     *   A new {@link Game}
     */
    public Game getGame(Player player1, Player player2) {
      return new Game(player1, player2);
    }

    public Collection<String> getOnlinePlayers(){
        return AuthInterface.getOnlinePlayers();
    }
}
