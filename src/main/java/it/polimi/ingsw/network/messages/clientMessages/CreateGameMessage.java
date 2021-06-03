package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;


public class CreateGameMessage extends ClientMessage {
    private final int numberOfPlayers;

    public CreateGameMessage(String nickname, long serverThreadID, int numberOfPlayers) {
        super(ClientMessageType.CREATEGAME, nickname, serverThreadID);
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Create a new ServerLobby upon player's request
     * @param socketConnection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        if(Server.checkNickname(getNickname())){
            ServerLobby serverLobby = new ServerLobby(numberOfPlayers,++Server.newServerID);
            serverLobby.newPlayerLogin(getNickname(), socketConnection);
            Server.lobbies.put(serverLobby.getLobbyId(), serverLobby);
        }
        else {
            if (getNickname().equals("Lorenzo il Magnifico"))
                socketConnection.send(Server.createReturnLobbiesMessage("This username: [" + getNickname() + "] is already taken in SinglePlayer!!").serialize());
            else
                socketConnection.send(Server.createReturnLobbiesMessage("This username: [" + getNickname() + "] is already taken!").serialize());

        }
    }
}
