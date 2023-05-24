package common.data;

import java.io.Serializable;

/**
 * Enum represent person's eye color
 */
public enum ColorEye implements Serializable {
    GREEN("GREEN"),
    RED("RED"),
    BLUE("BLUE"),
    ORANGE("ORANGE"),
    BROWN("BROWN"),
    DEFAULT_COLOR("default color");

    private final String color;

    ColorEye(String color){
        this.color = color;
    }

    /**
     * Method outputs all possible eye colors as string
     */
    public static String getAllValues(){
        return ColorEye.GREEN + ", " + ColorEye.RED+ ", " + ColorEye.BLUE +", " + ColorEye.ORANGE + ", " +ColorEye.BROWN;
    }

}
