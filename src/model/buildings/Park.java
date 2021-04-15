package model.buildings;
import context.GameState;
/**
 *
 * 公园 与世无争的公园，角色到这里什么大事也不会发生
 */
 public class Park extends Building{

    public Park(int posX, int posY) {
        super(posX, posY);
        this.name = "公园";
    }
    public int getEvent(){
        return GameState.PARK_EVENT;
    }
}
