package common.data;

import java.io.Serializable;

/**
 * Enum represent group's semester value
 */
public enum Semester implements Serializable {
    SECOND("SECOND"),
    SIXTH("SIXTH"),
    SEVENTH("SEVENTH"),
    DEFAULT_SEMESTER("default_semester");

    private final String semester;

    Semester(String semester){
        this.semester = semester;
    }

    /**
     * Method outputs all possible semester values as string
     */
    public static String getAllValues(){
        return Semester.SECOND + ", " + Semester.SIXTH + ", " + Semester.SEVENTH;
    }
}
