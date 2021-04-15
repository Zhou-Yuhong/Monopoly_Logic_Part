package model.card;

import context.GameState;
import model.PlayerModel;

/**
 *
 *嫁祸卡，遇到危险的时候自动生效，将陷害或大额租金（3000元以上）嫁祸给别人
 *
 *
 */
public class CrossingCard extends Card{

    public CrossingCard(PlayerModel owner) {
        super(owner);
        this.name = "CrossingCard";
        this.cName = "嫁祸卡";
        this.price = 120;
    }

    @Override
    public int useCard() {
        return GameState.CARD_CROSSING;
    }
}
