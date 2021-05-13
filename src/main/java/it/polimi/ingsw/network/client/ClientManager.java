package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.messages.clientMessages.CreateGameMessage;
import it.polimi.ingsw.network.messages.clientMessages.JoinGameMessage;
import it.polimi.ingsw.network.messages.clientMessages.PreGameResponseMessage;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.view.Cli;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientManager {
    private static final int DEFAULT_SOCKET_PORT = 1338;
    private static final String DEFAULT_SOCKET_IP = "127.0.0.1";
    private static final String DEFAULT_CHOICE = "cli";
    protected static final Logger LOGGER = Logger.getLogger("Client");

    private String nickname;
    private ClientSocket clientSocket;
    private long serverThreadID = -1;
    private View view;
    private ClientGameStatus gameStatus;

    /**
     * Creates client Object, handles client connection and instantiates view
     * @param address address chosen
     * @param socketPort port chosen
     */
    public ClientManager(String address, int socketPort,String choice) {
        initLogger();
        this.nickname = "defaultNickname";
        if(choice.equals("-cli"))
            this.view = new Cli(this);
        //else
            //this.view = new Gui();
        view.start();
        connection(address,socketPort);
    }
    public ClientGameStatus getGameStatus() { return gameStatus;}
    public String getNickname() {
        return nickname;
    }
    public long getServerThreadID() {
        return serverThreadID;
    }
    public View getView(){ return view;}
    public ClientSocket getClientSocket(){return clientSocket;}

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setServerThreadID(long serverThreadID) {
        this.serverThreadID = serverThreadID;

    }


    /**
     * Connection to the server
     * @param address address chosen
     * @param socketPort port chosen
     */
    public void connection(String address, int socketPort ){
        clientSocket = new ClientSocket(address, socketPort,this);
        clientSocket.startConnection();
        if(!clientSocket.isConnected())
            view.printMsg("Failed to connect to: "+ address+":" + socketPort);
    }

    /**
     * Handle a new message arrived from the server
     * @param msg serialized Json message
     */
    public void onMessage(String msg){
        ServerMessage deserializedMessage = ServerMessage.deserializeMessage(msg);
        deserializedMessage.useMessage(this);
    }

    /**
     *
     * @param numberOfPlayers for the game the player wants to create
     */
    public void createGame(int numberOfPlayers){
        String message = new CreateGameMessage(this.nickname,-1,numberOfPlayers).serialize();
        clientSocket.send(message);
    }

    /**
     * Creates and sends a JoinGameMessage
     *
     * @param serverThreadID chosen serverID for the game to join
     */
    public void joinGame(long serverThreadID){
        String message = new JoinGameMessage(this.nickname,serverThreadID).serialize();
        clientSocket.send(message);
    }

    public void preGameChoice(ArrayList<Resource> resources, ArrayList<LeaderCard> leaders){
        String message= new PreGameResponseMessage(this.nickname,serverThreadID,resources,leaders).serialize();
        clientSocket.send(message);
    }

    /**
     * Creating logger file handler
     */
    private void initLogger() {
        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("utilities/client_log/client-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
        } catch (IOException e) { LOGGER.severe(e.getMessage()); }
    }
}