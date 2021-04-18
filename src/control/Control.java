package control;
//import model.BackgroundModel;
import model.BuildingsModel;
import model.DiceModel;
//import model.EffectModel;
import model.EventsModel;
import model.LandModel;
import model.PlayerModel;
import model.GamePort;
import model.TextTipModel;
import model.buildings.Building;
import model.buildings.Hospital;
import model.buildings.News;
import model.buildings.Origin;
import model.buildings.Park;
import model.buildings.Point;
import model.buildings.Prison;
import model.buildings.Shop;
import model.card.Card;
import model.card.ControlDiceCard;
import model.card.TortoiseCard;
import context.GameState;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 游戏总控制器
 *
 */

 public class Control {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    /**
     *
     * 游戏对象
     *
     */
    private GameRunning run = null;
    private List<GamePort> models = new ArrayList<>();
    //角色经过的地块
    //private List<Building> passedBuilding=new ArrayList<>();
    private List<PlayerModel> players = null;
    private BuildingsModel building = null;
    private LandModel land = null;
    private TextTipModel textTip = null;
    private DiceModel dice = null;
    public Control() {
        // 创建一个游戏状态
        this.run = new GameRunning(this, players);
        // 初始化游戏对象
        this.initClass();
        // 向游戏状态中加入玩家模型
        this.run.setPlayers(players);
    }
    /**
     *
     * 初始化游戏对象
     *
     */
    private void initClass() {
        // 创建一个新的事件模型
       // this.events = new EventsModel();
       // this.models.add(events);
        // 创建一个新的场景效果模型
       // this.effect = new EffectModel();
      //  this.models.add(effect);
        // 创建新的背景模型
       // this.background = new BackgroundModel();
      //  this.models.add(background);
        // 创建新的土地模型
        this.land = new LandModel();
        this.models.add(land);
        // 创建新的文本显示模型
        this.textTip = new TextTipModel();
        this.models.add(textTip);
        // 创建一个新的建筑模型
        this.building = new BuildingsModel(land);
        this.models.add(building);
        // 创建一个新的玩家数组
        this.players = new ArrayList<PlayerModel>();
        this.players.add(new PlayerModel(1, this));
        this.players.add(new PlayerModel(2, this));
        this.players.add(new PlayerModel(3, this));
        this.players.add(new PlayerModel(4, this));
        //初始化每个玩家内置的所有玩家指针
        for(int i=0;i<this.players.size();i++){
            this.players.get(i).setAllplayers(players);
        }
        this.models.add(players.get(0));
        this.models.add(players.get(1));
        this.models.add(players.get(2));
        this.models.add(players.get(3));
        // 创建一个新的骰子模型
        this.dice = new DiceModel(run);
        this.models.add(dice);

    }

    /**
     *
     * 控制器启动
     *
     */
    public void start() {
        // 游戏环境开始
        String nameinput;
        this.textTip.showTextTip(null,"输入玩家1姓名");
        try{
            nameinput=reader.readLine();
            this.players.get(0).setName(nameinput);
        }
        catch (IOException e){
            System.out.print(e);
        }
        this.textTip.showTextTip(null,"请输入玩家2姓名");
        try{
            nameinput=reader.readLine();
            this.players.get(1).setName(nameinput);
        }
        catch (IOException e){
            System.out.print(e);
        }
        this.textTip.showTextTip(null,"请输入玩家3姓名");
        try{
            nameinput=reader.readLine();
            this.players.get(2).setName(nameinput);
        }
        catch (IOException e){
            System.out.print(e);
        }
        this.textTip.showTextTip(null,"请输入玩家4姓名");
        try{
            nameinput=reader.readLine();
            this.players.get(3).setName(nameinput);
        }
        catch (IOException e){
            System.out.print(e);
        }
        // 刷新对象初始数据
        for (GamePort temp : this.models) {
            temp.startGameInit();
        }
        this.run.startGameInit();
    }
    public void rungame(){
        GameRunning gr=this.getRunning();
        while(gr.gameContinue()){
            if(gr.getNowPlayerState()==gr.STATE_THROWDICE){
                playDice();
                continue;
            }
            if(gr.getNowPlayerState()==gr.STATE_MOVE){
                move();
                continue;
            }
            if(gr.getNowPlayerState()==gr.STATE_CARD){
                UseCard();
                continue;
            }
        }
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public BuildingsModel getBuilding() {
        return building;
    }


    public LandModel getLand() {
        return land;
    }



    public TextTipModel getTextTip() {
        return textTip;
    }

    public GameRunning getRunning() {
        return run;
    }

    public DiceModel getDice() {
        return dice;
    }
    /**
     *
     *
     * 按下骰子
     *
     *
     */
    public void playDice()  {
        PlayerModel player=this.run.getNowPlayer();
        if(player.getInHospital()>0||player.getInPrison()>0){
            this.run.nextState();  //由掷骰子状态转到移动
            if(player.getInHospital()>0){
                this.textTip.showTextTip(player,player.getName()+"住院中,不能掷骰子");
                //player.setInHospital(player.getInHospital()-1);

            }else if(player.getInPrison()>0){
                this.textTip.showTextTip(player,player.getName()+"在监狱中，不能掷骰子");
               // player.setInPrison(player.getInPrison()-1);
            }
            this.run.nextState();//由移动状态转到使用卡片
        }
        else{
            if(player.getEffectCards().empty()) {  //没有影响玩家的卡片，掷骰子
                this.textTip.showTextTip(player, "轮到" + player.getName() + "请输入骰子点数");
                try {
                    String ss = reader.readLine();
                    int result = Integer.parseInt(ss);
                    handleDicenum(result);
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
            else{
                Card card=player.getEffectCard();
                if(card.useCard()==GameState.CARD_TORTOISE){
                    handleDicenum(1);
                    return;
                }
                if(card.useCard()==GameState.CARD_STOP){
                    handleDicenum(0);
                    return;
                }
                if(card.useCard()==GameState.CARD_CONTROLDICE){
                    ControlDiceCard tmp=(ControlDiceCard) card;
                    handleDicenum(tmp.getDicenum());
                    return;
                }
            }
        }
    }
    /**
     * 使用卡片
     *
    * */
    public void UseCard(){
        PlayerModel player=this.run.getNowPlayer();
        if(player.getInHospital()>0||player.getInPrison()>0){
            this.run.nextState();  //由用卡片状态转到换人
            if(player.getInHospital()>0){
                this.textTip.showTextTip(player,player.getName()+"住院中,不能使用卡片");

            }else if(player.getInPrison()>0){
                this.textTip.showTextTip(player,player.getName()+"在监狱中，不能使用卡片");
            }
            return;
        }
        List<Card> cards=player.getCards();
        if(cards.size()==0){
            this.textTip.showTextTip(null,player.getName()+"没有卡片，跳过使用卡片环节");
            this.run.nextState();
            return;
        }
        else {
            this.textTip.showTextTip(null,"请输入编号来选择要使用的卡片(输入范围外数字表示不使用卡片)");
            for(int i=0;i<cards.size();i++){
                Integer tmp=i+1;
                this.textTip.showTextTip(null,tmp.toString()+" "+cards.get(i).getcName());
            }
            try{
              int num=Integer.parseInt(reader.readLine());
               if(num<=0||num>cards.size()) {
                   this.textTip.showTextTip(null,player.getName()+"不使用卡片");
                   this.run.nextState();
                   return;
               }
               else{
                 Card card=cards.get(num-1);
                 useCard(card);
                  this.run.nextState();
               }
                }
            catch (IOException e){
                System.out.print(e);
            }
        }
    }


    /**
     * 处理骰子数
    */
    private void handleDicenum(int num){
        //将点数传入游戏对象
        this.run.setPoint(num);
        //通过run的状态更新外部骰子
        this.dice.setPoint(this.run.getPoint());
        //转换转态到移动状态
        this.run.nextState();
    }
    /**
     *运动的总函数
    * */
    public void move(){
        List<Building> passedBuilding=this.run.getpassdeBuilding();
        passedBuilding.clear();
        PlayerModel player=this.run.getNowPlayer();

        for(int i=0;i<this.run.getPoint();i++){
            movePlayer();
        }
        //触发事件
        for(int i=0;i<passedBuilding.size()-1;i++){
            if(passedBuilding.get(i).getName()=="起点"){
                disposePassEvent(passedBuilding.get(i),GameState.ORIGIN_PASS_EVENT,player);
                break;    //最多经过一次起点
            }
        }
        //玩家停下操作
        this.playerStop();
        this.run.nextState();
    }
    /**
     *
     *
     * 玩家移动,下面的只移动一步
     *
     *
     */
    public void movePlayer(){
        List<Building> passedBuilding=this.run.getpassdeBuilding();
        //移动玩家,直接使用MAP3，
        PlayerModel player=this.run.getNowPlayer();
        if(player.getX()==0&&player.getY()!=GameState.ROW_NUM-1){
            player.setY(player.getY()+1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getX()==0&&player.getY()==GameState.ROW_NUM-1){
            player.setX(1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getY()==GameState.ROW_NUM-1&&player.getX()!=GameState.LINE_NUM-1){
            player.setX(player.getX()+1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getY()==GameState.ROW_NUM-1&&player.getX()==GameState.LINE_NUM-1){
            player.setY(player.getY()-1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getX()==GameState.LINE_NUM-1&&player.getY()!=0){
            player.setY(player.getY()-1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getX()==GameState.LINE_NUM-1&& player.getY()==0){
            player.setX(player.getX()-1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getY()==0){
            player.setX(player.getX()-1);
            passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
    }
 /**
  *
  * 经过房间事件处理,只有原点有
  */
 private void disposePassEvent(Building b, int event, PlayerModel player) {
     switch (event) {
         case GameState.ORIGIN_PASS_EVENT:
             // 中途经过原点
             passOrigin(b, player);
             break;
         default:
             break;
     }
 }
    /**
     *
     * 中途经过原点
     *
     */
    private void passOrigin(Building b, PlayerModel player) {
        this.textTip.showTextTip(player, player.getName() + " 路过原点，奖励 "
                + ((Origin) b).getPassReward() + "金币.");
        player.setCash(player.getCash() + ((Origin) b).getPassReward());
    }

    /**
     *
     * 玩家移动完毕，停下操作
     *
     */
    public void playerStop() {
        // 当前玩家
        PlayerModel player = this.run.getNowPlayer();
        // 该地点房屋
        Building building = this.building.getBuilding(player.getX(),player.getY());
        if (building != null) {// 获取房屋
            int event = building.getEvent();
            // 触发房屋信息
            disposeStopEvent(building, event, player);

        }
    }

    /**
     *
     * 停留房屋事件处理
     *
     *
     */
    private void disposeStopEvent(Building b, int event, PlayerModel player) {
        switch (event) {
            case GameState.HOSPITAL_EVENT:
                // 停留在医院
                stopInHospital(b, player);
                break;
            case GameState.HUOSE_EVENT:
                // 停留在可操作土地
                stopInHouse(b, player);
                break;
            case GameState.LOTTERY_EVENT:
                // 停留在乐透点上
                stopInLottery(b, player);
                break;
            case GameState.NEWS_EVENT:
                // 停留在新闻点上
                stopInNews(b, player);
                break;
            case GameState.ORIGIN_EVENT:
                // 停留在原点
                stopInOrigin(b, player);
                break;
            case GameState.PARK_EVENT:
                // 停留在公园
                stopInPack(b, player);
                break;
            case GameState.POINT_EVENT:
                // 停留在点卷位
                stopInPoint(b, player);
                break;
            case GameState.PRISON_EVENT:
                // 停留在监狱
                stopInPrison(b, player);
                break;
            case GameState.SHOP_EVENT:
                // 停留在商店
                stopInShop(b, player);
                break;
        }

    }
    /**
     *
     * 停留在商店
     *
     */
    private void stopInShop(Building b, PlayerModel player) {
        if (player.getNx() > 0) {
            // 为商店的货架从新生成商品
            ((Shop) b).createCards();
            // 为商店面板更新新的卡片商品
            this.textTip.showTextTip(player, player.getName() + "到达了商店");
        }
        else{
            this.textTip.showTextTip(player,player.getName()+"到达商店，但是没钱购买");
        }
    }/**
     *
     * 停留在监狱
     *
     */
    private void stopInPrison(Building b, PlayerModel player) {
        int days = (int) (Math.random() * 3) + 2;
        player.setInPrison(days);
        int random = (int) (Math.random() * ((Prison) b).getEvents().length);
        String text = ((Prison) b).getEvents()[random];
        this.textTip.showTextTip(player, player.getName() + text + "停留"
                + (days - 1) + "天.");
    }

    /**
     *
     * 停留在点卷位
     *
     */
    private void stopInPoint(Building b, PlayerModel player) {
        player.setNx(((Point) b).getPoint() + player.getNx());
        this.textTip.showTextTip(player, player.getName() + " 获得 "
                + ((Point) b).getPoint() + "点卷.");
    }
    /**
     *
     * 停留在公园
     *
     */
    private void stopInPack(Building b, PlayerModel player) {
        int random= (int) (Math.random()*10%3)+1;

        switch (random) {
            case 0:
            case 1:
                // 减一金币
                player.setCash(player.getCash() - 1);
                this.textTip.showTextTip(player, player.getName()+"经过公园，失去1金币");
                break;
            case 2:
                // 减200金币
                player.setCash(player.getCash() - 200);
                this.textTip.showTextTip(player,player.getName()+"经过公园，失去200金币");
                break;
            case 3:
                // 加200金币
                player.setCash(player.getCash() + 200);
                this.textTip.showTextTip(player,player.getName()+"经过公园，得到200金币");
                break;
        }

    }
    /**
     *
     * 停留在原点
     *
     */
    private void stopInOrigin(Building b, PlayerModel player) {
        this.textTip.showTextTip(player, player.getName() + " 在起点停留，奖励 "
                + ((Origin) b).getReward() + "金币.");
        player.setCash(player.getCash() + ((Origin) b).getReward());
    }
    /**
     *
     * 停留在新闻点上
     *
     */
    private void stopInNews(Building b, PlayerModel player) {
        int random = (int) (Math.random() * 17);
        switch (random) {
            case 0:
            case 1:
                // 设置天数
                player.setInHospital(player.getInHospital() + 4);
                this.textTip.showTextTip(player,player.getName()+"看电影遇鬼，被吓哭了，住进医院3天");
                // 玩家位置切换到医院位置
                    player.setX(LandModel.Hospital_x);
                    player.setY(LandModel.Hospital_y);

                break;
            case 2:
            case 3:
                player.setCash(player.getCash() - 1000);
                this.textTip.showTextTip(player, player.getName()+"过马路闯红灯,罚款1000金币");
                break;
            case 4:
                player.setCash(player.getCash() - 1500);
                this.textTip.showTextTip(player,player.getName()+"钱包丢失，损失1500金币");
                break;
            case 5:
                player.setCash(player.getCash() - 2000);
                this.textTip.showTextTip(player,player.getName()+"乐善好施，捐款2000金币");
                break;
            case 6:
            case 7:
                player.setCash(player.getCash() - 300);
                this.textTip.showTextTip(player,player.getName()+"随地大小便，罚款300金币");
                break;
            case 8:
                player.setCash(player.getCash() - 400);
                this.textTip.showTextTip(player,player.getName()+"请同学喝咖啡，花费400金币");
                break;
            case 9:
                // 点卷小于不能发生事件
                if (player.getNx() < 40) {
                    stopInNews(b, player);
                    return;
                }
                this.textTip.showTextTip(player,player.getName()+"网恋被人骗走40点券");
                player.setNx(player.getNx() - 40);
                break;
            case 10:
                this.textTip.showTextTip(player,player.getName()+"买股票损失500金币");
                player.setCash(player.getCash() - 500);
                break;
            case 11:
                this.textTip.showTextTip(player,player.getName()+"买彩票中了1000金币");
                player.setCash(player.getCash() + 1000);
                break;
            case 12:
            case 13:
                this.textTip.showTextTip(player,player.getName()+"投资基金，赚了2000金币");
                player.setCash(player.getCash() + 2000);
                break;
            case 14:
                this.textTip.showTextTip(player,player.getName()+"获得仙女钻石戒指，变卖得到3999金币，100点券");
                player.setCash(player.getCash() + 3999);
                player.setNx(player.getNx() + 100);
                break;
            case 15:
                this.textTip.showTextTip(player,player.getName()+"在自己家中挖到宝藏，收获300点券");
                player.setNx(player.getNx() + 300);
                break;
            case 16:
                for (int i = 0; i  < player.getCards().size();i++){
//				System.out.println(player.getCards().get(i).getcName());
                    // 嫁祸卡
                    if (player.getCards().get(i).getName().equals("CrossingCard")){
                        player.getCards().remove(i);
                        // 对手减少金钱.
                        player.getnextplayer().setCash(player.getnextplayer().getCash() - 3000);
                        this.textTip.showTextTip(player, player.getName() + "将一笔\"3000元\"嫁祸给 "+ player.getnextplayer().getName()+"。真是人算不如天算啊.");
                        return;
                    }
                }
                player.setCash(player.getCash() - 3000);
                break;
        }

    }
    /**
     *
     * 停留在乐透点上
     *
     */
    private void stopInLottery(Building b, PlayerModel player) {
       char flag;
       if(player.getCash()<1000){
        this.textTip.showTextTip(player,"对不起，"+player.getName()+"的金币不足1000，无法抽奖");
        return;
       }

      this.textTip.showTextTip(player,"亲爱的"+player.getName()+"，你愿意花费1000金币进行抽奖吗，按Y确认，按其他取消");
       try{
           flag=(char)reader.read();
           if(flag=='Y'||flag=='y'){
            player.setCash(player.getCash()-1000);
            int result=(int)(Math.random()*2000);
            this.textTip.showTextTip(player,"恭喜"+player.getName()+"，你得到了"+String.valueOf(result)+"金币");
            player.setCash(player.getCash()+result);
           }
       }
       catch (IOException e){

       }
    }

    /**
     *
     *
     * 停留在可操作土地
     *
     *
     */
    private void stopInHouse(Building b, PlayerModel player) {
        if (b.isPurchasability()) {// 玩家房屋
            if (b.getOwner() == null) { // 无人房屋
                // 执行买房操作
                this.buyHouse(b, player);
            } else {// 有人房屋
                if (b.getOwner().equals(player)) {// 自己房屋
                    // 执行升级房屋操作
                    this.upHouseLevel(b, player);
                } else {// 别人房屋
                    // 执行交税操作
                    this.giveTax(b, player);
                }
            }
        }
    }
    /**
     *
     * 执行交税操作
     *
     *
     */
    private void giveTax(Building b, PlayerModel player) {
        if (b.getOwner().getInHospital() > 0) {
            // 增加文本提示
            this.textTip.showTextTip(player, b.getOwner().getName()
                    + "正在住院,免交过路费.");
        } else if (b.getOwner().getInPrison() > 0) {
            // 增加文本提示
            this.textTip.showTextTip(player, b.getOwner().getName()
                    + "正在监狱,免交过路费.");
        } else {
            int revenue = b.getRevenue();
            // 该玩家减少金币
            player.setCash(player.getCash() - revenue);
            // 业主得到金币
            b.getOwner().setCash(b.getOwner().getCash() + revenue);
            // 增加文本提示
            this.textTip.showTextTip(player, player.getName() + "经过"
                    + b.getOwner().getName() + "的地盘，过路费:" + revenue + "金币.");

        }

    }
    /**
     *
     * 执行升级房屋操作
     *
     */
    private void upHouseLevel(Building b, PlayerModel player) {
        char flag='0';
        if (b.canUpLevel()) {
            // 升级房屋
            int price = b.getUpLevelPrice();
            String name = b.getName();
            String upName = b.getUpName();
           this.textTip.showTextTip(player,
                    "亲爱的:" + player.getName() + "\r\n" + "是否升级这块地？\r\n" + name
                            + "→" + upName + "\r\n" + "价格：" + price + " 金币. 按下Y表示确认");

           try{
               flag=(char)reader.read();
               String tmp=reader.readLine();
           }
           catch(IOException e){
               System.out.print(e);
           }
            if (flag == 'Y'||flag=='y') {
                if (player.getCash() >= price) {
                    b.setLevel(b.getLevel() + 1);
                    // 减少需要的金币
                    player.setCash(player.getCash() - price);
                    // 增加文本提示
                    this.textTip.showTextTip(player, player.getName() + " 从 "
                            + name + " 升级成 " + upName + ".花费了 " + price
                            + "金币. ");
                } else {
                    // 增加文本提示
                    this.textTip.showTextTip(player, player.getName()
                            + " 金币不足,操作失败. ");
                }
            }
        }

    }
    /**
     *
     * 执行买房操作
     *
     *
     */
    private void buyHouse(Building b, PlayerModel player) {
        char flag='0';
        int price = b.getUpLevelPrice();
        this.textTip.showTextTip(
                player,
                "亲爱的:" + player.getName() + "\r\n" + "是否购买下这块地？\r\n"
                        + b.getName() + "→" + b.getUpName() + "\r\n" + "价格："
                        + price + " 金币. 输入Y表示购买这块地");

        try{
            flag=(char)reader.read();
            String tmp=reader.readLine();
        }
        catch(IOException e){
            System.out.print(e);
        }
        if (flag=='Y'||flag=='y') {
            // 购买
            if (player.getCash() >= price) {
                b.setOwner(player);
                b.setLevel(1);
                // 将该房屋加入当前玩家的房屋列表下
                player.getBuildings().add(b);
                // 减少需要的金币
                player.setCash(player.getCash() - price);
                this.textTip.showTextTip(player, player.getName()
                        + " 买下了一块空地.花费了: " + price + "金币. ");
            } else {
                this.textTip.showTextTip(player, player.getName()
                        + " 金币不足,操作失败. ");
            }
        }

    }
    /**
     *
     * 停留在医院
     *
     */
    private void stopInHospital(Building b, PlayerModel player) {
        int days = (int) (Math.random() * 4) + 2;
        player.setInHospital(days);
        int random = (int) (Math.random() * ((Hospital) b).getEvents().length);
        String text = ((Hospital) b).getEvents()[random];
        this.textTip.showTextTip(player, player.getName() + text + "停留"
                + (days - 1) + "天.");
    }

    /**
     *
     * 使用卡片
     *
     */
    private void useCard(Card card) {
        switch (card.useCard()) {
            case GameState.CARD_ADDLEVEL:
                // 使用加盖卡
                useAddLevelCard(card);
                break;
            case GameState.CARD_AVERAGERPOOR:
                // 使用均贫卡
                useAveragerPoorCard(card);
                break;
            case GameState.CARD_CHANGE:
                // 使用换屋卡
                useChangeCard(card);
                break;
            case GameState.CARD_CONTROLDICE:
                // 使用遥控骰子卡
                useControlDiceCard(card);
                break;
            case GameState.CARD_HAVE:
                // 使用购地卡
                useHaveCard(card);
                break;
            case GameState.CARD_REDUCELEVEL:
                // 使用降级卡
                useReduceLevelCard(card);
                break;
            case GameState.CARD_ROB:
                // 使用抢夺卡
                useRobCard(card);
                break;
            case GameState.CARD_STOP:
                // 使用停留卡
                useStopCard(card);
                break;
            case GameState.CARD_TALLAGE:
                // 使用查税卡
                useTallageCard(card);
                break;
            case GameState.CARD_TORTOISE:
                // 使用乌龟卡
                useTortoiseCard(card);
                break;
            case GameState.CARD_TRAP:
                // 使用陷害卡
                useTrapCard(card);
                break;
            case GameState.CARD_CROSSING:
                // 使用嫁祸卡
                useCrossingCard(card);
                break;
        }
    }


    /**
     *
     * 使用嫁祸卡
     *
     */
    private void useCrossingCard(Card card) {
//        Object[] options1 = { "重新选择" };
//        JOptionPane.showOptionDialog(null, " 嫁祸卡在大事件发生时会自动使用.",
//                "卡片使用阶段.", JOptionPane.YES_OPTION,
//                JOptionPane.PLAIN_MESSAGE, null, options1,
//                options1[0]);
        this.textTip.showTextTip(null,"嫁祸卡使用成功，嫁祸卡会在大事件发生的时候自动使用");
    }
    /**
     *
     * 使用陷害卡
     *
     */
    private void useTrapCard(Card card) {
        this.textTip.showTextTip(null,card.geteOwner().getName()+" ,请选择你要陷害的玩家编号");
        try {
            int num = reader.read();
            reader.readLine();
            PlayerModel target=this.run.getplayerWithnum(num);
            if(target==null) {
                this.textTip.showTextTip(null,"该玩家不存在");
                useTrapCard(card);
                return;
            }
            else{
                target.setInPrison(target.getInPrison()+2);
                //玩家位置切换到监狱
                target.setX(LandModel.Prison_x);
                target.setY(LandModel.Prison_y);
                //文本提示
                this.textTip.showTextTip(card.geteOwner(),card.geteOwner().getName()+"使用了陷害卡，使"+target.getName()+"入狱两天");
                card.geteOwner().getCards().remove(card);
            }
        }
        catch (IOException e){
            System.out.print(e);
        }
    }
    /**
     *
     * 使用乌龟卡
     *
     *
     */
    private void useTortoiseCard(Card card) {
        this.textTip.showTextTip(card.geteOwner(),card.geteOwner().getName()+"请选择玩家编号使用乌龟卡");
        try{
            int num=reader.read();
            reader.readLine();
            PlayerModel target=this.run.getplayerWithnum(num);
            if(target==null) {
                this.textTip.showTextTip(null,"该玩家不存在");
                useTortoiseCard(card);
                return;
            }
            else{
                if(card.geteOwner().getNumber()==num){
                   //card.geteOwner().getEffectCards().add(card);
                    //card.seteOwner(card.getOwner());
                    for(int i=0;i<3;i++){
                        TortoiseCard tmp=new TortoiseCard(null);
                        card.getOwner().addEffecCard(tmp);
                    }
                   this.textTip.showTextTip(card.geteOwner(),card.geteOwner().getName()+"对自己使用了乌龟卡");
                   card.geteOwner().getCards().remove(card);
                }
                else{
                  target.getEffectCards().add(card);
                  card.seteOwner(target);
                  this.textTip.showTextTip(card.getOwner(),card.getOwner().getName()+"对"+target.getName()+"使用了乌龟卡");
                    for(int i=0;i<3;i++){
                        TortoiseCard tmp=new TortoiseCard(null);
                        target.addEffecCard(tmp);
                    }
                  card.geteOwner().getCards().remove(card);
                }
            }
        }
        catch(IOException e){
            System.out.print(e);
        }
    }
    /**
     *
     * 使用查税卡
     *
     *
     */
    private void useTallageCard(Card card) {
//        Object[] options = { "确认使用", "重新选择" };
//        int response = JOptionPane.showOptionDialog(null, "确认使用\"查税卡\"从 \""
//                        + card.getOwner().getOtherPlayer().getName() + "\"手中获得 10%税款?",
//                "卡片使用阶段.", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
//                null, options, options[0]);
//        if (response == 0) {
//            // 使用
//            int money = (int) (card.getOwner().getOtherPlayer().getCash() / 10);
//            card.getOwner().setCash(card.getOwner().getCash() + money);
//            card.getOwner()
//                    .getOtherPlayer()
//                    .setCash(card.getOwner().getOtherPlayer().getCash() - money);
//            // 增加文本提示
//            this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
//                    + " 使用了 \"查税卡\"，从 \""
//                    + card.getOwner().getOtherPlayer().getName()
//                    + "\"手中获得 10%税款", 2);
//            // 　减去卡片
//            card.getOwner().getCards().remove(card);
//        }
        this.textTip.showTextTip(card.geteOwner(),card.geteOwner().getName()+"请选择玩家编号使用查税卡");
        try{
            int num=reader.read();
            reader.readLine();
            PlayerModel target=this.run.getplayerWithnum(num);
            if(target==null) {
                this.textTip.showTextTip(null,"该玩家不存在");
                useTallageCard(card);
                return;
            }
            else{
              int money=(int) target.getCash()/10;
              card.getOwner().setCash(card.getOwner().getCash()+money);
              target.setCash(target.getCash()-money);
              //增加文本提示
                this.textTip.showTextTip(card.getOwner(),card.getOwner().getName()+"使用了查税卡,从"+target.getName()+"手中获得 10%税款");
                card.getOwner().getCards().remove(card);
            }
        }
        catch(IOException e){
            System.out.print(e);
        }

    }
    /**
     *
     *
     * 使用停留卡
     *
     */
    private void useStopCard(Card card) {
        this.textTip.showTextTip(card.geteOwner(),card.geteOwner().getName()+"请选择玩家编号使用停留卡");
        try{
            int num=reader.read();
            reader.readLine();
            PlayerModel target=this.run.getplayerWithnum(num);
            if(target==null) {
                this.textTip.showTextTip(null,"该玩家不存在");
                useStopCard(card);
                return;
            }
            else{
                if(card.geteOwner().getNumber()==num){
                    card.seteOwner(card.getOwner());
                    card.geteOwner().addEffecCard(card);
                    this.textTip.showTextTip(card.geteOwner(),card.geteOwner().getName()+"对自己使用了停留卡");
                    card.geteOwner().getCards().remove(card);
                }
                else{
                    target.addEffecCard(card);
                    card.seteOwner(target);
                    this.textTip.showTextTip(card.getOwner(),card.getOwner().getName()+"对"+target.getName()+"使用了停留卡");
                    card.geteOwner().getCards().remove(card);
                }
            }
        }
        catch(IOException e){
            System.out.print(e);
        }
    }
    /**
     *
     *
     * 使用抢夺卡
     *
     *
     */
    private void useRobCard(Card card) {
//        if (card.getOwner().getCards().size() >= PlayerModel.MAX_CAN_HOLD_CARDS) {
//            // 无法使用
//            Object[] options = { "重新选择" };
//            JOptionPane.showOptionDialog(null, " 您的卡片数量已经达到上限，无法使用\"抢夺卡\"",
//                    "卡片使用阶段.", JOptionPane.YES_OPTION,
//                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        } else if (card.getOwner().getOtherPlayer().getCards().size() == 0) {
//            // 无法使用
//            Object[] options = { "重新选择" };
//            JOptionPane.showOptionDialog(null, " \""
//                            + card.getOwner().getOtherPlayer().getName()
//                            + "\"没有卡片，无法使用\"抢夺卡\"", "卡片使用阶段.", JOptionPane.YES_OPTION,
//                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        } else {
//            PlayerModel srcPlayer = card.getOwner().getOtherPlayer();
//            // 随机选取一张
////			System.out.println(srcPlayer.getCards().size() + "zhang");
//            Card getCard = srcPlayer.getCards().get((int) (srcPlayer.getCards().size() * Math.random()));
//            // 对手丧失卡片
//            srcPlayer.getCards().remove(getCard);
//            // 卡片拥有者获得
//            card.getOwner().getCards().add(getCard);
//            // 更改获得卡片拥有者
//            getCard.setOwner(card.getOwner());
//            // 增加文本提示
//            this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
//                    + " 使用了 \"抢夺卡\"，抢夺了 \"" + srcPlayer.getName() + "\"的一张\""
//                    + getCard.getcName() + ".\". ", 2);
//            // 　减去卡片
//            card.getOwner().getCards().remove(card);
//        }
        this.textTip.showTextTip(card.getOwner(),"请输入玩家编号使用抢夺卡");
        try{
            int num= reader.read();
            reader.readLine();
            PlayerModel target=this.run.getplayerWithnum(num);
            if(target==null) {
                this.textTip.showTextTip(null,"该玩家不存在");
                useRobCard(card);
                return;
            }
            else if(target.getCards().size()==0){
                this.textTip.showTextTip(null,target.getName()+"没有卡片，无法使用抢夺卡");
                return;
            }
            else {
                Card getcard=target.getCards().get((int)(target.getCards().size()*Math.random()));
                //对手丧失卡片
                target.getCards().remove(getcard);
                //卡片拥有者获得
                card.getOwner().getCards().add(getcard);
                //更改卡片拥有者
                getcard.seteOwner(card.geteOwner());
                //提示信息
                this.textTip.showTextTip(card.getOwner(),card.getOwner().getName()+"使用了抢夺卡，抢夺了"+target.getName()+"的一张"+getcard.getcName());
                //减去卡片
                card.getOwner().getCards().remove(card);
            }
        }
        catch (IOException e){
            System.out.print(e);
        }
    }
    /**
     *
     * 使用降级卡
     *
     */
    private void useReduceLevelCard(Card card) {
        Building building = this.building.getBuilding(card.getOwner().getX(),card.getOwner().getY());
        if (building.getOwner() != null
                && !building.getOwner().equals(card.getOwner())) {// 是对手的房屋
            if (building.getLevel() > 0) { // 可以降级
                // 降级
                building.setLevel(building.getLevel() - 1);
                // 增加文本提示
                this.textTip.showTextTip(card.getOwner(), card.getOwner()
                        .getName()
                        + " 使用了 \"降级卡\"，将\""
                        + building.getOwner().getName()
                        + "\"的房屋等级降低一级. ");
                // 　减去卡片
                card.getOwner().getCards().remove(card);
            } else {
                // 无法使用,不可降级
               this.textTip.showTextTip(null,"当前房屋不可降级");
               //重新选择卡片
                UseCard();
                return;
            }
        } else {
            // 无法使用.
            this.textTip.showTextTip(null,"当前房屋不可使用降级卡");
            //重新选择卡片
            UseCard();
            return;
        }
    }
    /**
     *
     * 使用购地卡
     *
     */
    private void useHaveCard(Card card) {
        // 该地点房屋
        Building building = this.building.getBuilding(card.getOwner().getX(),card.getOwner().getY());
        if (building.getOwner() != null
                && !building.getOwner().equals(card.getOwner())) {// 是对方的房屋
            this.textTip.showTextTip(card.getOwner(),"确认使用\"购地卡\"将此地收购？需要花费：" + building.getAllPrice() + " 金币.");
            try{
                char choose=(char)reader.read();
                reader.readLine();
                if(choose=='Y'||choose=='y'){
                    if (card.getOwner().getCash() >= building.getAllPrice()) {
                        // 金币交换
                        building.getOwner().setCash(
                                building.getOwner().getCash()
                                        + building.getAllPrice());
                        card.getOwner().setCash(
                                card.getOwner().getCash() - building.getAllPrice());
                        building.setOwner(card.getOwner());
                        // 增加文本提示
                        this.textTip.showTextTip(card.getOwner(), card.getOwner()
                                .getName() + " 使用了 \"购地卡\"，收购获得了该土地. ");
                        // 　减去卡片
                        card.getOwner().getCards().remove(card);
                    } else {
                        this.textTip.showTextTip(null, " 金币不足，无法购买房屋!");
                        UseCard();
                        return;
                    }
                }
                else {
                    //取消使用
                    return;
                }
            }
            catch(IOException e){
                System.out.print(e);
            }
        } else {
           this.textTip.showTextTip(null, "此房屋无法使用该卡片.");
           UseCard();
           return;
        }
    }
    /**
     *
     *
     * 使用遥控骰子卡
     *
     *
     */
    private void useControlDiceCard(Card card) {
        this.textTip.showTextTip(card.getOwner(),"使用遥控骰子卡，请输入想获得的骰子点数（超出1-6区间为重新选择）");
        try{
            int num=Integer.parseInt(reader.readLine());
            if(num<0||num>6){
                useControlDiceCard(card);
                return;
            }
            else{
                this.run.setPoint(num);
                //文本提示
                this.textTip.showTextTip(card.getOwner(),card.getOwner().getName()+"使用了遥控骰子卡");
                ControlDiceCard tmp=(ControlDiceCard) card;
                tmp.setDicenum(num);
                card.getOwner().addEffecCard(card);
                card.getOwner().getCards().remove(card);
            }
        }
        catch(IOException e){
            System.out.print(e);
        }
    }
    /**
     *
     * 使用均贫卡
     *
     */
    private void useAveragerPoorCard(Card card) {
     this.textTip.showTextTip(null, "请输入玩家编号使用均贫卡");
     try{
         int num=Integer.parseInt(reader.readLine());
         PlayerModel target=this.run.getplayerWithnum(num);
         if(target==null){
             this.textTip.showTextTip(null,"该玩家不存在");
             useAveragerPoorCard(card);
             return;
         }
         if(target.equals(card.geteOwner())){
             this.textTip.showTextTip(null,"不能对自己使用均贫卡");
             useAveragerPoorCard(card);
             return;
         }
         else {
             // 使用
             int money = (int) (card.getOwner().getCash() + target.getCash()) / 2;
             card.getOwner().setCash(money);
             target.setCash(money);
             // 增加文本提示
             this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
                     + " 使用了 \"均贫卡\"，与"+target.getName()+"平分了现金,现在双方现金数为:" + money + " 金币. ");

             // 　减去卡片
             card.getOwner().getCards().remove(card);
         }
     }
     catch(IOException e) {
         System.out.print(e);
     }
    }
    /**
     *
     * 使用加盖卡
     *
     */

    private void useAddLevelCard(Card card) {
        Building building = this.building.getBuilding(card.getOwner().getX(),card.getOwner().getY());
        if (building.getOwner() != null
                && building.getOwner().equals(card.getOwner())) {// 是自己的房屋
            if (building.canUpLevel()) { // 可升级
                // 升级
                building.setLevel(building.getLevel() + 1);
                // 增加文本提示
                this.textTip.showTextTip(card.getOwner(), card.getOwner()
                        .getName() + " 使用了 \"加盖卡\"，将房屋等级提升一级. ");
                // 　减去卡片
                card.getOwner().getCards().remove(card);
            } else {
                // 无法使用,不可升级
              this.textTip.showTextTip(null, " 当前房屋不可升级.");
              UseCard();
              return;
            }
        } else {
            // 无法使用.
             this.textTip.showTextTip(null, " 当前房屋不能使用该卡片");
             UseCard();
             return;
        }
    }
    /**
     *
     * 使用换屋卡,随机与一名对手交换当前所处房屋
     *
     */
    private void useChangeCard(Card card) {
        Building building = this.building.getBuilding(card.getOwner().getX(),card.getOwner().getY());
        if (building.getOwner() != null
                && !building.getOwner().equals(card.getOwner())) {// 是对手房屋
           this.textTip.showTextTip(null,"请输入要使用换屋卡的玩家对象");
           try{
               int num=Integer.parseInt(reader.readLine());
               PlayerModel target=this.run.getplayerWithnum(num);
               if(target==null){
                   this.textTip.showTextTip(null,"该玩家不存在");
                   useChangeCard(card);
                   return;
               }
               if(target.equals(card.geteOwner())){
                   this.textTip.showTextTip(null,"不能对自己使用换屋卡");
                   useChangeCard(card);
                   return;
               }
               else {
                   if(target.getBuildings().size()==0){
                       this.textTip.showTextTip(null,"目标玩家没有房子，不能使用换屋卡");
                       return;
                   }
                   else{
                       Building changebuilding=target.getBuildings().get((int)(Math.random()*target.getBuildings().size()));
                       changebuilding.setOwner(card.getOwner());
                       building.setOwner(target);
                       target.getBuildings().remove(changebuilding);
                       card.getOwner().getBuildings().remove(building);
                       this.textTip.showTextTip(null,card.getOwner().getName()+"使用了换屋卡，与"+target.getName()+"交换了房屋");
                       card.getOwner().getCards().remove(card);
                   }
               }

           }
           catch(IOException e){
               System.out.print(e);
           }
        } else {
            this.textTip.showTextTip(null,"当前房屋不可使用换屋卡");
            UseCard();
            return;
        }
    }

    /**
     *
     * 游戏结束~
     *
     */

    public void gameOver () {
        this.run.setNowPlayerState(GameRunning.GAME_STOP);
    }
    }
