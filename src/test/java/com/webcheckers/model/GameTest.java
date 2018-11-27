package com.webcheckers.model;


import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeffery Russell 11-1-18
 * @author Sean Coyne 11-14-18
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
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  w  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

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
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

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


    /**
     * Tests the game's ability to remove the current moves
     * of the player.
     */
    @Test
    public void testBackupMoves()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Move randomMove = new Move(new Position(3,3),
                new Position(1,5));

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Message status = cut.validateMove(randomMove);

        cut.backupMoves();

        cut.applyMoves();

        assertTrue(cut.getBoard().isOccupied(new Position(3,3)));

        assertFalse(cut.getBoard().isOccupied(new Position(1,5)));

        Move correctMove = new Move(new Position(3,3),
                new Position(2,4));

        cut.validateMove(correctMove);

        cut.backupMoves();
        cut.applyMoves();
        assertTrue(cut.getBoard().isOccupied(new Position(3,3)));
        assertFalse(cut.getBoard().isOccupied(new Position(2,4)));
    }


    /**
     * Tests to see if an error is thrown when backup moves is empty
     */
    @Test
    public void testBackupWithNoMoves()
    {
        Game cut = new Game(new Player(name1), new Player(name2));

        try
        {
            cut.backupMoves();
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }


    /**
     * Tests the game's ability to apply valid moves of the current player to
     * the game
     */
    @Test
    public void testApplyMoves()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Move move = new Move(new Position(3,3),
                new Position(2,4));

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Message status = cut.validateMove(move);

        assertEquals(status.getType(), Message.MessageEnum.info);

        cut.applyMoves();

        //tests to see if turn has changed
        assertFalse(cut.isCurrentPlayer(new Player(name1)));

        assertFalse(cut.getBoard().isOccupied(new Position(3,3)));

        assertTrue(cut.getBoard().isOccupied(new Position(2,4)));
    }


    /**
     * Tests that when submit turn is requested and the player has not
     * done a full double jump move when one is available, a error
     * message is returned.
     */
    @Test
    public void testJumpMovesRequiredInDoubleMove()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  w  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  r  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  r  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);
        Move move = new Move(new Position(0,0),
                new Position(2,2));
        cut.validateMove(move);

        Message msg = cut.applyMoves();
        assertEquals(msg.getType(), Message.MessageEnum.error);
    }


    /**
     * Makes sure that the get player's board view works properly
     */
    @Test
    public void getPlayersBoardTest()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  R  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);

        assert(cut.getPlayersBoard(new Player(name1)).getTile(7,7)
                .getPiece().equals(new Piece(Piece.PieceEnum.KING, Piece.ColorEnum.RED)));

        assert(cut.getPlayersBoard(new Player(name2))
                .getTile(0,0).getPiece().equals(new Piece(Piece.PieceEnum.KING, Piece.ColorEnum.RED)));
    }


    /**
     * Ensures that all double moves must start with a jump move
     */
    @Test
    public void ensureDoubleMoveStartsWithJump()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Move randomMove = new Move(new Position(1,1),
                new Position(2,2));

        cut.validateMove(randomMove);

        Move sillyMove = new Move(new Position(2,2),
                new Position(4,4));

        Message message = cut.validateMove(sillyMove);

        assertEquals(message.getType(), Message.MessageEnum.error);
    }


    /**
     * Ensures that a player is unable to make a single move
     * in the middle of a jump move.
     */
    @Test
    public void testInvalidMiddleJumpMove()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  w  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  r  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);
        Game cut = new Game(new Player(name1), new Player(name2), board);

        Move randomMove = new Move(new Position(1,1),
                new Position(3,3));

        cut.validateMove(randomMove);

        Move sillyMove = new Move(new Position(3,3),
                new Position(4,4));

        Message message = cut.validateMove(sillyMove);

        assertEquals(message.getType(), Message.MessageEnum.error);
    }
    /**
     * Tests the getRecommendedMove function returns the correct move when
     * a valid normal move is available for a player.
     */
    @Test
    public void testPlayerHelpNormalMove(){

        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  r  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Move generatedRecommendedMove = cut.getRecommendedMove();

        Move expectedRecommendedMove = new Move(new Position(6,0), new Position(5,1));

        /** Asserts that recommended move is the same as the expected one **/

        assertEquals(generatedRecommendedMove.getEndPosition().getCell(),
                expectedRecommendedMove.getEndPosition().getCell());

        assertEquals(generatedRecommendedMove.getEndPosition().getRow(),
                expectedRecommendedMove.getEndPosition().getRow());

        assertEquals(generatedRecommendedMove.getStartPosition().getCell(),
                expectedRecommendedMove.getStartPosition().getCell());

        assertEquals(generatedRecommendedMove.getStartPosition().getRow(),
                expectedRecommendedMove.getStartPosition().getRow());

    }

    /**
     * Tests the getRecommendedMove function returns the correct move when
     * a single jump move is available for a player.
     */
    @Test
    public void testPlayerHelpSingleJump()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  w  @  *  @  " +
                /* 3 */ "  @  *  @  r  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Move generatedRecommendedMove = cut.getRecommendedMove();

        Move expectedRecommendedMove = new Move(new Position(3,3), new Position(1,5));

        /** Asserts that recommended move is the same as the expected one **/

        assertEquals(generatedRecommendedMove.getEndPosition().getCell(),
                expectedRecommendedMove.getEndPosition().getCell());

        assertEquals(generatedRecommendedMove.getEndPosition().getRow(),
                expectedRecommendedMove.getEndPosition().getRow());

        assertEquals(generatedRecommendedMove.getStartPosition().getCell(),
                expectedRecommendedMove.getStartPosition().getCell());

        assertEquals(generatedRecommendedMove.getStartPosition().getRow(),
                expectedRecommendedMove.getStartPosition().getRow());

    }


    /**
     * Tests to see if it catches when the board is empty, the game state changes
     */
    @Test
    public void testEndGameRedWon()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  * @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  w  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  R  @  *  @  *  ";
        /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);


        Move winningMove = new Move(new Position(7,3), new Position(5,1));
        cut.validateMove(winningMove);
        cut.applyMoves();

        assertTrue(cut.getGameState() == Game.GameState.WhiteLost);
    }


    /**
     * Tests to see if it catches when the board is empty, the game state changes
     */
    @Test
    public void testEndGameWhiteWon()
    {
        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  r  @  * @  *  @  " +
                /* 3 */ "  @  *  @  W  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  *  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
        /*         Red side of board       */

        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game cut = new Game(new Player(name1), new Player(name2), board);

        Move winningMove = new Move(new Position(3,3), new Position(1,1));
        cut.validateMove(winningMove);

        cut.applyMoves();

        assertTrue(cut.getGameState() == Game.GameState.RedLost);
    }


    /**
     * Tests the leave game function of the game
     */
    @Test
    public void testLeaveGame()
    {
        Game cut = new Game(new Player(name1), new Player(name2));

        assertNotNull(cut.getRedPlayer());
        assertNotNull(cut.getWhitePlayer());

        cut.leaveGame(new Player(name2));

        assertNull(cut.getWhitePlayer());

        cut.leaveGame(new Player(name1));
        assertNull(cut.getRedPlayer());
    }
}
