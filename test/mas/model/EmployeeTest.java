package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    private Employee employee1;
    private Employee employee2; // employee1 is this employee's supervisor

    @Before
    public void setup() {
        employee1 = new Employee(1, "David", "Town", "Java", "Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");
        employee2 = new Employee(2, "Pant", "John", "Python", 1, "Warsaw", "Rybacka",
                "Poland", "04-547", "PKO", "56347856835654");
    }

    // Funny story. All but the first test would fail when I run EmployeeTest and I was extremely confused as to why. Turns out
    // that @Before would try to re-initialize employee1(and 2) BEFORE every next test, but since the ID was already taken as every
    // new Employee is added to the static "extent" list it would throw a "Passed ID is already taken" Exception. Therefore I had
    // to make a .clearExtent() method, at least for now
    @After
    public void wipe() {
        Employee.clearExtent();
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
    public void testSetGetSupervisorID() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setSupervisorID(-1));
        assertThrows(IllegalArgumentException.class, () -> employee1.setSupervisorID(1));
        assertThrows(IllegalArgumentException.class, () -> employee2.setSupervisorID(1));
        assertThrows(IllegalArgumentException.class, () -> employee2.setSupervisorID(3));
        employee1.setSupervisorID(2);
        assertEquals(2, (long) employee1.getSupervisorID());
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
}
