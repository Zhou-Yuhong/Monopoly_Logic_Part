package interaction;

public class Input {
    //输入的信息类型
    int type;
     //发起的玩家编号
    int playernum;
    //是或者否
    boolean choice;
    //数字
    int num;
    public int getType(){
        return this.type;
    }
    public int getPlayernum(){
        return this.playernum;
    }
    public boolean getChoice(){
        return this.choice;
    }
    public int getNum(){
        return this.num;
    }
}
