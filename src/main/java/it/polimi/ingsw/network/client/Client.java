package it.polimi.ingsw.network.client;

public class Client {
    private static final int DEFAULT_SOCKET_PORT = 65500;
    private static final String DEFAULT_SOCKET_IP = "127.0.0.1";
    private static final String DEFAULT_CHOICE = "-cli";
    /**
     * IP - PORT - CLI/GUI
     * ex: -ip 127.0.0.1 -p 1337 -gui
     * @param args args passed when launching the .jar
     */
    public static void main(String[] args){
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
        new ClientManager(address,socketPort,choice);
    }

}
