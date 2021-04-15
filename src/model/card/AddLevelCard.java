package model.card;

import context.GameState;

import model.PlayerModel;
/**
 *
 * 加盖卡,当前房屋加盖一层
 * OK
 *
 */
public class AddLevelCard extends Card{

    public AddLevelCard(PlayerModel owner) {
        super(owner);
        this.name = "AddLevelCard";
        this.cName = "加盖卡";
        this.price = 30;
    }
    @Override
    public int useCard() {
        return GameState.CARD_ADDLEVEL;
    }
}
