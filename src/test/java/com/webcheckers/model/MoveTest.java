package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Simple test suite for the {@link Move} class
 *
 * @author Jeffery Russell 11-1-18
 */
@Tag("Model-tier")
public class MoveTest
{

    /**
     * Simple test which verifies that we
     * can create a move with a start and end
     * position and the getters reflect those
     * positions.
     *
     * **NOTE: We are using the {@link Position} class
     * because it has been intensively tested and is known
     * to be "Friendly".
     */
    @Test
    public void testGettersAndCreation()
    {
        Move cut = new Move(new Position(0,0), new Position(1,1));
        assertNotNull(cut);

        assertEquals(cut.getEndPosition(), new Position(1,1));

        assertEquals(cut.getStartPosition(), new Position(0,0));

        assertNotEquals(cut.getEndPosition(), null);
    }
}
