package org.game.piratesliarsdice;

import org.game.piratesliarsdice.GameState.GameCompletedState;
import org.game.piratesliarsdice.GameState.GamePlayingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

class GamePlayingStateTests {

    @Mock
    private Game mockGame;

    @Mock
    private Player mockPlayer;

    @InjectMocks
    private GamePlayingState gamePlayingState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        gamePlayingState = new GamePlayingState(mockGame);

        when(mockGame.setCurrentPlayer()).thenReturn(mockPlayer);
        when(mockGame.pickOutDice()).thenReturn(new Dice());
    }

    @Test
    void ShouldGetDiceAndMakeABit() {
        int bid = 32;

        when(mockGame.getHighestDiceValue(any(Dice.class), any(Dice.class))).thenReturn(bid);
        when(mockGame.getCurrentPlayerIndex()).thenReturn(0);
        when(mockGame.getLastBid()).thenReturn(bid);

        gamePlayingState.processing();

        verify(mockPlayer, times(1)).saveBid(bid);
        verify(mockGame, times(1)).setLastBid(bid);
    }

    @Test
    void ShouldSwitchGameStatusToCompletedIfHighestBidReached() {
        int bid = 66;

        when(mockGame.getHighestDiceValue(any(Dice.class), any(Dice.class))).thenReturn(bid);
        when(mockGame.getLastBid()).thenReturn(bid);
        when(mockGame.getCurrentPlayerIndex()).thenReturn(1);

        gamePlayingState.processing();

        verify(mockPlayer, times(1)).saveBid(bid);
        verify(mockGame, times(1)).setState(any(GameCompletedState.class));
    }

    @Test
    void ShouldNotSwitchGameStateUnlessLiarIsSpecified() {
        int bid = 15;

        when(mockGame.getHighestDiceValue(any(Dice.class), any(Dice.class))).thenReturn(bid);
        when(mockGame.getCurrentPlayerIndex()).thenReturn(0);
        when(mockGame.getLastBid()).thenReturn(bid);

        gamePlayingState.processing();

        verify(mockGame, never()).setState(any(GameCompletedState.class));
    }

    @Test
    void ShouldSwitchGameStatusToCompletedIfPlayerLiedBit() {
        int bid = 30;

        when(mockGame.getHighestDiceValue(any(Dice.class), any(Dice.class))).thenReturn(bid);
        when(mockGame.getCurrentPlayerIndex()).thenReturn(0);
        when(mockGame.getLastBid()).thenReturn(35);

        gamePlayingState.processing();

        verify(mockGame, times(1)).setState(any(GameCompletedState.class));
    }
}

