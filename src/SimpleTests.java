import mas.model.Employee;

import java.util.List;

public class SimpleTests {
    public static void main(String[] args) {
        Employee emp1 = new Employee(1, "David", "Town", "Java", "Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");

        Employee emp2 = new Employee(2, "Pant", "John", "Python", 1, "Warsaw", "Rybacka",
                "Poland", "04-547", "PKO", "56347856835654");

        List<Employee> test = Employee.getExtent();
        System.out.println(test);

        // setID test:
        try {
            emp1.setID(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        emp1.setID(3);
        System.out.println(emp1.getID());
    }
}
