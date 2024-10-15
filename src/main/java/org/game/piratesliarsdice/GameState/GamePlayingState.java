package org.game.piratesliarsdice.GameState;

import lombok.RequiredArgsConstructor;
import org.game.piratesliarsdice.Game;
import org.game.piratesliarsdice.Player;

@RequiredArgsConstructor
public class GamePlayingState implements GameState {
    private final Game game;

    @Override
    public void processing() {
        int HIGHEST_BID = 66;
        Player player = game.nextPlayer();
        int lastBid = game.getLastBid();

        int bid = game.getHighestDiceValue(game.pickOutDice(), game.pickOutDice());

        int placedBid = player.placeBid(bid, lastBid);

        //Game Over: Det høyeste budet eller kan ikke slå en annen spillers bud.
        if (placedBid == HIGHEST_BID || placedBid < lastBid) {
            game.setState(new GameCompletedState(game));
            return;
        }

        game.setLastBid(placedBid);
    }
}
