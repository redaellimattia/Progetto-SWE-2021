package it.polimi.ingsw.model.enumeration;

import it.polimi.ingsw.model.ResourceCount;

public enum Resource {
    COIN{
        @Override
        public void add(ResourceCount count,int n){
            count.addCoins(n);
        }
        @Override
        public void remove(ResourceCount count,int n){
            count.removeCoins(n);
        }
        @Override
        public int get(ResourceCount count){return count.getCoins();}
    },
    ROCK{
        @Override
        public void add(ResourceCount count,int n){
            count.addRocks(n);
        }
        @Override
        public void remove(ResourceCount count,int n){
            count.removeRocks(n);
        }
        @Override
        public int get(ResourceCount count){return count.getRocks();}
    },
    SHIELD{
        @Override
        public void add(ResourceCount count,int n){
            count.addShields(n);
        }
        @Override
        public void remove(ResourceCount count,int n){
            count.removeRocks(n);
        }
        @Override
        public int get(ResourceCount count){return count.getShields();}
    },
    SERVANT{
        @Override
        public void add(ResourceCount count,int n){
            count.addServants(n);
        }
        @Override
        public void remove(ResourceCount count,int n){
            count.removeRocks(n);
        }
        @Override
        public int get(ResourceCount count){return count.getServants();}
    },
    FAITH {
        @Override
        public void add(ResourceCount count, int n) {

        }
        @Override
        public void remove(ResourceCount count, int n) {

        }
        @Override
        public int get(ResourceCount count){return 0;}
    };

    public abstract void add(ResourceCount count,int n);
    public abstract void remove(ResourceCount count,int n);
    public abstract int get(ResourceCount count);
}
