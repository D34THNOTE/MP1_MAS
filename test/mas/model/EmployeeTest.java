package mas.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    private Employee employee1;
    private Employee employee2;

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


}
