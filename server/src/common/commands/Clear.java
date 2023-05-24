package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 * Command 'clear', clears the collection
 */
public class Clear extends Command{
;

    public Clear(){
        super("clear", "clear collection", null);

    }
    @Override
    public Response execute(CollectionManager collectionManager) {
        collectionManager.clearCollection();
        return new Response("Collection is cleared");
    }

}
