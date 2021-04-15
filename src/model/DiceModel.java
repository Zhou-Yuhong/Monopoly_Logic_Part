package model;
import control.Control;
import control.GameRunning;
public class DiceModel implements GamePort{
    //骰子点数
    private int point;
    /**
     *
     * 游戏运行
     *
     */
    private GameRunning running = null;
    /**
     * 骰子当前状态
     */
    private int diceState;
    public DiceModel(GameRunning running) {
        this.running = running;
    }
    public void setDiceState(int diceState) {
        this.diceState = diceState;
    }

    public int getDiceState() {
        return diceState;
    }
    public GameRunning getRunning() {
        return running;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    @Override
    public void startGameInit() {
    }
}
