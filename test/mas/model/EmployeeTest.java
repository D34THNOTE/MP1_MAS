package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeTest {

    private Employee employee1;
    private Details details1;
    private Employee employee2;
    private Details details2;

    @Before
    public void setup() {
        details1 = new Details("Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");
        details2 = new Details("Warsaw", "Rybacka",
                "Poland", "04-547", "PKO", "56347856835654");

        employee1 = new Employee(1, "David", "Town", LocalDate.of(1999, 6, 24),
                "Java", details1);

        employee2 = new Employee(2, "Pant", "Gary", "John", LocalDate.of(1995, 6, 24),
                "Python", details2);
        employee1.addProgrammingLanguage("Python");
        employee1.addProgrammingLanguage("C++");

        employee2.addProgrammingLanguage("Ruby");
    }

    @After
    public void wipe() {
        List<Employee> toRemove = Arrays.asList(employee1, employee2);
        for (Employee e : toRemove) {
            Employee.removeEmployee(e);
        }
    }

    @Test
    public void testSetGetID() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setID(-1));
        assertThrows(IllegalArgumentException.class, () -> employee1.setID(2));
        employee1.setID(4);
        assertEquals(4, employee1.getID());
    }

    @Test
    public void testSetGetFirstName() { // same for LastName so I won't test it
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName("     "));
        employee1.setFirstName("Test1");
        assertEquals("Test1", employee1.getFirstName());
    }

    @Test
    public void testSetGetMiddleName() { // the optional attribute
        assertThrows(IllegalArgumentException.class, () -> employee1.setMiddleName(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.setMiddleName("     "));
        employee1.setMiddleName(null);
        assertNull(employee1.getMiddleName());
        employee1.setMiddleName("Rich");
        assertEquals("Rich", employee1.getMiddleName());
    }

    @Test
    public void testSetGetBirthdate() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setBirthDate(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.setBirthDate(LocalDate.now().plusDays(1)));

        LocalDate testBd = LocalDate.of(2001, 4, 4);
        employee1.setBirthDate(testBd);
        assertEquals(testBd, employee1.getBirthDate());
    }

    @Test
    public void testSetGetDetails() {
        Details newDetails = new Details("Test", "Streeeeet",
                "Kotlet Country", "27-475", "S.A", "086785745367");

        employee1.setEmpDetails(newDetails);
        assertEquals(newDetails, employee1.getEmpDetails());
    }

    @Test
    public void testSetGetCompanyName() {
        assertThrows(IllegalArgumentException.class, () -> Employee.setCompanyName(null));
        assertThrows(IllegalArgumentException.class, () -> Employee.setCompanyName(""));
        assertThrows(IllegalArgumentException.class, () -> Employee.setCompanyName("   "));
        Employee.setCompanyName("TestCompName");
        assertThrows(IllegalArgumentException.class, () -> Employee.setCompanyName("TestCompName"));
        assertEquals("TestCompName", Employee.getCompanyName());
    }

    @Test
    public void testGetAge() { // derived attribute
        // birthday today case
        LocalDate today = LocalDate.now();
        LocalDate relativeToTestEarly = today.minusYears(25);
        employee1.setBirthDate(relativeToTestEarly);

        assertEquals(25, employee1.getAge());

        // birthday tomorrow case
        LocalDate relativeToTestLate_years = today.minusYears(25);
        LocalDate relativeToTestLate = relativeToTestLate_years.plusDays(1);
        employee1.setBirthDate(relativeToTestLate);
        assertEquals(24, employee1.getAge());


        employee1.setBirthDate(LocalDate.now());
        assertEquals(0, employee1.getAge());
    }

    @Test
    public void testFindEmployeeWithMostLanguages() { // class method
        ArrayList<Employee> expectedList = new ArrayList<>(Arrays.asList(employee1));
        assertEquals(expectedList, Employee.findEmployeesWithMostLanguages());

        employee2.addProgrammingLanguage("C++");
        expectedList.add(employee2);
        assertEquals(expectedList, Employee.findEmployeesWithMostLanguages());

        // Adding employee with 4 languages when the rest has 3
        Employee employee3 = new Employee(3, "Thomas", "Train", LocalDate.of(2003, 11, 11),
                "Node.js", details2);
        employee3.addProgrammingLanguage("R");
        employee3.addProgrammingLanguage("Assembly64");
        employee3.addProgrammingLanguage("Assembly65");
        expectedList.clear();
        expectedList.add(employee3);
        assertEquals(expectedList, Employee.findEmployeesWithMostLanguages());

        // To test the code below add method addEmployee similar to removeEmployee to Employee class temporarily, as the @After will fail if we don't add then back
//        Employee.removeEmployee(employee1);
//        Employee.removeEmployee(employee2);
//        assertThrows(IllegalStateException.class, () -> Employee.findEmployeeWithMostLanguages());
//        Employee.addEmployee(employee1);
//        Employee.addEmployee(employee2);
    }

    @Test
    public void testAddRemoveGetProgrammingLanguage() {
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage("Java"));

        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage("DoesntExist"));

        String testString = "TestProgrammingLanguage";
        employee1.addProgrammingLanguage(testString);
        assertTrue(employee1.getProgrammingLanguages().contains(testString));
        employee1.removeProgrammingLanguage(testString);
        assertFalse(employee1.getProgrammingLanguages().contains(testString));

        // testing unmodifiable list
        assertThrows(UnsupportedOperationException.class, () -> employee1.getProgrammingLanguages().add("Cheetos"));
        assertFalse(employee1.getProgrammingLanguages().contains("Cheetos"));

        // testing removing last element
        employee1.removeProgrammingLanguage("Python");
        employee1.removeProgrammingLanguage("C++");
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage("Java"));
    }

    @Test
    public void testGetExtent() {
        assertThrows(UnsupportedOperationException.class, () -> Employee.getExtent().remove(Employee.getExtent().get(0)));

        try {
            Employee employee3 = new Employee(3, "David", "Town", "  ", LocalDate.of(1999, 6, 24),
                    "Java", details1);
        } catch (IllegalArgumentException ignored) {}

        assertThrows(IndexOutOfBoundsException.class, () -> System.out.println(Employee.getExtent().get(2)));
    }
}
