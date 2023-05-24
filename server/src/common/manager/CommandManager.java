package common.manager;

import common.commands.*;
import common.data.FormOfEducation;
import common.data.StudyGroup;
import common.exceptions.ArgumentException;
import common.exceptions.CommandException;
import common.exceptions.IncorrectScriptException;
import common.exceptions.IncorrectValueException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {


    public List<String> stringSplit(String line){
        List<String> splitted = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean screening = false;
        for(char ch : line.toCharArray()){
            if(ch == ' ' && !screening){
                splitted.add(current.toString());
                current.setLength(0);
            } else if(ch == '"'){
                screening = !screening;
            } else {
                current.append(ch);
            }
        }
        splitted.add(current.toString());
        return splitted;
    }

    public String getCommandName(List<String> line){
        return line.get(0);
    }
    public List<String> getCommandArgs(List<String> line){
        line.remove(0);
        return line;
    }
    public Command initCommand(String line, ScannerManager scannerManager) throws IncorrectScriptException, CommandException{
        List<String> commandsWithArgs = stringSplit(line);
        String commandName = getCommandName(commandsWithArgs);
        List<String> commandArgs = getCommandArgs(commandsWithArgs);
        return createCommand(commandName, commandArgs, scannerManager);
    }

    public Command buildCommand(String input, ScannerManager scannerManager) throws IncorrectScriptException, CommandException {
        //try {
            Command command = initCommand(input, scannerManager);
            if (command == null) {
                throw new CommandException();
            }
            return command;
        /*} catch (CommandException e){
            ConsoleManager.printError("This is not a command, try again");
            return null;
        }

         */


    }
    public Command createCommand(String name, List<String> args, ScannerManager scannerManager) throws IncorrectScriptException, CommandException{
        int id;
        try{
            switch (name){
                case "add" -> {
                    if(!args.isEmpty()) {
                        throw new ArgumentException();
                    }
                    StudyGroup studyGroup = new StudyGroup(1, scannerManager.sayGroupName(), scannerManager.sayCoordinates(),
                            LocalDate.now(), scannerManager.sayStudentsCount(), scannerManager.sayFormOfEducation(),
                            scannerManager.saySemesterEnum(), scannerManager.sayPerson()  );
                    //if(studyGroup == null) throw new IncorrectValueException();
                    return new Add(studyGroup);
                }
                case "add_if_max" -> {
                    if(!args.isEmpty()) {
                        throw new ArgumentException();
                    }
                    StudyGroup studyGroup = new StudyGroup(1, scannerManager.sayGroupName(), scannerManager.sayCoordinates(),
                            LocalDate.now(), scannerManager.sayStudentsCount(), scannerManager.sayFormOfEducation(),
                            scannerManager.saySemesterEnum(), scannerManager.sayPerson()  );
                   // if(studyGroup == null) throw new IncorrectValueException();
                    return new AddIfMax(studyGroup);
                }
                case "clear" -> {
                    if(!args.isEmpty()){
                        throw  new ArgumentException();
                    }
                    return new Clear();
                }
                case "exit" -> {
                    if(!args.isEmpty()) {
                        throw new ArgumentException();
                    }
                    return new Exit();
                }
                case "filter_greater_than_group_admin" -> {
                    if(args.size() != 1){
                        throw new ArgumentException();
                    }
                    Integer height = Integer.parseInt(args.get(0));
                    return new FilterGreaterThanGroupAdmin(height);
                }
                case "help" -> {
                    if (!args.isEmpty()) {
                        throw new ArgumentException();
                    }
                    return new Help();
                }
                case "info" -> {
                    if(!args.isEmpty()){
                        throw new ArgumentException();
                    }
                    return new Info();
                }
                case "print_field_descending_form_of_education" -> {
                    if(!args.isEmpty()) {
                        throw new ArgumentException();
                    }
                    return new PrintFieldDescendingFormOfEducation();
                }
                case "remove_any_by_form_of_education" -> {
                    if(args.size() != 1) {
                        throw new ArgumentException();
                    }
                    FormOfEducation formOfEducation = FormOfEducation.valueOf(args.get(0));
                    return new RemoveAnyByFormOfEducation(formOfEducation);
                }

                case "remove_by_id" -> {
                    if(args.size() != 1){
                        throw new ArgumentException();
                    }
                    id = Integer.parseInt(args.get(0));
                    return new RemoveByID(id);

                }
                case "remove_greater" -> {
                    if(args.size() != 1){
                        throw new ArgumentException();
                    }
                    Integer count = Integer.parseInt(args.get(0));
                    return new RemoveGreater(count);
                }
                case "remove_lower" -> {
                    if(args.size() != 1){
                        throw new ArgumentException();
                    }
                    Integer count = Integer.parseInt(args.get(0));
                    return new RemoveLower(count);
                }
                case "show" -> {
                    if(!args.isEmpty()) {
                        throw new ArgumentException();
                    }
                    return new Show();
                }
                case "update_by_id" ->{
                    if(args.size() != 1){
                        throw new ArgumentException();
                    }
                    id = Integer.parseInt(args.get(0));
                    StudyGroup studyGroup = new StudyGroup(id, scannerManager.sayGroupName(), scannerManager.sayCoordinates(),
                            LocalDate.now(), scannerManager.sayStudentsCount(), scannerManager.sayFormOfEducation(),
                            scannerManager.saySemesterEnum(), scannerManager.sayPerson()  );
                    //if(studyGroup == null) throw new IncorrectValueException();
                    return new UpdateByID(studyGroup);
                }
                default -> throw new CommandException();
            }
        } catch (ArgumentException e){
            ConsoleManager.printError("Incorrect command");
            return null;
        }
    }


}
