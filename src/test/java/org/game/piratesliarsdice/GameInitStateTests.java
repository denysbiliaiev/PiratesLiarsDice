package org.game.piratesliarsdice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.game.piratesliarsdice.GameState.GameInitState;
import org.game.piratesliarsdice.GameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import java.io.IOException;
import java.net.URL;

class GameInitStateTests {

    @Mock
    private Game mockGame;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private GameInitState gameInitState;

    private final String diceUrl = "http://example.com/dices";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        gameInitState = new GameInitState(mockGame, diceUrl);
    }

    @Test
    void ShouldReturnIOException() throws IOException {
        when(mockObjectMapper.readValue(any(URL.class), any(TypeReference.class)))
                .thenThrow(new IOException(""));

        assertThrows(IOException.class, () -> gameInitState.processing());

        verify(mockGame, never()).setDices(any());

        verify(mockGame, never()).setState(any(GameState.class));
    }
}

