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
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private final PrintStream out;

    private final ClientManager clientManager;

    public Cli(ClientManager clientManager) {
        this.out = new PrintStream(System.out, true);
        this.clientManager = clientManager;
    }

    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     */
    public String readLine() {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        Thread inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            try {
                input = futureTask.get();
            } catch (ExecutionException e) {
                out.println(e.getMessage());
            }
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    @Override
    public void start() {
        printLogo();
    }

    public void printLogo() {
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
                "  \\/_/ /_/   \\/_____/   \\/_/ \\/_/   \\/_/\\/_/   \\/_/   \\/_____/   \\/_____/   \\/_/\\/_/   \\/_/ \\/_/   \\/_____/   \\/_____/ \n" +
                "Welcome to Masters Of Renaissance Board Game Digital Version (patent pending) created by Mattia Redaelli, Luca Rondini, Gabriele Rivi. \n" +
                "Have Fun playing the game! \n";
        //SITO PER GENERARE ASCII ART
        //https://patorjk.com/software/taag/#p=testall&f=Elite&t=Master%20of%20Renaissance%20
        String logo1 = PURPLE + "\n" +
                "• ▌ ▄ ·.  ▄▄▄· .▄▄ · ▄▄▄▄▄▄▄▄ .▄▄▄            ·▄▄▄    ▄▄▄  ▄▄▄ . ▐ ▄  ▄▄▄· ▪  .▄▄ · .▄▄ ·  ▄▄▄·  ▐ ▄  ▄▄· ▄▄▄ .    \n" +
                "·██ ▐███▪▐█ ▀█ ▐█ ▀. •██  ▀▄.▀·▀▄ █·    ▪     ▐▄▄·    ▀▄ █·▀▄.▀·•█▌▐█▐█ ▀█ ██ ▐█ ▀. ▐█ ▀. ▐█ ▀█ •█▌▐█▐█ ▌▪▀▄.▀·    \n" +
                "▐█ ▌▐▌▐█·▄█▀▀█ ▄▀▀▀█▄ ▐█.▪▐▀▀▪▄▐▀▀▄      ▄█▀▄ ██▪     ▐▀▀▄ ▐▀▀▪▄▐█▐▐▌▄█▀▀█ ▐█·▄▀▀▀█▄▄▀▀▀█▄▄█▀▀█ ▐█▐▐▌██ ▄▄▐▀▀▪▄    \n" +
                "██ ██▌▐█▌▐█ ▪▐▌▐█▄▪▐█ ▐█▌·▐█▄▄▌▐█•█▌    ▐█▌.▐▌██▌.    ▐█•█▌▐█▄▄▌██▐█▌▐█ ▪▐▌▐█▌▐█▄▪▐█▐█▄▪▐█▐█ ▪▐▌██▐█▌▐███▌▐█▄▄▌    \n" +
                "▀▀  █▪▀▀▀ ▀  ▀  ▀▀▀▀  ▀▀▀  ▀▀▀ .▀  ▀     ▀█▄▀▪▀▀▀     .▀  ▀ ▀▀▀ ▀▀ █▪ ▀  ▀ ▀▀▀ ▀▀▀▀  ▀▀▀▀  ▀  ▀ ▀▀ █▪·▀▀▀  ▀▀▀     \n";
        //ANSI SHADOWS
        String logo2 = CYAN + "\n" +
                "███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗      ██████╗ ███████╗    ██████╗ ███████╗███╗   ██╗ █████╗ ██╗███████╗███████╗ █████╗ ███╗   ██╗ ██████╗███████╗    \n" +
                "████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗    ██╔═══██╗██╔════╝    ██╔══██╗██╔════╝████╗  ██║██╔══██╗██║██╔════╝██╔════╝██╔══██╗████╗  ██║██╔════╝██╔════╝    \n" +
                "██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝    ██║   ██║█████╗      ██████╔╝█████╗  ██╔██╗ ██║███████║██║███████╗███████╗███████║██╔██╗ ██║██║     █████╗      \n" +
                "██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗    ██║   ██║██╔══╝      ██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║╚════██║╚════██║██╔══██║██║╚██╗██║██║     ██╔══╝      \n" +
                "██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║    ╚██████╔╝██║         ██║  ██║███████╗██║ ╚████║██║  ██║██║███████║███████║██║  ██║██║ ╚████║╚██████╗███████╗    \n" +
                "╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝     ╚═════╝ ╚═╝         ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚══════╝    \n" +
                "                                                                                                                                                                        \n"
                + RESET + "               Welcome to Masters Of Renaissance Board Game Digital Version (patent pending) created by Mattia Redaelli, Luca Rondini, Gabriele Rivi. \n" +
                "                                                        Have Fun playing the game! \n";
        out.println(logo2);
    }

    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        String input;
        if (availableGameLobbies.size() == 0) {
            do {
                out.println("There are no available lobbies, press C to create a new game: ");
                input = readLine();
            } while (!input.equalsIgnoreCase("C"));
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
                        "C: Create a new Game.");
                input = readLine();
            } while (!input.equalsIgnoreCase("J") && !input.equalsIgnoreCase("C"));

            if (input.equalsIgnoreCase("C"))
                createNewGame();
            if (input.equalsIgnoreCase("J"))
                joinExistingGame(availableGameLobbies);
        }
    }

    @Override
    public void createNewGame() {
        clearCli();
        out.println("Now you can create your own game!");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        int numberOfPlayers = askNumberOfPlayers();
        clientManager.createGame(numberOfPlayers);
        clearCli();
    }


    @Override
    public void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        out.println("Now you can choose the game to join! ");
        String nickname = askForNickname();
        clientManager.setNickname(nickname);
        long serverThreadID = askForServerID(availableGameLobbies);

        clientManager.joinGame(serverThreadID);
        clearCli();
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
        int num = 0;
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
        clearCli();
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
            Requirement requirement = l.getRequirement();
            out.println(requirement.toString());
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
                        "COINS: digit C \n" +
                        "ROCKS: digit R \n" +
                        "SHIELDS: digit SH \n" +
                        "SERVANTS: digit SE \n");
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
    public void yourTurn() {
        clearCli();
        out.println(RED + "|||Now it's your turn|||" + RESET);
        out.println("This is the actual state of your board: ");
        printPlayer(clientManager.getNickname());
        chooseAction();
    }

    public void chooseAction(){
        String input;
        boolean canDoProduction = (ResourceCount.resCountToInt(clientManager.getGameStatus().getClientDashboard(clientManager.getNickname()).getStorage().readStorage()) >= 2
                || ResourceCount.resCountToInt(clientManager.getGameStatus().getClientDashboard(clientManager.getNickname()).getChest()) >= 2);
        do {
            if (!clientManager.isMainActionDone()) {
                out.println(YELLOW + "You still have to do one of these before ending your turn: " + RESET);
                out.println("BUY A CARD : press B\n" +
                        "TAKE RESOURCES FROM MARKET: press M\n");

                if (canDoProduction)
                    out.println("USE THE PRODUCTION POWERS ON YOUR BOARD: press P");

            }
            out.println(YELLOW + "Secondary actions: " + RESET);
            int leadersInHand = 0;
            for (LeaderCard l: clientManager.getGameStatus().getClientDashboard(clientManager.getNickname()).getLeaderCards()) {
                if(!l.isInGame())
                    leadersInHand++;
            }
            if(leadersInHand >= 1) {
                out.println("PLAY A LEADER: press L\n" +
                        "DISCARD A LEADER: press D \n");
            }
            out.println("REORGANIZE RESOURCES: press O");

            if (clientManager.isMainActionDone())
                out.println(RED + "PRESS Q TO PASS YOUR TURN" + RESET);
            out.println("Choose one of the above to continue the game: ");
            input = readLine();
        }while(!input.equalsIgnoreCase("b")&& !input.equalsIgnoreCase("m")&&!input.equalsIgnoreCase("p")
                &&!input.equalsIgnoreCase("l")&&!input.equalsIgnoreCase("d")&&!input.equalsIgnoreCase("o") &&
                !input.equalsIgnoreCase("q"));

        //CASE WHEN I PRESS Q BUT I HAVE NOT THE POSSIBILITY TO PASS THE TURN
        if(input.equalsIgnoreCase("q") && !clientManager.isMainActionDone())
            chooseAction();
        if(input.equalsIgnoreCase("q") && clientManager.isMainActionDone())
            endTurn();
        if(input.equalsIgnoreCase("b") && !clientManager.isMainActionDone())
            buyCard();
        if(input.equalsIgnoreCase("m") && !clientManager.isMainActionDone())
            takeResourcesFromMarket();
        if(input.equalsIgnoreCase("p") && !clientManager.isMainActionDone() && canDoProduction)
            startProduction();
        if(input.equalsIgnoreCase("l"))
            playLeader();
        if(input.equalsIgnoreCase("d"))
            discardLeader();
        if(input.equalsIgnoreCase("o"))
            organizeResources();
    }

    @Override
    public void waitingForTurn() {
        String input;
        clearCli();
        out.println("\nWait for the other players to play their turn, in the meantime you can peek around the board to keep updated.");
        do {
            out.println("Choose what you want to see: \n" +
                    "Market: digit M \n" +
                    "Shop: digit S \n" +
                    "Players: digit P \n");
            input = readLine();
        } while (!input.equalsIgnoreCase("m") && !input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("p"));
        if (input.equalsIgnoreCase("m")) {
            clearCli();
            printMarket();
            do {
                out.println("Digit \"esc\" to go back.");
                input = readLine();
            } while (!input.equalsIgnoreCase("esc"));
            waitingForTurn();
        }
        if (input.equalsIgnoreCase("s")) {
            clearCli();
            printShop();
            do {
                out.println("Digit \"esc\" to go back.");
                input = readLine();
            } while (!input.equalsIgnoreCase("esc"));
            waitingForTurn();
        }
        if (input.equalsIgnoreCase("p")) {
            clearCli();
            ArrayList<String> nicknames = new ArrayList<>();
            for (PlayerDashboard p : clientManager.getGameStatus().getPlayers()) {
                nicknames.add(p.getNickname());
            }
            do {
                out.println("Choose which player you want to see: ");
                for (String s : nicknames) {
                    out.print("|" + s + "|\t");
                }
                input = readLine();
            } while (!nicknames.contains(input));
            printPlayer(input);
            do {
                out.println("Digit \"esc\" to go back.");
                input = readLine();
            } while (!input.equalsIgnoreCase("esc"));
            waitingForTurn();
        }
    }

    @Override
    public void endTurn(){}
    @Override
    public void buyCard(){}
    @Override
    public void takeResourcesFromMarket(){}
    @Override
    public void startProduction(){}
    @Override
    public void playLeader(){}
    @Override
    public void discardLeader(){}
    @Override
    public void organizeResources(){}



    private void printPlayer(String nickname) {
        clearCli();
        PlayerDashboard player = clientManager.getGameStatus().getClientDashboard(nickname);

    }

    private void printMarket() {
        MarketDashboard market = clientManager.getGameStatus().getMarket();
        MarketMarble[][] grid = market.getStructure();
        out.println(YELLOW + "MARKET DASHBOARD " + RESET);
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

    private void printShop() {
        Deck[][] shop = clientManager.getGameStatus().getShop().getGrid();
        ArrayList<Integer> firstCardID = new ArrayList<>();
        out.println(YELLOW + "SHOP GRID" + RESET);
        for (int i = 0; i < 3; i++) {
            out.print("Level: " + shop[i][0].getFirst().getLevel() + "\t");
            for (int j = 0; j < 4; j++) {
                firstCardID.add(shop[i][j].getFirst().getId());
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
        String input;
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
            } while (!firstCardID.contains(id));
            DevelopmentCard card = clientManager.getShopCardByID(id);
            printDevCard(card);
            out.println("\nPress esc to exit Development Card details lookup, another key to continue looking at Cards: ");
            input = readLine();
        }while(!input.equals("esc"));
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
    public void endGame(boolean lorenzoWin, PlayerPoints playerPoints) {

    }

    @Override
    public void endGame(ArrayList<PlayerPoints> scoreboard) {

    }

    @Override
    public void printMsg(String msg) {
        out.println(YELLOW + msg + RESET);
    }

    private void clearCli() {
        out.print("\033[H\033[2J");
        out.flush();
    }

}