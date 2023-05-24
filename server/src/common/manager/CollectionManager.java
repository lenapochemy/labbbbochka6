package common.manager;

import common.data.FormOfEducation;
import common.data.StudyGroup;
import common.exceptions.NullCollectionException;
import common.exceptions.NullException;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Class responsible for working with the collection
 */
public class CollectionManager {
    /**
     * Collection of groups
     */
    private HashSet<StudyGroup> studyGroupCollection = new HashSet<>();
    /**
     * Collection id values
     */
    private Set<Integer> idCollection = new HashSet<>();
    private int newId = 1;
    private final FileManager fileManager;

    /**
     * Time when the collection was last modified
     */
    private LocalDate lastInitDate;
    /**
     * The time when the collection was last saved to a file
     */
    private LocalDate lastSaveDate;

    public CollectionManager(FileManager fileManager){
        this.lastInitDate = null;
        this.lastSaveDate = null;
        this.fileManager = fileManager;
    }

    /**
     * Method creates an empty collection
     */
    public void createCollection(){
        this.studyGroupCollection = new HashSet<>();
    }

    /**
     * Method fills the collection with groups from the file
     */
    public void readFromFile(){
        try{
            this.studyGroupCollection = fileManager.readFromFile();
            if(collectionSize() !=0){
                for(StudyGroup group : studyGroupCollection){
                    if(group.getId() == StudyGroup.wrongId){
                        studyGroupCollection.remove(group);
                    } else{
                        idCollection.add(group.getId());
                    }
                }
            } else {
                ConsoleManager.printError("File is empty");
            }
            if(studyGroupCollection == null){
                ConsoleManager.printError("No correct study group in file");
                createCollection();
            }
        } catch (FileNotFoundException e){
            ConsoleManager.printError(e);
        }
    }

    /**
     * Method counts the number of elements in the collection
     * @return elements count
     */
    public int collectionSize(){
        try{
            if(studyGroupCollection == null) throw new NullCollectionException();
            return studyGroupCollection.size();
        }catch (NullCollectionException e){
            return 0;
        }
    }

    /**
     * Method writes the collection to a file
     */
    public void writeToFile(){
        fileManager.writeToFile(this.studyGroupCollection);
    }

    /**
     * Method generates a new unique id for the group
     * @return group's id
     */
    public int generateId(){
        while(!idCollection.add(newId)){
            newId++;
        }
        return newId;
    }

    /**
     * Method adds a new element to the collection
     * @param studyGroup new element for collection
     */
    public void addToCollection(StudyGroup studyGroup){
        studyGroupCollection.add(studyGroup);
        lastInitDate = LocalDate.now();
    }

    /**
     * Method clears the collection
     */
    public void clearCollection(){
        studyGroupCollection.clear();
    }

    /**
     * Method saves the collection to a file
     */
    public void saveCollection(){
        this.writeToFile();
        lastSaveDate = LocalDate.now();
    }

    /**
     * Method finds the group with the maximum number of students in the collection
     * @return max students count
     */
    public Integer getMaxGroup(){
        Integer max = 0;
        for(StudyGroup group : studyGroupCollection){
            if(group.getStudentsCount() > max) max = group.getStudentsCount();
        }
        return max;
    }

    /**
     * Method finds a group with the given id
     * @param id id of the desired group
     * @return desired group
     */
    public StudyGroup getByID(int id){
        for(StudyGroup group : studyGroupCollection){
            if(group.getId() == id)  return group;
        }
        return null;
    }

    /**
     * Method deletes group from the collection
     * @param studyGroup the group to delete
     */
    public void removeFromCollection(StudyGroup studyGroup){
        idCollection.remove(studyGroup.getId());
        studyGroupCollection.remove(studyGroup);
    }

    /**
     * Method removes a group by the id from the collection
     * @param id id of the group to delete
     */
    public boolean removeByID(int id){
        StudyGroup studyGroup = getByID(id);
        try {
            if(studyGroup == null) throw new NullException();
            idCollection.remove(id);
            studyGroupCollection.remove(studyGroup);
            return true;
        } catch (NullException e){
            ConsoleManager.printError("Study group with this ID is not exists");
            return false;
        }
    }

    /**
     * Method removes groups with a larger number of students from the collection
     * @param count max number of students in groups that remain in the collection
     */
    public void removeGreater(Integer count) throws NullCollectionException{
            if (studyGroupCollection.isEmpty()) throw new NullCollectionException();
            HashSet<Integer> idSet = new HashSet<>();
            for (StudyGroup group : studyGroupCollection) {
                if (group.getStudentsCount() > count) {
                    idSet.add(group.getId());
                }
            }
            for (Integer id : idSet) {
                removeByID(id);
            }

    }

    /**
     * Method removes groups with a smaller number of students from the collection
     * @param count min number of students in groups that remain in the collection
     */
    public void removeLower(Integer count) throws NullCollectionException{
        if (studyGroupCollection.isEmpty()) throw new NullCollectionException();
        HashSet<Integer> idSet = new HashSet<>();
        for(StudyGroup group : studyGroupCollection){
            if(group.getStudentsCount() < count){
                idSet.add(group.getId());
            }
        }
        for(Integer id: idSet){
            removeByID(id);
        }
    }

    /**
     * Method removes a group with this form of education from the collection
     * @param formOfEducation form of education
     */
    public void removeByFormOfEducation(FormOfEducation formOfEducation){
        for(StudyGroup group : studyGroupCollection){
            if(group.getFormOfEducation().equals(formOfEducation)){
                removeFromCollection(group);
                break;
                }
        }
    }

    /**
     * Method displays all elements from collection
     */
    public String printCollection(){
        StringBuilder print = new StringBuilder();
        for(StudyGroup group : studyGroupCollection){
            print.append(group.toString()).append("\n");
        }
        return print.toString();
    }

    public String showInfo() {

        LocalDate lastInitDate = getLastInitDate();
        String sLastInitDate;
        if (lastInitDate == null) sLastInitDate = "No command in this session";
        else sLastInitDate = lastInitDate.toString();
        LocalDate lastSaveDate = getLastSaveDate();
        String sLastSaveDate;
        if (lastSaveDate == null) sLastSaveDate = "No saved in this session";
        else sLastSaveDate = lastSaveDate.toString();
        String info = "Collection info:\n" +
                "  Type: " + collectionType() + "\n" +
                "  Last save: " + sLastSaveDate + "\n" +
                "  Last init: " + sLastInitDate + "\n" +
                "  Number of elements: " + collectionSize();
        return info;
    }
    /**
     * Method displays groups whose admins are above this height
     * @param height max height of admins of groups that remain in the collection
     */
    public String getGreater(Integer height){
        StringBuilder groups = new StringBuilder();
        for(StudyGroup group : studyGroupCollection){
            if(group.getGroupAdmin().getHeight() > height) {
                groups.append("\n");
                groups.append(group.toString());
            }
        }
        return groups.toString();
    }

    /**
     * Method displays the forms of education of all groups from the collection in descending order
     */
    public String printFromOfEducation(){
        StringBuilder print = new StringBuilder();
        int countDistance = 0;
        int countFullTime = 0;
        int countEventing = 0;
        for(StudyGroup group : studyGroupCollection){
            if(group.getFormOfEducation().equals(FormOfEducation.DISTANCE_EDUCATION)) countDistance++;
            if(group.getFormOfEducation().equals(FormOfEducation.FULL_TIME_EDUCATION)) countFullTime++;
            if(group.getFormOfEducation().equals(FormOfEducation.EVENTING_CLASSES)) countEventing++;
        }
        for(int i=0; i<countEventing; i++){
            print.append(FormOfEducation.EVENTING_CLASSES).append("\n");
        }
        for(int i=0; i<countFullTime; i++){
            print.append(FormOfEducation.FULL_TIME_EDUCATION).append("\n");
        }
        for(int i=0; i<countDistance; i++){
            print.append(FormOfEducation.DISTANCE_EDUCATION).append("\n");
        }
        return print.toString();
    }

    /**
     * Method finds the collection type
     * @return type of collection
     */
    public String collectionType(){
        try{
            if(studyGroupCollection.isEmpty()) throw new NullCollectionException();
            return studyGroupCollection.getClass().getName();
        } catch (NullCollectionException e){
            return "empty";
        }
    }

    /**
     * Method generates the date the group was created
     * @return creation date
     */
    public LocalDate generateCreationDate(){
        return LocalDate.now();
    }

    public LocalDate getLastInitDate(){
        return lastInitDate;
    }
    public LocalDate getLastSaveDate(){
        return lastSaveDate;
    }

    public HashSet<StudyGroup> getStudyGroupCollection(){
        return studyGroupCollection;
    }
}

