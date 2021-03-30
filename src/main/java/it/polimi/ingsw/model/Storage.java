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
        firstRow.getResourceType().add(count,firstRow.getContent()); //Reading storage first row
        secondRow.getResourceType().add(count,secondRow.getContent());//Reading storage second row
        thirdRow.getResourceType().add(count,thirdRow.getContent()); //Reading storage third row

        return count;
    }
}
