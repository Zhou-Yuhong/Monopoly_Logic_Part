package main;
import control.Control;
import interaction.Handler;
import interaction.Input;
import interaction.Output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args){
        int type;
        int playernum;
        boolean choice;
        int num;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Control gamecontrol=new Control();
        Input input=new Input();
        Output output=new Output();
        Handler handler=new Handler();
        while(true){
            try{
                type= Integer.parseInt(reader.readLine());
                playernum=Integer.parseInt(reader.readLine());
                int tmp=Integer.parseInt(reader.readLine());
                if(tmp==1){
                    choice=true;
                }
                else{
                    choice=false;
                }
                num=Integer.parseInt(reader.readLine());
                input.setInput(type,playernum,choice,num);
                Handler.handle(gamecontrol,input,output);
                int jjj=0;
            }
            catch (IOException e){
                System.out.print(e);
            }
        }
        //gamecontrol.start();
        //gamecontrol.rungame();
    }
}
