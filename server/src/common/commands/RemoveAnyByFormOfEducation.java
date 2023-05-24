package common.commands;

import common.data.FormOfEducation;
import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command "remove_by_any_form_of_education", deletes one element from the collection that has this form of education
 */
public class RemoveAnyByFormOfEducation extends Command{

    private FormOfEducation formOfEducation;

    public RemoveAnyByFormOfEducation(FormOfEducation formOfEducation){
        super("remove_any_by_form_of_education <form_of_education>", "remove one group from collection, which have the same form of education", formOfEducation);
        this.formOfEducation = formOfEducation;
    }

    @Override
    public Response execute(CollectionManager collectionManager){
        collectionManager.removeByFormOfEducation(formOfEducation);
        return new Response("Group with this form of education removed from collection!");

    }

}
