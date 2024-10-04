package org.game.piratesliarsdice.GameState;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.piratesliarsdice.Dice;
import org.game.piratesliarsdice.Game;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
public class GameInitState implements GameState {
    private final Game game;
    private final String diceUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void processing() throws IOException {
        List<Dice> dices = objectMapper.readValue(
            new URL(diceUrl),
            new TypeReference<>() {}
        );

        game.setDices(dices);
        game.setState(new GamePlayingState(game));
    }
}
