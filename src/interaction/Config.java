package interaction;

public class Config {
    //Input 的type类型
    //输入游戏开始的信号
    public final static int GAME_START=0;
    //传入骰子点数
    public final static int GIVE_DICE_NUM=1;
    //传入用户选择
    public final static int GIVE_CHOICE=2;
    //传入卡片编号
    public final static int GIVE_CARD_NUM=3;

    //output 相关
    //对地块的操作
    //归属为玩家1
    public final static int PLAYER_ONE_GET=1;
    //归属为玩家2
    public final static int PLAYER_TWO_GET=2;
    //归属为玩家3
    public final static int PLAYER_THREE_GET=3;
    //归属为玩家4
    public final static int PLAYER_FOUR_GET=4;
    //房屋升级
    public final static int LEVEL_UP=5;
    //房屋降级
    public final static int LEVEL_DOWN=6;
    //房屋操作失败(现金不足)
    public final static int FAILURE=7;

    //handle 的返回值
    //正常返回
    public final static int NORMAL_RETURN=1;
    //input 的玩家不是应该发出指令的玩家
    public final static int ERROR_PLAYER_MISMATCH=2;
    //状态不对应
    public final static int ERROR_STATE_MISTAKE=3;
}
