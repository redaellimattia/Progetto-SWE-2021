package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.ConvertWhiteMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class ConvertMarbleMessage extends MarketActionMessage {
    private final int leaderCardId;
    private final int storageRow;

    public ConvertMarbleMessage(String nickname, long serverThreadID, int leaderCardId, int storageRow) {
        super(nickname, serverThreadID, MarketActionType.CONVERTMARBLE);
        this.leaderCardId = leaderCardId;
        this.storageRow = storageRow;
    }

    /**
     * Creates a ConvertWhiteMarble action and adds it to the MarketChoice buffer.
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        LeaderCard leaderCard;
        leaderCard = null; // TO-DO: check behaviour with malicious client
        for(PlayerDashboard p: serverLobby.getGameLobby().getGameManager().getGame().getPlayers()) {
            if(p.getNickname().equals(getNickname())) {
                for(LeaderCard c: p.getLeaderCards()) {
                    if(c.getId() == leaderCardId) {
                        leaderCard = c;
                    }
                }
            }
        }
        ConvertWhiteMarble action = new ConvertWhiteMarble(leaderCard, storageRow);
        serverLobby.getTurnManager().addMarketChoice(action);
    }
}
