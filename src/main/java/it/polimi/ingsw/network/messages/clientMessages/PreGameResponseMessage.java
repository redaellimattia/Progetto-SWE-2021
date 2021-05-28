package it.polimi.ingsw.network.messages.clientMessages;


import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;
import java.util.logging.Level;

public class PreGameResponseMessage extends ClientMessage{
    private final ArrayList<Resource> chosen;
    private final ArrayList<LeaderCard> chosenLeaders;

    public PreGameResponseMessage(String nickname, long serverThreadID, ArrayList<Resource> chosen,ArrayList<LeaderCard> chosenLeaders) {
        super(ClientMessageType.PREGAMERESPONSE, nickname, serverThreadID);
        this.chosen = chosen;
        this.chosenLeaders = chosenLeaders;
    }

    /**
     * Proceeds to save the initial configuration chosen by the player
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        serverLobby.getGameLobby().preGame(this.getNickname(),chosen,chosenLeaders);
        serverLobby.getGameLobby().addReadyPlayer();
        Server.LOGGER.log( Level.INFO,"Player: "+ getNickname() + " is now ready to play, he finished PreGame.");
        serverLobby.deletePreGameTimer(getNickname());
        //Don't send if this is the last player to answer.
        if(!serverLobby.getGameLobby().readyToStartGame())
            socketConnection.send(new PrintMessage("Waiting other player's choices...").serialize());
    }
}
