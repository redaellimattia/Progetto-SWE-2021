package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class LeaderUpdateMessage extends PlayerUpdateMessage{
    private int position;

    public LeaderUpdateMessage(PlayerUpdateType type,String nickname, int position) {
        super(type, nickname);
        this.position = position;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        switch(getPlayerUpdateType()){
            case INGAMELEADER:  clientManager.getGameStatus().updateLeaderInGame(getNickname(),position);
                                break;
            case DISCARDLEADER: clientManager.getGameStatus().updateDiscardLeader(getNickname(),position);
                                break;
        }
        clientManager.getView().yourTurn();
    }
}
