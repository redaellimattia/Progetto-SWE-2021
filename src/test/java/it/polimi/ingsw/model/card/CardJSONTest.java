package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckShop;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CardJSONTest {
    @Test
    public void testCard() {
        ResourceCount cost = new ResourceCount(0,0,0,2,0);
        ResourceCount prodInput = new ResourceCount(1,0,0,0,0);
        ResourceCount prodOutput = new ResourceCount(0,0,0,0,1);
        DevelopmentCard card = new DevelopmentCard(1, 1, cost, new Production(prodInput, prodOutput), 1, CardColour.GREEN);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        ArrayList<Card> deck = new ArrayList<Card>();
        deck.add(card);
        String message = gson.toJson(deck);
        System.out.println(message);
    }

    @Test
    public void importCards() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        String json = "{\n" +
                "  \"cardsDeck\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"victoryPoints\": 1,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 2,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"GREEN\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"victoryPoints\": 3,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 3,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 2,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"PURPLE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"victoryPoints\": 3,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 3,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 2,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"BLUE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"victoryPoints\": 3,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 3,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 2,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"YELLOW\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"victoryPoints\": 4,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 2,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 2,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 2,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"GREEN\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"victoryPoints\": 4,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 2,\n" +
                "        \"servants\": 2,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 2,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"PURPLE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 7,\n" +
                "      \"victoryPoints\": 4,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 2,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 2,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 2,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"BLUE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 8,\n" +
                "      \"victoryPoints\": 4,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 2,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 2,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 2,\n" +
                "          \"faith\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"YELLOW\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 9,\n" +
                "      \"victoryPoints\": 5,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 4,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 2\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"GREEN\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10,\n" +
                "      \"victoryPoints\": 5,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 4,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 2\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"PURPLE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 11,\n" +
                "      \"victoryPoints\": 5,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 4,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 2\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"BLUE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 12,\n" +
                "      \"victoryPoints\": 1,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 2,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 1,\n" +
                "      \"colour\": \"PURPLE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 13,\n" +
                "      \"victoryPoints\": 5,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 4,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 2\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"YELLOW\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 14,\n" +
                "      \"victoryPoints\": 6,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 2,\n" +
                "        \"shields\": 3,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 3,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"GREEN\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 15,\n" +
                "      \"victoryPoints\": 6,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 2,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 3,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 1,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 3,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"PURPLE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 16,\n" +
                "      \"victoryPoints\": 6,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 3,\n" +
                "        \"rocks\": 2,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 1,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 3,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"BLUE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 17,\n" +
                "      \"victoryPoints\": 6,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 3,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 2,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 1,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 1,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 3,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"YELLOW\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 18,\n" +
                "      \"victoryPoints\": 7,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 0,\n" +
                "        \"shields\": 5,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 2,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 2,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 2\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"GREEN\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 19,\n" +
                "      \"victoryPoints\": 7,\n" +
                "      \"cost\": {\n" +
                "        \"coins\": 0,\n" +
                "        \"rocks\": 0,\n" +
                "        \"servants\": 5,\n" +
                "        \"shields\": 0,\n" +
                "        \"faith\": 0\n" +
                "      },\n" +
                "      \"productionPower\": {\n" +
                "        \"input\": {\n" +
                "          \"coins\": 0,\n" +
                "          \"rocks\": 2,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 0\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"coins\": 2,\n" +
                "          \"rocks\": 0,\n" +
                "          \"servants\": 0,\n" +
                "          \"shields\": 0,\n" +
                "          \"faith\": 2\n" +
                "        }\n" +
                "      },\n" +
                "      \"level\": 2,\n" +
                "      \"colour\": \"PURPLE\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        DeckShop fromJson = gson.fromJson(json, DeckShop.class);
        System.out.println(fromJson.getDeck().size());
        for (DevelopmentCard d: fromJson.getDeck()) {
            System.out.println("Id " + d.getId() + " Colore: " + d.getColour());
        }
    }
}
