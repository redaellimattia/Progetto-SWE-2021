package it.polimi.ingsw.model.enumeration;

import it.polimi.ingsw.model.card.ColourCount;

public enum CardColour {
    GREEN{
        @Override
        public void add(ColourCount count,int n){
            count.addGreen(n);
        }
        @Override
        public void remove(ColourCount count,int n){
            count.removeGreen(n);
        }
        @Override
        public int get(ColourCount count){return count.getGreen();}
        @Override
        public int getColumn(){return 0;}
    },
    BLUE{
        @Override
        public void add(ColourCount count,int n){
            count.addBlue(n);
        }
        @Override
        public void remove(ColourCount count,int n){
            count.removeBlue(n);
        }
        @Override
        public int get(ColourCount count){return count.getBlue();}
        @Override
        public int getColumn(){return 1;}
    },
    YELLOW{
        @Override
        public void add(ColourCount count,int n){
            count.addYellow(n);
        }
        @Override
        public void remove(ColourCount count,int n){
            count.removeYellow(n);
        }
        @Override
        public int get(ColourCount count){return count.getYellow();}
        @Override
        public int getColumn(){return 2;}
    },
    PURPLE{
        @Override
        public void add(ColourCount count,int n){
            count.addPurple(n);
        }
        @Override
        public void remove(ColourCount count,int n){
            count.removePurple(n);
        }
        @Override
        public int get(ColourCount count){return count.getPurple();}
        @Override
        public int getColumn(){return 3;}
    };

    public abstract void add(ColourCount count,int n);
    public abstract void remove(ColourCount count,int n);
    public abstract int get(ColourCount count);
    public abstract int getColumn();
}
