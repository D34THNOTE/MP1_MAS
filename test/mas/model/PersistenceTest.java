package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PersistenceTest {

    private ArrayList<Employee> extentCopy;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;

    private String TEST_FILE_PATH = "test.dat";

    @Before
    public void setup() {
        employee1 = new Employee(1, "David", "McCoffee", "Java", "Warsaw", "Qwerty",
                "Poland", "12-467", "PKO", "09854987539446");
//        employee2 = new Employee(2, "Manson", "Zegar", "Java", "Warsaw", "Collection",
//                "Poland", "04-453", "mBank", "1987678456");
//        employee3 = new Employee(3, "Filip", "Office", "Python", 1, "Warsaw", "Internet",
//                "Poland", "04-877", "PKO", "58296575656");
//        employee4 = new Employee(4, "John", "Mazur", "Ruby", 3, "Warsaw", "Cool",
//                "Poland", "04-279", "C3PO", "1094765677654356");
//        employee5 = new Employee(5, "Kuba", "Głowacki", "SQL", 3, "Warsaw", "Hyper",
//                "Poland", "04-098", "mBank", "190376584563");
//        employee1.addProgrammingLanguage("SecondProgramming");
//        employee1.addProgrammingLanguage("ThirdProgramming");
//        employee2.addProgrammingLanguage("Ruby");
//        employee3.addProgrammingLanguage("C++");

        extentCopy = new ArrayList<>(Employee.getExtent());
    }

    @After
    public void deleteFile() {
        Employee.clearExtent();

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
    public void persistenceTest() {
        Employee.saveExtent(TEST_FILE_PATH);

        try {
            Employee.loadExtent(TEST_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Employee> expectedList = Collections.unmodifiableList(extentCopy);
        for (int i=0; i < expectedList.size(); i++) {
            System.out.println(expectedList.get(i));
            System.out.println(Employee.getExtent().get(i));
            assertEquals(expectedList.get(i), Employee.getExtent().get(i));
        }
    }
}
