package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LoadedEmployeeTest {
    private Employee employee1;
    private Details details1;
    private Employee employee2; // employee1 is this employee's supervisor
    private Details details2;
    private String TEST_FILE_PATH = "test.dat";

    @Before
    public void setup() {
        details1 = new Details("Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");
        details2 = new Details("Warsaw", "Rybacka",
                "Poland", "04-547", "PKO", "56347856835654");

        employee1 = new Employee(1, "David", "Town", LocalDate.of(1992, 4, 12),"Java", details1);
        employee2 = new Employee(2, "Pant", "John", LocalDate.of(2001, 9, 23), "Python", details2);

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
        assertNull(employee1.getMiddleName());
        employee1.setMiddleName("Rich");
        assertEquals("Rich", employee1.getMiddleName());
    }

    @Test
    public void testSetGetBirthdate() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setBirthDate(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.setBirthDate(LocalDate.now().plusDays(1)));
        employee1.setBirthDate(LocalDate.of(2001, 4, 4));
        assertEquals(LocalDate.of(2001, 4, 4), employee1.getBirthDate());
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
        LocalDate today = LocalDate.now();
        LocalDate relativeToTest = today.minusYears(25);
        employee1.setBirthDate(relativeToTest);

        assertEquals(25, employee1.getAge());

        employee1.setBirthDate(LocalDate.now());
        assertEquals(0, employee1.getAge());
    }

    @Test
    public void testFindEmployeeWithMostLanguages() { // class method
        assertEquals(employee1, Employee.findEmployeeWithMostLanguages());
    }

    @Test
    public void testAddRemoveGetProgrammingLanguage() {
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage("Java"));
        String testString = "TestProgrammingLanguage";
        employee1.addProgrammingLanguage(testString);
        assertTrue(employee1.getProgrammingLanguages().contains(testString));

        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> employee1.removeProgrammingLanguage("DoesntExist"));
        employee1.removeProgrammingLanguage(testString);
        assertFalse(employee1.getProgrammingLanguages().contains(testString));

        // testing unmodifiable list
        assertThrows(UnsupportedOperationException.class, () -> employee1.getProgrammingLanguages().add("Cheetos"));
        assertFalse(employee1.getProgrammingLanguages().contains("Cheetos"));
    }
}
