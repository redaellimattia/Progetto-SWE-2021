package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.messages.ActionMessage;
import it.polimi.ingsw.network.messages.CardShopMessage;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server implements Runnable{
    private final Object clientsLock = new Object();
    private static final int DEFAULT_SOCKET_PORT = 1337;
    private int socketPort;

    private Map<String, SocketConnection> clients;

    private GameLobby gameLobby;

    private Thread serverThread;

    protected static final Logger LOGGER = Logger.getLogger("Server");

    /**
     *
     * @param socketPort port chosen while launching the server
     */
    private Server(int socketPort){
        initLogger();

        this.socketPort = socketPort;
        this.gameLobby = new GameLobby();

        synchronized (clientsLock) {
            this.clients = new HashMap<>();
        }

        startServer();

        LOGGER.log(Level.INFO, "Game lobby created.");

        serverThread = new Thread(this);
        LOGGER.log(Level.INFO, "Server Thread created.");
    }

    //Parameter -p socketPort
    public static void main(String[] args){
        int socketPort = DEFAULT_SOCKET_PORT;
        if(args.length > 0 && args.length < 3) { //1 Parameter
            int i=0;
            while (i < args.length) {
                if (args[i].charAt(0) == '-' && args[i].length() == 2) {
                    switch (args[i].charAt(1)) {
                        case 'p':
                            socketPort = Integer.parseInt(args[++i]);
                            break;
                        default:
                            break;
                    }
                }
                i++;
            }
        }
        new Server(socketPort);
    }

    /**
     * Create LOGGER
     */
    private void initLogger() {
        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("log/server-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
        } catch (IOException e) { LOGGER.severe(e.getMessage()); }
    }

    private void playerLogin(){

    }

    /**
     *
     * @param sockConnection Client that is sending the message
     * @param msg String msg wrote by the client
     */
    public void onMessage(SocketConnection sockConnection,String msg){
        switch(Message.onMessage(msg).getClass().getSimpleName()){
            case "ConnectionMessage": playerLogin();
                break;
            case "DisconnectionMessage": //resilienzaDisconnessioni
                break;
            case "ActionMessage":   //createAction()
                break;
            default: break;
        }
    }

    /**
     *
     * @param sockConnection Client that is disconnecting
     */
    public void onDisconnect(SocketConnection sockConnection){
        //Gestito per resilienza
    }

    /**
     * Launch Socket Server
     */
    private void startServer() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startSocketServer();

        LOGGER.info("Socket Server listening on port: "+socketPort);
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (clientsLock) {
                for(Map.Entry<String, SocketConnection> c: clients.entrySet())
                    if (c.getValue() == null || !c.getValue().isConnected()){
                        //Resilienza Disconnessioni
                    }
            }
        }
    }

}
