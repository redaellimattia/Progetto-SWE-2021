package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Cli implements View {
    public static final String RESET = CliColours.RESET.toString();
    public static final String BLACK = CliColours.BLACK.toString();
    public static final String RED = CliColours.RED.toString();
    public static final String GREEN = CliColours.GREEN.toString();
    public static final String YELLOW = CliColours.YELLOW.toString();
    public static final String BLUE = CliColours.BLUE.toString();
    public static final String PURPLE = CliColours.PURPLE.toString();
    public static final String CYAN = CliColours.CYAN.toString();
    public static final String WHITE = CliColours.WHITE.toString();

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

    private String readLine(){
        return in.next();
    }


    @Override
    public void start() {
        printLogo();
    }

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


    @Override
    public void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        out.println("Now you can choose the game to join! ");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        long serverThreadID = askForServerID(availableGameLobbies);

        clientManager.joinGame(serverThreadID);
        clearView();
    }

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
                chosenId = Long.parseLong(chosen);
            } while (!id.contains(chosenId));
            out.println("The choosen Lobby ID is : " + chosenId + "\n" +
                    "Do you want to confirm? Press Y (confirm) / N (deny)");
            input = readLine();
        } while (!input.equalsIgnoreCase("Y"));

        return chosenId;
    }

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

    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources) {
        out.println("The game is about to start, choose your initial setup!\n");
        ArrayList<Resource> resources = new ArrayList<>();
        if (numberOfResources != 0) {
            resources = askResources(numberOfResources);
        }
        clearView();
        ArrayList<LeaderCard> chosenLeaders = askLeaders(leaders);
        clientManager.preGameChoice(resources, chosenLeaders);
    }

    private ArrayList<LeaderCard> askLeaders(ArrayList<LeaderCard> leaders) {
        ArrayList<LeaderCard> leadersChosen = new ArrayList<>();
        int counter = 2;
        String chosenID;
        int chosen;
        ArrayList<Integer> id = new ArrayList<>();
        out.println("You can choose two leaders between these four:");
        out.println("-----------------");
        printLeaders(leaders);
        for (LeaderCard l : leaders)
            id.add(l.getId());
        do {
            out.print("You still have " + counter + " leaders to choose.\n");

            do {
                out.println("Insert the ID of the choosen leader: ");
                chosenID = readLine();
                chosen = Integer.parseInt(chosenID);
            } while (!id.contains(chosen));

            for (LeaderCard leader : leaders)
                if (leader.getId() == chosen)
                    leadersChosen.add(leader);

            counter--;
        } while (counter != 0);
        return leadersChosen;
    }

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
                out.println("This card is in game!");
            //SPECIALABILITY PRINT:
            SpecialAbility specialAbility = l.getSpecialAbility();
            out.println(specialAbility.toString());
            out.println("-----------------");
        }
    }

    private ArrayList<Resource> askResources(int numberOfResources) {
        String resource;
        ArrayList<Resource> resources = new ArrayList<>();
        out.println("You have the right to choose " + numberOfResources + " resource to start the game with!");
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

    @Override
    public void yourTurn() {
        clearView();
        out.println(RED + "|||It's your turn|||" + RESET);
        out.println("This is the actual state of your board: ");
        printPlayer(clientManager.getNickname());
        chooseAction();
    }

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
                out.println("DISCARD A LEADER: press D \n");
                if(clientManager.canPlayLeader())
                    out.println("PLAY A LEADER: press L\n");
            }

            //Reorganize
            out.println("REORGANIZE RESOURCES: press R");

            if (clientManager.isMainActionDone())
                out.println(RED + "PRESS Q TO PASS YOUR TURN" + RESET);

            out.println("Choose one of the above to continue the game: ");
            input = readLine();
        }while(!input.equalsIgnoreCase("b")&& !input.equalsIgnoreCase("m")&&!input.equalsIgnoreCase("p")
                &&!input.equalsIgnoreCase("l")&&!input.equalsIgnoreCase("d")&&!input.equalsIgnoreCase("o") &&
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
                            playLeader(clientManager.getNotPlayedLeaders());
                        else {
                            if (input.equalsIgnoreCase("d"))
                                discardLeader(clientManager.getNotPlayedLeaders());
                            else {
                                if (input.equalsIgnoreCase("o"))
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
    @Override
    public void endTurn(){
        clientManager.endTurn();
    }

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
        ResourceCount storagePayment = new ResourceCount(0,0,0,0,0);
        ResourceCount chestPayment = new ResourceCount(0,0,0,0,0);
        cost.sumCounts(card.getCost());
        askPayment(cost,storagePayment,chestPayment);
        int position=-1;
        do{
            out.println("Now insert the position of the deck of which you want to put the card on top: ");
            input = readLine();
            try{position = Integer.parseInt(input);}catch(NumberFormatException e) {position = -1;}
        }while((position <1 || position >3) || clientManager.positionPossible(position,card.getLevel()));


        clientManager.buyCard(storagePayment,chestPayment,id,position);
    }
    private void askPayment(ResourceCount cost, ResourceCount storage, ResourceCount chest){
        String input;
        do{
            out.println("Press S to start payment from the storage, C from the chest: ");
            input = readLine();
        }while(!input.equalsIgnoreCase("s")&&!input.equalsIgnoreCase("C"));

        if(input.equalsIgnoreCase("s")){
            storage= askStoragePayment(cost,false);
            if(ResourceCount.resCountToInt(cost) !=0)
                chest = askChestPayment(cost,true);
        }
        else{
            chest = askChestPayment(cost,false);
            if(ResourceCount.resCountToInt(cost) !=0)
                storage = askStoragePayment(cost,true);
        }
    }
    private ResourceCount askStoragePayment(ResourceCount cost,boolean needToCover){
        String input;
        out.println("To buy this card you need to pay: " + cost);
        out.println("Now insert the resources you want to pay with FROM THE STORAGE: ");
        ResourceCount temporaryStorage = clientManager.getThisClientDashboard().getStorage().readStorage();
        ResourceCount storagePayment = new ResourceCount(0,0,0,0,0);
        addResourceToPayment(temporaryStorage,storagePayment,cost,needToCover);
        return storagePayment;
    }

    private ResourceCount askChestPayment(ResourceCount cost,boolean needToCover){
        out.println("To buy this card you need to pay: " + cost);
        out.println("Now insert the resources you want to pay with FROM THE CHEST: ");
        ResourceCount temporaryChest = clientManager.getThisClientDashboard().getChest();
        ResourceCount chestPayment = new ResourceCount(0,0,0,0,0);
        addResourceToPayment(temporaryChest,chestPayment,cost,needToCover);
        return chestPayment;
    }

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
        int pos;
        int maxCount;
        MarketMarble[] marbles;
        ArrayList<AtomicMarketAction> choices;
        do {
            out.println("Type \"row\" if you want to select a row; \"col\" if you want to select a column");
            input = readLine();
        } while(!input.equalsIgnoreCase("row")  && !input.equalsIgnoreCase("col"));
        if(input.equalsIgnoreCase("row")) {
            do {
                out.println("Chosen row: ");
                input = readLine();
                try {pos = Integer.parseInt(input);} catch(NumberFormatException e) {pos = -1;}
            } while(pos < 1 || pos > 3);
            maxCount = 4;
        }
        else {
            do {
                out.println("Chosen column: ");
                input = readLine();
                try {pos = Integer.parseInt(input);} catch(NumberFormatException e) {pos = -1;}
            } while(pos < 1 || pos > 4);
            maxCount = 3;
        }
        //marbles = getMarbles
        for (int i = 1; i < maxCount + 1 ; i++) {

        }
    }

    @Override
    public void startProduction(){}

    @Override
    public void playLeader(ArrayList<LeaderCard> playableLeaders){
        ArrayList<Integer> id = new ArrayList<>();
        int ID;
        String input;
        for (LeaderCard l : playableLeaders)
            if(!l.isInGame()&& clientManager.isRequirementPossible(l.getRequirement())) {
                id.add(l.getId());
            }

        out.println("Choose the ID of the leader that you want to play: ");
        do{
            out.println("-----------------");
            printLeaders(playableLeaders);
            do {
                out.println("Insert the ID of the chosen leader: ");
                input = readLine();
                ID = Integer.parseInt(input);
            } while (!id.contains(ID));
            for(LeaderCard l:playableLeaders)
                if(l.getId()==ID) {
                    clientManager.playLeader(l);
                    playableLeaders.remove(l);
                    break;
                }

            if(playableLeaders.size()>0) {
                out.println("You still have "+playableLeaders.size()+" leader card in your hand!");
                out.println("\nPress esc to exit, another key to play another leader: ");
                input = readLine();
            }
            else
                input = "esc";
        }while(!input.equals("esc"));
        chooseAction();
    }


    @Override
    public void discardLeader(ArrayList<LeaderCard> discardableLeaders){
        ArrayList<Integer> id = new ArrayList<>();
        int ID;
        String input;
        for (LeaderCard l : discardableLeaders)
            if(!l.isInGame()) {
                id.add(l.getId());
            }

        out.println("Choose the ID of the leader that you want to discard: ");
        do{
            out.println("-----------------");
            printLeaders(discardableLeaders);
            do {
                out.println("Insert the ID of the chosen leader: ");
                input = readLine();
                ID = Integer.parseInt(input);
            } while (!id.contains(ID));
            for(LeaderCard l:discardableLeaders)
                if(l.getId()==ID) {
                    clientManager.discardLeader(l);
                    discardableLeaders.remove(l);
                    break;
                }
            if(discardableLeaders.size()>0) {
                out.println("You still have "+discardableLeaders.size()+" leader card in your hand!");
                out.println("\nPress esc to exit, another key to discard another leader: ");
                input = readLine();
            }
            else
                input = "esc";
        }while(!input.equals("esc"));
    }
    @Override
    public void organizeResources(){}



    private void printPlayer(String nickname) {
        PlayerDashboard player = clientManager.getGameStatus().getClientDashboard(nickname);
        if(nickname.equals(clientManager.getNickname()))
            out.println(BLUE+"~~YOUR DASHBOARD"+"~~"+RESET);
        else
            out.println(BLUE+"~~"+nickname+"'S DASHBOARD"+"~~"+RESET);
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

    void printDevCard(DevelopmentCard card){
        out.print("ID: " + card.getId() +"\n" +
                "Level: " + card.getLevel() +"\n" +
                "Victory Points: " + card.getVictoryPoints() + "\n");
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
                "Production Power: " + "\n" +
                "Cost: " + card.getProductionPower().getInput().toString() + "\n" +
                "Output: " + card.getProductionPower().getOutput().toString());
    }

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

    private void printPlayerLeaderCards(ArrayList<LeaderCard> leaderCards){
        out.println(PURPLE+"--LEADER CARDS--"+RESET);
        if(leaderCards.size()!=0)
            printLeaders(leaderCards);
        else
            out.println("No leader cards in game!");
    }

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

    private void printArrayDeposits(ArrayList<CounterTop> arrayDeposits){
        out.println(PURPLE+"--SPECIAL ABILITY DEPOSITS--"+RESET);
        for(CounterTop counterTop:arrayDeposits)
            printCounterTop(counterTop);
    }

    private void printBufferProduction(ResourceCount bufferProduction){
        out.println(PURPLE+"--PRODUCTION RESOURCES--"+RESET);
        out.println(bufferProduction);
    }

    private void printChest(ResourceCount chest){
        out.println(PURPLE+"--CHEST--"+RESET);
        if(ResourceCount.resCountToInt(chest)>0)
            out.println(chest);
        else
            out.println("EMPTY");
    }

    private void printStorage(Storage storage){
        out.println(PURPLE+"--STORAGE--"+RESET);
        printCounterTop(storage.getFirstRow());
        printCounterTop(storage.getSecondRow());
        printCounterTop(storage.getThirdRow());
    }

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

    @Override
    public void endGame(boolean lorenzoWin, int playerPoints) {
        if(lorenzoWin)
            out.println(RED+lostLogo+RESET+"\n                 You had: "+playerPoints+" victory points.");
        else
            out.println(GREEN+winLogo+RESET+"\n                You had: "+playerPoints+" victory points.");
    }

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

    @Override
    public void printMsg(String msg) {
        out.println(YELLOW + msg + RESET);
    }

    @Override
    public void clearView() {
        out.print("\033[H\033[2J");
        out.flush();
    }

}