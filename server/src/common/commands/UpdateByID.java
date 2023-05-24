package common.commands;

import common.data.StudyGroup;
import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command "update_by_id", updates one element from collection by id
 */
public class UpdateByID extends Command{


    private StudyGroup studyGroup;

    public UpdateByID(StudyGroup studyGroup){
        super("update_by_id <id, name, coordinate_x, coordinate_y, students_count, form_of_education, " +
                "semester, admin_name, admin_height, admin_eye_color, admin_hair_color, admin_nationality>", "update element from collection by id", studyGroup);
        this.studyGroup = studyGroup;
    }


    @Override
    public Response execute(CollectionManager collectionManager){
        if(collectionManager.collectionSize() == 0) return new Response("Collection is empty");
        int id = studyGroup.getId();
        StudyGroup group = collectionManager.getByID(id);
        if(group == null) return new Response("Study group with this ID is not exists");
        studyGroup.setCreationDate(group.getCreationDate());
        collectionManager.removeFromCollection(group);
        collectionManager.addToCollection(studyGroup);
        return new Response("Element from collection was updated!");

    }

}
