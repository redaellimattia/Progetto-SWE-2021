package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class PreGameMessage extends ServerMessage{
    ArrayList<LeaderCard> leaders;
    int numberOfResources;

    public PreGameMessage(ArrayList<LeaderCard> leaders,int numberOfResources) {
        super(ServerMessageType.PREGAME);
        this.leaders = leaders;
        this.numberOfResources = numberOfResources;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().preGameChoice(this.leaders,this.numberOfResources);
    }
}
