package common.manager.requestManager;

import common.data.*;
import common.exceptions.IncorrectGroupValueException;
import common.manager.ConsoleManager;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Class parses a collection of groups from StudyGroup format to json format and back
 */
public class Parser {
    public Parser(){
    }

    /**
     * Method translates the value of one group into a json string
     * @param group group value in StudyGroup format
     * @return string in json format containing the value of group
     */
    public static String groupToJSON(StudyGroup group){
        return "{\n" +
                " \"id\": " + group.getId() + ",\n" +
                " \"name\": \"" + group.getName() + "\",\n" +
                " \"coordinates\": {\n" +
                "  \"x\": " + group.getCoordinates().getCoordinatesX() + ",\n" +
                "  \"y\": " + group.getCoordinates().getCoordinatesY() + "\n" +
                " },\n" +
                " \"creationDate\": \"" + group.getCreationDate() + "\",\n" +
                " \"formOfEducation\": \"" + group.getFormOfEducation() + "\",\n" +
                " \"semesterEnum\": \"" + group.getSemesterEnum() + "\",\n" +
                " \"groupAdmin\": {\n" +
                "  \"name\": \"" + group.getGroupAdmin().getName() + "\",\n" +
                "  \"height\": " + group.getGroupAdmin().getHeight() + ",\n" +
                "  \"eyeColor\": \"" + group.getGroupAdmin().getEyeColor() + "\",\n" +
                "  \"hairColor\": \"" + group.getGroupAdmin().getHairColor() + "\",\n" +
                "  \"nationality\": \"" + group.getGroupAdmin().getNationality() + "\"\n" +
                "  }\n" +
                "}";
    }

    /**
     * Method translates the all collection of groups into a json string
     * @param studyGroupCollection collection
     * @return string in json format with a description of all groups from the collection
     */
    public String parseToJSON(HashSet<StudyGroup> studyGroupCollection){
        StringBuilder json = new StringBuilder();
        String stringGroup;
        if(studyGroupCollection.size() == 1){
            for(StudyGroup group: studyGroupCollection) {
                json = new StringBuilder(groupToJSON(group));
            }

        } else {
            json = new StringBuilder("[\n ");
            for (StudyGroup group : studyGroupCollection) {
                stringGroup = groupToJSON(group) + ",\n";
                json.append(stringGroup);
            }
            json = new StringBuilder(json.substring(0, json.length() - 2) + "\n");
            json.append("]");
        }
        return json.toString();
    }

    /**
     * Method translates string in json format into a group in the StudyGroup format
     * @param json a string with information about the group in json format
     * @return group in StudyGroup format
     */
    public static StudyGroup groupFromJSON(String json){
        int i1, i2;
        try {
            i1 = json.indexOf("\"id\"") + 4;
            i2 = json.indexOf(",", i1);
            int id = Integer.valueOf(json.substring(i1 + 2, i2));

            i1 = json.indexOf("\"", json.indexOf("\"name\"") + 6);
            i2 = json.indexOf("\"", i1 + 1);
            String name = json.substring(i1 + 1, i2);

            i1 = json.indexOf("\"x\"") + 3;
            i2 = json.indexOf(",", i1);
            int x = Integer.parseInt(json.substring(i1 + 2, i2));
            i1 = json.indexOf("\"y\"") + 3;
            i2 = json.indexOf(" }", i1);
            Double y = Double.parseDouble(json.substring(i1 + 2, i2));
            Coordinates coord = new Coordinates(x, y);

            i1 = json.indexOf("\"", json.indexOf("\"creationDate\"") + 14);
            i2 = json.indexOf("\"", i1 + 1);
            LocalDate date = LocalDate.parse(json.substring(i1 + 1, i2));

            i1 = json.indexOf("\"studentsCount\"") + 15;
            i2 = json.indexOf(",", i1);
            Integer count = Integer.parseInt(json.substring(i1 + 2, i2));

            i1 = json.indexOf("\"", json.indexOf("\"formOfEducation\"") + 17);
            i2 = json.indexOf("\"", i1 + 1);
            FormOfEducation form = FormOfEducation.valueOf(json.substring(i1 + 1, i2));

            i1 = json.indexOf("\"", json.indexOf("\"semesterEnum\"") + 14);
            i2 = json.indexOf("\"", i1 + 1);
            Semester semester = Semester.valueOf(json.substring(i1 + 1, i2));

            int i = json.indexOf("groupAdmin") + 11;
            i1 = json.indexOf("\"", json.indexOf("\"name\"", i) + 6);
            i2 = json.indexOf("\"", i1 + 1);
            String adminName = json.substring(i1 + 1, i2);

            i1 = json.indexOf("\"height\"") + 8;
            i2 = json.indexOf(",", i1);
            Integer height = Integer.parseInt(json.substring(i1 + 2, i2));

            i1 = json.indexOf("\"", json.indexOf("\"eyeColor\"") + 10);
            i2 = json.indexOf("\"", i1 + 1);
            ColorEye eye = ColorEye.valueOf(json.substring(i1 + 1, i2));

            i1 = json.indexOf("\"", json.indexOf("\"hairColor\"") + 11);
            i2 = json.indexOf("\"", i1 + 1);
            ColorHair hair = ColorHair.valueOf(json.substring(i1 + 1, i2));

            i1 = json.indexOf("\"", json.indexOf("\"nationality\"") + 13);
            i2 = json.indexOf("\"", i1 + 1);
            Country nationality = Country.valueOf(json.substring(i1 + 1, i2));


            Person admin = new Person();
            admin.setName(adminName);
            admin.setHeight(height);
            admin.setEyeColor(eye);
            admin.setHairColor(hair);
            admin.setNationality(nationality);

            StudyGroup group = new StudyGroup();
            group.setId(id);
            group.setName(name);
            group.setCoordinates(coord);
            group.setCreationDate(date);
            group.setStudentsCount(count);
            group.setFormOfEducation(form);
            group.setSemesterEnum(semester);
            group.setGroupAdmin(admin);

            return group;
        } catch (IncorrectGroupValueException e){
            ConsoleManager.printError("Incorrect study group in file");
        }
        return null;
    }

    /**
     * Method translates collection of group from json format StudyGroup format
     * @param json string in json format, containing information about collection
     * @return groups collection in StudyGroup format
     * @throws IncorrectGroupValueException incorrect group value
     */
    public HashSet<StudyGroup> parseFromJSON(String json) throws IncorrectGroupValueException {
        HashSet<StudyGroup> studyGroupCollection = new HashSet<>();

        if(json.substring(0,1).equals("{")){
            StudyGroup group = groupFromJSON(json);
            studyGroupCollection.add(group);
        } else {
            String [] array = (json + "},\n  \\{").split("},\n  \\{" );
            for(String str: array){
                StudyGroup group = groupFromJSON(str);
                studyGroupCollection.add(group);
            }
        }
        return studyGroupCollection;
    }


}
