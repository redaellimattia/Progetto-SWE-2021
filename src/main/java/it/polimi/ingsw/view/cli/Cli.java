package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.controller.action.marketAction.ConvertWhiteMarble;
import it.polimi.ingsw.controller.action.marketAction.DiscardResource;
import it.polimi.ingsw.controller.action.marketAction.GetResource;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Cli implements View {
    private static final String RESET = CliColours.RESET.toString();
    private static final String BLACK = CliColours.BLACK.toString();
    private static final String RED = CliColours.RED.toString();
    private static final String GREEN = CliColours.GREEN.toString();
    private static final String YELLOW = CliColours.YELLOW.toString();
    private static final String BLUE = CliColours.BLUE.toString();
    private static final String PURPLE = CliColours.PURPLE.toString();
    private static final String CYAN = CliColours.CYAN.toString();
    private static final String WHITE = CliColours.WHITE.toString();
    private static final String BLACK_BOLD = CliColours.BLACK_BOLD.toString();
    private static final String RED_BOLD = CliColours.RED_BOLD.toString();
    private static final String GREEN_BOLD = CliColours.GREEN_BOLD.toString();
    private static final String YELLOW_BOLD = CliColours.YELLOW_BOLD.toString();
    private static final String BLUE_BOLD = CliColours.BLUE_BOLD.toString();
    private static final String PURPLE_BOLD = CliColours.PURPLE_BOLD.toString();
    private static final String CYAN_BOLD = CliColours.CYAN_BOLD.toString();
    private static final String WHITE_BOLD = CliColours.WHITE_BOLD.toString();
    private static final String BLACK_UNDERLINE = CliColours.BLACK_UNDERLINED.toString();
    private static final String RED_UNDERLINED = CliColours.RED_UNDERLINED.toString();
    private static final String GREEN_UNDERLINED = CliColours.GREEN_UNDERLINED.toString();
    private static final String YELLOW_UNDERLINED = CliColours.YELLOW_UNDERLINED.toString();
    private static final String BLUE_UNDERLINED = CliColours.BLUE_UNDERLINED.toString();
    private static final String PURPLE_UNDERLINED = CliColours.PURPLE_UNDERLINED.toString();
    private static final String CYAN_UNDERLINED = CliColours.CYAN_UNDERLINED.toString();
    private static final String WHITE_UNDERLINED = CliColours.WHITE_UNDERLINED.toString();

    private final String lostLogo =     "██╗   ██╗ ██████╗ ██╗   ██╗    ██╗      ██████╗ ███████╗████████╗\n" +
                                        "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║     ██╔═══██╗██╔════╝╚══██╔══╝\n" +
                                        " ╚████╔╝ ██║   ██║██║   ██║    ██║     ██║   ██║███████╗   ██║   \n" +
                                        "  ╚██╔╝  ██║   ██║██║   ██║    ██║     ██║   ██║╚════██║   ██║   \n" +
                                        "   ██║   ╚██████╔╝╚██████╔╝    ███████╗╚██████╔╝███████║   ██║   \n" +
                                        "   ╚═╝    ╚═════╝  ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝   ╚═╝";
    private final String winLogo =  "██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗ ██████╗ ███╗   ██╗██╗\n" +
                                    "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██╔═══██╗████╗  ██║██║\n" +
                                    " ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║   ██║██╔██╗ ██║██║\n" +
                                    "  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║   ██║██║╚██╗██║╚═╝\n" +
                                    "   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝╚██████╔╝██║ ╚████║██╗\n" +
                                    "   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═══╝╚═╝";

    private final PrintStream out;
    private final Scanner in;
    private final ClientManager clientManager;


    public Cli(ClientManager clientManager) {
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out, true);
        this.clientManager = clientManager;
    }

    /**
     *  method to get an input from keyboard
     * @return a String containing the input of the user
     */
    private String readLine(){
        return in.next();
    }

    /**
     * method called upon the creation of a new Client
     */
    @Override
    public void start() {
        printLogo();
    }

    /**
     * prints the logo of the game
     */
    public void printLogo() {
        //SITO PER GENERARE ASCII ART
        //https://patorjk.com/software/taag/#p=testall&f=Elite&t=Master%20of%20Renaissance%20
        //ANSI SHADOWS
        String logo = CYAN + "\n" +
                "███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗      ██████╗ ███████╗    ██████╗ ███████╗███╗   ██╗ █████╗ ██╗███████╗███████╗ █████╗ ███╗   ██╗ ██████╗███████╗    \n" +
                "████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗    ██╔═══██╗██╔════╝    ██╔══██╗██╔════╝████╗  ██║██╔══██╗██║██╔════╝██╔════╝██╔══██╗████╗  ██║██╔════╝██╔════╝    \n" +
                "██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝    ██║   ██║█████╗      ██████╔╝█████╗  ██╔██╗ ██║███████║██║███████╗███████╗███████║██╔██╗ ██║██║     █████╗      \n" +
                "██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗    ██║   ██║██╔══╝      ██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║╚════██║╚════██║██╔══██║██║╚██╗██║██║     ██╔══╝      \n" +
                "██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║    ╚██████╔╝██║         ██║  ██║███████╗██║ ╚████║██║  ██║██║███████║███████║██║  ██║██║ ╚████║╚██████╗███████╗    \n" +
                "╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝     ╚═════╝ ╚═╝         ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚══════╝    \n" +
                "                                                                                                                                                                        \n"
                + RESET + "               Welcome to Masters Of Renaissance Board Game Digital Version (patent pending) created by Mattia Redaelli, Luca Rondini, Gabriele Rivi. \n" +
                "                                                        Have Fun playing the game! \n";
        out.println(logo);
    }

    /**
     * shows to the player all the lobby already created and display choice on how to proceed
     * @param availableGameLobbies arraylist of lobbies provided by the server upon connection
     */
    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        String input;
        if (availableGameLobbies.size() == 0) {
            do {
                out.println("There are no available lobbies, press: \n" +
                            "C: Create a new game: \n" +
                            "A: Ask the lobbies again:");
                input = readLine();
            } while (!input.equalsIgnoreCase("C")&&!input.equalsIgnoreCase("A"));
            if (input.equalsIgnoreCase("C"))
                createNewGame();
        } else {
            out.println("Here are the available Lobbies:");
            out.println("-----------------");
            for (ReturnLobbiesMessage.availableGameLobbies lobby : availableGameLobbies) {
                out.println("Lobby ID: " + lobby.getServerThreadID());
                out.println("Number of Players for this game: " + lobby.getNumberOfPlayers());
                out.println("Players:");
                for (String p : lobby.getPlayers()) {
                    out.println(p);
                }
                out.println("-----------------");
            }
            do {
                out.println("Now choose: \n" +
                        "J: Join and existing match. \n" +
                        "C: Create a new Game. \n" +
                        "A: Ask the lobbies again: ");
                input = readLine();
            } while (!input.equalsIgnoreCase("J") && !input.equalsIgnoreCase("C") && !input.equalsIgnoreCase("A"));

            if (input.equalsIgnoreCase("C"))
                createNewGame();
            if (input.equalsIgnoreCase("J"))
                joinExistingGame(availableGameLobbies);
        }
        if (input.equalsIgnoreCase("A"))
            clientManager.askLobbies();
    }

    /**
     * method called when the player wants to create a new game
     */
    @Override
    public void createNewGame() {
        clearView();
        out.println("Now you can create your own game!");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        int numberOfPlayers = askNumberOfPlayers();
        clientManager.createGame(numberOfPlayers);
        clearView();
    }

    /**
     * method called when the player wants to join an already existing game
     * @param availableGameLobbies arraylist used to do proper controls on the choice of the player
     */
    @Override
    public void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        out.println("Now you can choose the game to join! ");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        long serverThreadID = askForServerID(availableGameLobbies);

        clientManager.joinGame(serverThreadID);
        clearView();
    }

    /**
     *
     * @param availableGameLobbies arraylist used to do proper controls on the choice of the player
     * @return the serverID chosen by the user for the game to join
     */
    public long askForServerID(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        String chosen;
        String input;
        ArrayList<Long> id = new ArrayList<>();
        long chosenId;

        for (ReturnLobbiesMessage.availableGameLobbies lobby : availableGameLobbies)
            id.add(lobby.getServerThreadID());

        do {
            do {
                out.println("Insert the Lobby ID you want to Join!");
                chosen = readLine();
                try{
                chosenId = Long.parseLong(chosen);}catch(NumberFormatException e){chosenId = -1;}
            } while (!id.contains(chosenId));
            out.println("The choosen Lobby ID is : " + chosenId + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        } while (!input.equalsIgnoreCase("Y"));

        return chosenId;
    }

    /**
     *
     * @return number of players chosen by the player when creating a new game
     */
    public int askNumberOfPlayers() {
        String number;
        String input;
        int num;
        do {
            do {
                out.println("Insert the number of players for your game (must be between 1 and 4)");
                do {
                    number = readLine();
                } while (number.equals(""));

                try{num = Integer.parseInt(number);}catch(NumberFormatException e) { num = 0;}
                } while (num < 1 || num > 4);
            out.println("The choosen number of player is : " + number + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        } while (!input.equalsIgnoreCase("Y"));

        return num;
    }

    /**
     *
     * @return nickname chosen by the user to represent him in the game
     */
    public String askForNickname() {
        String nickname;
        String input;
        do {
            do {
                out.println("Insert a nickname (\"Lorenzo il Magnifico\" is not available as nickname) :");
                nickname = readLine();
            } while (nickname.equals("Lorenzo il Magnifico"));
            out.println("The choosen nickname is : " + nickname +
                    "\nDo you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        } while (!input.equalsIgnoreCase("Y"));

        return nickname;
    }

    /**
     * initialization of game, with leaders and resources
     * @param leaders four leaders provided by the server, of which the player needs to choose two
     * @param numberOfResources (optional) choice of resources by the player to start the game with
     */
    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources) {
        out.println("The game is about to start, choose your initial setup!\n");
        ArrayList<Resource> resources = new ArrayList<>();
        if (numberOfResources != 0) {
            out.println("You have the right to choose " + numberOfResources + " resource to start the game with!");
            resources = askResources(numberOfResources);
        }
        clearView();
        ArrayList<LeaderCard> chosenLeaders = askLeaders(leaders);
        clientManager.preGameChoice(resources, chosenLeaders);
    }

    /**
     *
     * @param leaders four leaders provided by the server
     * @return an arraylist of two leaders, chosen by the player
     */
    private ArrayList<LeaderCard> askLeaders(ArrayList<LeaderCard> leaders) {
        ArrayList<LeaderCard> leadersChosen = new ArrayList<>();
        int counter = 2;
        int chosen;
        ArrayList<Integer> id = new ArrayList<>();
        out.println("You can choose two leaders between these four:");
        out.println("-----------------");
        printLeaders(leaders);
        for (LeaderCard l : leaders)
            id.add(l.getId());
        do {

            out.print("You still have " + counter + " leaders to choose.\n");
            chosen = askCardID(id,"Insert the ID of the choosen leader: ");
            for (LeaderCard leader : leaders)
                if (leader.getId() == chosen)
                    leadersChosen.add(leader);

            counter--;
        } while (counter != 0);
        return leadersChosen;
    }

    /**
     * method used to print on screen leader cards
     * @param leaders the arraylist of leaders to print
     */
    private void printLeaders(ArrayList<LeaderCard> leaders) {
        for (LeaderCard l : leaders) {
            out.println("ID: " + l.getId() + "\n" +
                    "Victory Points: " + l.getVictoryPoints());
            //REQUIREMENT PRINT
            if(!l.isInGame()) {
                Requirement requirement = l.getRequirement();
                out.println(requirement.toString());
            }
            else
                out.println(GREEN+"This card is in game!"+RESET);
            //SPECIALABILITY PRINT:
            SpecialAbility specialAbility = l.getSpecialAbility();
            out.println(specialAbility.toString());
            out.println("-----------------");
        }
    }

    /**
     * method used in the pregame settings to give the 2nd, 3rd and 4th player 1,1 and 2 resources each respectively
     * @param numberOfResources counter of resources the player need to choose
     * @return an arraylist containing the chosen resources
     */
    private ArrayList<Resource> askResources(int numberOfResources) {
        String resource;
        ArrayList<Resource> resources = new ArrayList<>();
        int counter = numberOfResources;
        do {
            out.println("You still have " + counter + " resources to choose.\n");
            do {
                out.println("Choose between 4 types of resources: \n" +
                        "COINS: press C \n" +
                        "ROCKS: press R \n" +
                        "SHIELDS: press SH \n" +
                        "SERVANTS: press SE \n");
                resource = readLine();
            } while (!resource.equalsIgnoreCase("c") && !resource.equalsIgnoreCase("r") && !resource.equalsIgnoreCase("sh") && !resource.equalsIgnoreCase("se"));

            if (resource.equalsIgnoreCase("c")) {
                resources.add(Resource.COIN);
                out.println(YELLOW + "COIN ADDED\n" + RESET);
            }
            if (resource.equalsIgnoreCase("r")) {
                resources.add(Resource.ROCK);
                out.println(YELLOW + "ROCK ADDED\n" + RESET);
            }
            if (resource.equalsIgnoreCase("sh")) {
                resources.add(Resource.SHIELD);
                out.println(YELLOW + "SHIELD ADDED\n" + RESET);
            }
            if (resource.equalsIgnoreCase("se")) {
                resources.add(Resource.SERVANT);
                out.println(YELLOW + "SERVANT ADDED\n" + RESET);
            }

            counter--;
        } while (counter != 0);
        out.println("-----------------");

        return resources;
    }

    /**
     * displays the current situation of the game to a waiting player, upon update from the server
     */
    @Override
    public void waitingForTurn() {
        out.println("\nWait for the other players to play their turn, in the meantime you can peek around the board to keep updated.");
        printMarket();
        out.println();
        for (PlayerDashboard p : clientManager.getGameStatus().getPlayers()) {
            printPlayer(p.getNickname());
            out.println();
        }
        printShop(false);
        printMsg("Waiting updates...");
    }

    /**
     * shows current situation of the board of a player who has the turn
     */
    @Override
    public void yourTurn() {
        clearView();
        out.println(RED_BOLD + "|||It's your turn|||" + RESET);
        out.println("This is the actual state of your board: ");
        chooseAction();
    }

    /**
     * used to ask the player how he wants to go on with his turn, the choice for the action to make
     */
    public void chooseAction(){
        PlayerDashboard thisPlayerDashboard = clientManager.getThisClientDashboard();
        printPlayer(thisPlayerDashboard.getNickname());
        String input;
        do {
            if (!clientManager.isMainActionDone()) {
                out.println(YELLOW + "You still have to do one of these before ending your turn: " + RESET);
                out.println("TAKE RESOURCES FROM MARKET: press M");
                if(clientManager.canBuyCardFromShop())
                    out.println("BUY A CARD : press B");
                if (clientManager.canDoProduction())
                    out.println("USE THE PRODUCTION POWERS ON YOUR BOARD: press P");
            }
            out.println(YELLOW + "Secondary actions: " + RESET);

            //PlayLeader || DiscardLeader
            if(clientManager.leadersInHand()) {
                out.println("DISCARD A LEADER: press D");
                if(clientManager.canPlayLeader())
                    out.println("PLAY A LEADER: press L");
            }

            //Reorganize
            if(clientManager.canMoveResources())
                out.println("REORGANIZE RESOURCES: press R");

            if (clientManager.isMainActionDone())
                out.println(RED + "PRESS Q TO PASS YOUR TURN" + RESET);

            out.println("Choose one of the above to continue the game: ");
            input = readLine();
        }while(!input.equalsIgnoreCase("b")&& !input.equalsIgnoreCase("m")&&!input.equalsIgnoreCase("p")
                &&!input.equalsIgnoreCase("l")&&!input.equalsIgnoreCase("d")&&!input.equalsIgnoreCase("r") &&
                !input.equalsIgnoreCase("q"));


        if (input.equalsIgnoreCase("q") && clientManager.isMainActionDone())
            endTurn();
        else {
            if (input.equalsIgnoreCase("b") && !clientManager.isMainActionDone() && clientManager.canBuyCardFromShop())
                buyCard();
            else {
                if (input.equalsIgnoreCase("m") && !clientManager.isMainActionDone())
                    takeResourcesFromMarket();
                else {
                    if (input.equalsIgnoreCase("p") && !clientManager.isMainActionDone() && clientManager.canDoProduction())
                        startProduction();
                    else {
                        if (input.equalsIgnoreCase("l") && clientManager.canPlayLeader())
                            leaderAction(clientManager.getNotPlayedLeaders(),false);
                        else {
                            if (input.equalsIgnoreCase("d") && clientManager.leadersInHand())
                                leaderAction(clientManager.getNotPlayedLeaders(),true);
                            else {
                                if (input.equalsIgnoreCase("r") && clientManager.canMoveResources())
                                    organizeResources();
                                else
                                    chooseAction();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * upon choice to end the turn, call the clientManager method who handles the choice
     */
    @Override
    public void endTurn(){
        clientManager.endTurn();
    }

    /**
     * upon choice to buy a card, shows the market and calls the method to continue the purchase
     */
    @Override
    public void buyCard(){
        String input;
        ArrayList<Integer> cardID = clientManager.getAllShopID();
        int id;
        out.println(PURPLE + "Take a look at the shop first, choose the card, and when you're ready buy it!" + RESET);
        printShop(true);
        do{
            out.println("Now insert the id of the card you want to buy (make sure you can afford it): ");
            input = readLine();
            try{id = Integer.parseInt(input);}catch(NumberFormatException e) {id = -1;}
        }while(!cardID.contains(id) || (cardID.contains(id) && !clientManager.canBuySpecificCard(id)));

        DevelopmentCard card = clientManager.getShopCardByID(id);
        ResourceCount cost = new ResourceCount(0,0,0,0,0);

        cost.sumCounts(card.getCost());
        ArrayList<ResourceCount> payments = askPayment(cost);
        ResourceCount storagePayment = payments.get(0);
        ResourceCount chestPayment = payments.get(1);
        int position;
        do{
            out.println("Now insert the position of the deck of which you want to put the card on top: ");
            input = readLine();
            try{position = Integer.parseInt(input);}catch(NumberFormatException e) {position = -1;}
        }while((position <1 || position >3) || clientManager.positionPossible(position,card.getLevel()));
        clientManager.setMainActionDone(true);
        clientManager.buyCard(storagePayment,chestPayment,id,position);
    }

    /**
     * ask the player how he prefers to pay (where to take resources first) and calls proper methods
     * @param cost cost that needs to be covered to complete the purchase
     */
    private ArrayList<ResourceCount> askPayment(ResourceCount cost){
        String input;
        ResourceCount storage = new ResourceCount(0,0,0,0,0);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        ArrayList<ResourceCount> payments = new ArrayList<>();
        do{
            out.println(GREEN+"Press S to start payment from the storage, C from the chest: "+RESET);
            input = readLine();
        }while(!input.equalsIgnoreCase("s")&&!input.equalsIgnoreCase("C"));

        if(input.equalsIgnoreCase("s")){
            storage = askStoragePayment(cost,false);
            if(ResourceCount.resCountToInt(cost) !=0)
                chest = askChestPayment(cost,true);
        }
        else{
            chest = askChestPayment(cost,false);
            if(ResourceCount.resCountToInt(cost) !=0)
                storage = askStoragePayment(cost,true);
        }
        payments.add(storage);
        payments.add(chest);
        return payments;
    }

    /**
     *  ask for the resources from the Storage to pay with
     * @param cost cost that still needs to be covered
     * @param needToCover boolean (the cost may not have already been covered entirely if coming from the chest payment)
     * @return a ResourceCount containing the resources chosen from the storage
     */
    private ResourceCount askStoragePayment(ResourceCount cost,boolean needToCover){
        out.println("You need to pay: " + cost);
        out.println("Now insert the resources you want to pay with FROM THE STORAGE: ");
        ResourceCount temporaryStorage = clientManager.getThisClientDashboard().getStorage().readStorage();
        ResourceCount storagePayment = new ResourceCount(0,0,0,0,0);
        addResourceToPayment(temporaryStorage,storagePayment,cost,needToCover);
        return storagePayment;
    }

    /**
     *  ask for the resources from the Chest to pay with
     * @param cost cost that still needs to be covered
     * @param needToCover boolean (the cost may not have already been covered entirely if coming from the storage payment)
     * @return a ResourceCount containing the resources chosen from the chest
     */
    private ResourceCount askChestPayment(ResourceCount cost,boolean needToCover){
        out.println("You need to pay: " + cost);
        out.println("Now insert the resources you want to pay with FROM THE CHEST: ");
        ResourceCount temporaryChest = clientManager.getThisClientDashboard().getChest();
        ResourceCount chestPayment = new ResourceCount(0,0,0,0,0);
        addResourceToPayment(temporaryChest,chestPayment,cost,needToCover);
        return chestPayment;
    }

    /**
     *
     * @param temporary ResourceCount that keeps track of the choices of the player (representing storage or chest)
     * @param payment ResourceCount that will contain the chosen resources to pay
     * @param cost amount of resources that need to be covered
     * @param needToCover boolean representing if the cost still needs to be covered completely
     */
    private void addResourceToPayment(ResourceCount temporary, ResourceCount payment, ResourceCount cost, boolean needToCover){
        String input;
        do {
            do {
                out.println("Remaining cost to cover: " + cost);
                out.println("Chosen Resources: " + payment);
                out.println("Resources in the stock: " + temporary);
                out.println("Choose between 4 types of resources: \n" +
                        "COINS: press C \n" +
                        "ROCKS: press R \n" +
                        "SHIELDS: press SH \n" +
                        "SERVANTS: press SE");
                input = readLine();
            } while (!input.equalsIgnoreCase("c")&&!input.equalsIgnoreCase("r")&&!input.equalsIgnoreCase("sh")&&!input.equalsIgnoreCase("se"));
            input= input.toUpperCase();
            boolean choiceOk = false;
            switch(input){
                case "C": if(temporary.getCoins()>=1 && cost.getCoins()>=1){
                    temporary.removeCoins(1);
                    cost.removeCoins(1);
                    payment.addCoins(1);
                    choiceOk = true;
                }
                    break;
                case "R": if(temporary.getRocks()>=1 && cost.getRocks()>=1){
                    temporary.removeRocks(1);
                    payment.addRocks(1);
                    cost.removeRocks(1);
                    choiceOk = true;
                }
                    break;
                case "SH": if(temporary.getShields()>=1 && cost.getShields()>=1){
                    temporary.removeShields(1);
                    payment.addShields(1);
                    cost.removeShields(1);
                    choiceOk = true;
                }
                    break;
                case "SE": if(temporary.getServants()>=1 && cost.getServants()>=1){
                    temporary.removeServants(1);
                    payment.addServants(1);
                    cost.removeServants(1);
                    choiceOk = true;
                }
                    break;
            }
            if(choiceOk) {
                do {
                    out.println("Choice registered, press C to continue adding resources from the storage, N to exit:");
                    input = readLine();
                } while (!input.equalsIgnoreCase("c") && !input.equalsIgnoreCase("N"));
            }
            else {
                out.println("Invalid choice, you don't have this resource in the storage.");
                input = "c";
            }
            if(needToCover && ResourceCount.resCountToInt(cost) != 0)
                input = "c";
            if(ResourceCount.resCountToInt(cost) == 0)
                input = "n";
        }while(input.equalsIgnoreCase("c"));
    }

    @Override
    public void takeResourcesFromMarket(){
        String input;
        int type;
        int pos;
        int max;
        int row;
        int action;
        int count;
        boolean validChoice;
        MarketMarble[] marbles;

        // Print the market
        printMarket();

        // Choose the row/column
        do {
            out.println("Type \"row\" if you want to select a row; \"col\" if you want to select a column:");
            input = readLine();
        } while(!input.equalsIgnoreCase("row")  && !input.equalsIgnoreCase("col"));
        if(input.equalsIgnoreCase("row")) {
            do {
                out.println("Row number (starting from 1): ");
                input = readLine();
                try {pos = Integer.parseInt(input);} catch(NumberFormatException e) {pos = -1;}
            } while(pos < 1 || pos > 3);
            type = 0;
        }
        else {
            do {
                out.println("Column number (starting from 1): ");
                input = readLine();
                try {pos = Integer.parseInt(input);} catch(NumberFormatException e) {pos = -1;}
            } while(pos < 1 || pos > 4);
            type = 1;
        }

        // Get marbles
        marbles = clientManager.getMarketMarbles(type, pos);

        // Action choice
        for (MarketMarble m: marbles) {
            if(m.getColour() != MarbleColour.RED && m.getColour() != MarbleColour.WHITE) {
                do {
                    do {
                        if(clientManager.hasAdditionalDeposit(m.getColour().convertToResource())) {
                            out.println("In witch deposit do you want to store the " + m.getColour().convertToResource() + "?\n0: discard resource;\n1-3: regular storage;\n4: additional storage");
                            max = 4;
                        }
                        else {
                            out.println("In witch deposit do you want to store the " + m.getColour().convertToResource() + "?\n0: discard resource;\n1-3: regular storage");
                            max = 3;
                        }
                        input = readLine();
                        try {row = Integer.parseInt(input);} catch(NumberFormatException e) {row = -1;}
                    } while(row < 0 || row > max);
                    validChoice = false;
                    if(row == 0) {
                        clientManager.discardResource();
                        validChoice = true;
                    }
                    else {
                        try {
                            clientManager.checkAddToStorage(row, m.getColour().convertToResource());
                            clientManager.getResource(row);
                            out.println("Resource stored successfully!");
                            validChoice = true;
                        }catch (MasterOfRenaissanceRuntimeException e) {
                            out.println(e.getMessage());
                        }
                    }
                } while(!validChoice);
            }
            if(m.getColour() == MarbleColour.WHITE) {
                if(clientManager.hasWhiteChangeAbility()) {
                    do {
                        do {
                            out.println("What do you want to do with this white marble?\n0: Take nothing;");
                            count = 1;
                            for(LeaderCard c: clientManager.getThisClientDashboard().getLeaderCards()) {
                                if(c.getSpecialAbility().useWhiteChangeAbility() != null && c.isInGame()) {
                                    out.println(count + ": Convert to " + c.getSpecialAbility().useWhiteChangeAbility().name() + " with Leader Card");
                                    count++;
                                }
                            }
                            input = readLine();
                            try {action = Integer.parseInt(input);} catch(NumberFormatException e) {action = -1;}
                        } while(action < 0 && action >= count);
                        validChoice = false;
                        if(action == 0) {
                            clientManager.getResource(0);
                            validChoice = true;
                        }
                        else {
                            LeaderCard chosenLeaderCard = clientManager.getWhiteChangeCard(action);
                            Resource convertedResource = chosenLeaderCard.getSpecialAbility().getResourceType();
                            do {
                                if(clientManager.hasAdditionalDeposit(convertedResource)) {
                                    out.println("In witch deposit do you want to store the " + convertedResource + "?\n1-3: regular storage; 4: additional storage");
                                    max = 4;
                                }
                                else {
                                    out.println("In witch deposit do you want to store the " + convertedResource + "?\n1-3: regular storage");
                                    max = 3;
                                }
                                input = readLine();
                                try {row = Integer.parseInt(input);} catch(NumberFormatException e) {row = -1;}
                            } while(row < 1 || row > max);
                            try {
                                clientManager.checkAddToStorage(row, convertedResource);
                                clientManager.convertMarble(chosenLeaderCard, row);
                                out.println("Resource stored successfully!");
                                validChoice = true;
                            }
                            catch (MasterOfRenaissanceRuntimeException e) {
                                out.println(e.getMessage());
                            }
                        }
                    } while(!validChoice);
                }
                else {
                    out.println("You don't have a Leader Card that can convert the white marble.");
                    clientManager.getResource(0); // Storage row is not important (a white marble doesn't produce a resource)
                }
            }
            if(m.getColour() == MarbleColour.RED) {
                out.println("You took a red marble, other players will gain 1 faith point.");
            }
        }
        clientManager.endMarketAction(type, pos);
    }

    /**
     * Start the production action asking what type of production the player wants to do
     * B: Basic, L: Leader Card, D: Development Card
     */
    @Override
    public void startProduction(){
        PlayerDashboard thisPlayer = clientManager.getThisClientDashboard();
        String input;
        out.println("Here are the available productions: ");
        do {
            if (clientManager.canDoBasicProduction(thisPlayer))
                out.println("The basic production is available, press B to start it: ");
            if (clientManager.canDoLeaderCardProduction(thisPlayer))
                out.println("A Leader card production is available, press L to start it: ");
            if (clientManager.canDoDevCardProduction(thisPlayer))
                out.println("A Development card production is available, press D to start it: ");
            if (clientManager.isProductionActionOnGoing())
                out.println("press Q to quit the production action: ");
            input = readLine();
        }while(!input.equalsIgnoreCase("B")&&!input.equalsIgnoreCase("L")&&!input.equalsIgnoreCase("D")&&!input.equalsIgnoreCase("Q"));
        if(input.equalsIgnoreCase("B")) {
            doBasicProduction(thisPlayer);
            if(!clientManager.isProductionActionOnGoing())
                clientManager.setProductionActionOnGoing(true);
        }
        if(input.equalsIgnoreCase("L")) {
            doLeaderCardProduction(clientManager.getProductionLeaders());
            if(!clientManager.isProductionActionOnGoing())
                clientManager.setProductionActionOnGoing(true);
        }
        if(input.equalsIgnoreCase("D")) {
            ArrayList<DevelopmentCard> devCards = new ArrayList<>();
            for(DeckDashboard d:thisPlayer.getDevCards()){
                if(d.getDeck().size()>0)
                    devCards.add(d.getFirst());
            }
            doDevCardProduction(devCards);
            if(!clientManager.isProductionActionOnGoing())
                clientManager.setProductionActionOnGoing(true);
        }
        if(input.equalsIgnoreCase("Q")&&clientManager.isProductionActionOnGoing())
            clientManager.endAction();

        if(!clientManager.isMainActionDone())
            clientManager.setMainActionDone(true);
    }

    /**
     * Starts the Basic Production
     *
     * @param p PlayerDashboard of the player
     */
    public void doBasicProduction(PlayerDashboard p){
        out.println(PURPLE+"--BASIC PRODUCTION--"+RESET);
        ResourceCount chosenInput = new ResourceCount(0,0,0,0,0);
        do {
            out.println(GREEN+"Choose 2 resources that you have as input of the production: "+RESET);
            ArrayList<Resource> inputResources = askResources(2);
            inputResources.get(0).add(chosenInput,1);
            inputResources.get(1).add(chosenInput,1);
        }while(!p.getTotalResources().hasMoreOrEqualsResources(chosenInput));
        ArrayList<ResourceCount> payments = askPayment(chosenInput);
        ResourceCount storagePayment =payments.get(0);
        ResourceCount chestPayment = payments.get(1);
        Resource outputResource = askChosenResourceOutput().get(0);
        clientManager.basicProduction(storagePayment,chestPayment,outputResource);
    }

    /**
     * Asks type of payment and the chosen resource as output
     */
    public ArrayList<Resource> askChosenResourceOutput(){
        out.println(GREEN+"Choose the resource that will be the output of the production: "+RESET);
        return askResources(1);
    }

    /**
     * Starts the Leader Card production
     *
     * @param passedLeaders Leader Cards that have a production ability
     */
    public void doLeaderCardProduction(ArrayList<LeaderCard> passedLeaders){
        out.println(PURPLE+"--LEADER CARD PRODUCTION--"+RESET);
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        ArrayList<Integer> id = new ArrayList<>();
        int ID;
        for (LeaderCard l : passedLeaders)
            if(!l.isInGame())
                id.add(l.getId());
        printLeaders(passedLeaders);
        out.println("Choose the leaderCard ID of the card you want to use the production: ");
        ID = askCardID(id,"Insert the ID of the chosen leader: " );
        int index;
        LeaderCard chosenCard = null;
        for(index=0;index<passedLeaders.size();index++) {
            LeaderCard l = passedLeaders.get(index);
            if (l.getId() == ID) {
                l.getSpecialAbility().getResourceType().add(cost, 1);
                chosenCard = l;
            }
        }
        ArrayList<ResourceCount> payments = askPayment(cost);
        ResourceCount storagePayment =payments.get(0);
        ResourceCount chestPayment = payments.get(1);
        Resource outputResource = askChosenResourceOutput().get(0);
        clientManager.leaderProduction(index,chosenCard,storagePayment,chestPayment,outputResource);
    }

    /**
     * Starts the Development Card production
     *
     * @param devCards Development Card on the dashboard
     */
    public void doDevCardProduction(ArrayList<DevelopmentCard> devCards){
        out.println(PURPLE+"--LEADER CARD PRODUCTION--"+RESET);
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        ArrayList<Integer> id = new ArrayList<>();
        int ID;
        for (DevelopmentCard d : devCards) {
            id.add(d.getId());
            printDevCard(d);
        }
        out.println("Choose the development card ID of the card you want to use the production: ");
        ID = askCardID(id,"Insert the ID of the chosen development card: " );
        int index;
        DevelopmentCard chosenCard = null;
        for(index=0;index<devCards.size();index++) {
            DevelopmentCard d = devCards.get(index);
            if (d.getId() == ID) {
                cost.sumCounts(d.getCost());
                chosenCard = d;
            }
        }
        ArrayList<ResourceCount> payments = askPayment(cost);
        ResourceCount storagePayment = payments.get(0);
        ResourceCount chestPayment = payments.get(1);
        clientManager.devCardProduction(index,chosenCard,storagePayment,chestPayment);
    }

    /**
     * method used to ask the player for a card ID
     * @param id arraylist containing possible ids to chose within
     * @param msg message to print before the choice
     * @return chosen id
     */
    public int askCardID(ArrayList<Integer> id,String msg){
        String input;
        int ID;
        do {
            out.println(msg);
            input = readLine();
            try {
                ID = Integer.parseInt(input);
            }catch(NumberFormatException e){ID=-1;}
        } while (ID==-1||!id.contains(ID));
        return ID;
    }

    /**
     * Starts a leader action, play or discard a leader
     * @param passedLeaders available leader cards
     * @param isDiscard true if the action is a discard a leader action
     */
    @Override
    public void leaderAction(ArrayList<LeaderCard> passedLeaders,boolean isDiscard){
        ArrayList<Integer> id = new ArrayList<>();
        int ID;
        String input;
        for (LeaderCard l : passedLeaders)
            if(!l.isInGame())
                id.add(l.getId());

        if(isDiscard)
            out.println("Choose the ID of the leader that you want to discard: ");
        else
            out.println("Choose the ID of the leader that you want to play: ");
        out.println("-----------------");
        printLeaders(passedLeaders);
        do {
            out.println("Insert the ID of the chosen leader: ");
            input = readLine();
            try{ID = Integer.parseInt(input);}catch(NumberFormatException e) { ID = 0;}
        } while (!id.contains(ID));
        for(LeaderCard l:passedLeaders)
            if(l.getId()==ID) {
                if(isDiscard)
                    clientManager.discardLeader(l);
                else
                    clientManager.playLeader(l);
                passedLeaders.remove(l);
                break;
            }
    }

    /**
     * called upon player's choice to organize resources, display possible choices
     */
    @Override
    public void organizeResources(){
        int count = 1;
        String input;
        PlayerDashboard player = clientManager.getThisClientDashboard();
        //Storage print
        printStorage(player.getStorage());
        //Leader deposit print, if there are any;
        if(player.getArrayDeposit().size()>0)
            printArrayDeposits(player.getArrayDeposit());
        do{
            out.println("You can select which resources you want to move:");
            out.println("Press \""+count+"\" to move two shelves on the storage;");
            count++;
            if(player.getArrayDeposit().size()>0)
                for (CounterTop c: player.getArrayDeposit()) {
                    out.println("Press \""+count+"\" to move resources with the Special deposit containing: " + c.getResourceType());
                }
            input = readLine();
        }while(!input.equals("1") && !input.equals("2") && !input.equals("3"));

        if(input.equals("1"))
            organizeStorage();
        else
            if(input.equals("2") && player.getArrayDeposit().size()>=1)
                leaderMoveResources(player.getArrayDeposit().get(0));
            else
                if(input.equals("3") && player.getArrayDeposit().size()==2)
                    leaderMoveResources(player.getArrayDeposit().get(1));
                else
                    organizeResources();
    }

    /**
     * organizing resources concerning a special ability's deposit
     * @param leaderDeposit the chosen special ability's deposit
     */
    private void leaderMoveResources(CounterTop leaderDeposit){
        String input;
        do{
            if(clientManager.canMoveToLeader(leaderDeposit.getResourceType()) && leaderDeposit.getContent()<2)
                out.println("Press \"from\" if you want to move resources from the chosen special deposit;");
            if(clientManager.canMoveFromLeader(leaderDeposit.getResourceType()) && leaderDeposit.getContent()>0)
                out.println("Press \"to\" if you want to move resources to the chosen special deposit;");
            out.println("Press \"Q\" to quit this action.");
            input = readLine();
        }while(!input.equalsIgnoreCase("from") && !input.equalsIgnoreCase("to") && !input.equalsIgnoreCase("q"));

        if(input.equalsIgnoreCase("from"))
            moveFromLeader(leaderDeposit);
        if(input.equalsIgnoreCase("to"))
            moveToLeader(leaderDeposit);
        if(input.equalsIgnoreCase("q"))
            chooseAction();
    }

    /**
     * moving resources from a leader's deposit to the storage
     * @param leaderDeposit chosen leader's deposit
     */
    private void moveFromLeader(CounterTop leaderDeposit){
        int num = askNumberResourcesToMove(leaderDeposit);
        clientManager.moveLeaderResources(leaderDeposit.getResourceType(), num,true);
    }

    /**
     * asking how many resources the player wants to include in the move
     * @param counterTop shelf from which the resources will be removed
     * @return chosen number of resources
     */
    private int askNumberResourcesToMove(CounterTop counterTop){
        String input;
        int num = -1;
        do{
            out.println("How many resources do you want to move?");
            input = readLine();
            try{num = Integer.parseInt(input);}catch(NumberFormatException e) { num = -1;}
        }while(num <= counterTop.getContent());
        return num;
    }

    /**
     * moving resources from the storage to a leader's deposit
     * @param leaderDeposit chosen leader's deposit
     */
    private void moveToLeader(CounterTop leaderDeposit){
        int num = askNumberResourcesToMove(leaderDeposit);
        clientManager.moveLeaderResources(leaderDeposit.getResourceType(), num,false);
    }

    /**
     * swap of two shelves on the storage
     */
    private void organizeStorage(){
        String input;
        int num = -1;
        do{
            out.println("Insert the number of the shelf you want to start the swap FROM (1,2 or 3): ");
            input = readLine();
            try{num = Integer.parseInt(input);}catch(NumberFormatException e) { num = -1;}
        }while(num <1 || num>3);
        int from = num;
        do{
            out.println("Insert the number of the shelf you want to move TO (1,2 or 3): ");
            input = readLine();
            try{num = Integer.parseInt(input);}catch(NumberFormatException e) { num = -1;}
        }while(num <1 || num>3);
        int to = num;
        if(to == from)
            chooseAction();
        if(clientManager.swapOk(from,to))
            clientManager.organizeStorage(from,to);
        else {
            printMsg("Action not possible!");
            chooseAction();
        }
    }

    /**
     * printing the board of a specific player
     * @param nickname of the player we want to print the board
     */
    private void printPlayer(String nickname) {
        PlayerDashboard player = clientManager.getGameStatus().getClientDashboard(nickname);
        if(nickname.equals(clientManager.getNickname()))
            out.println(BLUE_BOLD+"~~YOUR DASHBOARD"+"~~"+RESET);
        else
            out.println(BLUE_BOLD+"~~"+nickname+"'S DASHBOARD"+"~~"+RESET);
        //VictoryPoints
        out.println(PURPLE+"VICTORY POINTS: "+player.getPoints()+RESET);

        //PathPosition
        printPathPosition(player.getPathPosition(),nickname);

        //BufferProduction
        if(ResourceCount.resCountToInt(player.getBufferProduction())>0)
            printBufferProduction(player.getBufferProduction());
        printStorage(player.getStorage());

        //ArrayDeposit
        if(player.getArrayDeposit().size()>0)
            printArrayDeposits(player.getArrayDeposit());

        //Chest
        printChest(player.getChest());

        //DevCards
        printPlayerDevCards(player.getDevCards());

        //LeaderCards
        if(nickname.equals(clientManager.getNickname()))
            printPlayerLeaderCards(player.getLeaderCards());
        else{
            ArrayList<LeaderCard> leadersInGame = new ArrayList<>();
            for(LeaderCard l:player.getLeaderCards()) {
                if (l.isInGame())
                    leadersInGame.add(l);
            }
            printPlayerLeaderCards(leadersInGame);
        }
    }

    /**
     * print the current situation of the Market Dashboard
     */
    private void printMarket() {
        MarketDashboard market = clientManager.getGameStatus().getMarket();
        MarketMarble[][] grid = market.getStructure();
        out.println(BLUE + "~~MARKET DASHBOARD~~" + RESET);
        for (int i = 0; i < 3; i++) {
            out.print("\t\t");
            for (int j = 0; j < 4; j++) {
                switch (grid[i][j].getColour()) {
                    case WHITE:
                        out.print("[W] \t" + RESET);
                        break;
                    case RED:
                        out.print(RED + "[R] \t" + RESET);
                        break;
                    case YELLOW:
                        out.print(YELLOW + "[Y] \t" + RESET);
                        break;
                    case GREY:
                        out.print(WHITE + "[G] \t" + RESET);
                        break;
                    case PURPLE:
                        out.print(PURPLE + "[P] \t" + RESET);
                        break;
                    case BLUE:
                        out.print(BLUE + "[B] \t" + RESET);
                        break;
                }
            }
            out.print("\n");
        }
        out.print("The marble left out: ");
        switch (market.getFreeMarble().getColour()) {
            case WHITE:
                out.print("[W] \t" + RESET);
                break;
            case RED:
                out.print(RED + "[R] \t" + RESET);
                break;
            case YELLOW:
                out.print(YELLOW + "[Y] \t" + RESET);
                break;
            case GREY:
                out.print(WHITE + "[G] \t" + RESET);
                break;
            case PURPLE:
                out.print(PURPLE + "[P] \t" + RESET);
                break;
            case BLUE:
                out.print(BLUE + "[B] \t" + RESET);
                break;
        }
        out.println("Legend: W -> White |" + RED + " R -> Red |" + RESET + YELLOW + " Y -> Yellow |" + RESET + WHITE + " G -> Gray |" + RESET + PURPLE + " P -> Purple |" + RESET + BLUE + " B -> Blue " + RESET);
    }

    /**
     * print the current situation of the Shop
     * @param isMyTurn if it's my turn, the Id-lookup functionality is activated in case of a card shop action
     */
    private void printShop(boolean isMyTurn) {
        Deck[][] shop = clientManager.getGameStatus().getShop().getGrid();
        out.println(BLUE + "~~SHOP GRID~~" + RESET);
        for (int i = 0; i < 3; i++) {
            out.print("Level: " + shop[i][0].getFirst().getLevel() + "\t");
            for (int j = 0; j < 4; j++) {
                switch (shop[i][j].getFirst().getColour()) {
                    case GREEN:
                        out.print(GREEN);
                        break;
                    case YELLOW:
                        out.print(YELLOW);
                        break;
                    case BLUE:
                        out.print(BLUE);
                        break;
                    case PURPLE:
                        out.print(PURPLE);
                        break;
                }
                if (shop[i][j].getFirst() == null)
                    out.print("****");
                if (shop[i][j].getFirst().getId() >= 10)
                    out.print("[" + shop[i][j].getFirst().getId() + "]");
                else
                    out.print("[ " + shop[i][j].getFirst().getId() + "]");

                for (int k = 0; k < shop[i][j].getDeck().size() - 1; k++)
                    out.print("]");
                out.print("\t" + RESET);
            }
            out.print("\n");
        }
        if(isMyTurn) {
            String input;
            ArrayList<Integer> cardID = clientManager.getAllShopID();
            int id;
            do {
                do {
                    out.println("Insert the ID of the card you want to see");
                    input = readLine();
                    try {
                        id = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        id = -1;
                    }
                } while (!cardID.contains(id));
                DevelopmentCard card = clientManager.getShopCardByID(id);
                printDevCard(card);
                out.println("\nPress \"esc\" if you have decided which card to buy, another key to continue looking at Cards: ");
                input = readLine();
            } while (!input.equals("esc"));
        }
    }

    /**
     * method to print a specific Development Card
     * @param card that needs to be printed
     */
    void printDevCard(DevelopmentCard card){
        out.print("ID: " + card.getId() +"\n" +
                "Level: " + card.getLevel() +"\n" +
                "Victory Points: " + card.getVictoryPoints() + "\n" +
                "Cost: " + card.getCost() +"\n");
        out.print("Colour: ");
        switch (card.getColour()){
            case GREEN: out.print(GREEN);
                break;
            case YELLOW: out.print(YELLOW);
                break;
            case BLUE: out.print(BLUE);
                break;
            case PURPLE: out.print(PURPLE);
                break;
        }

        out.print(card.getColour()+ RESET + "\n" +
               CYAN_BOLD+ "Production Power: " + "\n" +
                "Cost: " + card.getProductionPower().getInput().toString() + "\n" +
                "Output: " + card.getProductionPower().getOutput().toString());
    }

    /**
     * called upon Vatican Report activation, show the player if they benefits from it or not
     * @param victoryPoints points gained from the vatican report
     * @param nicknames arraylist containing all the names of the players affected by the vatican report
     */
    @Override
    public void vaticanReportActivated(int victoryPoints,ArrayList<String> nicknames){
        String playerNickname = clientManager.getNickname();
        printMsg("A vatican report worth "+victoryPoints+" victory points has been activated!");
        if(!nicknames.contains(playerNickname))
            out.println(RED+"Unfortunately, you weren't affected."+RESET);
        else
            out.println(GREEN+"Well done, you have been affected by the vatican report!"+RESET);
        printMsg("Other players affected by the vatican report: ");
        for(int i=0;i<nicknames.size();i++){
            String nick = nicknames.get(i);
            if(!nick.equals(playerNickname)) {
                out.print(YELLOW + nick);
                if(i!=nicknames.size()-1&&(i==nicknames.size()-2&&!nicknames.get(i+1).equals(playerNickname)))
                    out.print(" - ");
            }
        }
        out.println(RESET);
    }

    /**
     * print the player's leader cards
     * @param leaderCards arraylist of the leader cards in his possess
     */
    private void printPlayerLeaderCards(ArrayList<LeaderCard> leaderCards){
        out.println(PURPLE+"--LEADER CARDS--"+RESET);
        if(leaderCards.size()!=0)
            printLeaders(leaderCards);
        else
            out.println("No leader cards in game!");
    }

    /**
     * print the development cards in possess of a player
     * @param devCards decks of cards to print
     */
    private void printPlayerDevCards(DeckDashboard[] devCards){
        out.println(PURPLE+"--DEVELOPMENT CARDS--"+RESET);
        boolean printed = false;
        for(DeckDashboard d:devCards){
            if(d.getDeck().size()>0) {
                out.println(d.getFirst().toString());
                printed = true;
            }
        }
        if(!printed)
            out.println("No development cards yet!");
    }

    /**
     * print the position of a player on the faith path
     * @param position the specific position
     * @param nickname of the player
     */
    private void printPathPosition(int position,String nickname){
        out.println(PURPLE+"--FAITH PATH--"+RESET);
        if(nickname.equals(clientManager.getNickname()))
            out.println(BLUE+"YOU"+RESET+" are here:"+BLUE+" *"+RESET+", vatican report and points: ex:["+YELLOW+"VR2"+RESET+"], victory points in that cell: ex:["+GREEN+"20"+YELLOW+"VR2"+RESET+"]");
        else
            out.println(BLUE+nickname+RESET+" is here:"+BLUE+" *"+RESET+", vatican report and points: ex:["+YELLOW+"VR2"+RESET+"], victory points in that cell: ex:["+GREEN+"20"+YELLOW+"VR2"+RESET+"]");
        for(int i=0;i<25;i++){
            switch(i){
                case 3: printFaithPathCell(position==i,1, false,0);
                    break;
                case 6: printFaithPathCell(position==i,2, false,0);
                    break;
                case 8:
                case 16:
                    printFaithPathCell(position==i,0, true,2);
                    break;
                case 9: printFaithPathCell(position==i,4, false,0);
                    break;
                case 12: printFaithPathCell(position==i,6, false,0);
                    break;
                case 15: printFaithPathCell(position==i,9, false,0);
                    break;
                case 18: printFaithPathCell(position==i,12, false,0);
                    break;
                case 21: printFaithPathCell(position==i,16, false,0);
                    break;
                case 24: printFaithPathCell(position==i,20, true,4);
                    break;
                default: printFaithPathCell(position==i,0, false,0);
            }
        }
        out.println();
    }

    /**
     * used to model the faith path
     * @param playerIsHere indicates the player is on the specific cell
     * @param victoryPoints granted by surpassing the cell on the faith path
     * @param isVaticanReport is a Pope's dialogue cell that activates a vatican report
     * @param vaticanReportPoints points granted by the vatican report
     */
    private void printFaithPathCell(boolean playerIsHere,int victoryPoints,boolean isVaticanReport,int vaticanReportPoints){
        if(playerIsHere&&victoryPoints!=0&&isVaticanReport)
            out.print("["+GREEN+victoryPoints+BLUE+"*"+YELLOW+"VR"+vaticanReportPoints+RESET+"]");
        else {
            if (!playerIsHere && victoryPoints != 0 && isVaticanReport)
                out.print("[" + GREEN + victoryPoints + YELLOW + "VR" + vaticanReportPoints + RESET +"]");
            else {
                if (playerIsHere && victoryPoints == 0 && isVaticanReport)
                    out.print("[" + BLUE + "*" + YELLOW + "VR" + vaticanReportPoints + RESET +"]");
                else {
                    if (!playerIsHere && victoryPoints == 0 && isVaticanReport)
                        out.print("["+YELLOW+"VR" + vaticanReportPoints + RESET+"]");
                    else {
                        if (playerIsHere && victoryPoints != 0)
                            out.print("["+GREEN + victoryPoints + BLUE + "*" + RESET + "]");
                        else
                            if (!playerIsHere && victoryPoints != 0)
                                out.print("[" +GREEN+ victoryPoints +RESET+ "]");
                            else
                                if(playerIsHere)
                                    out.print("[" + BLUE + "*" + RESET + "]");
                                else
                                    out.print("[]");
                    }
                }
            }
        }
    }

    /**
     * print the player's special ability deposits
     * @param arrayDeposits arraylist of the special ability deposits
     */
    private void printArrayDeposits(ArrayList<CounterTop> arrayDeposits){
        out.println(PURPLE+"--SPECIAL ABILITY DEPOSITS--"+RESET);
        for(CounterTop counterTop:arrayDeposits)
            printCounterTop(counterTop);
    }

    /**
     * print the temporary situation of the buffer during a production action
     * @param bufferProduction temporary buffer
     */
    private void printBufferProduction(ResourceCount bufferProduction){
        out.println(PURPLE+"--PRODUCTION RESOURCES--"+RESET);
        out.println(bufferProduction);
    }

    /**
     * print the resources contained in the chest
     * @param chest player's chest
     */
    private void printChest(ResourceCount chest){
        out.println(PURPLE+"--CHEST--"+RESET);
        if(ResourceCount.resCountToInt(chest)>0)
            out.println(chest);
        else
            out.println("EMPTY");
    }

    /**
     * print the situation of the countertops in the storage
     * @param storage player's storage
     */
    private void printStorage(Storage storage){
        out.println(PURPLE+"--STORAGE--"+RESET);
        printCounterTop(storage.getFirstRow());
        printCounterTop(storage.getSecondRow());
        printCounterTop(storage.getThirdRow());
    }

    /**
     * print a specific countertop (resource,content)
     * @param counterTop specific countertop
     */
    private void printCounterTop(CounterTop counterTop){
        if(counterTop.getContent()>0)
            out.print("Resource: "+counterTop.getResourceType()+" ");
        else
            out.println("EMPTY");
        for(int i=0;i<counterTop.getContent();i++)
            out.print("*");
        if(counterTop.getContent()>0)
            out.println();
    }

    /**
     * endgame method for a single player game, displays final points and whether is a win or a lose
     * @param lorenzoWin true if Lorenzo won the game
     * @param playerPoints final points of the player
     */
    @Override
    public void endGame(boolean lorenzoWin, int playerPoints) {
        if(lorenzoWin)
            out.println(RED+lostLogo+RESET+"\n                 You had: "+playerPoints+" victory points.");
        else
            out.println(GREEN+winLogo+RESET+"\n                You had: "+playerPoints+" victory points.");
    }

    /**
     * endgame method for a multiplayer game, displays the scoreboard and a logo
     * @param scoreboard final scoreboard sorted by the number of victory points
     */
    @Override
    public void endGame(ArrayList<PlayerPoints> scoreboard) {
        PlayerPoints winner = scoreboard.get(0);
        if(winner.getPlayer().equals(clientManager.getNickname()))
            out.println(GREEN+winLogo+RESET+"\n                You had: "+winner.getVictoryPoints()+" victory points.");
        else {
            for(PlayerPoints p:scoreboard)
                if(p.getPlayer().equals(clientManager.getNickname()))
                    out.println(RED + lostLogo + RESET + "\n                 You had: " + p.getVictoryPoints() + " victory points.");
        }
        out.println(PURPLE+"                      --SCOREBOARD--"+RESET);
        out.println("               \tNickname\t\tVictory Points");
        for(int i=0;i<scoreboard.size();i++)
            switch(i) {
                case 0: out.println(YELLOW+"             "+(i+1)+"\t"+scoreboard.get(i).getPlayer()+"\t\t\t"+scoreboard.get(i).getVictoryPoints()+RESET);
                    break;
                case 1: out.println(WHITE+"             "+(i+1)+"\t"+scoreboard.get(i).getPlayer()+"\t\t\t"+scoreboard.get(i).getVictoryPoints()+RESET);
                    break;
                case 2: out.println(RED+"             "+(i+1)+"\t"+scoreboard.get(i).getPlayer()+"\t\t\t"+scoreboard.get(i).getVictoryPoints()+RESET);
                    break;
                default: out.println("             "+(i+1)+"\t"+scoreboard.get(i).getPlayer()+"\t\t\t"+scoreboard.get(i).getVictoryPoints());
            }
        out.println();
    }

    /**
     * generic method used to print a message
     * @param msg String that needs to be printed
     */
    @Override
    public void printMsg(String msg) {
        out.println(YELLOW + msg + RESET);
    }

    /**
     * used to clear the cli window.
     */
    @Override
    public void clearView() {
        out.print("\033[H\033[2J");
        out.flush();
    }

}