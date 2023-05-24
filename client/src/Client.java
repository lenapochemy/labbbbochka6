import common.commands.Command;
import common.commands.Connect;
import common.exceptions.*;
import common.manager.*;
import common.manager.requestManager.Response;
import common.manager.requestManager.Serializer;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    private final String host;
    private final int port;
    private SocketChannel client;
    private ByteBuffer buffer;

    private ScannerManager scannerManager;

    public Client(String host, int port) throws ConnectException{
        this.host = host;
        this.port = port;
        this.scannerManager = new ScannerManager(new Scanner(System.in));
        buffer = ByteBuffer.allocate(100000);
        findServer();
    }

    private void findServer() throws ConnectException{
        ConsoleManager.printInfo("Try to connect to server");
        Connect connect = new Connect();
        Response result = start(connect);
        if(!result.getMessage().equals("Connecting successful")){
            throw new ConnectException();
        }
        ConsoleManager.printSuccess(result.getMessage());
    }

    public Response start(Command o){
        Response out;
        try{
            client = SocketChannel.open(new InetSocketAddress(host, port));
            client.configureBlocking(false);

            sendObject(o);

            out = (Response) getObject();
            client.close();
        } catch (IOException e){
            ConsoleManager.printError("Problem with connecting to server");
            return new Response("No connecting to server");
        } catch (UnresolvedAddressException e){
            ConsoleManager.printError("Wrong host name");
            return new Response("No connecting to server");
        }
        return out;
    }

    private void sendObject(Command o) throws IOException{
        client.write(Serializer.serialize(o));
        ConsoleManager.printInfo("Sending command: " + o.getName());
    }

    private Object getObject(){
        while (true) {
            try {
                client.read(buffer);
                Object o = Serializer.deserialize(buffer);
                buffer = ByteBuffer.allocate(100000);
                return o;
            } catch (ClassNotFoundException e) {
                ConsoleManager.printError("Problem with deserialize");

            } catch (IOException e) {

            }
        }
    }

    public void run(CommandManager commandManager, Scanner scanner){
        ConsoleManager.printSuccess("Program is working! Print command (maybe 'help')");
        String input = "run";
        while (!input.equals("exit")){

            Command command = null;
            while (command == null) {
                input = scanner.nextLine().trim();

                List<String> splitLine = commandManager.stringSplit(input);
                if (splitLine.get(0).equals("execute_script") && splitLine.size() == 2) {
                    String fileName = splitLine.get(1);
                    scriptReader(fileName, commandManager);

                } else {
                    try {
                        command = commandManager.buildCommand(input, scannerManager);
                    } catch (IncorrectScriptException e){

                    } catch (CommandException e){
                        ConsoleManager.printError("This is not a command, print 'help'");
                    }
                }
            }

            Response commandResp = start(command);
            ConsoleManager.printSuccess(commandResp.getMessage());
        }
    }


    private void scriptReader(String fileName, CommandManager commandManager){
        try {
            HashSet<String> scriptCollection = new HashSet<>();
            scriptCollection.add(fileName);
            String path = System.getenv("STUDY_GROUP_PATH") + fileName;
            File file = new File(path);
            if(file.exists() && !file.canRead()) throw new FileException();
            Scanner scriptScan = new Scanner(file);

            Scanner scannerOld = scannerManager.getScanner();
            scannerManager.setScanner(scriptScan);
            scannerManager.setFileMode();
            while (scriptScan.hasNext()) {
                String input = scriptScan.nextLine().trim();
                System.out.println(input);

                if(input.equals("exit")){
                    ConsoleManager.printSuccess("Program is finished");
                    System.exit(0);
                }
                try {
                    List<String> splitLine = commandManager.stringSplit(input);
                    if (splitLine.get(0).equals("execute_script") && splitLine.size() == 2) {
                        String newFileName = splitLine.get(1);
                        for (String script : scriptCollection) {
                            if (script.equals(newFileName)) throw new RecurentScriptException();
                        }
                        scriptCollection.add(newFileName);
                        scriptReader(newFileName, commandManager);
                    }
                    Command command = commandManager.buildCommand(input, scannerManager);
                    Response commandResp = start(command);
                    ConsoleManager.printSuccess(commandResp.getMessage());
                }catch (RecurentScriptException e){
                    ConsoleManager.printError("Recurse in script");
                } catch (IncorrectScriptException e){
                    ConsoleManager.printError("Script is incorrect");
                } catch (CommandException e){
                    ConsoleManager.printError("Incorrect command in script");
                }

            }

            scannerManager.setUserMode();
            scannerManager.setScanner(scannerOld);
        } catch (FileNotFoundException e){
            ConsoleManager.printError("File is not found");
        } catch (FileException e){
            ConsoleManager.printError("No commands in file");
        }
    }
}
