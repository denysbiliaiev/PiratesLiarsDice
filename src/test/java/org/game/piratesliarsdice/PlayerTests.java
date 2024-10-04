package org.game.piratesliarsdice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;

class PlayerTests {
    @Spy
    private List<Integer> spyBids = new ArrayList<>();

    @InjectMocks
    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new Player("Player1");

        player.setBids(spyBids);
    }

    @Test
    void ShouldPlayerInitialize() {
        assertEquals("Player1", player.getName());
        assertTrue(spyBids.isEmpty());
    }

    @Test
    void ShouldSaveBid() {
        player.saveBid(50);

        verify(spyBids, times(1)).add(any());

        assertEquals(1, spyBids.size());
        assertEquals(50, spyBids.get(0));

        player.saveBid(75);
        assertEquals(2, spyBids.size());
        assertEquals(75, spyBids.get(1));
    }

    @Test
    void ShouldReturntLastBid() {
        player.saveBid(30);
        player.saveBid(60);

        assertEquals(60, player.getLastBid());
    }

    @Test
    void ShouldToStringWithBids() {
        player.saveBid(10);
        player.saveBid(20);

        String expectedOutput = "Player1 10 | 20\n";
        assertEquals(expectedOutput, player.toString());
    }
}

