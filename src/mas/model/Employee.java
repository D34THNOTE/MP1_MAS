package mas.model;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Employee implements Serializable {
    // 1. Class extent
    private static List<Employee> extent = new ArrayList<>();

    // 5. Mandatory attribute
    private long ID;
    private String firstName, lastName;
    private LocalDate birthDate;

    // 4. Optional attribute
    private String middleName = null;

    // 7. Class attribute
    private static String companyName;

    // 6. Multi-value attribute(assumption: a potential employee has to know at least one programming language to be employed)
    private Set<String> programmingLanguages = new LinkedHashSet<>();

    // 3. Complex attribute
    private Details empDetails;

    public Employee(long ID, String firstName, String lastName, LocalDate birthDate, String programmingLanguage,
                    Details details) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        addProgrammingLanguage(programmingLanguage);
        this.empDetails = details;

        extent.add(this);
    }

    // 11. Constructor with the optional attribute(Constructor overloading)
    public Employee(long ID, String firstName, String lastName, String middleName, LocalDate birthDate, String programmingLanguage, Details details) {
        setID(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setMiddleName(middleName);
        setBirthDate(birthDate);
        addProgrammingLanguage(programmingLanguage);
        this.empDetails = details;

        extent.add(this);
    }


    public static List<Employee> getExtent() {
        // unmodifiable
        return Collections.unmodifiableList(extent);
    }

    public static void removeEmployee(Employee e) {
        if(e == null) throw new IllegalArgumentException("Please choose an employee to remove");
        if(!extent.contains(e)) throw new IllegalArgumentException("Chosen employee doesn't exist in the system");

        extent.remove(e);
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        // This allows middleName to be set to null
        if(middleName != null && middleName.isBlank()) throw new IllegalArgumentException("Middle name cannot consist of only whitespaces");

        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) throw new IllegalArgumentException("Birth date is required");
        if (birthDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("Birth date cannot be a date past the current moment");

        this.birthDate = birthDate;
    }

    public static String getCompanyName() {
        return Employee.companyName;
    }

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

    public void removeProgrammingLanguage(String programmingLanguage) {
        if(programmingLanguage == null || programmingLanguage.isBlank()) throw new IllegalArgumentException("Pass a non-empty language name to remove it");
        if(!programmingLanguages.contains(programmingLanguage)) throw new IllegalArgumentException("Passed language isn't assigned to this employee");
        if((programmingLanguages.size() <= 1)) throw new IllegalArgumentException("Cannot remove the last language from the employee's list");

        programmingLanguages.remove(programmingLanguage);
    }

    public Details getEmpDetails() {
        return empDetails;
    }

    public void setEmpDetails(Details empDetails) {
        this.empDetails = empDetails;
    }

    // 8. Derived attribute
    public int getAge() {
        Period age = Period.between(birthDate, LocalDate.now());
        return age.getYears();
    }

    // 9. Class method - operates on class extent
    public static Employee findEmployeeWithMostLanguages() {
        if(extent.isEmpty()) throw new IllegalStateException("No employees exist in the system");

        Employee mostLanguagesEmployee = extent.get(0);
        for(int i=1; i < extent.size(); i++) {
            if(extent.get(i).getProgrammingLanguages().size() > mostLanguagesEmployee.getProgrammingLanguages().size()) {
                mostLanguagesEmployee = extent.get(i);
            }
        }

        return mostLanguagesEmployee;
    }

    public static void saveExtent(String path) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            // order is important!!
            outputStream.writeObject(extent);
            outputStream.writeObject(companyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked") // They told me to do this: https://stackoverflow.com/questions/5201555/unchecked-cast-warning-how-to-avoid-this
    public static void loadExtent(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) throw new FileNotFoundException("No file was found for the provided path");

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
            // order is important!!
            extent = (ArrayList<Employee>) inputStream.readObject();
            companyName = (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 10. Method overriding
    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                (middleName == null ? "" : ", middleName='" + middleName) + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + getAge() +
                ", birthDate=" + birthDate +
                ", programmingLanguages=" + programmingLanguages +
                ", empDetails=" + empDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return ID == employee.ID && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && birthDate.equals(employee.birthDate) && Objects.equals(middleName, employee.middleName) && programmingLanguages.equals(employee.programmingLanguages) && empDetails.equals(employee.empDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, firstName, lastName, birthDate, middleName, programmingLanguages, empDetails);
    }
}
