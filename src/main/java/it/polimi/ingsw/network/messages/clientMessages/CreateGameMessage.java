package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;


public class CreateGameMessage extends ClientMessage {
    private int numberOfPlayers;

    public CreateGameMessage(String nickname, long serverThreadID, int numberOfPlayers) {
        super(ClientMessageType.CREATEGAME, nickname, serverThreadID);
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     *
     * @param socketConnection socketConnection of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        if(Server.checkNickname(getNickname())) {
            ServerThread serverThread = new ServerThread(numberOfPlayers);
            serverThread.newPlayerLogin(getNickname(), socketConnection);
            Server.serverThreads.put(serverThread.getThreadId(), serverThread);
        }
        else
            socketConnection.send(new PrintMessage("This username: [" + getNickname() +"] is already taken!").serialize());
    }
}
