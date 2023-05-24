package common.commands;

import common.exceptions.NullCollectionException;
import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command "remove_lower", deletes elements from collection with less students
 */
public class RemoveLower extends Command{
    private Integer count;
    public RemoveLower(Integer count){
        super("remove_lower <count>", "remove all groups from collection that have less students", count);
        this.count = count;
    }

    @Override
    public Response execute(CollectionManager collectionManager){
        try {
            collectionManager.removeLower(count);
            return new Response("Smaller groups removed from collection!");
        } catch (NullCollectionException e){
            return new Response("Collection is empty");
        }
    }

}
