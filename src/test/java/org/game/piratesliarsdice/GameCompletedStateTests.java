package org.game.piratesliarsdice;

import org.game.piratesliarsdice.GameState.GameCompletedState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import java.util.List;

class GameCompletedStateTests {

    @Mock
    private Game mockGame;

    @Mock
    private Player mockPlayer1;

    @Mock
    private Player mockPlayer2;

    @InjectMocks
    private GameCompletedState gameCompletedState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        gameCompletedState = new GameCompletedState(mockGame);

        when(mockGame.determineWinner()).thenReturn(mockPlayer1);
        when(mockPlayer1.getName()).thenReturn("Player 1");
    }

    @Test
    void ShouldDetermineWinnerPrintResultsAnsFinishGame() {
        when(mockGame.getLastBid()).thenReturn(42);  // Last bid is 42
        when(mockGame.getPlayers()).thenReturn(List.of(mockPlayer1, mockPlayer2));

        when(mockPlayer1.toString()).thenReturn("Player 1's bids: 10 | 20 | 30\n");
        when(mockPlayer2.toString()).thenReturn("Player 2's bids: 15 | 25 | 35\n");

        gameCompletedState.processing();

        String expectedOutput = "\n\nPlayer 1 vinner, han lyver aldri \n" +
                "Wills siste bud: 42 \n\n" +
                "Player 1's bids: 10 | 20 | 30\n" +
                "Player 2's bids: 15 | 25 | 35\n";

        verify(mockGame, times(1)).determineWinner();
        verify(mockGame, times(2)).getPlayers();
        verify(mockGame, times(1)).setIsGameOver(true);
    }
}

