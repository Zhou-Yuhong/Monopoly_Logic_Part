package model;
import model.buildings.Building;
import model.card.*;
import control.Control;
import control.GameRunning;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private Stack<Card> effectCards=new Stack<Card>();

    /**
     *
     * 所有玩家
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

    public Stack<Card> getEffectCards() {
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
       int size=this.allplayers.size();
       if(this.number==allplayers.get(size-1).getNumber()){
           return allplayers.get(0);
       }
       else{
           for(int i=0;i<size;i++){
               if(this.number==allplayers.get(i).getNumber()){
                   return allplayers.get(i+1);
               }
           }
       }
       return null;
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
    public void addEffecCard(Card card){
        this.effectCards.push(card);
    }
    public Card getEffectCard(){
        Card card=this.effectCards.pop();
        return card;
    }
    public boolean IfEffectCardsEmpty(){
        return this.effectCards.empty();
    }

    /**
     *
     * 开始游戏设置
     *
     */
    public void startGameInit() {

        // 初始化玩家金钱
        this.cash = GameRunning.PLAYER_CASH;
        //给每个玩家随机发两张道具卡
        for(int i=0;i<2;i++){
            int num=(int)(Math.random()*13);
            switch(num){
                case 0:
                case 1:
                    this.getCards().add(new AddLevelCard(this));
                    break;
                case 2:
                    this.getCards().add(new AveragerPoorCard(this));
                    break;
                case 3:
                    this.getCards().add(new ChangeCard(this));
                    break;
                case 4:
                    this.getCards().add(new ControlDiceCard(this));
                    break;
                case 5:
                    this.getCards().add(new CrossingCard(this));
                    break;
                case 6:
                    this.getCards().add(new HaveCard(this));
                    break;
                case 7:
                    this.getCards().add(new ReduceLevelCard(this));
                    break;
                case 8:
                    this.getCards().add(new RobCard(this));
                    break;
                case 9:
                    this.getCards().add(new TallageCard(this));
                    break;
                case 10:
                    this.getCards().add(new TortoiseCard(this));
                    break;
                case 11:
                    this.getCards().add(new TrapCard(this));
                    break;
                case 12:
                    this.getCards().add(new StopCard(this));
                    break;
                default:
                    break;
            }

        }
    }
}
