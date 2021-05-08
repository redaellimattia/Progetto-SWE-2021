package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {

    private final PrintStream out;
    private Thread inputThread;
    private ClientManager clientManager;

    public Cli(ClientManager clientManager){
        this.out = new PrintStream(System.out,true);
        this.clientManager = clientManager;
    }
    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     */
    public String readLine(){
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            try {
                input = futureTask.get();
            }catch (ExecutionException e){}
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }
    @Override
    public void start(){
        printLogo();
    }

    public void printLogo(){
        String logo = " __    __     ______     ______     ______   ______     ______     ______        ______     ______                    \n" +
                "/\\ \"-./  \\   /\\  __ \\   /\\  ___\\   /\\__  _\\ /\\  ___\\   /\\  == \\   /\\  ___\\      /\\  __ \\   /\\  ___\\                   \n" +
                "\\ \\ \\-./\\ \\  \\ \\  __ \\  \\ \\___  \\  \\/_/\\ \\/ \\ \\  __\\   \\ \\  __<   \\ \\___  \\     \\ \\ \\/\\ \\  \\ \\  __\\                   \n" +
                " \\ \\_\\ \\ \\_\\  \\ \\_\\ \\_\\  \\/\\_____\\    \\ \\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\/\\_____\\     \\ \\_____\\  \\ \\_\\                     \n" +
                "  \\/_/  \\/_/   \\/_/\\/_/   \\/_____/     \\/_/   \\/_____/   \\/_/ /_/   \\/_____/      \\/_____/   \\/_/                     \n" +
                "                                                                                                                      \n" +
                " ______     ______     __   __     ______     __     ______     ______     ______     __   __     ______     ______   \n" +
                "/\\  == \\   /\\  ___\\   /\\ \"-.\\ \\   /\\  __ \\   /\\ \\   /\\  ___\\   /\\  ___\\   /\\  __ \\   /\\ \"-.\\ \\   /\\  ___\\   /\\  ___\\  \n" +
                "\\ \\  __<   \\ \\  __\\   \\ \\ \\-.  \\  \\ \\  __ \\  \\ \\ \\  \\ \\___  \\  \\ \\___  \\  \\ \\  __ \\  \\ \\ \\-.  \\  \\ \\ \\____  \\ \\  __\\  \n" +
                " \\ \\_\\ \\_\\  \\ \\_____\\  \\ \\_\\\\\"\\_\\  \\ \\_\\ \\_\\  \\ \\_\\  \\/\\_____\\  \\/\\_____\\  \\ \\_\\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_____\\  \\ \\_____\\\n" +
                "  \\/_/ /_/   \\/_____/   \\/_/ \\/_/   \\/_/\\/_/   \\/_/   \\/_____/   \\/_____/   \\/_/\\/_/   \\/_/ \\/_/   \\/_____/   \\/_____/ \n"+
                "Welcome to Masters Of Renaissance Board Game Digital Version (patent pending) created by Mattia Redaelli, Luca Rondini, Gabriele Rivi. \n"+
                "Have Fun playing the game! \n";
        out.println(logo);
    }
    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies){
        String input;
        if(availableGameLobbies.size()==0){
            out.println("There are no available lobbies, press C to create a new game: ");
            do{
                 input = readLine();
            }while(!input.equalsIgnoreCase("C"));
            createNewGame();
        }
        else{
            out.println("Here are the available Lobbies: \n");
            for (ReturnLobbiesMessage.availableGameLobbies lobby : availableGameLobbies) {
                out.println("ServerID: " + lobby.getServerThreadID() + "\n");
                out.println("Number of Players for this game: " + lobby.getNumberOfPlayers() + "\n");
                out.println("Players: \n");
                for (String p : lobby.getPlayers()) {
                    out.println(p + "\n ");
                }
            }
            out.println("Now choose: \n" +
                    "J: Join and existing match. \n" +
                    "C: Create a new Game.");

        }
        do{
            input = readLine();
        }while(!input.equalsIgnoreCase("J") || !input.equalsIgnoreCase("C"));

        if(input.equalsIgnoreCase("C"))
            createNewGame();
        if(input.equalsIgnoreCase("J"))
            joinExistingGame();
    }

    @Override
    public void createNewGame(){
        clearCli();

        out.println("Now you can create your own game! \n ");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        //int numberOfPlayers = askNumberOfPlayers();
        //clientManager.createGame();
    }


    @Override
    public void joinExistingGame(){}

    /*public int askNumberOfPlayers(){
        String number;
        String input;
        do{
            out.println("Insert the number of players for your game (must be between 1 and 4) \n");
            number = readLine();
            out.println("The choosen number of player is : " +number + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny) \n");
            input = readLine();
        }while(!input.equalsIgnoreCase("Y"));

        return nickname;
    }*/
    public String askForNickname(){
        String nickname;
        String input;
        do{
            out.println("Insert a nickname: \n");
            nickname = readLine();
            out.println("The choosen nickname is : " + nickname + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny) \n");
            input = readLine();
        }while(!input.equalsIgnoreCase("Y"));

        return nickname;
    }
    private void clearCli(){
        out.println("\033[H\033[2J");
        out.flush();
    }

    @Override
    public void printError(String msg){
        out.println(msg);
    }
}