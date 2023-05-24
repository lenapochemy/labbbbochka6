package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command 'help', displays information about all commands
 */
public class Help extends Command{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";



    public Help(){
        super("help", "displays help for available commands", null);

    }

    private String com = ANSI_GREEN + "Command: " + ANSI_CYAN;
    private String des = ANSI_GREEN + "Description: " + ANSI_YELLOW;
    @Override
    public Response execute(CollectionManager collectionManager){
        return new Response(
                com + "add <study_group>, " + des +  "add a new element to collection\n" +
                        com + "add_if_max <study_group>, " + des +"add new element to collection, if this students count is more than max students count in collection\n" +
                        com + "clear, " + des + "clear collection\n" +
                        com + "execute_script <file_name>, " + des +"execute script from file\n" +
                        com + "exit, " + des + "finishing program\n" +
                        com + "filter_greater_than_group_admin <height>, " + des + "display all groups from collection, whose admin is higher\n" +
                        com + getName() + ", " + des + getDescription() + "\n" +
                        com + "info, " + des + "displays information about collection\n" +
                        com + "print_field_descending_form_of_education, " + des +
                        "display all forms of education from collection in descending order\n" +
                        com + "remove_any_by_form_of_education <form_of_education>, " + des +
                        "remove one group from collection, which have the same form of education\n" +
                        com + "remove_by_id <id>, " + des + "remove element from collection by id\n" +
                        com + "remove_greater <count>, " + des + "remove all groups from collection that have more student\n" +
                        com + "remove_lower <count>, " + des + "remove all groups from collection that have less student\n" +
                        com + "show, " + des + "display all elements from collection\n" +
                        com + "update_by_id <id, study_group> , " + des + "update element from collection by id"
        );
    }


}
