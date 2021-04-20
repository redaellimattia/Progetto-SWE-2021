package it.polimi.ingsw.model.enumeration;

public enum MarbleColour {
    WHITE {
        @Override
        public Resource convertToResource() {
            return null;
        }
    },
    RED {
        @Override
        public Resource convertToResource() {
            return Resource.FAITH;
        }
    },
    YELLOW {
        @Override
        public Resource convertToResource() {
            return Resource.COIN;
        }
    },
    GREY {
        @Override
        public Resource convertToResource() {
            return Resource.ROCK;
        }
    },
    PURPLE {
        @Override
        public Resource convertToResource() {
            return Resource.SERVANT;
        }
    },
    BLUE {
        @Override
        public Resource convertToResource() {
            return Resource.SHIELD;
        }
    };

    public abstract Resource convertToResource();
}
