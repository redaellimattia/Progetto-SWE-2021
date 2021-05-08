package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.view.View;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    /**
     * Creates client Object, handles client connection and instantiates view
     * @param address address chosen
     * @param socketPort port chosen
     */
    public ClientManager(String address, int socketPort, View view) {
        //qui verrà avviata la view e questa prenderà tutte le informazioni dal client
        initLogger();
        this.view = view;
        view.start();
        this.nickname = "nickname"; //------------DEBUG------------------
        connection(address,socketPort);
    }

    public String getNickname() {
        return nickname;
    }
    public long getServerThreadID() {
        return serverThreadID;
    }
    public View getView(){ return view;}
    public ClientSocket getClientSocket(){return clientSocket;}

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
            view.printError("Failed to connect to: "+ address+":" + socketPort);
    }

    /**
     * Handle a new message arrived from the server
     * @param msg serialized Json message
     */
    public void onMessage(String msg){
        ServerMessage deserializedMessage = ServerMessage.onMessage(msg);
        deserializedMessage.useMessage(this);
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