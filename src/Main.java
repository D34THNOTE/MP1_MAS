import mas.model.Employee;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Employee emp = new Employee(1, "Mark", "Town", "Java", "Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");

        Employee emp2 = new Employee(2, "Pant", "John", "Python", 1, "Warsaw", "Rybacka",
                "Poland", "04-547", "PKO", "56347856835654");

        List<Employee> test = Employee.getExtent();
        System.out.println(test.get(0));
        System.out.println(emp2);
    }
}