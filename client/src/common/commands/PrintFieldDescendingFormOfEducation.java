package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command 'print_field_descending_form_of_education", displays all forms of education from collection in descending order
 */
public class PrintFieldDescendingFormOfEducation extends Command{


    public PrintFieldDescendingFormOfEducation(){
        super("print_field_descending_form_of_education","display all forms of education from collection in descending order", null);

    }

    @Override
    public Response execute(CollectionManager collectionManager){
        if(collectionManager.collectionSize() == 0) return new Response("Collection is empty");
        return new Response("Form of education: \n" + collectionManager.printFromOfEducation());
    }


}

