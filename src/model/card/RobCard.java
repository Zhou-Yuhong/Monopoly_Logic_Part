package model.card;
import context.GameState;

import model.PlayerModel;
/**
 *
 *
 * 抢夺卡，使用此卡可以从对手处抢夺一卡，但是是随机的。
 *
 *
 */
public class RobCard extends Card{
    public RobCard(PlayerModel owner) {
        super(owner);
        this.name = "RobCard";
        this.cName = "抢夺卡";
        this.price = 50;
    }

    @Override
    public int useCard() {
        return GameState.CARD_ROB;
    }
}
