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
    private List<Building> passedBuilding=new ArrayList<>();
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
            if(gr.getNowPlayerState()==gr.STATE_CARD){
                playDice();
                continue;
            }
            if(gr.getNowPlayerState()==gr.STATE_MOVE){
                move();
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
            this.run.nextState();//由移动状态转到换人
        }
        else{
            this.textTip.showTextTip(player,"轮到"+player.getName()+"请输入骰子点数");
          try{
              String ss=reader.readLine();
          int result=Integer.parseInt(ss);
          handleDicenum(result);}
          catch(IOException e){
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
        this.passedBuilding.clear();
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
        //移动玩家,直接使用MAP3，
        PlayerModel player=this.run.getNowPlayer();
        if(player.getX()==0&&player.getY()!=GameState.ROW_NUM-1){
            player.setY(player.getY()+1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getX()==0&&player.getY()==GameState.ROW_NUM-1){
            player.setX(1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getY()==GameState.ROW_NUM-1&&player.getX()!=GameState.LINE_NUM-1){
            player.setX(player.getX()+1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getY()==GameState.ROW_NUM-1&&player.getX()==GameState.LINE_NUM-1){
            player.setY(player.getY()-1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getX()==GameState.LINE_NUM-1&&player.getY()!=0){
            player.setY(player.getY()-1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getX()==GameState.LINE_NUM-1&& player.getY()==0){
            player.setX(player.getX()-1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
            return;
        }
        if(player.getY()==0){
            player.setX(player.getX()-1);
            this.passedBuilding.add(this.building.getBuilding(player.getX(),player.getY()));
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
        int random = (int) (Math.random() * 16);
        switch (random) {
            case 0:
            case 1:
                // 设置天数
                player.setInHospital(player.getInHospital() + 4);
                this.textTip.showTextTip(player,player.getName()+"看电影遇鬼，被吓哭了，住进医院3天");
                // 玩家位置切换到医院位置
//                if (LandModel.hospital != null) {
//                    player.setX(LandModel.hospital.x);
//                    player.setY(LandModel.hospital.y);
//                }
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
//            case 16:
//                for (int i = 0; i  < player.getCards().size();i++){
////				System.out.println(player.getCards().get(i).getcName());
//                    // 嫁祸卡
//                    if (player.getCards().get(i).getName().equals("CrossingCard")){
//                        player.getCards().remove(i);
//                        // 对手减少金钱.
//                        player.getOtherPlayer().setCash(player.getOtherPlayer().getCash() - 3000);
//                        this.textTip.showTextTip(player, player.getName() + "将一笔\"3000元\"嫁祸给 "+ player.getOtherPlayer().getName()+"。真是人算不如天算啊.", 6);
//                        this.events.showImg(((News) b).get3000(), 3, new Point(
//                                420, 160, 0));
//                        new Thread(new MyThread(run, 3)).start();
//                        return;
//                    }
//                }
//                player.setCash(player.getCash() - 3000);
//                break;
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
     * 游戏结束~
     *
     */
    public void gameOver () {
        this.run.setNowPlayerState(GameRunning.GAME_STOP);
    }
    }
