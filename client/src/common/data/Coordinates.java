package common.data;

import common.exceptions.*;
import common.manager.ConsoleManager;

import java.io.Serializable;

/**
 * Class represent coordinates - group location
 */
public class Coordinates implements Serializable {
    private int x; //Максимальное значение поля: 79
    public static final int MAX_X = 79;
    private Double y; // поле не может быть null


    public Coordinates(int x, Double y) throws IncorrectGroupValueException{
        this.setX(x);
        this.setY(y);
    }

    /**
     * Method checks the correctness of coordinate X value
     * @param coordinatesX verifiable coordinate X
     * @throws IncorrectGroupValueException incorrect coordinate X value
     */
    public void setX(int coordinatesX) throws IncorrectGroupValueException{
        try {
            if(coordinatesX > MAX_X) throw new IncorrectValueException();
            this.x = coordinatesX;
        } catch (IncorrectValueException e){
            ConsoleManager.printError("Coordinate X can't be more than " + MAX_X );
            throw new IncorrectGroupValueException();
        }
    }

    /**
     * Method checks the correctness of coordinate X value
     * @param coordinatesY verifiable coordinate Y
     * @throws IncorrectGroupValueException incorrect coordinate Y value
     */
    public void setY(Double coordinatesY) throws IncorrectGroupValueException{
        try {
            if(coordinatesY == null) throw new NullException();
            this.y = coordinatesY;
        } catch (NullException e) {
            ConsoleManager.printError("Coordinate Y can't be null");
            throw new IncorrectGroupValueException();
        }
    }

    public int getCoordinatesX(){
        return x;
    }
    public Double getCoordinatesY(){
        return y;
    }


    @Override
    public String toString(){
        return "(" + getCoordinatesX() + ", " + getCoordinatesY() + ")";
    }

}
