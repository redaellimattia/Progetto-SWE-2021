package it.polimi.ingsw.network.messages.clientMessages.actionMessages;


import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.SocketConnection;

public class PlayLeaderMessage extends ActionMessage {
    private LeaderCard card;

    public PlayLeaderMessage(String nickname, long serverThreadID) {
        super(ActionType.PLAYLEADER, nickname, serverThreadID);
    }

    /**
     * set a specific leader card in game
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        PlayLeaderAction action = new PlayLeaderAction(card);
        useSideActionMessage(action,socketConnection);
    }
}
