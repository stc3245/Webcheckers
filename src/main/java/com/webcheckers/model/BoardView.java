package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.lang.Iterable;


/**
 * Representation of a checkers board using
 * iterables
 *
 * @author Jeffery Russell 10-14-18
 */
public class BoardView implements Iterable<Row>
{
    /**
     * Simple enum for storing state of player
     */
    public enum ViewModeEnum
    {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    /** List of rows */
    private List<Row> board;


    /**
     * Creates a new Board
     */
    public BoardView()
    {
        this.board = new ArrayList<>(8);
        for(int i = 0; i < 8; i++)
        {
            board.add(new Row(i));
        }
    }


    /**
     * overloaded constructor for BoardView
     *
     * @param view List<Row> of Row
     */
    public BoardView(List<Row> view)
    {
        this.board = view;
    }


    /**
     * returns the inverted display for the black player.
     */
    public BoardView getInverted()
    {
        ArrayList<Row> temp = new ArrayList<>();
        for(int i = 7; i >= 0; i--)
        {
            temp.add(board.get(i).inverted());
        }
        return new BoardView(temp);
    }


    /**
     * Returns a specific tile.
     *
     * @param r row id -- zero based
     * @param c col id -- zero based
     * @return
     */
    public Space getTile(int r, int c)
    {
        return this.board.get(r).getSpace(c);
    }


    /**
     * Returns a tile at a specific position
     *
     * @param p position
     * @return
     */
    public Space getTile(Position p)
    {
        return this.getTile(p.getRow(), p.getCell());
    }


    /**
     * Returns a piece at a location. The idea
     * of this method is to decrease coupling
     *
     * @param p position
     * @return piece
     */
    public Piece getPiece(Position p)
    {
        return this.getTile(p).getPiece();
    }


    /**
     * Determines if the position is occupied.
     *
     * @param p
     * @return
     */
    public boolean isOccupied(Position p)
    {
        Space s = this.getTile(p);
        return s.getPiece() != null;
    }


    /**
     * getter for board iterator
     * returns board iterator
     */
    public Iterator<Row> iterator() 
    {
        return board.iterator();
    }


    /**
     * Returns all occupied positions on the board
     * @return occupied positions
     */
    public List<Position> getAllActivePositions()
    {
        List<Position> occPositions = new ArrayList<>();
        for(Row row : this)
        {
            for(Space space: row)
            {
                if(space.getPiece() != null)
                {
                    occPositions.add(new Position(row.getIndex(), space.getCellIdx()));
                }
            }
        }
        return occPositions;
    }


    /**
     * Creates a deep copy of the board
     *
     * @return copy of board
     */
    public BoardView makeCopy()
    {
        List<Row> rows = new ArrayList<>();
        for(Row r : this.board)
        {
            rows.add(r.makeCopy());
        }
        return new BoardView(rows);
    }


    /**
     * Counts the number of pieces a particular player
     * has on the board.
     *
     * @param color of the player
     * @return number of pieces the player has
     */
    public int getPieceCount(Piece.ColorEnum color)
    {
        return (int)this.getAllActivePositions().stream()
                .filter(p -> this.getPiece(p).getColor() == color)
                .count();
    }
}
