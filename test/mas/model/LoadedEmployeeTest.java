package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LoadedEmployeeTest {
    private Employee employee1;
    private Details details1;
    private Employee employee2;
    private Details details2;
    private String TEST_FILE_PATH = "test.dat";

    @Before
    public void setup() {
        details1 = new Details("Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");
        details2 = new Details("Warsaw", "Rybacka",
                "Poland", "04-547", "PKO", "56347856835654");

        employee1 = new Employee(1, "David", "Town", LocalDate.of(1992, 4, 12),"Python", details1);
        employee2 = new Employee(2, "Pant", "John", LocalDate.of(2001, 9, 23), "Java", details2);

        Employee.saveExtent(TEST_FILE_PATH);
        try {
            Employee.loadExtent(TEST_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteFile() {
        List<Employee> toRemove = new ArrayList<>(Employee.getExtent());
        for (Employee e : toRemove) {
            Employee.removeEmployee(e);
        }

        boolean success = false;
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            success = file.delete();
        }

        try {
            if(!success) throw new IOException("There was a problem deleting the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetGetID() {
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setID(-1));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setID(2));
        Employee.getExtent().get(0).setID(4);
        assertEquals(4, Employee.getExtent().get(0).getID());
    }

    @Test
    public void testSetGetFirstName() { // same for LastName so I won't test it
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setFirstName("     "));
        Employee.getExtent().get(0).setFirstName("Test1");
        assertEquals("Test1", Employee.getExtent().get(0).getFirstName());
    }

    @Test
    public void testSetGetMiddleName() { // the optional attribute
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setMiddleName(""));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setMiddleName("     "));
        Employee.getExtent().get(0).setMiddleName(null);
        assertNull(Employee.getExtent().get(0).getMiddleName());
        Employee.getExtent().get(0).setMiddleName("Rich");
        assertEquals("Rich", Employee.getExtent().get(0).getMiddleName());
    }

    @Test
    public void testSetGetBirthdate() {
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setBirthDate(null));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).setBirthDate(LocalDate.now().plusDays(1)));

        LocalDate testBd = LocalDate.of(2001, 4, 4);
        Employee.getExtent().get(0).setBirthDate(testBd);
        assertEquals(testBd, Employee.getExtent().get(0).getBirthDate());
    }

    @Test
    public void testSetGetDetails() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setEmpDetails(null));
        Details newDetails = new Details("Test", "Streeeeet",
                "Kotlet Country", "27-475", "S.A", "086785745367");

        Employee.getExtent().get(0).setEmpDetails(newDetails);
        assertEquals(newDetails, Employee.getExtent().get(0).getEmpDetails());
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
        Employee.getExtent().get(0).setBirthDate(relativeToTestEarly);

        assertEquals(25, Employee.getExtent().get(0).getAge());

        // birthday tomorrow case
        LocalDate relativeToTestLate_years = today.minusYears(25);
        LocalDate relativeToTestLate = relativeToTestLate_years.plusDays(1);
        Employee.getExtent().get(0).setBirthDate(relativeToTestLate);
        assertEquals(24, Employee.getExtent().get(0).getAge());


        Employee.getExtent().get(0).setBirthDate(LocalDate.now());
        assertEquals(0, Employee.getExtent().get(0).getAge());
    }

    @Test
    public void testFindEmployeeWithMostLanguages() { // class method
        Employee.getExtent().get(0).addProgrammingLanguage("C");
        ArrayList<Employee> expectedList = new ArrayList<>(Arrays.asList(Employee.getExtent().get(0)));

        assertEquals(expectedList, Employee.findEmployeesWithMostLanguages());

        Employee.getExtent().get(1).addProgrammingLanguage("C++");
        expectedList.add(Employee.getExtent().get(1));
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

        // Don't need to remove it like in EmployeeTest because here we reload extent from file for every test, leaving this here because I found
        // it interesting
        //Employee.removeEmployee(employee3);

        // To test the code below add method addEmployee similar to removeEmployee to Employee class temporarily, as the @After will fail if we don't add then back
//        Employee.removeEmployee(Employee.getExtent().get(0));
//        Employee.removeEmployee(Employee.getExtent().get(1));
//        assertThrows(IllegalStateException.class, () -> Employee.findEmployeeWithMostLanguages());
//        Employee.addEmployee(Employee.getExtent().get(0));
//        Employee.addEmployee(Employee.getExtent().get(1));
    }

    @Test
    public void testAddRemoveGetProgrammingLanguage() {
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).addProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).addProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).addProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).addProgrammingLanguage("Python"));

        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).removeProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).removeProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).removeProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).removeProgrammingLanguage("DoesntExist"));

        String testString = "TestProgrammingLanguage";
        Employee.getExtent().get(0).addProgrammingLanguage(testString);
        assertTrue(Employee.getExtent().get(0).getProgrammingLanguages().contains(testString));
        Employee.getExtent().get(0).removeProgrammingLanguage(testString);
        assertFalse(Employee.getExtent().get(0).getProgrammingLanguages().contains(testString));

        // testing unmodifiable list
        assertThrows(UnsupportedOperationException.class, () -> Employee.getExtent().get(0).getProgrammingLanguages().add("Cheetos"));
        assertFalse(Employee.getExtent().get(0).getProgrammingLanguages().contains("Cheetos"));

        // testing removing last element
        assertThrows(IllegalArgumentException.class, () -> Employee.getExtent().get(0).removeProgrammingLanguage("Python"));
    }

    @Test
    public void testGetAddExtent() {
        assertThrows(UnsupportedOperationException.class, () -> Employee.getExtent().remove(Employee.getExtent().get(0)));

        try {
            Employee employee3 = new Employee(3, "David", "Town", "  ", LocalDate.of(1999, 6, 24),
                    "Java", details1);
        } catch (IllegalArgumentException ignored) {}

        assertThrows(IndexOutOfBoundsException.class, () -> System.out.println(Employee.getExtent().get(2)));

        Employee employee3 = new Employee(3, "Marry", "Town", "snikch", LocalDate.of(1999, 6, 24),
                "Java", details1);
        assertTrue(Employee.getExtent().contains(employee3));
    }
}