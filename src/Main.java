import mas.model.Details;
import mas.model.Employee;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Details details = new Details("Warsaw", "Katiuszy",
//                "Poland", "04-453", "PKO", "09854987539446");
//        Employee emp = new Employee(1, "Mark", "Town", "Java", details);
//
//        Details details2 = new Details("Warsaw", "Rybacka",
//                "Poland", "04-547", "PKO", "56347856835654");
//        Employee emp2 = new Employee(2, "Pant", "John", "Python", 1, details2);
//
//        Employee.setCompanyName("BMI");
//        Employee.saveExtent("test.dat");

        try {
            Employee.loadExtent("test.dat");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Employee.getExtent());
        System.out.println(Employee.getCompanyName());
    }
}