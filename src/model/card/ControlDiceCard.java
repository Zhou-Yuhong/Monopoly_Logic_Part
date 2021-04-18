package model.card;
import context.GameState;

import model.PlayerModel;
/**
 *
 *
 * 遥控骰子,使用遙控骰子，可以自由控制下一次骰子點數。
 * OK
 *
 */
public class ControlDiceCard extends Card{
    public ControlDiceCard(PlayerModel owner) {
        super(owner);
        this.name = "ControlDiceCard";
        this.cName = "遥控骰子卡";
        this.price = 30;
    }
    private int dicenum=0;  //需要遥控的点数
    @Override
    public int useCard() {
        return GameState.CARD_CONTROLDICE;
    }
    public int getDicenum(){
        return this.dicenum;
    }
    public void setDicenum(int num){
        this.dicenum=num;
    }
}
