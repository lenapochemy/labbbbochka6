import common.commands.Command;
import common.commands.Help;
import common.exceptions.CommandException;
import common.exceptions.IncorrectScriptException;
import common.manager.*;
import common.manager.requestManager.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Server {

    private final int port;
    private Socket socket;
    private ServerSocket server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final InputStream stream = System.in;
    public FileManager fileManager = new FileManager();
    public CollectionManager collectionManager = new CollectionManager(fileManager);
    private Scanner scanner = new Scanner(System.in);
    private final ScannerManager scannerManager = new ScannerManager(scanner);
    private CommandManager commandManager = new CommandManager();


    public Server(){
        this.port = scannerManager.sayPort();
        boolean connect = false;

        while (!connect){
            try{
                server = new ServerSocket(port);
                connect = true;
                ConsoleManager.printSuccess("Server is running with port: " + port);

            } catch (IOException e){
                ConsoleManager.printError("Problem with starting server");
            }
        }
        // stream = System.in;

        //FileManager fileManager = new FileManager();
        //CollectionManager collectionManager = new CollectionManager(fileManager);

        String resp;
        if(fileManager.isFileEmpty()){
            collectionManager.createCollection();
            resp = "File 'study_groups.json' is not found or empty, so collection is empty";
        } else{
            collectionManager.readFromFile();
            resp = "Collection is filled from file 'study_groups.json'!";
        }
        ConsoleManager.printInfo(resp);

    }

    public void run(){
        try{
            socket = server.accept();

           Command command = null;
            while (command == null) {
                try {
                    reader();
                    command = (Command) getCommand();
                    ConsoleManager.printInfo("Receive command: " + command.getName());
                } catch (ClassNotFoundException e) {
                    ConsoleManager.printError("Class not found");
                }
            }

            Response commandResp = command.execute(collectionManager);
            ConsoleManager.printSuccess("Sent response: " + commandResp.getMessage());
            sendResponse(commandResp);


        } catch (IOException e){
           // ConsoleManager.printError("Problem with");
        }
    }

    /*private void requestHandler(){
        try {


            socket = server.accept();
            System.out.println("we in request handler");

            Command command = null;
            while (command == null) {

                try {
                    System.out.println("try getting command in socket");
                    command = (Command) getCommand();
                    ConsoleManager.printInfo("Receive command: " + command.getName());
                } catch (ClassNotFoundException e) {
                    ConsoleManager.printError("Class not found");
                }
            }

            Response commandResp = command.execute(collectionManager);
            ConsoleManager.printSuccess("Sent response: " + commandResp.getMessage());
            sendResponse(commandResp);
        }catch (IOException e){
            ConsoleManager.printError("some IO problem");
        }
    }

     */

    private Object getCommand() throws IOException, ClassNotFoundException{
        inputStream = new ObjectInputStream(socket.getInputStream());
        return inputStream.readObject();
    }

    private void sendResponse(Response o) throws IOException{
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(o);
            outputStream.flush();

    }



  /*  public void commandReader(){
        ConsoleManager.printSuccess(Help.ANSI_GREEN + "Print command (maybe 'help')" + Help.ANSI_RESET);
        String input = "run";
        while (!input.equals("exit")){

           // requestHandler();
            Command command = null;
            while (command == null) {
                input = scanner.nextLine().trim();
                    try {
                        command = commandManager.buildCommand(input, scannerManager);
                    } catch (IncorrectScriptException e){

                    } catch (CommandException e){
                        ConsoleManager.printError("This is not a command, print 'help'");
                    }

            }

            Response commandResp = command.execute(collectionManager);
            ConsoleManager.printSuccess(commandResp.getMessage());
            requestHandler();
        }
    }

   */
    /* private void commanReader(){
        String input;
        if(scanner.hasNext()) {
            System.out.print("ura, we read");
            input = scanner.nextLine().trim();
            System.out.println(input);
            Command command = null;
            while (command == null) {
                try {
                    command = commandManager.buildCommand(input, scannerManager);
                } catch (CommandException e){
                    ConsoleManager.printError("This is not a command, print 'help'");
                } catch (IncorrectScriptException e){
                    ConsoleManager.printError("Incorrect script");
                }
            }

            Response commandResp = command.execute(collectionManager);
            ConsoleManager.printSuccess(commandResp.getMessage());
        }
    }

     */

    private void reader(){
        try{
            if(stream.available() > 0){
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                if(reader.readLine().trim().equals("save")){
                    save();
                }
                if(reader.readLine().trim().equals("exit")){
                    exit();
                }
            }
        } catch (IOException e){
            ConsoleManager.printError("Scanner problem");
        }
    }
    private void save(){
        collectionManager.saveCollection();
        ConsoleManager.printSuccess("Collection is saved to file");
    }

    private void exit(){
        save();
        ConsoleManager.printSuccess("Program is finished");
        System.exit(0);
    }


}
