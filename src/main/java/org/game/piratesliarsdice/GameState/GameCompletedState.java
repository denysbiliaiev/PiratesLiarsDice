package org.game.piratesliarsdice.GameState;

import lombok.RequiredArgsConstructor;
import org.game.piratesliarsdice.Game;

@RequiredArgsConstructor
public class GameCompletedState implements GameState {
    private final Game game;

    @Override
    public void processing() {
        StringBuilder output = new StringBuilder();

        output.append("\n\n");
        output.append("%s vinner, han lyver aldri \n".formatted(game.determineWinner().getName()));
        output.append("%s's siste bud: %s \n\n".formatted(game.getPlayer2().getName(), game.getLastBid()));
        output.append(game.getPlayer1());
        output.append(game.getPlayer2());

        System.out.println(output);

        game.setIsGameOver(true);
    }
}
