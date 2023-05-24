package common.manager;

import java.util.Scanner;

/**
 * Class responsible for the operation of the program and the execution of commands
 */
public class ConsoleManager {
    private final Scanner scanner;

    public ConsoleManager(Scanner scanner) {
        this.scanner = scanner;

    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    /**
     * Method displays information about the successful execution of commands
     * @param message success message
     */
    public static void printSuccess(Object message){
        System.out.println(ANSI_CYAN + message + ANSI_RESET);
    }

    /**
     * Method displays information about error
     * @param message error message
     */
    public static void printError(Object message){
        System.out.println(ANSI_RED + "Error: " + message + ANSI_RESET);
    }

    /**
     * Method displays information about the operation of the program
     * @param message information message
     */
    public static void printInfo(Object message){
        System.out.println(ANSI_PURPLE + message + ANSI_RESET);
    }





}
