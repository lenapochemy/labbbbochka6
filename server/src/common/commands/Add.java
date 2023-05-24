package common.commands;

import common.data.StudyGroup;
import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command 'add', adds a new element to the collection
 */
public class Add extends Command {

    private final StudyGroup studyGroup;

    public Add(StudyGroup studyGroup){
        super("add <study_group>",
                "add a new element to collection", studyGroup);
        this.studyGroup = studyGroup;
    }


    @Override
    public Response execute(CollectionManager collectionManager) {
        studyGroup.setId(collectionManager.generateId());
        studyGroup.setCreationDate(collectionManager.generateCreationDate());
        collectionManager.addToCollection(studyGroup);
        return new Response("New Study Group added to collection!");
    }

}
