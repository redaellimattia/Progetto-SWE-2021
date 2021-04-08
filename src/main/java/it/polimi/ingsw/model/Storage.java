package it.polimi.ingsw.model;

public class Storage {
    private CounterTop firstRow;
    private CounterTop secondRow;
    private CounterTop thirdRow;

    public Storage(CounterTop firstRow, CounterTop secondRow, CounterTop thirdRow) {
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.thirdRow = thirdRow;
    }

    //GETTERS
    public CounterTop getFirstRow() {
        return firstRow;
    }
    public CounterTop getSecondRow() {
        return secondRow;
    }
    public CounterTop getThirdRow() {
        return thirdRow;
    }

    //SETTERS
    public void setFirstRow(CounterTop firstRow) throws IllegalArgumentException{
        if(firstRow.getContent() > 1 ){
            throw new IllegalArgumentException();
        }
        this.firstRow = firstRow;
    }

    public void setSecondRow(CounterTop secondRow) throws IllegalArgumentException{
        if(secondRow.getContent() > 2 ){
            throw new IllegalArgumentException();
        }
        this.secondRow = secondRow;
    }

    public void setThirdRow(CounterTop thirdRow) throws IllegalArgumentException{
        if(thirdRow.getContent() > 3){
            throw new IllegalArgumentException();
        }
        this.thirdRow = thirdRow;
    }
    //METHODS THAT ADD TO THE COUNTERTOP AND RETURNS HOW MANY ELEMENTS ARE BEING LEFT FOR NO SPACE;
    public int addToFirstRow(int n){
        if(firstRow.getContent() == 0 && n == 1){
            firstRow.addContent(n);
            return 0;
        }
        return n;
    }
    public int addToSecondRow(int n){
        int val = n-secondRow.getContent();
        if(n<=2){
            secondRow.addContent(val);
            return n - val;
        }
        return n;
    }
    public int addToThirdRow(int n){
        int val = n-thirdRow.getContent();
        if(n<=3){
            thirdRow.addContent(val);
            return n - val;
        }
        return n;
    }

    //RETURN IN A RESOURCECOUNT THE TOTAL OF THE RESOURCES PRESENT IN THE STORAGE;
    public ResourceCount readStorage(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        firstRow.getResourceType().add(count,firstRow.getContent()); //Reading storage first row
        secondRow.getResourceType().add(count,secondRow.getContent());//Reading storage second row
        thirdRow.getResourceType().add(count,thirdRow.getContent()); //Reading storage third row

        return count;
    }
}
