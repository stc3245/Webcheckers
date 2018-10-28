package com.webcheckers.model;

import com.webcheckers.appl.*;

import java.util.LinkedList;
import java.util.Queue;

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
    private Piece.ColorEnum activeColor;

    /** Representation of the game board */
    private BoardView board;

    /** The status of the game Is always active for now */
    private BoardView.ViewModeEnum viewMode;


    private Queue<Move> currentMoves;


    /**
     * Constructs a new game with two players 
     * 
     * @param redPlayer
     * @param whitePlayer
     */
    public Game(Player redPlayer, Player whitePlayer)
    {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.viewMode = BoardView.ViewModeEnum.PLAY;
        this.activeColor = Piece.ColorEnum.RED;
        this.currentMoves = new LinkedList<>();
    }

    
    public boolean playerInGame(String playerName)
    {
        return playerName.equals(this.redPlayer.getName()) || 
            playerName.equals(this.whitePlayer.getName());
    }


    /**
     * getter for active color
     * return: activeColor(colorEnum)
     */
    public Piece.ColorEnum getActiveColor()
    {
        return activeColor;
    }

    /**
     * getter for RedPlayer
     * return: redPlayer
     */
    public Player getRedPlayer()
    {
        return this.redPlayer;
    }

    /**
     * getter for WhitePlayer
     * return: whitePlayer
     */
    public Player getWhitePlayer()
    {
        return this.whitePlayer;
    }

    /**
     * getter for viewMode
     * return: viewMode
     */
    public BoardView.ViewModeEnum getViewMode()
    {
        return this.viewMode;
    }

    /**
     * getter for BoardView
     * return the board
     */
    public BoardView getBoard()
    {
        return this.board;
    }


    /**
     * getter for PlayerBoard
     * return: an either inverted or normal PlayerBoard
     */
    public BoardView getPlayersBoard(Player p)
    {
        if(!this.getWhitePlayer().equals(p))
        {
            return getBoard();
        }
        return getBoard().getInverted();
    }


    /**
     * Checks to see if it is the current player's turn
     *
     * @param player
     * @return if it is the current players turn
     */
    public boolean isCurrentPlayer(Player player)
    {
        if(this.activeColor == Piece.ColorEnum.RED)
        {
            return this.redPlayer.equals(player);
        }
        return this.whitePlayer.equals(player);
    }


    /**
     *
     * @param move
     * @return
     */
    public Message validateMove(Move move)
    {
        MoveValidator.MoveStatus status = MoveValidator.validateMove(this.board, move);
        switch(status)
        {
            case VALID:
                this.currentMoves.add(move);
                return new Message(Message.MessageEnum.info, "Valid Move");
            case INVALID:
                return new Message(Message.MessageEnum.error, "Invalid Move");
            case JUMP_REQUIRED:
                return new Message(Message.MessageEnum.error, "You are required to make a jump move.");
        }
        return null;
    }


    public void backupMoves()
    {
        this.currentMoves.clear();
    }


    public Message applyMoves()
    {
        MoveValidator.MoveStatus status = MoveApplyer.applyMove(this.currentMoves, board);

        switch (status)
        {
            case VALID:
                this.activeColor = (this.activeColor == Piece.ColorEnum.RED) ?
                        Piece.ColorEnum.WHITE: Piece.ColorEnum.RED;
                return new Message(Message.MessageEnum.info, "Move Applied");
            case INVALID:
                return new Message(Message.MessageEnum.error, "Invalid Move");
        }
        return null;
    }

}