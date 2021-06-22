package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class ExceptionMessage extends ServerMessage {
    private final String msg;

    public ExceptionMessage(String msg) {
        super(ServerMessageType.EXCEPTION);
        this.msg = msg;
    }

    /**
     * Print the message, if the production is still going and only 1 action has been rejected, then set the mainActionDone to false.
     * If it's a production set the lastDoneProduction to not done and put the view in startProduction mode.
     * Otherwise (If not a production) set the mainAction done to false and put the player in yourturn mode.
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().printMsg(msg);
        if(clientManager.isProductionActionOnGoing()) {
            if(clientManager.getNumOfProd()==1)
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
