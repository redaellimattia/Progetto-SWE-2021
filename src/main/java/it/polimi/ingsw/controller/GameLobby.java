package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class GameLobby {


    private ArrayList<String> players;
    private ArrayList<GameManager> games;

    public void addPlayer(String nickname) {
        this.players.add(nickname);
    }
    /**
     *
     * @param chosenNickname chosenNickname by the view while trying to access the game
     * @return true if the nickname isn't used yet
     */
    public boolean checkNickname(String chosenNickname) {
        for (String nickname: players) {
            if(chosenNickname.equals(nickname))
                return false;
        }
        return true;
    }

    public void createGameManager() {
        //games.add(new GameManager(new Game(players,...),null));
    }
}
