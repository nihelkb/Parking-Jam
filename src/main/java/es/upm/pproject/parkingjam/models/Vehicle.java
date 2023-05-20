package es.upm.pproject.parkingjam.models;

public class Vehicle {
    private Character id;
    private int length;
    private char orientation;
    private int positionX;
    private int positionY;
    private boolean redCar;

    public Vehicle (Character id, int length, char orientation, int positionX, int positionY, boolean redCar){
        this.id = id;
        this.length = length;
        this.orientation = orientation;
        this.positionX = positionX;
        this.positionY = positionY;
        this.redCar = redCar;
    }
    
    public Character getId(){
        return id;
    }
    
    public int getLength(){
        return length;
    }

    public char getOrientation(){
        return orientation;
    }
    
    public int getpositionX(){
        return positionX;
    }
    
    public int getpositionY(){
        return positionY;
    }
    
    public void setPosition (int x, int y) {
        positionX = x;
        positionY = y;
    }

    public String toString(){
        return id+ " "+ length +" " + orientation +" "+ positionX +" "+ positionY + " "+ redCar;
    }
    public void move(String direction, int distance){
        if(this.orientation == 'H'){
            if(direction.equals("left")){
                this.positionY = this.positionY - distance;
            }
            else if(direction.equals("right")){
                this.positionY = this.positionY + distance;
            }
        }
        else if(this.orientation == 'V'){
            if(direction.equals("up")){
                this.positionX = this.positionX - distance;
            }
            else if(direction.equals("down")){
                this.positionX = this.positionX + distance;
            }
        }
    }
}





