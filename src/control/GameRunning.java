package control;
import model.PlayerModel;

import java.util.List;

/**
 *
 * 游戏运转处理
 *
 *
 */
public class GameRunning {
    /**
     * 玩家列表
     */
    private List<PlayerModel> players = null;

    /**
     * 当前操作玩家
     */
    private PlayerModel nowPlayer = null;

    /**
     * 骰子当前点数
     */
    private int point;

    /**
     * 玩家使用卡片状态
     */
    public static int STATE_CARD = 1;
    /**
     * 玩家卡片作用状态
     */
    public static int STATE_CARD_EFFECT = 2;
    /**
     * 玩家掷点状态
     */
    public static int STATE_THROWDICE = 3;
    /**
     * 玩家移动状态
     */
    public static int STATE_MOVE = 4;
    /**
     *
     * 游戏终止状态
     *
     */
    public static int GAME_STOP = 5;
    /**
     *
     * 玩家目前状态
     *
     */
    private int nowPlayerState;

    /**
     *
     * 游戏进行天数
     *
     */
    public static int day = 1;

    /**
     *
     * 当前地图代码
     *
     */
    public static int MAP = 3;
    /**
     *
     * 游戏上限天数 - 1为无上限
     *
     */
    public static int GAME_DAY = -1;
    /**
     *
     * 游戏金钱上线（即胜利条件）-1为无上限
     *
     */
    public static int MONEY_MAX = -1;

    /**
     *
     * 初始化玩家初始金钱
     *
     */
    public static int PLAYER_CASH = 4000;

    private Control control;

    public GameRunning(Control control, List<PlayerModel> players) {
        this.control = control;
        this.players = players;
    }

    /**
     *
     * 获得当前玩家状态
     *
     */
    public int getNowPlayerState() {
        return this.nowPlayerState;
    }

    /**
     *
     * 转换玩家状态
     *
     */
//    public void nextState() {
//        // 判断游戏是否得出结果
//        if (gameContinue()) {
//            if (this.nowPlayerState == STATE_CARD) {
//                // “掷点状态”
//                this.nowPlayerState = STATE_CARD_EFFECT;
//                // 卡片BUFF
//                this.control.cardsBuff();
//            } else if (this.nowPlayerState == STATE_CARD_EFFECT) {
//                // “卡片生效状态”
//                this.nowPlayerState = STATE_THROWDICE;
//            } else if (this.nowPlayerState == STATE_THROWDICE) {
//                // 移动状态
//                this.nowPlayerState = STATE_MOVE;
//            } else if (this.nowPlayerState == STATE_MOVE) {
//                // 换人操作
//                this.nowPlayerState = STATE_CARD;
//                this.nextPlayer();
//                // 产生一个点数
//                this.setPoint((int) (Math.random() * 6));
//                // 完毕后执行下一个玩家的动作 - STATE_CARD
//                this.control.useCards();
//            }
//        }
//    }
       public void nextState(){
           if(gameContinue()){
               if(this.nowPlayerState==STATE_CARD){
                   //若当前是“掷点状态”，则接下来进入移动状态
                   this.nowPlayerState=STATE_MOVE;
               }
               else if(this.nowPlayerState==STATE_MOVE){
                   //若现在的状态是”移动状态“，则接下来会换人并进入掷点状态
                   this.nowPlayerState=STATE_CARD;
                   this.nextPlayer();
               }
           }
       }
    /**
     *
     * 获取当前玩家
     *
     */
    public PlayerModel getNowPlayer() {
        return this.nowPlayer;
    }

    public void setNowPlayerState(int nowPlayerState) {
        this.nowPlayerState = nowPlayerState;
    }

    /**
     *
     * 获取下个玩家
     *
     */
    public PlayerModel getNextPlayer() {
        return this.nowPlayer.getnextplayer();
    }

    /**
     * 换人操作
     */
    private void nextPlayer() {
        // 减少时间
        if (this.nowPlayer.getInPrison() > 0) {
            this.nowPlayer.setInPrison(this.nowPlayer.getInPrison() - 1);
        }
        if (this.nowPlayer.getInHospital() > 0) {
            this.nowPlayer.setInHospital(this.nowPlayer.getInHospital() - 1);
        }
        // 换人
        if (this.nowPlayer.equals(this.players.get(this.players.size()-1))) {
            this.nowPlayer = this.players.get(0);
            //一轮后游戏天数增加
            day++;
        } else {
            this.nowPlayer = this.nowPlayer.getnextplayer();
        }
    }

    /**
     *
     * 判断游戏是否结束
     *
     *
     */
    public boolean gameContinue() {
        // 天数
        if (GAME_DAY > 0 && day >= GAME_DAY) {
            this.control.gameOver();
            //提示
            System.out.println("游戏结束");
            return false;
        }
        // 最大金钱
         for(int i=0;i<players.size();i++){
             if(MONEY_MAX>0&&players.get(i).getCash()>=MONEY_MAX){
              this.control.gameOver();
              System.out.println("游戏结束");
              return false;
             }
         }
        // 破产
        for (int i=0;i<players.size();i++){
            if(players.get(i).getCash()<0){
                System.out.println(players.get(i).getName()+"破产淘汰");
                players.remove(i);
            }
        }
        if(players.size()==1) {
            this.control.gameOver();
            System.out.println("游戏结束,玩家"+players.get(0).getName()+"获胜");
            return false;
        }
        return true;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getDay() {
        return day;
    }
    public List<PlayerModel> getPlayers(){
        return this.players;
    }

    /**
     *
     * 开始游戏设置
     *
     */
    public void startGameInit() {
        // 设定当前游戏玩家
        this.nowPlayer = this.players.get(0);
        // 设定当前玩家状态为“使用卡片”
        this.nowPlayerState = STATE_CARD;
        // 首个玩家使用卡片
        //this.control.useCards();
    }
}
