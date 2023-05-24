package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command "show", displays all elements from collection
 */
public class Show extends Command{

    public Show(){
        super("show", "display all elements from collection", null);
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        if (collectionManager.collectionSize() == 0) return new Response("Collection is empty");
        return new Response(collectionManager.printCollection());
    }
}
