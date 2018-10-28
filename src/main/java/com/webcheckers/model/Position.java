package com.webcheckers.model;

import java.util.Objects;

/**
 * Represents a position within the webcheckers game
 *
 * @author Sean Coyne 10-21-18
 */
public class Position
{

    //Private instance variables

    /** Row of the piece */
    private int row;

    /** col of the piece */
    private int cell;

    public Position(int row, int cell)
    {
        this.row = row;
        this.cell = cell;
    }

    /**
     * gets the row of the position
     *
     * @return int - row number
     */
    public int getRow()
    {
        return row;
    }

    /**
     * gets the cell of the position
     *
     * @return int - cell number
     */
    public int getCell()
    {
        return cell;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                cell == position.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", cell=" + cell +
                '}';
    }
}
