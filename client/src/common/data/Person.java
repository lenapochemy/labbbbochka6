package common.data;

import common.exceptions.*;
import common.manager.ConsoleManager;

import java.io.Serializable;
import java.util.List;

/**
 * Class represent person and his description
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer height; //Поле не может быть null, Значение поля должно быть больше 0
    private ColorEye eyeColor; //Поле может быть null
    private ColorHair hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null

    public Person(){

    }
    public Person(String name, Integer height, ColorEye eyeColor, ColorHair hairColor, Country nationality){
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public static Person setPerson(List<String> args){
        Person person = new Person();
        try {
            person.setName(args.get(0));
            person.setHeight(Integer.parseInt(args.get(1)));
            person.setEyeColor(ColorEye.valueOf(args.get(2)));
            person.setHairColor(ColorHair.valueOf(args.get(3)));
            person.setNationality(Country.valueOf(args.get(4)));
        } catch (IncorrectGroupValueException e){
            ConsoleManager.printError("Incorrect person value");
            return null;
        }
        return person;
    }
    /**
     * Method checks the correctness of person's name
     * @param name verifiable name
     * @throws IncorrectGroupValueException incorrect name
     */
    public void setName(String name) throws IncorrectGroupValueException{
        try {
            if(name == null || name.isEmpty()) throw new NullException();
            this.name = name;
        } catch (NullException e){
            ConsoleManager.printError("Name can't be empty");
            throw new IncorrectGroupValueException();
        }
    }

    /**
     * Method checks the correctness of person's height value
     * @param height verifiable height
     * @throws IncorrectGroupValueException incorrect height
     */
    public void setHeight(Integer height) throws IncorrectGroupValueException{
        try {
            if (height == null) throw new NullException();
            if (height <= 0) throw new IncorrectValueException();
            this.height = height;
        } catch (NullException e) {
            ConsoleManager.printError("Height can't be hull");
            throw new IncorrectGroupValueException();
        } catch (IncorrectValueException e) {
            ConsoleManager.printError("Height can't be negative");
            throw new IncorrectGroupValueException();
        }
    }

    /**
     * Method checks the correctness of person's eye color value
     * @param eyeColor eye color
     */
    public void setEyeColor(ColorEye eyeColor){
        this.eyeColor = eyeColor;
    }

    /**
     * Method checks the correctness of person's hair color value
     * @param hairColor verifiable hair color
     */
    public void setHairColor(ColorHair hairColor){
        try {
            if(hairColor == null) throw new NullException();
            this.hairColor = hairColor;
        } catch (NullException e){
            ConsoleManager.printError("Hair color can't be hull");
        }
    }

    /**
     * Method checks the correctness of person's nationality value
     * @param nationality verifiable nationality
     */
    public void setNationality(Country nationality){
        try {
            if(nationality == null) throw new NullException();
            this.nationality = nationality;
        } catch (NullException e){
            ConsoleManager.printError("Nationality can't be hull");
        }
    }

    public String getName(){
        return name;
    }
    public Integer getHeight(){
        return height;
    }
    public ColorEye getEyeColor(){
        return eyeColor;
    }
    public ColorHair getHairColor(){
        return hairColor;
    }
    public Country getNationality(){
        return nationality;
    }

    @Override
    public String toString(){
        return "Person{" +
                "name=" + name +
                ", height=" + height +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                "}";
    }

    @Override
    public int hashCode(){
        return name.hashCode() + height.hashCode() + eyeColor.hashCode() + hairColor.hashCode() + nationality.hashCode();
    }
}
