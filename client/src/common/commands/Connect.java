package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

public class Connect extends Command{

    public Connect(){
        super("connect", "connecting client with server", null);
    }

    @Override
    public Response execute(CollectionManager collectionManager){
        return new Response("Connecting successful");
    }

}
