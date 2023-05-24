package common.commands;

import common.data.StudyGroup;
import common.manager.CollectionManager;;
import common.manager.requestManager.Response;

/**
 * Command 'add_if_max', adds a new element to the collection if his students count is more than other groups in collection
 */
public class AddIfMax extends Command{

    private final StudyGroup studyGroup;

    public AddIfMax(StudyGroup studyGroup){
        super("add_if_max <study_group>",
                "add new element to collection, if this students count is more than max students count in collection", studyGroup);
        this.studyGroup = studyGroup;
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        studyGroup.setId(collectionManager.generateId());
        studyGroup.setCreationDate(collectionManager.generateCreationDate());
        Integer max = collectionManager.getMaxGroup();
        if(studyGroup.getStudentsCount() > max) {
            collectionManager.addToCollection(studyGroup);
            return new Response("Study Group added to collection");
        } else {
            return new Response("Study Group s not added to collection, because this group is not max");
        }

    }


}
