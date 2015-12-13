package model;

import java.io.Serializable;


public class Wind implements Serializable {

    public Wind(){

    }
    public Wind(Speed speed, Direction direction){
        this.speed = speed;
        this.direction = direction;
    }
    private Speed speed;
    private Direction direction;

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
