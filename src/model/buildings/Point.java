package model.buildings;

import context.GameState;

/**
 *
 * 10 30 50 点卷位 角色到达点卷位的时候，可以获得响应 点卷 的金额  点卷可以在商店购买道具
 *
 *
 *
 */
public class Point extends Building{
    private int point;


    public Point(int posX, int posY, int point) {
        super(posX, posY);
        this.name = point + "点卷位";
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    @Override
    public int getEvent() {
        return GameState.POINT_EVENT;
    }
}
