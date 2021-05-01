package it.polimi.ingsw.network.server;

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

public class Server {
    private static final int DEFAULT_SOCKET_PORT = 1337;
    private final int socketPort;

    protected static final Logger LOGGER = Logger.getLogger("ServerThread");

    public static Map<Long, ServerThread> serverThreads;

    private Server(int socketPort){
        initLogger();

        this.socketPort = socketPort;

        this.serverThreads = new HashMap<>();
        LOGGER.log(Level.INFO, "Server running.");

        startServer();
        ServerThread serverThread = new ServerThread();
        serverThreads.put(serverThread.getThreadId(),serverThread);
        LOGGER.log(Level.INFO, "ServerThread created, waiting for clients.");
    }
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
     *
     * @param sockConnection Client that is sending the message
     * @param msg String msg wrote by the client
     */
    public void onMessage(SocketConnection sockConnection,String msg){
        Message deserializedMessage = Message.onMessage(msg);
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
