package com.webcheckers.model;

import com.webcheckers.appl.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    /** Queue of current moves the player wants to make */
    private List<Move> currentMoves;


    /**
     * Constructs a new game with two players 
     * 
     * @param redPlayer red player
     * @param whitePlayer white player
     */
    public Game(Player redPlayer, Player whitePlayer)
    {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.viewMode = BoardView.ViewModeEnum.PLAY;
        this.activeColor = Piece.ColorEnum.RED;
        this.currentMoves = new ArrayList<>();
    }


    /**
     * Constructor which allows us to inject a board into the game
     * creation. This is mostly used for testing, however, later down the
     * line this can allow us to start the game with different configurations.
     *
     * @param redPlayer red player
     * @param whitePlayer white player
     * @param board checkers board
     */
    public Game(Player redPlayer, Player whitePlayer, BoardView board)
    {
        this(redPlayer, whitePlayer);
        this.board = board;
    }


    /**
     * Checks to see if a particular player is in this game
     *
     * @param playerName name of player
     * @return whether player is in the game
     */
    public boolean playerInGame(String playerName)
    {
        if(playerName == null) return false;
        return playerName.equals(this.redPlayer.getName()) || 
            playerName.equals(this.whitePlayer.getName());
    }


    /**
     * getter for active color
     * @return the color of the player's who's turn it currently is
     */
    public Piece.ColorEnum getActiveColor()
    {
        return activeColor;
    }


    /**
     * getter for RedPlayer
     * @return the red player
     */
    public Player getRedPlayer()
    {
        return this.redPlayer;
    }


    /**
     * getter for WhitePlayer
     * @return whitePlayer
     */
    public Player getWhitePlayer()
    {
        return this.whitePlayer;
    }


    /**
     * getter for viewMode
     * @return viewMode
     */
    public BoardView.ViewModeEnum getViewMode()
    {
        return this.viewMode;
    }


    /**
     * getter for BoardView
     * @return the board
     */
    public BoardView getBoard()
    {
        return this.board;
    }


    /**
     * getter for PlayerBoard
     * @return an either inverted or normal PlayerBoard
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
     * Calls upon the {@link MoveValidator} to determine the status of
     * applying a particular move. Based on the status enum value returned
     * this generates a {@link Message}.
     *
     * This is called by {@link com.webcheckers.ui.ajaxHandelers.PostValidateMove}
     *
     * @param move currently being attempted by player
     * @return {@link Message} to return in Ajax call
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
        return null; //shouldn't happen
    }


    /**
     * Clears the queue of currently being applied moves.
     * So that the player can verify/apply different moves.
     * This is primarily called by {@link com.webcheckers.ui.ajaxHandelers.PostBackupMove}
     */
    public void backupMoves()
    {
        if(!currentMoves.isEmpty())
            this.currentMoves.remove(currentMoves.size() -1);
    }


    /**
     * Applies the players current moves to the the game board.
     *
     * Primarily called by {@link com.webcheckers.ui.ajaxHandelers.PostSubmitTurn}
     *
     * @return {@link Message} to return in Ajax call
     */
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
        return null; //shouldn't happen
    }
}