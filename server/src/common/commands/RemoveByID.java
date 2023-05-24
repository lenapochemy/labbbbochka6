package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command "remove_by_id", deletes one element from the collection with this id
 */
public class RemoveByID extends Command{

    private int id;
    public RemoveByID(int id){
        super("remove_by_id <id>", "remove element from collection by id", id);
        this.id = id;
    }


    @Override
    public Response execute(CollectionManager collectionManager){
        if(collectionManager.removeByID(id)) return new Response("Element removed from collection!");
        else return new Response("Study group with this ID is not exists");
    }

}
