package org.game.piratesliarsdice;

public class Player2 extends Player {
    public Player2(String name) {
        super(name);
    }

    @Override
    public int placeBid(int bid, int lastBid) {
        int placedBid = bid;

        this.saveBid(bid);

        //Player2 velger han å lyve om hva han fikk.
        if (bid < lastBid && lastBid < HIGHEST_BID) {
            placedBid = lastBid + 1;
        }

        return placedBid;
    }
}
