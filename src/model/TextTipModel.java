package model;

import control.Control;

import java.util.*;

/**
 *
 * 文字提示更新
 *
 *
 */
public class TextTipModel implements GamePort{
    private PlayerModel player = null;

    //private String tipString = "游戏开始！谁才是最后的大富翁呢？";
    private Vector<String> tipString;
    public TextTipModel (){
    }

    public  List<String> getTipString() {
        return tipString;
    }

    public void setTipString(String tipString) {
       this.tipString.add(tipString);
    }
    public void clearTipstring(){
        this.tipString.clear();
    }
    /**
     *
     * 开始游戏设置
     *
     */
    public void startGameInit (){
       tipString=new Vector<>();
    }
    public void showTextTip(PlayerModel player,String str) {
        this.player=player;
        this.setTipString(str);
        System.out.println(str);
       }
}
