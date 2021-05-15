package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {

    private final PrintStream out;

    private final ClientManager clientManager;

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
        Thread inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            try {
                input = futureTask.get();
            }catch (ExecutionException e){out.println(e.getMessage());}
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
            do{
                out.println("There are no available lobbies, press C to create a new game: ");
                input = readLine();
            }while(!input.equalsIgnoreCase("C"));
            createNewGame();
        }
        else{
            out.println("Here are the available Lobbies:");
            for (ReturnLobbiesMessage.availableGameLobbies lobby : availableGameLobbies) {
                out.println("ServerID: " + lobby.getServerThreadID());
                out.println("Number of Players for this game: " + lobby.getNumberOfPlayers());
                out.println("Players:");
                for (String p : lobby.getPlayers()) {
                    out.println(p);
                }
            }
            do{
                out.println("Now choose: \n" +
                        "J: Join and existing match. \n" +
                        "C: Create a new Game.");
                input = readLine();
            }while(!input.equalsIgnoreCase("J") && !input.equalsIgnoreCase("C"));

            if(input.equalsIgnoreCase("C"))
                createNewGame();
            if(input.equalsIgnoreCase("J"))
                joinExistingGame(availableGameLobbies);
        }
    }

    @Override
    public void createNewGame(){
        clearCli();
        out.println("Now you can create your own game!");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        int numberOfPlayers = askNumberOfPlayers();
        clientManager.createGame(numberOfPlayers);
        clearCli();
    }


    @Override
    public void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies){
        out.println("Now you can choose the game to join! ");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        long serverThreadID = askForServerID(availableGameLobbies);

        clientManager.joinGame(serverThreadID);
        clearCli();
    }

    public long askForServerID(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies){
        String chosen;
        String input;
        ArrayList<Long> id = new ArrayList<>();
        long chosenId;

        for (ReturnLobbiesMessage.availableGameLobbies lobby : availableGameLobbies)
            id.add(lobby.getServerThreadID());

        do{
            do {
                out.println("Insert the serverID you want to Join!");
                chosen = readLine();
                chosenId = Long.parseLong(chosen);
            }while(!id.contains(chosenId));
            out.println("The choosen serverID is : " + chosenId + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        }while(!input.equalsIgnoreCase("Y"));

        return chosenId;
    }

    public int askNumberOfPlayers(){
        String number;
        String input;
        int num;
        do{
            do {
                out.println("Insert the number of players for your game (must be between 1 and 4)");
                number = readLine();
                num = Integer.parseInt(number);
            }while(num <1 || num >4);
            out.println("The choosen number of player is : " +number + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        }while(!input.equalsIgnoreCase("Y"));

        return num;
    }

    public String askForNickname(){
        String nickname;
        String input;
        do{
            out.println("Insert a nickname:");
            nickname = readLine();
            out.println("The choosen nickname is : " + nickname +
                    "\nDo you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        }while(!input.equalsIgnoreCase("Y"));

        return nickname;
    }

    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){
        out.println("The game is about to start, choose your initial setup!");
        ArrayList<Resource> resources = new ArrayList<>();
        if(numberOfResources != 0){
            resources = askResources(numberOfResources);
        }
        clearCli();
        ArrayList<LeaderCard> chosenLeaders = askLeaders(leaders);
        clientManager.preGameChoice(resources,chosenLeaders);
    }

    private ArrayList<LeaderCard> askLeaders(ArrayList<LeaderCard> leaders){
        ArrayList<LeaderCard> leadersChosen = new ArrayList<>();
        int counter = 0;
        String chosenID;
        int chosen;
        ArrayList<Integer> id = new ArrayList<>();
        out.println("You can choose two leaders between these four.");
        printLeaders(leaders);
        for (LeaderCard l: leaders)
            id.add(l.getId());
        do{
            out.println("You still have " +counter +" leaders to choose.\n");

            do{
                out.println("Insert the ID of the choosen leader: ");
                chosenID = readLine();
                chosen = Integer.parseInt(chosenID);
            }while(!id.contains(chosen));

            for (LeaderCard leader : leaders)
                if (leader.getId() == chosen)
                    leadersChosen.add(leader);

            counter++;
        }while(counter != 2);
        return leadersChosen;
    }

    private void printLeaders(ArrayList<LeaderCard> leaders){
        for (LeaderCard l: leaders) {
            out.println("ID: " + l.getId() + "\n" +
                    "Victory Points: " + l.getVictoryPoints() +"\n");
            //REQUIREMENT PRINT
            Requirement requirement = l.getRequirement();
            out.println(requirement.toString());
            //SPECIALABILITY PRINT:
            SpecialAbility specialAbility = l.getSpecialAbility();
            out.println(specialAbility.toString());
        }
    }

    private ArrayList<Resource> askResources(int numberOfResources){
        String resource;
        ArrayList<Resource> resources = new ArrayList<>();
        out.println("You have the right to choose " + numberOfResources + "resources to start the game  with!");
        int counter = numberOfResources;
        do{
            out.println("You still have " +counter +" resources to choose.\n");
            do{
                out.println("Choose between 4 types of resources: \n" +
                        "COINS: digit C;" +
                        "ROCKS: digit R;" +
                        "SHIELDS: digit SH;" +
                        "SERVANTS: digit SE;");
                resource = readLine();
            }while(!resource.equalsIgnoreCase("c") && !resource.equalsIgnoreCase("r") && !resource.equalsIgnoreCase("sh") && !resource.equalsIgnoreCase("se"));

            if(resource.equalsIgnoreCase("c"))
                resources.add(Resource.COIN);
            if(resource.equalsIgnoreCase("r"))
                resources.add(Resource.ROCK);
            if(resource.equalsIgnoreCase("sh"))
                resources.add(Resource.SHIELD);
            if(resource.equalsIgnoreCase("se"))
                resources.add(Resource.SERVANT);

            counter--;
        }while(counter != 0);

        return resources;
    }
    @Override
    public void waitingForTurn(){
        String input;
        clearCli();
        out.println("Wait for the other players to play their turn, in the meantime you can peek around the board to keep updated.");
            do{
                out.println("Choose what you want to see:" +
                        "Market: digit M;" +
                        "Shop: digit S;" +
                        "Players: digit P;");
                input = readLine();
            }while(!input.equalsIgnoreCase("m") && !input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("p"));
            if(input.equalsIgnoreCase("m")){
                clearCli();
                printMarket();
                do{
                    out.println("Digit \"esc\" to go back.");
                    input = readLine();
                }while(!input.equalsIgnoreCase("esc"));
                waitingForTurn();
            }
            if(input.equalsIgnoreCase("s")){
                clearCli();
                printShop();
                do{
                    out.println("Digit \"esc\" to go back.");
                    input = readLine();
                }while(!input.equalsIgnoreCase("esc"));
                waitingForTurn();
            }
            if(input.equalsIgnoreCase("p")){
                clearCli();
                ArrayList<String> nicknames = new ArrayList<>();
                for (PlayerDashboard p :clientManager.getGameStatus().getPlayers()) {
                    nicknames.add(p.getNickname());
                }
                do{
                    out.println("Choose which player you want to see: ");
                    for (String s: nicknames) {
                        out.print("|" + s + "|\t");
                    }
                    input = readLine();
                }while(!nicknames.contains(input));
                printPlayer(input);
                do{
                    out.println("Digit \"esc\" to go back.");
                    input = readLine();
                }while(!input.equalsIgnoreCase("esc"));
                waitingForTurn();
            }
    }

    private void printPlayer(String nickname){
        clearCli();
        PlayerDashboard player = clientManager.getGameStatus().getClientDashboard(nickname);
        
    }
    private void printMarket(){
        MarketDashboard market = clientManager.getGameStatus().getMarket();
        MarketMarble[][] grid = market.getStructure();
        for(int i=0; i<3; i++){
            for(int j=0; j<4;j++) {
                switch (grid[i][j].getColour()) {
                    case WHITE:
                        out.print("[W] \t");
                        break;
                    case RED:
                        out.print("[R] \t");
                        break;
                    case YELLOW:
                        out.print("[Y] \t");
                        break;
                    case GREY:
                        out.print("[G] \t");
                        break;
                    case PURPLE:
                        out.print("[P] \t");
                        break;
                    case BLUE:
                        out.print("[B] \t");
                        break;
                }
            }
            out.print("\n");
        }
        out.println("The marble left out: ");
            switch (market.getFreeMarble().getColour()){
                case WHITE:
                    out.println("[W] \t");
                    break;
                case RED:
                    out.println("[R] \t");
                    break;
                case YELLOW:
                    out.println("[Y] \t");
                    break;
                case GREY:
                    out.println("[G] \t");
                    break;
                case PURPLE:
                    out.println("[P] \t");
                    break;
                case BLUE:
                    out.println("[B] \t");
                    break;
            }
        out.println("Legend: W -> White | R -> Red | Y -> Yellow | G -> Gray | P -> Purple | -> B -> Blue ");
    }

    private void printShop(){
        Deck[][] shop = clientManager.getGameStatus().getShop().getGrid();
        ArrayList<Integer> idLine = new ArrayList<>();
        ArrayList<Integer> vPointsLine = new ArrayList<>();
        ArrayList<Integer> levelLine = new ArrayList<>();
        ArrayList<CardColour> colourLine = new ArrayList<>();
        ArrayList<ResourceCount> costLine = new ArrayList<>();
        ArrayList<Production> productionLine = new ArrayList<>();

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                idLine.add(shop[i][j].getFirst().getId());
                vPointsLine.add(shop[i][j].getFirst().getVictoryPoints());
                levelLine.add(shop[i][j].getFirst().getLevel());
                colourLine.add(shop[i][j].getFirst().getColour());
                costLine.add(shop[i][j].getFirst().getCost());
                productionLine.add(shop[i][j].getFirst().getProductionPower());
            }
            for(int j=0; j<4; j++)
                out.println("|ID: "+ idLine.get(j) + " \t|");
            for(int j=0; j<4; j++)
                out.println("|Victory Points: "+ vPointsLine.get(j) + "\t|");
            for(int j=0; j<4; j++)
                out.println("|Level: "+ levelLine.get(j) + "\t|");
            for(int j=0; j<4; j++)
                out.println("|Colour: "+ colourLine.get(j) + "\t|");
            for(int j=0; j<4; j++) {
                out.print("|Cost: ");
                out.print(costLine.get(j).toString());
                out.print("\t|");
                out.print("\n");
            }
            for(int j=0; j<4; j++) {
                out.print("|Production: ");
                out.print("Cost ->");
                out.print(productionLine.get(j).getInput().toString());
                out.print("Outcome -> ");
                out.print(productionLine.get(j).getOutput().toString());
                out.print("\t|");
                out.print("\n");
            }
            out.print("\n\n");
        }
    }

    @Override
    public void yourTurn(){

    }

    @Override
    public void endGame(boolean lorenzoWin, PlayerPoints playerPoints){

    }

    @Override
    public void endGame(ArrayList<PlayerPoints> scoreboard){

    }
    @Override
    public void printMsg(String msg){
        out.println(msg);
    }

    private void clearCli() {
        out.print("\033[H\033[2J");
        out.flush();
    }
}