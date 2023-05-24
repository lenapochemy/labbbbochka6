package common.manager;

import common.data.*;
import common.exceptions.*;;

import java.util.Scanner;

import static common.data.Coordinates.MAX_X;

/**
 * Class responsible for reading data from the console to fill the collection
 */
public class ScannerManager {
    public Scanner scanner;
    /**
     * fileMode is equal to 1 if the collection is filled from a file, and 0 if it is filled through the console
     */
    private boolean fileMode;

    public ScannerManager(Scanner scanner){
        this.scanner = scanner;
    }

    public Scanner getScanner(){
        return scanner;
    }
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * Enables filling a collection from a file
     */
    public void setFileMode(){
        fileMode = true;
    }

    /**
     * Enables filling in a collection from a console
     */
    public void setUserMode(){
        fileMode = false;
    }

    public String sayHost(){
        String host = "";
        while(host.equals("")){
            try{
                System.out.println("Give host name: ");
                host = scanner.nextLine().trim();
                if(host.equals("")) throw new NullException();
            }catch(NullException e){
                ConsoleManager.printError("Host name can't be empty");
            }
        }
        return host;
    }

    public int sayPort(){
        String port = "";
        int pport = 0;
        while(port.equals("")){
            try{
                System.out.println("Give port: ");
                port = scanner.nextLine().trim();
                if(port.equals("")) throw new NullException();
                pport = Integer.parseInt(port);
            } catch (NullException e){
                ConsoleManager.printError("Port can't be empty");
            }
        }
        return pport;
    }
    /**
     * Method gets the name from the input
     * @param asking prompt to enter a name
     * @param nameType type of name: group's or people's
     * @return entered name
     * @throws IncorrectScriptException incorrect name in script
     */


    public String sayName(String asking, String nameType) throws IncorrectScriptException{
        String name = "default_name";
        while(name.equals("default_name") || name.equals("")){
            try{
                System.out.print(asking);
                name = scanner.nextLine().trim();
                if(fileMode) System.out.println(name);
                if(name.equals("")) throw  new NullException();
            } catch (NullException e){
                ConsoleManager.printError(nameType + " can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            }
        }
        return name;
    }

    /**
     * Method gets the group's name from the input
     * @return group's name
     * @throws IncorrectScriptException incorrect group's name in script
     */
    public String sayGroupName() throws IncorrectScriptException{
        return sayName("Give Study Group Name:", "Study Group name");
    }

    /**
     * Method gets the person's name from the input
     * @return person's name
     * @throws IncorrectScriptException incorrect person's name in script
     */
    public String sayPersonName() throws IncorrectScriptException{
        return sayName("Give Admin name:", "Person name");
    }

    /**
     * Method gets the coordinate X value from the input
     * @return coordinate X value
     * @throws IncorrectScriptException incorrect coordinate X value in script
     */
    public int sayCoordinateX() throws IncorrectScriptException{
        String sX;
        int x = 0;
        while(x == 0 || x > MAX_X){
            try{
                System.out.print("Give coordinate X:");
                sX = scanner.nextLine().trim();
                if(fileMode) System.out.println(sX);
                if(sX.equals("")) throw new NullException();
                x = Integer.parseInt(sX);
                if(x > MAX_X) throw new IncorrectValueException();
            } catch (NullException e){
                ConsoleManager.printError("Coordinate X can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IncorrectValueException e){
                ConsoleManager.printError("Coordinate X can't be more than " + MAX_X);
                if(fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException e){
                ConsoleManager.printError("Coordinate X should be a int number");
            }
        }
        return x;
    }

    /**
     * Method gets the coordinate Y value from the input
     * @return coordinate Y value
     * @throws IncorrectScriptException incorrect coordinate Y value in script
     */
    public Double sayCoordinateY() throws IncorrectScriptException{
        String sY;
        Double y = 0D;
        while (y == 0D){
            try{
                System.out.print("Give coordinate Y:");
                sY = scanner.nextLine().trim();
                if(fileMode) System.out.println(sY);
                if(sY.equals("")) throw new NullException();
                y = Double.parseDouble(sY);
            } catch (NullException e){
                ConsoleManager.printError("Coordinate Y can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException e){
                ConsoleManager.printError("Coordinate Y should be a number");
            }
        }
        return y;
    }

    /**
     * Method gets the coordinates value from the input
     * @return group's coordinates
     * @throws IncorrectScriptException incorrect group's coordinates value in script
     */
    public Coordinates sayCoordinates() throws IncorrectScriptException{
        try{
            int x = sayCoordinateX();
            Double y = sayCoordinateY();
            return new Coordinates(x,y);
        } catch (IncorrectGroupValueException e){
            ConsoleManager.printError(e);
            return null;
        }
    }

    /**
     * Method gets the students count value from the input
     * @return students count
     * @throws IncorrectScriptException incorrect students count value in script
     */
    public Integer sayStudentsCount() throws IncorrectScriptException{
        String sCount;
        Integer count = 0;
        while (count <= 0){
            try{
                System.out.print("Give the number of students in the group:");
                sCount = scanner.nextLine().trim();
                if(fileMode) System.out.println(sCount);
                if(sCount.equals("")) throw new NullException();
                count = Integer.valueOf(sCount);
                if(count <= 0) throw new IncorrectValueException();

            } catch (NullException e){
                ConsoleManager.printError("Students count can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IncorrectValueException e){
                ConsoleManager.printError("Students count can't be negative");
                if(fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException e){
                ConsoleManager.printError("Students count should be a number");
            }
        }
        return count;
    }

    /**
     * Method gets the semester value from the input
     * @return semester value
     * @throws IncorrectScriptException incorrect semester value in script
     */
    public Semester saySemesterEnum() throws IncorrectScriptException{
        String sSemester;
        Semester semester = Semester.DEFAULT_SEMESTER;
        while (semester.equals(Semester.DEFAULT_SEMESTER)){
            try{
                System.out.print("Semester list: ");
                System.out.println(Semester.getAllValues());
                System.out.print("Give semester:");
                sSemester = scanner.nextLine().trim();
                if(fileMode) System.out.println(sSemester);
                if(sSemester.equals("")) throw new NullException();
                semester = Semester.valueOf(sSemester);
                if(semester.equals(Semester.DEFAULT_SEMESTER)) throw new IllegalArgumentException();
            } catch (NullException e){
                ConsoleManager.printError("Semester can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException e){
                ConsoleManager.printError("Semester value should be from the list");
            }
        }
        return semester;
    }

    /**
     * Method gets the form of education value from the input
     * @return form of education
     * @throws IncorrectScriptException incorrect form of education value in script
     */
    public FormOfEducation sayFormOfEducation() throws IncorrectScriptException{
        String sFormOfEducation;
        FormOfEducation formOfEducation = FormOfEducation.DEFAULT_FORM_OR_EDUCATION;
        while(formOfEducation.equals(FormOfEducation.DEFAULT_FORM_OR_EDUCATION)){
            try{
                System.out.print("Form of education list: ");
                System.out.println(FormOfEducation.getAllValues());
                System.out.print("Give form of education:");
                sFormOfEducation = scanner.nextLine().trim();
                if(fileMode) System.out.println(sFormOfEducation);
                if(sFormOfEducation.equals("")) throw new NullException();
                formOfEducation = FormOfEducation.valueOf(sFormOfEducation);
                if(formOfEducation.equals(FormOfEducation.DEFAULT_FORM_OR_EDUCATION)) throw new IllegalArgumentException();
            } catch (NullException e){
                ConsoleManager.printError("Form of education can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException e){
                ConsoleManager.printError("Form of education value should be from the list");
            }
        }
        return formOfEducation;
    }

    /**
     * Method gets the person's eye color from the input
     * @return eye color
     * @throws IncorrectScriptException incorrect person's eye color value in script
     */
    public ColorEye sayColorEye() throws IncorrectScriptException{
        String sColorEye;
        ColorEye colorEye = ColorEye.DEFAULT_COLOR;
        while (colorEye.equals(ColorEye.DEFAULT_COLOR)){
            try{
                System.out.print("Eye color list: ");
                System.out.println(ColorEye.getAllValues());
                System.out.print("Give eye color:");
                sColorEye = scanner.nextLine().trim();
                if(fileMode) System.out.println(sColorEye);
                if(sColorEye.equals("")) throw new NullException();
                colorEye = ColorEye.valueOf(sColorEye);
                if(colorEye.equals(ColorEye.DEFAULT_COLOR)) throw new IllegalArgumentException();
            } catch (NullException e){
                ConsoleManager.printError("Eye color can't  be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException e){
                ConsoleManager.printError("Eye color should be from the list");
            }
        }
        return colorEye;
    }

    /**
     * Method gets the person's hair color from the input
     * @return hair color
     * @throws IncorrectScriptException incorrect person's hair color value in script
     */
    public ColorHair sayColorHair() throws IncorrectScriptException{
        String sColorHair;
        ColorHair colorHair = ColorHair.DEFAULT_COLOR;
        while (colorHair.equals(ColorHair.DEFAULT_COLOR)){
            try {
                System.out.print("Hair color list:");
                System.out.println(ColorHair.getAllValues());
                System.out.print("Give hair color:");
                sColorHair = scanner.nextLine().trim();
                if(fileMode) System.out.println(sColorHair);
                if(sColorHair.equals("")) throw new NullException();
                colorHair = ColorHair.valueOf(sColorHair);
                if(colorHair.equals(ColorHair.DEFAULT_COLOR)) throw new IllegalArgumentException();
            } catch (NullException e){
                ConsoleManager.printError("Hair color can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException e){
                ConsoleManager.printError("Hair color should be from the list");
            }
        }
        return colorHair;
    }

    /**
     * Method gets the person's nationality from the input
     * @return nationality
     * @throws IncorrectScriptException incorrect person's nationality value in script
     */
    public Country sayNationality() throws IncorrectScriptException{
        String sCountry;
        Country country = Country.DEFAULT_COUNTRY;
        while (country.equals(Country.DEFAULT_COUNTRY)){
            try{
                System.out.print("Country list: ");
                System.out.println(Country.getAllValues());
                System.out.print("Give country:");
                sCountry = scanner.nextLine().trim();
                if(fileMode) System.out.println(sCountry);
                if(sCountry.equals("")) throw new NullException();
                country = Country.valueOf(sCountry);
                if(country.equals(Country.DEFAULT_COUNTRY)) throw new IllegalArgumentException();
            } catch (NullException e){
                ConsoleManager.printError("Country can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException e){
                ConsoleManager.printError("Country should be from the list");
            }
        }
        return country;
    }

    /**
     * Method gets the person's height from the input
     * @return person's height
     * @throws IncorrectScriptException incorrect person's height value in script
     */
    public Integer sayHeight() throws IncorrectScriptException{
        String sHeight;
        Integer height = 0;
        while ( height <= 0){
            try{
                System.out.print("Give person height:");
                sHeight = scanner.nextLine().trim();
                if(fileMode) System.out.println(sHeight);
                if(sHeight.equals("")) throw new NullException();
                height = Integer.valueOf(sHeight);
                if(height <= 0) throw new IncorrectValueException();
            } catch (NullException e){
                ConsoleManager.printError("Height can't be empty");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IncorrectValueException e){
                ConsoleManager.printError("Height can't be negative");
                if(fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException e){
                ConsoleManager.printError("Height should be a number");
            }
        }
        return height;
    }

    /**
     * Method gets the group's admin from the input
     * @return group's admin
     * @throws IncorrectScriptException incorrect person's description in script
     */
    public Person sayPerson() throws IncorrectScriptException{
        return new Person(sayPersonName(), sayHeight(), sayColorEye(), sayColorHair(), sayNationality());
    }




}

