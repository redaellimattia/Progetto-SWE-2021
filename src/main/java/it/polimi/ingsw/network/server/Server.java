package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    private static final int DEFAULT_SOCKET_PORT = 65500;
    private final int socketPort;
    public static long newServerID;
    public static final Logger LOGGER = Logger.getLogger("Server");

    public static Map<Long, ServerLobby> lobbies;

    /**
     *
     * @param socketPort default socketPort or chosen when running the Server
     */
    private Server(int socketPort){
        initLogger();
        newServerID = 0;
        this.socketPort = socketPort;

        lobbies = new HashMap<>();
        LOGGER.log(Level.INFO, "Server running.");

        startServer();
    }

    /**
     * Reading parameters
     * ex: -p 38 [port 38]
     * @param args args passed when launching the .jar
     */
    public static void main(String[] args){
        int socketPort = DEFAULT_SOCKET_PORT;
        if(args.length > 0 && args.length < 3) { //1 Parameter
            int i=0;
            while (i < args.length) {
                if(args[i].equals("-p"))
                    socketPort= Integer.parseInt(args[++i]);
                i++;
            }
        }
        new Server(socketPort);
    }

    /**
     * If serverLobby = -1 means that the client it's not in a game yet, so it must be a Connection or AskLobby
     * If serverLobby != -1 the correct serverLobby will be called, and it will handle the request
     *
     * @param sockConnection Client that is sending the message
     * @param msg String msg wrote by the client
     */
    public void onMessage(SocketConnection sockConnection,String msg){
        //if the player sending the message has not the turn active, i don't do it and i send him a message.
        ClientMessage deserializedMessage = ClientMessage.deserializeMessage(msg);
        long serverThreadID = deserializedMessage.getServerThreadID();

        if(serverThreadID!=-1&& lobbies.containsKey(serverThreadID))
            lobbies.get(serverThreadID).onMessage(sockConnection, deserializedMessage);
        else
            deserializedMessage.useMessage(sockConnection);
    }

    /**
     * Launch Socket Server Thread
     */
    private void startServer() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startSocketServer();

        LOGGER.info("Socket ServerLobby listening on port: "+socketPort);
    }

    /**
     *
     * @param nickname nickname chosen by the player
     * @return true if it's possible to add that player, nickname is unique
     */
    public static synchronized boolean checkNickname(String nickname){
        boolean checkNickname = true;
        if(nickname.equals("Lorenzo il Magnifico"))
            return false;
        for(Long key: lobbies.keySet())
            checkNickname = lobbies.get(key).getGameLobby().checkNickname(nickname);
        return checkNickname;
    }

    /**
     *
     * @param socketConnection passed client connection
     * @return ServerLobby of the passed socketConnection
     */
    public static synchronized ServerLobby getServerThread(SocketConnection socketConnection){
        for(Long key: lobbies.keySet()) {
            ServerLobby serverLobby = lobbies.get(key);
            for (String nickname : serverLobby.getClients().keySet())
                if(serverLobby.getClients().get(nickname).equals(socketConnection))
                    return serverLobby;
        }
        return null;
    }

    /**
     * Creates a ReturnLobbiesMessage
     * @return a ReturnLobbiesMessage
     */
    public static synchronized ReturnLobbiesMessage createReturnLobbiesMessage(String message){
        if(lobbies.size()!=0) {
            ArrayList<GameLobby> gameLobbies = new ArrayList<>();
            for (Long key : lobbies.keySet())
                gameLobbies.add(lobbies.get(key).getGameLobby());
            return new ReturnLobbiesMessage(gameLobbies,message);
        }
        else
            return new ReturnLobbiesMessage(new ArrayList<>(),message);
    }

    /**
     * Removes the lobby from the HashMap of lobbies
     * @param serverLobbyID ID of the lobby that is closing
     */
    public static synchronized void closeLobby(long serverLobbyID){
        lobbies.remove(serverLobbyID);
        LOGGER.info("Lobby: "+serverLobbyID+" CLOSED!");
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
