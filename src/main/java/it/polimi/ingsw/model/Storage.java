package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;


public class Storage {
    private CounterTop firstRow;
    private CounterTop secondRow;
    private CounterTop thirdRow;

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


    //SETTERS
    /**
     *
     * @param firstRow new first row to put as storage.firstRow
     * @throws IllegalArgumentException if the passed object has o.getContent > 1 i can't put it as firstRow
     */
    public void setFirstRow(CounterTop firstRow) throws IllegalArgumentException{
        if(firstRow.getContent() > 1 ){
            throw new IllegalArgumentException();
        }
        this.firstRow = firstRow;
    }

    /**
     *
     * @param secondRow new second row to put as storage.secondRow
     * @throws IllegalArgumentException if the passed object has o.getContent > 2 i can't put it as secondRow
     */
    public void setSecondRow(CounterTop secondRow) throws IllegalArgumentException{
        if(secondRow.getContent() > 2 ){
            throw new IllegalArgumentException();
        }
        this.secondRow = secondRow;
    }

    /**
     *
     * @param thirdRow new third row to put as storage.thirdRow
     * @throws IllegalArgumentException if the passed object has o.getContent > 3 i can't put it as secondRow
     */
    public void setThirdRow(CounterTop thirdRow) throws IllegalArgumentException{
        if(thirdRow.getContent() > 3){
            throw new IllegalArgumentException();
        }
        this.thirdRow = thirdRow;
    }



    /**
     *
     * @param n number of elements to add at firstRow.content
     * @return the number of elements that don't fit in the firstRow
     */
    //METHODS THAT ADD TO THE COUNTERTOP AND RETURNS HOW MANY ELEMENTS ARE BEING LEFT FOR NO SPACE;
    public int addToFirstRow(int n){
        if(firstRow.getContent() == 0 && n == 1){
            firstRow.addContent(n);
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
        int i = 0;
        for(i = 0; i<n && secondRow.getContent()<2; i++)
                secondRow.addContent(1);
        return n-i;
    }

    /**
     *
     * @param n number of elements to add at thirdRow.content
     * @return the number of elements that don't fit in the thirdRow
     */
    public int addToThirdRow(int n){
        int i = 0;
        for(i = 0; i<n && thirdRow.getContent()<3; i++)
                thirdRow.addContent(1);

        return n-i;
    }


    /**
     *
     * @param to where i want to put the "from" row
     * @param from the row from which the swap is started
     * @return the result of the swap (true/false)
     */
    //CHECKS IF ITS POSSIBLE TO SWAP THE SELECTED COUNTERTOPS, AND IF SO IT DOES IT;
    public boolean swapRows(int to, int from){
        ArrayList<CounterTop> supportShelves = getShelvesArray();
        if(supportShelves.get(to-1).getContent() <= from){
            swap(from-1,to-1);
            return true;
        }
        return false;
    }


    /**
     *
     * @param from the row from which the swap is started
     * @param to where i want to put the "from" row
     */
    //METHOD THAT SWAP TWO SHELVES, NEEDED CHECKS ARE MADE BY THE METHOD WHO INVOKES THIS ONE
    public void swap(int from, int to){
        ArrayList<CounterTop> supportShelves = getShelvesArray();
        Collections.swap(supportShelves,from,to);

        setFirstRow(supportShelves.get(0));
        setSecondRow(supportShelves.get(1));
        setThirdRow(supportShelves.get(2));
    }



    /**
     *
     * @return a ResourceCount containing all the resources contained in the storage
     */
    //RETURN IN A RESOURCECOUNT THE TOTAL OF THE RESOURCES PRESENT IN THE STORAGE;
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
    //RETURN AN ARRAYLIST WITH THE SHELVES OF STORAGE;
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
    //CHECK IF EVERY COUNTERTOP OF THE STORAGE HAS A DIFFERENT RESOURCETYPE
    public boolean checkShelves(){
        return !this.getFirstRow().getResourceType().equals(this.getSecondRow().getResourceType()) &&
                !this.getFirstRow().getResourceType().equals(this.getThirdRow().getResourceType()) &&
                !this.getSecondRow().getResourceType().equals(this.getThirdRow().getResourceType());
    }
}
