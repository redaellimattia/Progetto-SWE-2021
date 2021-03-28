package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

public class Storage {
    private CounterTop firstRow;
    private CounterTop secondRow;
    private CounterTop thirdRow;

    public CounterTop getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(CounterTop firstRow) throws IllegalArgumentException{
        if(firstRow.getContent() > 1 ){
            throw new IllegalArgumentException();
        }
        this.firstRow = firstRow;
    }

    public CounterTop getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(CounterTop secondRow) throws IllegalArgumentException{
        if(secondRow.getContent() > 2 ){
            throw new IllegalArgumentException();
        }
        this.secondRow = secondRow;
    }

    public CounterTop getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(CounterTop thirdRow) throws IllegalArgumentException{
        if(thirdRow.getContent() > 3){
            throw new IllegalArgumentException();
        }
        this.thirdRow = thirdRow;
    }

    public ResourceCount readStorage(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        readResource(firstRow.getResourceType(),firstRow.getContent(),count); //Reading storage first row
        readResource(secondRow.getResourceType(),secondRow.getContent(),count); //Reading storage second row
        readResource(thirdRow.getResourceType(),thirdRow.getContent(),count);//Reading storage third row

        return count;
    }

    private void readResource(Resource res,int addNum,ResourceCount count){
        switch (res){
            case COIN: count.addResources(addNum,0,0,0); //If res is COIN then add (addNum) values to count
                break;
            case ROCK: count.addResources(0,addNum,0,0); //If res is ROCK then add (addNum) values to count
                break;
            case SERVANT: count.addResources(0,0,addNum,0); //If res is SERVANT then add (addNum) values to count
                break;
            case SHIELD: count.addResources(0,0,0,addNum); //If res is SHIELD then add (addNum) values to count
                break;
            case FAITH:
                break;
        }
    }
}
