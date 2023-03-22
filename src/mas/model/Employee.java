package mas.model;

import java.io.*;
import java.util.*;

public class Employee implements Serializable {
    // 1. Class extent
    private static List<Employee> extent = new ArrayList<>();

    // 5. Mandatory attribute
    private long ID;
    private String firstName, lastName;

    // 4. Optional attribute(using Long instead of long to make it nullable)
    private Long supervisorID = null;

    // 7. Class attribute
    private static String companyName;

    // 6. Multi-value attribute(assumption: every employee has to know at least one programming language to be employed)
    private Set<String> programmingLanguages = new LinkedHashSet<>();

    // 3. Complex attribute
    private Details empDetails;

    public Employee(long ID, String firstName, String lastName, String programmingLanguage,
                    String city, String street, String country, String postalCode, String bankName, String accountNumber) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        addProgrammingLanguage(programmingLanguage);
        this.empDetails = new Details(city, street, country, postalCode, bankName, accountNumber);

        extent.add(this);
    }

    // 11. Constructor with the optional attribute(Constructor overloading)
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

    // 9. Class method
    public static List<Employee> getExtent() {
        // unmodifiable
        return Collections.unmodifiableList(extent);
    }

    // TODO is this allowed to stay?
    public static void clearExtent() {
        extent.clear();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        // long is a primitive time which isn't nullable, so don't need to check for nulls
        if(ID < 0) throw new IllegalArgumentException("ID must be a positive number");
        for (Employee possibleDuplicateID: extent) {
            if(possibleDuplicateID.getID() == ID) throw new IllegalArgumentException("Passed ID is already taken");
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

    public Long getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(long supervisorID) {
        if(supervisorID < 0) throw new IllegalArgumentException("ID must be a positive number");

        if (this.ID == supervisorID) throw new IllegalArgumentException("An employee cannot be their own supervisor");
        if (this.supervisorID != null && this.supervisorID == supervisorID) throw new IllegalArgumentException("This ID is already set as the supervisor for the chosen employee");

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

    // 9. Class method
    public static String getCompanyName() {
        return Employee.companyName;
    }

    // 9. Class method
    public static void setCompanyName(String companyName) {
        if(companyName == null || companyName.isBlank()) throw new IllegalArgumentException("Company name cannot be empty");
        if(Employee.companyName != null && Employee.companyName.equals(companyName)) throw new IllegalArgumentException("Entered company name is already used");

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

    // 8. Derived attribute
    public int getNumberOfProgrammingLanguages() {
        return this.programmingLanguages.size();
    }

    // 10. Method overriding
    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", supervisorID=" + (supervisorID == null ? "Unassigned" : supervisorID) +
                ", companyName='" + companyName + '\'' +
                ", programmingLanguages=" + programmingLanguages +
                ", empDetails=" + empDetails +
                '}';
    }

    public static void saveExtent(String path) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(extent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked") // They told me to do this: https://stackoverflow.com/questions/5201555/unchecked-cast-warning-how-to-avoid-this
    public static void loadExtent(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) throw new FileNotFoundException("No file was found for the provided path");

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
            extent = (ArrayList<Employee>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return ID == employee.ID && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && Objects.equals(supervisorID, employee.supervisorID) && programmingLanguages.equals(employee.programmingLanguages) && empDetails.equals(employee.empDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, firstName, lastName, supervisorID, programmingLanguages, empDetails);
    }
}
