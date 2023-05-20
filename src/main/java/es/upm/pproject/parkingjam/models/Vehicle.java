package es.upm.pproject.parkingjam.models;

public class Vehicle {
    private String name;
    private int length;
    private char direction;
    private int positionX;
    private int positionY;
    private boolean redCar;

    public Vehicle (String name, int length, char direction, int positionX, int positionY, boolean redCar){
        this.name = name;
        this.length = length;
        this.direction = direction;
        this.positionX = positionX;
        this.positionY = positionY;
        this.redCar = redCar;
    }
    
    public String getName(){
        return name;
    }
    
    public int getLength(){
        return length;
    }

    public char getDirection(){
        return direction;
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
        return name+ " "+ length +" " + direction +" "+ positionX +" "+ positionY + " "+ redCar;
    }
    public void move(String direction, int distance){
        if(this.direction == 'H'){
            if(direction.equals("left")){
                this.positionX = this.positionX - distance;
            }
            else if(direction.equals("right")){
                this.positionX = this.positionX + distance;
            }
        }
        else if(this.direction == 'V'){
            if(direction.equals("up")){
                this.positionY = this.positionY - distance;
            }
            else if(direction.equals("down")){
                this.positionY = this.positionY + distance;
            }
        }
    }
}





