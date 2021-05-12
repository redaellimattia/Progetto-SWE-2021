package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    private static final int DEFAULT_SOCKET_PORT = 1338;
    private final int socketPort;

    public static final Logger LOGGER = Logger.getLogger("Server");

    public static Map<Long, ServerThread> serverThreads;

    /**
     *
     * @param socketPort default socketPort or chosen when running the Server
     */
    private Server(int socketPort){
        initLogger();

        this.socketPort = socketPort;

        serverThreads = new HashMap<>();
        LOGGER.log(Level.INFO, "Server running.");

        startServer();
    }

    /**
     * Reading parameters
     *
     * @param args args passed when launching the .jar
     */
    public static void main(String[] args){
        int socketPort = DEFAULT_SOCKET_PORT;
        if(args.length > 0 && args.length < 3) { //1 Parameter
            int i=0;
            while (i < args.length) {
                if (args[i].charAt(0) == '-' && args[i].length() == 2) {
                    if (args[i].charAt(1)=='p')
                        socketPort = Integer.parseInt(args[++i]);
                }
                i++;
            }
        }
        new Server(socketPort);
    }

    /**
     * If serverThread = -1 means that the client it's not in a game yet, so it must be a Connection or AskLobby
     * If serverThread != -1 the correct serverThread will be called, and it will handle the request
     *
     * @param sockConnection Client that is sending the message
     * @param msg String msg wrote by the client
     */
    public void onMessage(SocketConnection sockConnection,String msg){
        //if the player sending the message has not the turn active, i don't do it and i send him a message.
        ClientMessage deserializedMessage = ClientMessage.deserializeMessage(msg);
        long serverThreadID = deserializedMessage.getServerThreadID();

        if(serverThreadID!=-1&&serverThreads.containsKey(serverThreadID))
            serverThreads.get(serverThreadID).onMessage(sockConnection, deserializedMessage);
        else
            deserializedMessage.useMessage(sockConnection);
    }

    /**
     * Launch Socket Server Thread
     */
    private void startServer() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startSocketServer();

        LOGGER.info("Socket ServerThread listening on port: "+socketPort);
    }

    /**
     *
     * @param nickname nickname chosen by the player
     * @return true if it's possible to add that player, nickname is unique
     */
    public static synchronized boolean checkNickname(String nickname){
        boolean checkNickname = true;
        for(Long key: serverThreads.keySet())
            checkNickname = serverThreads.get(key).getGameLobby().checkNickname(nickname);
        return checkNickname;
    }

    /**
     * Creating logger file handler
     */
    private void initLogger() {
        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("utilities/server_log/server-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
        } catch (IOException e) { LOGGER.severe(e.getMessage()); }
    }


}
