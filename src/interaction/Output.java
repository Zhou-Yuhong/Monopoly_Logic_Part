package interaction;
import control.*;

import java.util.List;

public class Output {
    PlayerMessage playergroup[]=new PlayerMessage[4];
    String information;
    //地图变化
    int map_x;
    int map_y;
    int change;
    public void updateOutput(Control control){
      for(int i=0;i<4;i++){
          if(this.playergroup[i]==null) continue;
          this.playergroup[i].updatePlayerMessage(control);
      }
      //information 简单的把信息加到一起，中间用$分割
        information="";
        for(int i=0;i<control.getTextTip().getTipString().size();i++){
            information+=control.getTextTip().getTipString().get(i)+"$";
        }
        //清空TextTip
        control.getTextTip().clearTipstring();
        //更新地图变化
        this.map_x=control.getRunning().getBuilding_x();
        this.map_y=control.getRunning().getBuilding_y();
        this.change=control.getRunning().getChange_type();
    }
    public Output(){
        for(int i=0;i<4;i++){
            playergroup[i].setPlayernum(i+1);
        }
    }
}
