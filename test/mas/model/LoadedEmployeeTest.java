package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

        employee1 = new Employee(1, "David", "Town", "Java", details1);
        employee2 = new Employee(2, "Pant", "John", "Python", 1, details2);

        Employee.saveExtent(TEST_FILE_PATH);
        try {
            Employee.loadExtent(TEST_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Funny story. All but the first test would fail when I run EmployeeTest and I was extremely confused as to why. Turns out
    // that @Before would try to re-initialize employee1(and 2) BEFORE every next test, but since the ID was already taken as every
    // new Employee is added to the static "extent" list it would throw a "Passed ID is already taken" Exception. Therefore I had
    // to make a .clearExtent() method, at least for now
    @After
    public void deleteFile() {
        Employee.removeEmployee();

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
    public void testSetGetFirstName() { // same for LastName so I won't test it
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName("     "));
        employee1.setFirstName("Test1");
        assertEquals("Test1", employee1.getFirstName());
    }

    @Test
    public void testSetGetID() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setID(-1));
        assertThrows(IllegalArgumentException.class, () -> employee1.setID(2));
        employee1.setID(4);
        assertEquals(4, employee1.getID());
    }

    @Test
    public void testGetSupervisorID() {
        assertNull(employee1.getSupervisorID());
        employee1.setSupervisorID(2);
        assertEquals(2, (long) employee1.getSupervisorID());
    }
    @Test
    public void testSetSupervisorID() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setSupervisorID(-1));
        assertThrows(IllegalArgumentException.class, () -> employee1.setSupervisorID(1));
        assertThrows(IllegalArgumentException.class, () -> employee2.setSupervisorID(1));
        assertThrows(IllegalArgumentException.class, () -> employee2.setSupervisorID(3));

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
    public void testAddProgrammingLanguage() {
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage("   "));
        assertThrows(IllegalArgumentException.class, () -> employee1.addProgrammingLanguage("Java"));
    }

    @Test
    public void testGetNumberOfProgrammingLanguages() {
        assertEquals(1, employee1.getNumberOfProgrammingLanguages());
        employee1.addProgrammingLanguage("Ruby");
        assertEquals(2, employee1.getNumberOfProgrammingLanguages());
    }
}
