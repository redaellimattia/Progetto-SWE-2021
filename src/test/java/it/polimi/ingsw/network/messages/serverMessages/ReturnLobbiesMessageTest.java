package it.polimi.ingsw.network.messages.serverMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.controller.GameLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReturnLobbiesMessageTest {

    @Test
    void constructorTest(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        GameLobby g1 = new GameLobby(1,4);
        GameLobby g2 = new GameLobby(2,2);
        GameLobby g3 = new GameLobby(3,3);
        GameLobby g4 = new GameLobby(4,4);

        ArrayList<GameLobby> gameLobbies = new ArrayList<>();
        gameLobbies.add(g1);
        gameLobbies.add(g2);
        gameLobbies.add(g3);
        gameLobbies.add(g4);

        g1.addPlayer("Gianni");
        g1.addPlayer("Mario");

        g2.addPlayer("Romolo");

        g3.addPlayer("Remo");

        g4.addPlayer("Mattia");

        ReturnLobbiesMessage returnLobbiesMessage = new ReturnLobbiesMessage(gameLobbies,null);
        System.out.println(returnLobbiesMessage.serialize());
    }

}