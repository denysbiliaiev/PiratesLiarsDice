package org.game.piratesliarsdice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import org.game.piratesliarsdice.GameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.anyInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GameTests {
    @Mock
    private GameState mockState;

    @Spy
    private List<Player> spyPlayers = new ArrayList<>();

    @Spy
    private List<Dice> spyDices = new ArrayList<>();

    @InjectMocks
    private Game game;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game("http://example.com/dices");

        game.setState(mockState);
        game.setPlayers(spyPlayers);
        game.setDices(spyDices);

        game.addPlayer("Player1");
        game.addPlayer("Player2");
    }

    @Test
    void ShouldAddPlayersToGame() {
        verify(spyPlayers, times(2)).add(any(Player.class));
        assertEquals(2, spyPlayers.size());
        assertEquals("Player1", spyPlayers.get(0).getName());
        assertEquals("Player2", spyPlayers.get(1).getName());
    }

    @Test
    void ShouldReturnExceptionIfAddMoreThanToPlayer() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            game.addPlayer("Player3");
        });

        String expectedMessage = "Can't add more than two player to the game";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(2, spyPlayers.size());
    }

    @Test
    void ShouldFindHighestDiceValue() {
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);

        when(dice1.getValue()).thenReturn(5);
        when(dice2.getValue()).thenReturn(6);

        int highestDiceValue = game.getHighestDiceValue(dice1, dice2);

        assertEquals(65, highestDiceValue);
    }

    @Test
    void ShouldReturnDice() {
        Dice dice1 = mock(Dice.class);

        doReturn(dice1).when(spyDices).get(anyInt());

        Dice pickedDice = game.pickOutDice();

        assertNotNull(pickedDice);
        assertTrue(pickedDice.equals(dice1));
    }

    @Test
    void ShouldPlayersTakeTurnsPlaying() {
        Player currentPlayer = game.setCurrentPlayer();

        assertEquals("Player1", currentPlayer.getName());
        assertEquals(0, game.getCurrentPlayerIndex());

        currentPlayer = game.setCurrentPlayer();

        assertEquals("Player2", currentPlayer.getName());
        assertEquals(1, game.getCurrentPlayerIndex());

        currentPlayer = game.setCurrentPlayer();

        assertEquals("Player1", currentPlayer.getName());
        assertEquals(0, game.getCurrentPlayerIndex());
    }

    @Test
    void ShouldFirstHighestBidWins() {
        game.setCurrentPlayerIndex(1);
        game.setLastBid(66);

        game.getPlayers().get(0).saveBid(10);
        game.getPlayers().get(1).saveBid(20);
        game.getPlayers().get(0).saveBid(66);

        Player winner = game.determineWinner();
        assertEquals("Player1", winner.getName());
    }

    @Test
    void ShouldLosesPlayerWhoLiedBid() {
        game.setCurrentPlayerIndex(0);
        game.setLastBid(53);

        game.getPlayers().get(0).saveBid(30);
        game.getPlayers().get(1).saveBid(43);

        Player winner = game.determineWinner();
        assertEquals("Player1", winner.getName());
    }

    @Test
    void ShouldLosesPlayerWhoCannotRaiseBid() {
        game.setCurrentPlayerIndex(0);
        game.setLastBid(23);

        game.getPlayers().get(0).saveBid(10);
        game.getPlayers().get(1).saveBid(23);

        Player winner = game.determineWinner();
        assertEquals("Player2", winner.getName());
    }

    @Test
    void ShouldCallonProcessAtLeastOnce() throws IOException {
        doAnswer(invocation -> {
            game.setIsGameOver(true);
            return null;
        }).when(mockState).processing();

        game.start();

        verify(mockState, atLeastOnce()).processing();
    }

    @Test
    void ShouldOnProcessDelegatesToGameState() throws IOException {
        game.processing();

        verify(mockState, times(1)).processing();
    }
}
