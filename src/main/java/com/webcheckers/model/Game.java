package com.webcheckers.model;

import com.webcheckers.appl.*;

/**
 * Used to store the status of an active game
 * 
 * @author Jeffery Russell 10-10-18
 */
public class Game
{
    /** Red player in the game */
    private Player redPlayer;

    /** White Player in the game */
    private Player whitePlayer;

    /** The current color of the current player */
    private ColorEnum activeColor;

    /** Representation of the game board */
    private BoardView board;

    /** The status of the game Is always active for now */
    private ViewModeEnum viewMode;


    /**
     * Constructs a new game with two players 
     * 
     * @param redPlayer
     * @param whitePlayer
     */
    public Game(Player redPlayer, Player whitePlayer)
    {
        //adds players to the game
        redPlayer.setGame(this);
        whitePlayer.setGame(this);

        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.viewMode = ViewModeEnum.PLAY;
        this.activeColor = ColorEnum.RED;
    }

    public ColorEnum getActiveColor()
    {
        return activeColor;
    }

    public Player getRedPlayer()
    {
        return this.redPlayer;
    }

    public Player getWhitePlayer()
    {
        return this.whitePlayer;
    }


    public ViewModeEnum getViewMode()
    {
        return this.viewMode;
    }

    public BoardView getBoard()
    {
        return this.board;
    }

}