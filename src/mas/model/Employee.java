package mas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Employee implements Serializable {
    // 1. Class extent
    private static List<Employee> extent = new ArrayList<>();

    // 5. Mandatory attribute
    private long ID;
    private String firstName, lastName;

    // 4. Optional attribute
    private long supervisorID;

    // 7. Class attribute
    private String companyName;

    // 6. Multi-value attribute(assumption: every employee has to know at least one programming language to be employed)
    private List<String> programmingLanguages;

    // 3. Complex attribute
    //TODO well, this

    public static List<Employee> getExtent() {
        // unmodifiable
        return Collections.unmodifiableList(extent);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        // long is a primitive time which isn't nullable, so don't need to check for nulls
        if(ID < 0) throw new IllegalArgumentException("ID must be a positive number");
        for (Employee possibleDuplicateID: extent) {
            if(possibleDuplicateID.getID() == ID) throw new IllegalArgumentException("Passed ID is already taken by a different employee");
        }

        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        // I chose isBlank() instead of isEmpty() because isBlank() also checks if the string consists only of whitespaces
        if(firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        this.lastName = lastName;
    }

    public long getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(long supervisorID) {
        if(supervisorID < 0) throw new IllegalArgumentException("ID must be a positive number");

        if (this.supervisorID == supervisorID) {
            throw new IllegalArgumentException("This ID is already set as the supervisor for the chosen employee");
        }

        boolean foundSupervisor = false;
        for (Employee supervisorExists: extent) {
            if(supervisorExists.getID() == supervisorID) {
                this.supervisorID = supervisorID;
                foundSupervisor = true;
                break;
            }
        }

        if(!foundSupervisor) throw new IllegalArgumentException("Supervisor ID must exist in the system");
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        
        this.companyName = companyName;
    }

    public List<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }
}
