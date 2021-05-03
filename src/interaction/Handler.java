package interaction;
import control.*;



public class Handler {
    public static int handle(Control control,Input input,Output output){
         int type=input.getType();
         switch (type){
             case Config.GAME_START:
                 control.start();
                 output.updateOutput(control);
                 return Config.NORMAL_RETURN;
             case Config.GIVE_DICE_NUM:
                 if(control.getRunning().getNowPlayer().getNumber()!=input.getPlayernum()){
                     return Config.ERROR_PLAYER_MISMATCH;
                 }
                 if(control.getRunning().getNowPlayerState()!=GameRunning.STATE_THROWDICE){
                     return Config.ERROR_STATE_MISTAKE;
                 }
                 control.playDice(input.getNum());
                 control.move();
                 //更新output
                 output.updateOutput(control);
                 return Config.NORMAL_RETURN;
             case Config.GIVE_CHOICE:
                 if(control.getRunning().getNowPlayer().getNumber()!=input.getPlayernum()){
                     return Config.ERROR_PLAYER_MISMATCH;
                 }
                 if(control.getRunning().getNowPlayerState()!=GameRunning.STATE_WAIT_CHOICE){
                     return Config.ERROR_STATE_MISTAKE;
                 }
                 control.playerChoose(input.getChoice());
                 output.updateOutput(control);
                 return Config.NORMAL_RETURN;
             case Config.GIVE_CARD_NUM:
                 if(control.getRunning().getNowPlayer().getNumber()!=input.getPlayernum()){
                     return Config.ERROR_PLAYER_MISMATCH;
                 }
                 if(control.getRunning().getNowPlayerState()!=GameRunning.STATE_USE_CARD){
                     return Config.ERROR_STATE_MISTAKE;
                 }
                 control.UseCard(input.num);
                 output.updateOutput(control);
                 return Config.NORMAL_RETURN;
         }
         return Config.NORMAL_RETURN;
    }
}
