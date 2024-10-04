package org.game.piratesliarsdice;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Player {
    private String name;
    private List<Integer> bids = new ArrayList<>();
    
    public Player(String name) {
        this.name = name;
    }

    public void saveBid(int bid) {
        this.bids.add(bid);
    }

    public int getLastBid() {
        return this.bids.isEmpty() ? 0 : bids.get(this.bids.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder playerOutput = new StringBuilder();

        playerOutput.append(this.name);
        playerOutput.append(
            this.getBids().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" | ", " ", "\n"))
        );

        return playerOutput.toString();
    }
}
