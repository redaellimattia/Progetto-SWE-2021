package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.exceptions.network.NicknameAlreadyUsedException;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;


public class JoinGameMessage extends ClientMessage {

    public JoinGameMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.JOINGAME, nickname, serverThreadID);
    }

    /**
     *
     * @param socketConnection socketConnection of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection,ServerThread serverThread){
        if(Server.checkNickname(getNickname()))
            serverThread.playerLogin(getNickname(),socketConnection);
        else{
            socketConnection.send(new PrintMessage("This username: [" + getNickname() + "] is already taken!").serialize());
            socketConnection.send(createReturnLobbiesMessage().serialize());
            //throw new NicknameAlreadyUsedException(getNickname());
        }
    }
}
