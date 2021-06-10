package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class ExceptionMessage extends ServerMessage {
    private final String msg;

    public ExceptionMessage(String msg) {
        super(ServerMessageType.EXCEPTION);
        this.msg = msg;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().printMsg(msg);
        if(clientManager.isProductionActionOnGoing()) {
            if(clientManager.getNumProd()==1)
                clientManager.setMainActionDone(false);
            switch (clientManager.getLastProduction()){
                case 1: clientManager.setBasicProductionDone(false);
                        break;
                case 2: clientManager.removeIndexLeaderCardProductionDone();
                        break;
                case 3: clientManager.removeIndexDevCardProductionDone();
                        break;
            }
            clientManager.getView().startProduction();
        }
        else {
            clientManager.setMainActionDone(false);
            clientManager.getView().yourTurn();
        }
    }
}
