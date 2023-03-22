package mas.model;

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

    @Test
    public void testSetFirstName() { // same for LastName so I won't test it
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> employee1.setFirstName("     "));
    }

    @Test
    public void testSetID() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setID(-1));
        assertThrows(IllegalArgumentException.class, () -> employee1.setID(2));
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


}
