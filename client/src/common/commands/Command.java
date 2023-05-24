package common.commands;

import common.manager.CollectionManager;
import common.manager.requestManager.Response;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract command with name and description
 */
public abstract class Command implements Serializable {
    private final String name;
    private final String description;
    private final Object args;

    public Command(String name, String description, Object args){
        this.name = name;
        this.description = description;
        this.args = args;
    }

    public abstract Response execute(CollectionManager collectionManager);

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return name.equals(command.name) && description.equals(command.description);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name = '" + name + "'," +
                " description = '" + description +
                " args = " + args.toString() +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

}
