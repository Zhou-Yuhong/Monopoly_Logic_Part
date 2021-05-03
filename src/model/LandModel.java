package model;

import java.awt.*;
import control.GameRunning;
import model.buildings.Hospital;

public class LandModel implements GamePort{
    /**
     * 空地
     */
    public final static int SPACE = 1;
    /**
     * 10元奖券
     */
    public final static int PIONT_10 = 2;
    /**
     * 30元奖券
     */
    public final static int PIONT_30 = 3;
    /**
     * 50元奖券
     */
    public final static int PIONT_50 = 4;
    /**
     * 商店
     */
    public final static int SHOP = 5;
    /**
     * 乐透
     */
    public final static int LOTTERY = 6;
    /**
     * 新闻
     */
    public final static int NEWS = 7;
    /**
     * 医院
     */
    public final static int HOSPITAL = 8;
    /**
     * 公园
     */
    public final static int PARK = 9;
    /**
     * 起点
     */
    public final static int ORIGIN = 10;
    /**
     * 监狱
     */
    public final static int PRISON = 11;
    /**
     * 无建筑
     */
    public final static int NULL_SET = 0;
    /**
     * 医院坐标，使用land3
     */

    public static int Hospital_x=7;
    public static int Hospital_y=8;
    /**
     * 监狱坐标，使用land3
     */
    public static int Prison_x=0;
    public static int Prison_y=7;
    public static Point prison = new Point(0, 0);
    public LandModel(){ }
    //15*10地图
    protected int [][] land4={
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
            {SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE,SPACE},
    };
    protected int[][] land3 = {
            // 模仿大富翁其中一个地图设置
            { ORIGIN, SHOP, NEWS, SPACE, NEWS, SPACE, NEWS, PRISON,NEWS, SPACE, NEWS, SPACE, NEWS },
            { PIONT_30, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, PIONT_30 },
            { PIONT_50, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, PIONT_50 },
            { PIONT_10, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, PIONT_10 },
            { PIONT_50, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, PIONT_50 },
            { PIONT_30, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, PIONT_10 },
            { PIONT_50, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, PIONT_50 },
            { ORIGIN, NEWS, SPACE, NEWS, SPACE, NEWS, SPACE, NEWS,HOSPITAL, NEWS, SPACE, SHOP, SPACE }};
    protected int[][] land2 = {
            // 模仿大富翁其中一个地图设置
            { ORIGIN, SHOP, SPACE, SPACE, PIONT_50, NEWS, PRISON, SPACE,SPACE, SPACE, PIONT_50, SPACE, NEWS },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },
            { PARK, NULL_SET, NULL_SET, NULL_SET, NEWS, SPACE, SPACE, SPACE,SHOP, SPACE, PIONT_50, SPACE, HOSPITAL },
            { PIONT_50, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET, NULL_SET,NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SHOP, PIONT_10, PIONT_30, PIONT_10,PARK, SPACE, SPACE, SPACE, PIONT_50 },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET, NULL_SET,SPACE, NULL_SET, NULL_SET, NULL_SET, NULL_SET },
            { PIONT_30, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET, NULL_SET,SPACE, NULL_SET, NULL_SET, NULL_SET, NULL_SET },
            { PRISON, SPACE, SPACE, HOSPITAL, NEWS, SPACE, PARK, SPACE,NEWS, NULL_SET, NULL_SET, NULL_SET, NULL_SET }};
    protected int[][] land1 = {
            // 模仿大富翁其中一个地图设置
            { ORIGIN, SPACE, HOSPITAL, PIONT_50, SHOP, SPACE, PRISON, SPACE,
                    SPACE, PARK, NULL_SET, NULL_SET, NULL_SET },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET,
                    NULL_SET, NULL_SET, PIONT_30, SPACE, SPACE, NEWS },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET,
                    NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET,
                    NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET,
                    NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },
            { SPACE, NULL_SET, NULL_SET, NULL_SET, SPACE, NULL_SET, NULL_SET,
                    NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },
            { PARK, NULL_SET, NULL_SET, NULL_SET, PIONT_30, NULL_SET, NULL_SET,
                    NULL_SET, NULL_SET, NULL_SET, NULL_SET, NULL_SET, SPACE },

            { NEWS, PIONT_30, SPACE, SPACE, SHOP, PIONT_10, SPACE, SPACE,
                    SPACE, SPACE, HOSPITAL, PIONT_50, SPACE } };
    protected int[][] land;
    public int[][] getLand() {
        return land;
    }
    /**
     * 匹配地图事件
     * */
    public int matchLand(PlayerModel player) {
        return land[player.getY()][player.getX()];
    }
    /**
     *
     * 开始游戏设置
     *
     */
    public void startGameInit() {
        if (GameRunning.MAP == 1){
            land = land1;
        } else if (GameRunning.MAP == 2){
            land = land2;
        } else if (GameRunning.MAP == 3) {
            land = land3;
        } else if(GameRunning.MAP==4){
            land = land4;
        }
    }
}
