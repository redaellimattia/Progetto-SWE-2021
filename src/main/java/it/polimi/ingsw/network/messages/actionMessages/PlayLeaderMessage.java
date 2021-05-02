package it.polimi.ingsw.network.messages.actionMessages;


import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.server.SocketConnection;

public class PlayLeaderMessage extends ActionMessage {
    private LeaderCard card;
    /*public PlayLeaderMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/
    @Override
    public void useMessage(SocketConnection socketConnection){
        PlayLeaderAction action = new PlayLeaderAction(card);
        useActionMessage(action,socketConnection);
    }
}
