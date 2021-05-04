package interaction;
import control.*;
import model.PlayerModel;

import java.util.List;

public class PlayerMessage {
    //玩家编号
    int playernum;
    //玩家位置,-1代表该玩家出局
    int pos_x;
    int pos_y;
    //玩家的金钱、点券。
    int cash;
    int nx;
    //玩家有的卡片，0代表莫得卡片，
    int []cards={0,0,0,0,0,0};
    public void updatePlayerMessage(Control control){
       List<PlayerModel> players=control.getRunning().getPlayers();
       for(int i=0;i<players.size();i++){
           if(players.get(i).getNumber()==this.playernum){
               this.pos_x=players.get(i).getX();
               this.pos_y=players.get(i).getY();
               this.cash=players.get(i).getCash();
               this.nx=players.get(i).getNx();
               int j=0;
               for( j=0;j<players.get(i).getCards().size();j++){
                   cards[j]=players.get(i).getCards().get(j).useCard();
               }
               while(j<6){
                   cards[j]=0;
                   j++;
               }
               return;
           }
       }
       this.pos_x=-1;
       this.pos_y=-1;
    }
    public void setPlayernum(int num){
        this.playernum=num;
    }
}
