package com.webcheckers.model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Simple test suite for the position class
 *
 * @author Jeffery Russell 10-1-18
 */
@Tag("Model-tier")
public class PositionTest
{
    /**
     * Tested the hash code and equals function of the
     * {@link Position} class
     */
    @Test
    public void testHashCodeAndEquals()
    {
        Position position = new Position(1,1);
        Position p2 = new Position(3,1);

        assertFalse(position.equals(p2));

        assertTrue(position.equals(position));

        assertFalse(position.equals(null));

        Position same = new Position(1,1);
        assertTrue(same.equals(position));

        assertTrue(position.hashCode()== same.hashCode());

        assertNotSame(position.hashCode(), p2.hashCode());
    }


    /**
     * Tested the getters of the {@link Position} class.
     */
    @Test
    public void testGetRowAndCol()
    {
        Position position = new Position(3,4);
        Position p2 = new Position(-3,-1);

        assertSame(-3, p2.getRow());
        assertSame(-1, p2.getCell());

        assertSame(3, position.getRow());
        assertSame(4, position.getCell());

        assertNotSame(-9, p2.getCell());
        assertNotSame(-10, p2.getRow());
    }
}
