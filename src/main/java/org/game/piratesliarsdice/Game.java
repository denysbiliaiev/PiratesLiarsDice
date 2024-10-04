package org.game.piratesliarsdice;

import lombok.Getter;
import lombok.Setter;
import org.game.piratesliarsdice.GameState.GameInitState;
import org.game.piratesliarsdice.GameState.GameState;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Game implements GameState {
    private GameState state;
    private List<Dice> dices;
    private List<Player> players = new ArrayList<>();
    private int lastBid = 0;
    private int currentPlayerIndex = 1;
    private Boolean isGameOver = false;

    public Game(String diceUrl) {
        this.setState(new GameInitState(this, diceUrl));
    }

    public void start() throws IOException {
        while (!this.isGameOver) {
            this.processing();
        }
    }

    public void addPlayer(String name) {
        if (this.players.size() == 2) {
            throw new IllegalStateException("Can't add more than two player to the game");
        }

        this.players.add(new Player(name));
    }

    public Dice pickOutDice() {
        return dices.get(new Random().nextInt(49 - 0) + 0);
    }

    public int getHighestDiceValue(Dice dice1, Dice dice2) {
        int dice1Value = dice1.getValue();
        int dice2Value = dice2.getValue();

        return dice1Value > dice2Value ?
                Integer.parseInt(dice1Value + Integer.toString(dice2Value))
                : Integer.parseInt(dice2Value + Integer.toString(dice1Value));
    }

    public int getNextPlayerIndex() {
        return this.currentPlayerIndex == 0 ? 1 : 0;
    }

    public Player setCurrentPlayer() {
        int playerIndex = this.getNextPlayerIndex();
        this.setCurrentPlayerIndex(playerIndex);

        return this.getPlayers().get(playerIndex);
    }

    public Player determineWinner() {
        Player winner = players.get(currentPlayerIndex);
        Player nextPlayer = players.get(this.getNextPlayerIndex());

        if (nextPlayer.getLastBid() == this.getLastBid()) {
            winner = nextPlayer;
        }

        return winner;
    }

    @Override
    public void processing() throws IOException {
        this.state.processing();
    }
}
