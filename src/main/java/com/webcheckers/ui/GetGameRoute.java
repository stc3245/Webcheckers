package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.*;
import com.webcheckers.appl.*;
import static spark.Spark.halt;

import spark.*;

/**
* The UI Controller to GET the Game page.
*
* @author <a href='mailto:jxr8142@rit.edu'>Jeffery Russell</a> 10-9-18
*/
public class GetGameRoute implements Route 
{

    public static final String CURRENTPLAYER = "currentPlayer";
    public static final String VIEWMODE = "viewMode";
    public static final String REDPLAYER = "redPlayer";
    public static final String WHITEPLAYER = "whitePlayer";
    public static final String ACTIVECOLOR = "activeColor";
    public static final String BOARD = "board";
    public static final String CURRENTSTATE = "currentState";

    public static final String TITLE = "Game!";
    public static final String VIEW_NAME = "game.ftl";

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private final TemplateEngine templateEngine;

    /** Lobby which keeps track of players and their game status */
    public PlayerLobby lobby;

    /**
    * Create the Spark Route (UI controller) for the
    * {@code GET /} HTTP request.
    *
    * @param templateEngine
    *   the HTML template rendering engine
    */
    GetGameRoute(final TemplateEngine templateEngine, PlayerLobby lobby)
    {
        this.lobby = lobby;
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetGameRoute is initialized.");
    }


    /**
    * Render the WebCheckers Home page.
    *
    * @param request
    *   the HTTP request
    * @param response
    *   the HTTP response
    *
    * @return
    *   the rendered HTML for the Home page
    */
    @Override
    public Object handle(Request request, Response response)
    {
        final Session httpSession = request.session();
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", TITLE);

        Player player = httpSession.attribute(GetHomeRoute.PLAYERSERVICES_KEY);
        if(player == null || !lobby.inGame(player.getName()))
        {
            response.redirect(WebServer.HOME_URL);
            halt();
        }

        Game game = lobby.getGame(player.getName());

        // if game isn't in progress, redirect home
        if (game.getGameState() != Game.GameState.GameInProgress)
        {
            String endOfGameMessage = getEndGameMessage(player, game);
            lobby.leaveGame(player);
            return renderWithEndGameMessage(player, endOfGameMessage);
        }
        else
        {
            vm.put(CURRENTPLAYER, player);

            vm.put(VIEWMODE, game.getViewMode());
            vm.put(BOARD, game.getPlayersBoard(player));

            vm.put(REDPLAYER, game.getRedPlayer());

            vm.put(WHITEPLAYER, game.getWhitePlayer());

            vm.put(ACTIVECOLOR, game.getActiveColor());

            vm.put(CURRENTSTATE, game.getGameState());

            return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
        }
    }


    /**
     * Returns string to display to the player at the end of the game
     *
     * @param player
     * @param game
     * @return
     */
    public String getEndGameMessage(Player player, Game game)
    {
        Game.GameState state = game.getGameState();
        Piece.ColorEnum playerColor = (player.equals(game.getRedPlayer())) ?
                Piece.ColorEnum.RED : Piece.ColorEnum.WHITE;

        String winningStatus = "";
        String resignationStatus = "";

        switch (playerColor)
        {
            case WHITE:
                switch (state)
                {
                    case RedResigned:
                        resignationStatus = "Other player resigned.";
                    case RedLost:
                        winningStatus = "You won!";
                        break;
                    case WhiteResigned:
                        resignationStatus = "You resigned.";
                    case WhiteLost:
                        winningStatus = "You Lost!";
                        break;
                }
                break;
            case RED:
                switch (state)
                {
                    case RedResigned:
                        resignationStatus = "You resigned.";
                    case RedLost:
                        winningStatus = "You Lost!";
                        break;
                    case WhiteResigned:
                        resignationStatus = "Other player resigned.";
                    case WhiteLost:
                        winningStatus = "You won!";
                        break;
                }
                break;
        }
        return winningStatus + " " + resignationStatus;
    }


    /**
     * Renders the home page with a end of game message.
     *
     * @param player player which is being rendered for
     * @param endGameMessage the message to display to the player
     * @return a rendered html object
     */
    public Object renderWithEndGameMessage(Player player, String endGameMessage)
    {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);
        vm.put(GetHomeRoute.ERROR, false);
        vm.put(GetHomeRoute.WELCOME_MSG_ATTR,
                String.format(GetHomeRoute.WELCOME_MSG, player.getName()));
        vm.put(GetHomeRoute.PLAYER_LIST,
                lobby.getOtherPlayers(player.getName()));
        vm.put(GetHomeRoute.BOT_LIST, lobby.getBotNames());
        vm.put(GetHomeRoute.USER_NUM_ATTR,
                String.format(GetHomeRoute.USER_NUM, lobby.getOnlinePlayers().size()));

        vm.put(GetHomeRoute.GAME_END_ATTR, true);

        vm.put(GetHomeRoute.GAME_MSG_ATTR, endGameMessage);

        vm.put(GetHomeRoute.ERROR_MSG, "");
        return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME));
    }

}