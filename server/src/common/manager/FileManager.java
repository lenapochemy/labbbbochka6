package common.manager;

import common.data.StudyGroup;
import common.exceptions.IncorrectGroupValueException;
import common.manager.requestManager.Parser;

import java.io.*;
import java.util.HashSet;

/**
 * Class responsible for working with files
 */
public class FileManager {

    /**
     * Name of the initial file
     */
    private final String fileName = "study_groups.json";
    /**
     * Path to the directory where the initial file is located and where the files will be saved
     */
    private final String PATH = System.getenv("STUDY_GROUP_PATH");
    /**
     * Full link to the initial file
     */
    private final String STUDY_PATH = PATH + fileName;
    public FileManager(){
    }

    /**
     * Method reads groups collection from the initial file
     * @return collection of groups in StudyGroup format
     * @throws FileNotFoundException file is missing in the directory
     */
    public HashSet<StudyGroup> readFromFile() throws FileNotFoundException{
        HashSet<StudyGroup> studyGroupCollection = new HashSet<>();
        try{
            FileInputStream file = new FileInputStream(STUDY_PATH);
            InputStreamReader reader = new InputStreamReader(file);
            Parser parser = new Parser();

            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            String json = stringBuilder.toString();
            studyGroupCollection = parser.parseFromJSON(json);
        } catch (IncorrectGroupValueException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            ConsoleManager.printError("Problem with input file");
        }
        return studyGroupCollection;
    }

    /**
     * Method writes a collection of groups to a file in json format
     * @param studyGroupCollection collection of groups
     */
    public void writeToFile(HashSet<StudyGroup> studyGroupCollection){
        Parser parser = new Parser();
        File file = new File(STUDY_PATH);
        try(FileOutputStream writer = new FileOutputStream(file)) {
            writer.write(parser.parseToJSON(studyGroupCollection).getBytes());

        } catch (IOException e){
            ConsoleManager.printError("Problem with write to file");
        }

    }

    /**
     * The method checks if the file is empty
     * @return 1 if file is empty, and 0 if file is not empty
     */
    public boolean isFileEmpty(){
        File file = new File(STUDY_PATH);
        return !file.canRead();

    }

}

