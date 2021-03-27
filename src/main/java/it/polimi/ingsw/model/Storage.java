package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

public class Storage {
    private Resource firstRow;
    private Resource[] secondRow = new Resource[2];
    private Resource[] thirdRow = new Resource[3];

    public Resource getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Resource firstRow){
        this.firstRow = firstRow;
    }

    public Resource[] getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(Resource[] secondRow) throws IllegalArgumentException{
        if(secondRow.length > 2 ){
            throw new IllegalArgumentException();
        }
        this.secondRow = secondRow;
    }

    public Resource[] getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(Resource[] thirdRow) throws IllegalArgumentException{
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

    private int calcAddNum(Resource resVet[]){
        int addNum = 0;
        for(int i=0;i<resVet.length;i++)
            if(resVet[i]!=null)
                addNum++;
        return addNum;
    }
}
