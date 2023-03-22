import mas.model.Employee;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Employee emp = new Employee(1, "Mark", "Town", "Java", "Warsaw", "Katiuszy",
                "Poland", "04-453", "PKO", "09854987539446");

        List test = Employee.getExtent();
        System.out.println(test.get(0).toString());
    }
}