package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;

import java.util.ArrayList;
import java.util.Collections;


public class Storage {
    private CounterTop firstRow;
    private CounterTop secondRow;
    private CounterTop thirdRow;
    private transient StorageObserver observer;
    private transient PlayerDashboard player;

    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Storage
     */
    public void addObserver(StorageObserver observer) {
        this.observer = observer;
    }

    /**
     * Constructor of the object
     * @param firstRow storage.firstRow
     * @param secondRow storage.secondRow
     * @param thirdRow storage.thirdRow
     */
    public Storage(CounterTop firstRow, CounterTop secondRow, CounterTop thirdRow) {
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.thirdRow = thirdRow;
    }

    //GETTERS
    /**
     *
     * @return storage.firstRow
     */
    public CounterTop getFirstRow() {
        return firstRow;
    }

    /**
     *
     * @return storage.secondRow
     */
    public CounterTop getSecondRow() {
        return secondRow;
    }

    /**
     *
     * @return storage.thirdRow
     */
    public CounterTop getThirdRow() {
        return thirdRow;
    }

    public void setPlayer(PlayerDashboard p){this.player = p;}
    //SETTERS
    /**
     *
     * @param firstRow new first row to put as storage.firstRow
     * @throws IllegalArgumentException if the passed object has o.getContent > 1 i can't put it as firstRow
     */
    public void setFirstRow(CounterTop firstRow) throws CounterTopOverloadException{
        if(firstRow.getContent() > 1 )
            throw new CounterTopOverloadException(player);
        this.firstRow = firstRow;
        if(observer!=null)
            observer.updateFirstRow(this.firstRow);
    }

    /**
     *
     * @param secondRow new second row to put as storage.secondRow
     * @throws IllegalArgumentException if the passed object has o.getContent > 2 i can't put it as secondRow
     */
    public void setSecondRow(CounterTop secondRow) throws CounterTopOverloadException {
        if(secondRow.getContent() > 2 )
            throw new CounterTopOverloadException(player);
        this.secondRow = secondRow;
        if(observer!=null)
            observer.updateSecondRow(this.secondRow);
    }

    /**
     *
     * @param thirdRow new third row to put as storage.thirdRow
     * @throws IllegalArgumentException if the passed object has o.getContent > 3 i can't put it as secondRow
     */
    public void setThirdRow(CounterTop thirdRow) throws CounterTopOverloadException{
        if(thirdRow.getContent() > 3)
            throw new CounterTopOverloadException(player);
        this.thirdRow = thirdRow;
        if(observer!=null)
            observer.updateThirdRow(this.thirdRow);
    }



    /**
     * METHODS THAT ADD TO THE COUNTERTOP AND RETURNS HOW MANY ELEMENTS ARE BEING LEFT FOR NO SPACE;
     * @param n number of elements to add at firstRow.content
     * @return the number of elements that don't fit in the firstRow
     */
    public int addToFirstRow(int n){
        if(firstRow.getContent() == 0 && n == 1){
            firstRow.addContent(n);
            if(observer != null) {
                observer.updateFirstRow(this.firstRow);
            }
            return 0;
        }
        return n;
    }

    /**
     *
     * @param n number of elements to add at secondRow.content
     * @return the number of elements that don't fit in the secondRow
     */
    public int addToSecondRow(int n){
        int i;
        for(i = 0; i<n && secondRow.getContent()<2; i++)
                secondRow.addContent(1);
        if((n-i)!=n && observer != null)
            observer.updateSecondRow(this.secondRow);
        return n-i;
    }

    /**
     *
     * @param n number of elements to add at thirdRow.content
     * @return the number of elements that don't fit in the thirdRow
     */
    public int addToThirdRow(int n){
        int i;
        for(i = 0; i<n && thirdRow.getContent()<3; i++)
                thirdRow.addContent(1);
        if((n-i)!=n && observer != null)
            observer.updateThirdRow(this.thirdRow);
        return n-i;
    }


    /**
     * CHECKS IF ITS POSSIBLE TO SWAP THE SELECTED COUNTERTOPS, AND IF SO IT DOES IT;
     * @param to where i want to put the "from" row
     * @param from the row from which the swap is started
     * @return the result of the swap (true/false)
     */
    public boolean swapRows(int to, int from){
        ArrayList<CounterTop> supportShelves = getShelvesArray();
        if(supportShelves.get(to-1).getContent() <= from){
            swap(from-1,to-1);
            return true;
        }
        return false;
    }


    /**
     * METHOD THAT SWAP TWO SHELVES, NEEDED CHECKS ARE MADE BY THE METHOD WHO INVOKES THIS ONE
     * @param from the row from which the swap is started
     * @param to where i want to put the "from" row
     */
    public void swap(int from, int to){
        ArrayList<CounterTop> supportShelves = getShelvesArray();
        Collections.swap(supportShelves,from,to);

        try{setFirstRow(supportShelves.get(0));
            setSecondRow(supportShelves.get(1));
            setThirdRow(supportShelves.get(2));}
        catch(CounterTopOverloadException e){

        };
    }



    /**
     *
     * @return a ResourceCount containing all the resources contained in the storage
     */
    public ResourceCount readStorage(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        firstRow.getResourceType().add(count,firstRow.getContent()); //Reading storage first row
        secondRow.getResourceType().add(count,secondRow.getContent());//Reading storage second row
        thirdRow.getResourceType().add(count,thirdRow.getContent()); //Reading storage third row
        return count;
    }



    /**
     *
     * @return an ArrayList of Countertop containing the storage's rows
     */
    public ArrayList<CounterTop> getShelvesArray(){
        ArrayList<CounterTop> array = new ArrayList<>();
        array.add(0,thirdRow);
        array.add(0,secondRow);
        array.add(0,firstRow);
        return array;
    }



    /**
     *
     * @return true if every row of the storage contains a different ResourceType from the others
     */
    public boolean checkShelves(){
        return !this.getFirstRow().getResourceType().equals(this.getSecondRow().getResourceType()) &&
                !this.getFirstRow().getResourceType().equals(this.getThirdRow().getResourceType()) &&
                !this.getSecondRow().getResourceType().equals(this.getThirdRow().getResourceType());
    }
}
