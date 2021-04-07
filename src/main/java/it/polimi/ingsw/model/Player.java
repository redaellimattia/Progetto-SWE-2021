package it.polimi.ingsw.model;

public abstract class Player {
    private int position;
    private String nickname;
    private int points;

    public Player(int position, String nickname, int points) {
        this.position = position;
        this.nickname = nickname;
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public String getNickName() {
        return nickname;
    }

    public int getPoints() {
        return points;
    }
}
