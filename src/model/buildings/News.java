package model.buildings;

import context.GameState;

public class News extends Building{
    public News(int posX, int posY) {
        super(posX, posY);
        this.name = "新闻";
    }
    @Override
    public int getEvent() {
        return GameState.NEWS_EVENT;
    }
}
