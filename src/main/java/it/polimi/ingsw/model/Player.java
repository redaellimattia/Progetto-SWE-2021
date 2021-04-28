package it.polimi.ingsw.model;

public abstract class Player implements Comparable<Player>{
    private int position;
    private String nickname;
    private int points;
    private boolean isLorenzo;

    public Player(int position, String nickname, int points, boolean isLorenzo) {
        this.position = position;
        this.nickname = nickname;
        this.points = points;
        this.isLorenzo = isLorenzo;
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

    public void addPoints(int number){ points += number;}

    @Override
    public int compareTo(Player p){
        if (this.getPoints() < p.getPoints()) return -1;
        else if (this.getPoints() > p.getPoints()) return 1;
        return 0;
    }

}
