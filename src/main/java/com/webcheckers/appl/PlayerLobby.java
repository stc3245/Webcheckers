package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class PlayerLobby {

    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    private ArrayList<Player> players;
    private Player currentPlayer;

    public PlayerLobby() {

        players = new ArrayList<>();

        LOG.config("PlayerLobby is initialized.");

    }

    // returns list of current players
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> temp = new ArrayList<>();
        for (Player p : players) {
            if (p != currentPlayer) {
                temp.add(p);
            }
        }
        return temp;
    }

    // creates and adds a new player to Players[] array
    public void createPlayer(String username) {
        Player temp = new Player(username);
        currentPlayer = temp;
        players.add(temp);
    }

    public boolean userExists(String username) {
        boolean returnable = false;
        for (Player p : players) {
            if (p.getName().equals(username)) {
                returnable = true;
            }
        }
        return returnable;
    }

    public boolean isValid(String username) {
        char[] chars = username.toCharArray();
        // counter of the types of values
        int alphaNum = 0;
        int nonAlphaNum = 0;
        int spaceNum = 0;
        // iterates through the username in question
        for (char c : chars) {
            if (Character.isLetterOrDigit(c)) {
                alphaNum+=1;
            } else if (Character.isSpaceChar(c)) {
                spaceNum+=1;
            } else {
                nonAlphaNum+=1;
            }
        }
        if (alphaNum > 0 && nonAlphaNum == 0 && spaceNum == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean signedIn() {
        return currentPlayer.signedIn();
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

}
