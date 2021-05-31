package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class PrintMessage extends ServerMessage{
    private final String msg;
    public PrintMessage(String msg){
        super(ServerMessageType.PRINTMESSAGE);
        this.msg = msg;
    }

    /**
     * Prints a message
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager) {
        clientManager.setMessage(this.msg);
        //clientManager.getView().printMsg(this.msg);
    }
}
