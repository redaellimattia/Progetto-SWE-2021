package it.polimi.ingsw.model;

public abstract class Player {
    private int position;
    private String nickName;
    private int points;

    public int getPosition() {
        return position;
    }

    public String getNickName() {
        return nickName;
    }

    public int getPoints() {
        return points;
    }
}
