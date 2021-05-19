package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;


public class JoinGameMessage extends ClientMessage {

    public JoinGameMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.JOINGAME, nickname, serverThreadID);
    }

    /**
     *
     * @param socketConnection socketConnection of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        if(Server.checkNickname(getNickname())||serverLobby.getGameLobby().getPlayers().contains(getNickname()))
            serverLobby.playerLogin(getNickname(), socketConnection);
        else{
            socketConnection.send(new PrintMessage("This username: [" + getNickname() + "] is already taken!").serialize());
            socketConnection.send(Server.createReturnLobbiesMessage().serialize());
            //throw new NicknameAlreadyUsedException(getNickname());
        }
    }
}
