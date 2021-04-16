package model;
import model.buildings.Building;
import model.card.Card;
import control.Control;
import control.GameRunning;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家信息
 *
 *
 */
public class PlayerModel implements GamePort{
    /**
     * 姓名
     */
    private String name;
    /**
     * 玩家使用人物
     */
    private int part = 0;
    /**
     * 现金
     */
    private int cash;
    /**
     * 点卷
     */
    private int nx;

    /**
     * 当前坐标 x 人物右下角点X
     */
    private int x;
    /**
     * 当前坐标 y 人物右下角点y
     */
    private int y;

    /**
     *
     * 剩余住院天数
     *
     */
    private int inHospital;
    /**
     *
     * 剩余监狱天数
     *
     */
    private int inPrison;

    /**
     *
     * 玩家编号,显示房屋图片使用
     *
     */
    private int number =0;

    /**
     *
     * 玩家拥有房屋链表
     *
     */
    private List<Building> buildings = new ArrayList<Building>();

    /**
     *
     * 拥有卡片
     *
     */
    private List<Card> cards = new ArrayList<Card>();

    /**
     *
     * 最大可持有卡片
     *
     */
    public static int MAX_CAN_HOLD_CARDS = 8;

    /**
     *
     * 附加身上的EFFECT 卡片
     *
     */
    private List<Card> effectCards = new ArrayList<Card>();

    /**
     *
     * 对方玩家
     *
     */
    private List<PlayerModel> allplayers = null;
    /**
     *
     * 游戏控制器
     *
     */
    private Control control = null;

    public PlayerModel(int number, Control control) {
        this.name = "";
        this.number = number;
        this.control = control;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getEffectCards() {
        return effectCards;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public int getInPrison() {
        return inPrison;
    }

    public void setInPrison(int inPrison) {
        this.inPrison = inPrison;
    }

    public PlayerModel getnextplayer(){
        if(this.number==4){
            return this.allplayers.get(0);
        }
        else{
            return this.allplayers.get(this.number);
        }
    }

    public void setAllplayers(List<PlayerModel> allplayers) {
        this.allplayers=allplayers;
    }

    public int getNumber() {
        return number;
    }

    public int getInHospital() {
        return inHospital;
    }

    public void setInHospital(int inHospital) {
        this.inHospital = inHospital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void debug() {
        System.out.println("玩家:" + name + ",坐标：[" + x + "," + y + "].");
    }

    /**
     *
     * 开始游戏设置
     *
     */
    public void startGameInit() {

        // 初始化玩家金钱
        this.cash = GameRunning.PLAYER_CASH;
    }
}
