package model.buildings;
import model.PlayerModel;
import context.GameState;

/**
 *
 * 乐透
 * 角色到达这里时，可以进行下注游戏，可能输赢奖金.
 */
public class Lottery extends Building{
    private PlayerModel player;

    public Lottery(int posX, int posY) {
        super(posX, posY);
        this.name = "乐透";
    }

    @Override
    public int getEvent() {
        return GameState.LOTTERY_EVENT;
    }
 }
