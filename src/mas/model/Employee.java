package mas.model;

import java.io.Serializable;
import java.util.*;

public class Employee implements Serializable {
    // 1. Class extent
    private static List<Employee> extent = new ArrayList<>();

    // 5. Mandatory attribute
    private long ID;
    private String firstName, lastName;

    // 4. Optional attribute
    private long supervisorID;

    // 7. Class attribute
    private static String companyName;

    // 6. Multi-value attribute(assumption: every employee has to know at least one programming language to be employed)
    private Set<String> programmingLanguages = new HashSet<>();

    // 3. Complex attribute
    private Details empDetails;

    // TODO: derived attribute, class method, method overriding, toString displaying unassigned supervisorID as 0

    public Employee(long ID, String firstName, String lastName, String programmingLanguage,
                    String city, String street, String country, String postalCode, String bankName, String accountNumber) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        addProgrammingLanguage(programmingLanguage);
        this.empDetails = new Details(city, street, country, postalCode, bankName, accountNumber);

        extent.add(this);
    }

    // Constructor with the optional attribute
    public Employee(long ID, String firstName, String lastName, String programmingLanguage, long supervisorID,
                    String city, String street, String country, String postalCode, String bankName, String accountNumber) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setSupervisorID(supervisorID);
        addProgrammingLanguage(programmingLanguage);
        this.empDetails = new Details(city, street, country, postalCode, bankName, accountNumber);

        extent.add(this);
    }

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

        if (this.ID == supervisorID) throw new IllegalArgumentException("An employee cannot be their own supervisor");
        if (this.supervisorID == supervisorID) throw new IllegalArgumentException("This ID is already set as the supervisor for the chosen employee");

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
        return Employee.companyName;
    }

    public void setCompanyName(String companyName) {
        if(companyName == null || companyName.isBlank()) throw new IllegalArgumentException("Company name cannot be empty");
        if(Employee.companyName.equals(companyName)) throw new IllegalArgumentException("Entered company name is already used");

        Employee.companyName = companyName;
    }

    public Set<String> getProgrammingLanguages() {
        return Collections.unmodifiableSet(programmingLanguages);
    }

    public void addProgrammingLanguage(String programmingLanguage) {
        if(programmingLanguage == null || programmingLanguage.isBlank()) throw new IllegalArgumentException("Cannot add an empty input for programming language");

        // Check for duplicate
        if (programmingLanguages.contains(programmingLanguage)) throw new IllegalArgumentException
                (programmingLanguage + " programming language already exists for this employee");

        this.programmingLanguages.add(programmingLanguage);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", supervisorID=" + supervisorID +
                ", companyName='" + companyName + '\'' +
                ", programmingLanguages=" + programmingLanguages +
                ", empDetails=" + empDetails +
                '}';
    }
}
