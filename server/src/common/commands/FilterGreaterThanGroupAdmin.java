package common.commands;
import common.manager.CollectionManager;
import common.manager.requestManager.Response;

/**
 *  Command 'filter_greater_than_group_admin', displays all the elements of collection, the admin's height value in which of greater
 */
public class FilterGreaterThanGroupAdmin extends Command{
    private final Integer height;

    public FilterGreaterThanGroupAdmin(Integer height){
        super("filter_greater_than_group_admin <height>", "display all groups from collection, whose admin is higher", height);
        this.height = height;
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        return new Response("Greater groups:" + collectionManager.getGreater(height));
    }

}
