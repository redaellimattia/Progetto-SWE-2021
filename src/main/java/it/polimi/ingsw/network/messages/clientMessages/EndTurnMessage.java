package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class EndTurnMessage extends ClientMessage{
    public EndTurnMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.ENDTURN, nickname, serverThreadID);
    }

    /**
     * A player has passed the turn, call the serverLobby method to end the round
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        Server.LOGGER.log(Level.INFO,"LobbyID: "+serverLobby.getLobbyId()+" "+getNickname()+" ended his round!");
        serverLobby.endRound();
    }
}
