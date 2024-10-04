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
        output.append("Wills siste bud: %s \n\n".formatted(game.getLastBid()));
        output.append(game.getPlayers().get(0));
        output.append(game.getPlayers().get(1));

        System.out.println(output);

        game.setIsGameOver(true);
    }
}
