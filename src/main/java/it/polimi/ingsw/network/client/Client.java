package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.Server;

public class Client {
    private static final int DEFAULT_SOCKET_PORT = 1338;
    private static final String DEFAULT_SOCKET_IP = "127.0.0.1";
    private static final String DEFAULT_CHOICE = "cli";


    private String nickname;
    private ClientSocket clientSocket;

    private Client(String address, int socketPort, String choice) {
        //qui verrà avviata la view e questa prenderà tutte le informazioni dal client
        //logger
        //questo sarà nella view
        connection("nickname" /* verrà passato da view*/, address,socketPort);
    }
    public void connection(String nickname,String address, int socketPort ){
        clientSocket = new ClientSocket(nickname, address, socketPort);
        clientSocket.startConnection();
    }


    // IP - PORT - CLI/GUI
    // -ip 127.0.0.1 -p 1337 -gui
    public static void main(String args[]){
        String address = DEFAULT_SOCKET_IP;
        int socketPort = DEFAULT_SOCKET_PORT;
        String choice = DEFAULT_CHOICE;

        if(args.length > 0 && args.length < 6) { //3 parameter
            int i=0;
            while (i < args.length) {
                if (args[i].equals("-ip"))
                    address = args[++i];
                if(args[i].equals("-p"))
                    socketPort= Integer.parseInt(args[++i]);
                if(args[i].equals("-gui"))
                    choice = args[i];
                i++;
            }
        }
        new Client(address,socketPort,choice);
    }

}
