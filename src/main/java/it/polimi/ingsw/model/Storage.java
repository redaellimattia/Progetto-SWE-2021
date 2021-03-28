package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

public class Storage {
    private CounterTop firstRow;
    private CounterTop[] secondRow = new CounterTop[2];
    private CounterTop[] thirdRow = new CounterTop[3];

    public CounterTop getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(CounterTop firstRow){
        this.firstRow = firstRow;
    }

    public CounterTop[] getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(CounterTop[] secondRow) throws IllegalArgumentException{
        if(secondRow.length > 2 ){
            throw new IllegalArgumentException();
        }
        this.secondRow = secondRow;
    }

    public CounterTop[] getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(CounterTop[] thirdRow) throws IllegalArgumentException{
        if(thirdRow.length > 3){
            throw new IllegalArgumentException();
        }
        this.thirdRow = thirdRow;
    }

    public ResourceCount readStorage(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        readResource(firstRow,1,count); //Reading storage first row
        readResource(secondRow[0],calcAddNum(secondRow),count); //Reading storage second row
        readResource(thirdRow[0],calcAddNum(thirdRow),count);//Reading storage third row

        return count;
    }

    private void readResource(CounterTop res,int addNum,ResourceCount count){
        Resource type = res.getResourceType();
        switch (type){
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

    private int calcAddNum(CounterTop resVet[]){
        int addNum = 0;
        for(int i=0;i<resVet.length;i++)
            if(resVet[i]!=null)
                addNum++;
        return addNum;
    }
}
