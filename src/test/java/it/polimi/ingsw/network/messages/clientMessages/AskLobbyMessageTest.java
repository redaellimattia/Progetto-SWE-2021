package it.polimi.ingsw.network.messages.clientMessages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AskLobbyMessageTest {

    @Test
    void test(){
        String msg = "{\n" +
                "  \"type\": \"ASKLOBBIES\",\n" +
                "  \"nickname\": \"nickname\",\n" +
                "  \"serverThreadID\": -1\n" +
                "}";
        ClientMessage deserializedMessage = ClientMessage.onMessage(msg);
        System.out.println(deserializedMessage.serialize());
    }

}