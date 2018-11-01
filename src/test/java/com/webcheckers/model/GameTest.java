package com.webcheckers.model;


import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author Jeffery Russell 11-1-18
 */
@Tag("Model-tier")
public class GameTest
{
    /** Content under test */
    private Game cut;


    /** Generic names to use in CUT  */
    private String name1 = "Jeff";
    private String name2 = "Pardeep";


    /**
     * Initializes the CUT with two players
     *
     * **NOTE: We are using the other objects in the
     * model tier since they have been intensively tested.
     * I am referring to {@link Player}, {@link BoardView}
     * {@link com.webcheckers.model.Piece.ColorEnum},
     * {@link MoveValidator}.
     */
    @BeforeEach
    public void setup()
    {
        cut = new Game(new Player(name1), new Player(name2));
    }


    /**
     * Tests the basic start game parameters
     */
    @Test
    public void testInitialGameSettings()
    {
        assertEquals(cut.getActiveColor(), Piece.ColorEnum.RED);

        assertEquals(cut.getRedPlayer(), new Player(name1));
        assertEquals(cut.getWhitePlayer(), new Player(name2));
        assertEquals(cut.getViewMode(), BoardView.ViewModeEnum.PLAY);

        assertTrue(cut.isCurrentPlayer(new Player(name1)));
        assertFalse(cut.isCurrentPlayer(new Player(name2)));
        assertNotNull(cut.getBoard());
    }


    /**
     * Test to determine if a player is in a
     * game of checkers.
     */
    @Test
    public void testPlayersInGame()
    {
        assertTrue(cut.playerInGame(name1));
        assertTrue(cut.playerInGame(name2));
        assertFalse(cut.playerInGame("WOW!"));
        assertFalse(cut.playerInGame(null));
    }


    /**
     * Tests that the user is required to make a jump
     * move by the game class.
     */
    @Test
    public void testValidateMoveJumpMoveRequired()
    {
        String boardString =
                /** '@'= white tile '*' = empty black tile */
                /** r = red, w = white, caps means king    */
                /**         White side of board      */
                /**         0  1  2  3  4  5  6  7   */
                /** 0 */ "  *  @  *  @  *  @  *  @  " +
                /** 1 */ "  @  r  @  *  @  *  @  *  " +
                /** 2 */ "  *  @  *  @  w  @  *  @  " +
                /** 3 */ "  @  *  @  r  @  *  @  *  " +
                /** 4 */ "  *  @  *  @  *  @  *  @  " +
                /** 5 */ "  @  *  @  *  @  *  @  *  " +
                /** 6 */ "  *  @  *  @  *  @  *  @  " +
                /** 7 */ "  @  *  @  *  @  *  @  *  ";
                /**         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Move testRequireJumpMove = new Move(new Position(1,1), new Position(0,0));

        Message status = cut.validateMove(testRequireJumpMove);

        assertEquals(status.getType(), Message.MessageEnum.error);
    }


    /**
     * Tests that the user is able to make a valid move
     */
    @Test
    public void testValidAndInvalidMove()
    {
        String boardString =
                /** '@'= white tile '*' = empty black tile */
                /** r = red, w = white, caps means king    */
                /**         White side of board      */
                /**         0  1  2  3  4  5  6  7   */
                /** 0 */ "  *  @  *  @  *  @  *  @  " +
                /** 1 */ "  @  w  @  *  @  *  @  *  " +
                /** 2 */ "  *  @  *  @  *  @  *  @  " +
                /** 3 */ "  @  *  @  r  @  *  @  *  " +
                /** 4 */ "  *  @  *  @  *  @  *  @  " +
                /** 5 */ "  @  *  @  *  @  *  @  *  " +
                /** 6 */ "  *  @  *  @  *  @  *  @  " +
                /** 7 */ "  @  *  @  *  @  *  @  *  ";
                /**         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Move randomMove = new Move(new Position(3,3),
                new Position(1,5));

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Message status = cut.validateMove(randomMove);
        assertNotNull(status.getText());

        assertEquals(status.getType(), Message.MessageEnum.error);

        Move validMove = new Move(new Position(3,3),
                new Position(2,2));

        status = cut.validateMove(validMove);

        assertEquals(status.getType(), Message.MessageEnum.info);

        assertNotNull(status.getText());
    }


    @Test
    public void testBackupMoves()
    {

    }


    public void testApplyMoves()
    {

    }

}
