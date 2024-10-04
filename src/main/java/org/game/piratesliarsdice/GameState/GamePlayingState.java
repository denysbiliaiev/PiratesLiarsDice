package org.game.piratesliarsdice.GameState;

import lombok.RequiredArgsConstructor;
import org.game.piratesliarsdice.Game;
import org.game.piratesliarsdice.Player;

@RequiredArgsConstructor
public class GamePlayingState implements GameState {
    private final Game game;
    private final int HIGHEST_BID = 66;

    @Override
    public void processing() {
        Player player = game.setCurrentPlayer();

        int bid = game.getHighestDiceValue(game.pickOutDice(), game.pickOutDice());
        player.saveBid(bid);

        //Will har ikke mulighet til å legge inn et høyere bud og velger han å lyve om hva han fikk.
        if (game.getCurrentPlayerIndex() == 1 && bid < game.getLastBid() && game.getLastBid() < 66) {
            bid = game.getLastBid() + 1;
        }

        //Game over: høyeste bud eller jeg har ingen sjanse til å slå Wills bud.
        if (game.getLastBid() == HIGHEST_BID || game.getCurrentPlayerIndex() == 0 && bid < game.getLastBid()) {
            game.setState(new GameCompletedState(game));
            return;
        }

        game.setLastBid(bid);
    }
}
