package it.polimi.ingsw.network.messages.clientMessages;


import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class PreGameResponseMessage extends ClientMessage{
    ArrayList<Resource> chosen;
    ArrayList<LeaderCard> chosenLeaders;
    public PreGameResponseMessage(String nickname, long serverThreadID, ArrayList<Resource> chosen,ArrayList<LeaderCard> chosenLeaders) {
        super(ClientMessageType.PREGAMERESPONSE, nickname, serverThreadID);
        this.chosen = chosen;
        this.chosenLeaders = chosenLeaders;
    }

    @Override
    public void useMessage(SocketConnection socketConnection, ServerThread serverThread){
        serverThread.getGameLobby().preGame(this.getNickname(),chosen,chosenLeaders);
    }
}
