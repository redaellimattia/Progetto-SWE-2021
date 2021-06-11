package it.polimi.ingsw.model;

public abstract class Player implements Comparable<Player>{
    private final String nickname;
    private int points;
    private final boolean isLorenzo;
    private boolean isPlaying;
    private transient String mainActionError;
    private transient String sideActionError;

    public Player(String nickname, int points, boolean isLorenzo) {
        this.nickname = nickname;
        this.points = points;
        this.isLorenzo = isLorenzo;
        this.isPlaying = true;
    }

    public String getMainActionError() {
        return mainActionError;
    }

    public String getSideActionError() {
        return sideActionError;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMainActionError(String mainActionError) {
        this.mainActionError = mainActionError;
    }

    public void setSideActionError(String sideActionError) {
        this.sideActionError = sideActionError;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isLorenzo() {
        return isLorenzo;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int number){ points += number;}

    /**
     * Compare 2 players points
     * @param p passed player
     * @return -1 if this has more points, 1 if p has more points, otherwise 0
     */
    @Override
    public int compareTo(Player p){
        if (this.getPoints() > p.getPoints()) return -1;
        else if (this.getPoints() < p.getPoints()) return 1;
        return 0;
    }
}
