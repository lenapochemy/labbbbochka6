import common.manager.CommandManager;
import common.manager.ConsoleManager;
import common.manager.ScannerManager;

import java.net.ConnectException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ScannerManager scannerManager = new ScannerManager(scanner);

        int port = scannerManager.sayPort();
        String host = scannerManager.sayHost();
        try{
            Client client = new Client(host, port);
            CommandManager commandManager = new CommandManager();
            client.run(commandManager, scanner);


        } catch (ConnectException e){
            ConsoleManager.printError("Problem with connecting");
        }
    }
}