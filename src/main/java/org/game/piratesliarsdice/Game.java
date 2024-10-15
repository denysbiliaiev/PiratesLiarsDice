package org.game.piratesliarsdice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.game.piratesliarsdice.GameState.GamePlayingState;
import org.game.piratesliarsdice.GameState.GameState;

import java.util.List;
import java.util.Random;

@Getter
@RequiredArgsConstructor
public class Game implements GameState {
    private final List<Die> dice;
    private final Player player1;
    private final Player player2;
    @Setter
    private GameState state;
    @Setter
    private Player activePlayer;
    @Setter
    private int lastBid = 0;
    @Setter
    private Boolean hasLiar = false;
    @Setter
    private Boolean isGameOver = false;

    public void start() {
        this.state = new GamePlayingState(this);

        while (!this.isGameOver) {
            this.processing();
        }
    }

    public Die pickOutDice() {
        return dice.get(new Random().nextInt(49 - 0) + 0);
    }

    public int getHighestDiceValue(Die die1, Die die2) {
        int dice1Value = die1.getValue();
        int dice2Value = die2.getValue();

        return dice1Value > dice2Value ?
                Integer.parseInt(dice1Value + Integer.toString(dice2Value))
                : Integer.parseInt(dice2Value + Integer.toString(dice1Value));
    }

    public Player nextPlayer() {
        activePlayer = activePlayer == null || activePlayer.getClass() == Player2.class ? player1 : player2;

        return activePlayer;
    }

    public Player determineWinner() {
        Player winner = this.activePlayer;
        Player nextPlayer = this.nextPlayer();

        if (nextPlayer.getLastBid() == this.getLastBid()) {
            winner = nextPlayer;
        }

        return winner;
    }

    @Override
    public void processing() {
        this.state.processing();
    }
}
