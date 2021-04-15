package model.card;
import context.GameState;

import model.PlayerModel;
/**
 *
 * 乌龟卡,對對手或自己使用烏龜卡，會讓對手或自己連續三次只走一步。
 *
 * 一步卡、六步卡、烏龜卡、停留卡效果會互相覆蓋。
 *
 */
public class TortoiseCard extends Card{
    private int life = 3;

    public TortoiseCard(PlayerModel owner) {
        super(owner);
        this.name = "TortoiseCard";
        this.cName = "乌龟卡";
        this.price = 50;
    }

    @Override
    public int useCard() {
        return GameState.CARD_TORTOISE;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    /**
     *
     * 卡片持续效果
     *
     */
    @Override
    public int cardBuff() {
		/*
		// 增加文本提示
		this.owner.showTextTip(this.owner.getName() + " 受\"乌龟卡\" 作用，只能移动一步.. ", 2);
		this.owner.getRunning().setPoint(1);
		if (life < 0) {
			this.owner.getEffectCards().remove(this);
		}
		*/
        return GameState.CARD_BUFF_TORTOISE;
    }
}
