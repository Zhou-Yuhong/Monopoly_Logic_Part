package model.card;
import context.GameState;
import model.PlayerModel;
/**
 *
 * 均贫卡,对一个对手使用，平分现金。
 *
 *
 */
public class AveragerPoorCard extends Card{
    public AveragerPoorCard(PlayerModel owner) {
        super(owner);
        this.name = "AveragerPoorCard";
        this.cName = "均贫卡";
        this.price = 200;
    }

    @Override
    public int useCard() {
        return GameState.CARD_AVERAGERPOOR;
    }
}
