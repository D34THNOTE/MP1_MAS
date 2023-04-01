package mas.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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
        employee1 = new Employee(1, "David", "McCoffee", "Krzak", LocalDate.of(1996, 5, 14),
                "Java", new Details("Warsaw", "Qwerty",
                "Poland", "12-467", "PKO", "09854987539446"));

        employee2 = new Employee(2, "Manson", "Zegar", "Krzak", LocalDate.of(1998, 7, 23),
                "Java", new Details("Warsaw", "Collection",
                "Poland", "04-453", "mBank", "1987678456"));

        employee3 = new Employee(3, "Filip", "Office", LocalDate.of(1998, 11, 6), "Python",
                new Details("Warsaw", "Internet", "Poland", "04-877", "PKO", "58296575656"));

        employee4 = new Employee(4, "John", "Mazur", LocalDate.of(1975, 4, 23),"Ruby",
                new Details("Warsaw", "Cool", "Poland", "04-279", "C3PO", "1094765677654356"));

        employee5 = new Employee(5, "Kuba", "Głowacki", LocalDate.of(1986, 1, 30), "SQL",
                new Details("Warsaw", "Hyper", "Poland", "04-098", "mBank", "190376584563"));
        employee1.addProgrammingLanguage("SecondProgramming");
        employee1.addProgrammingLanguage("ThirdProgramming");
        employee2.addProgrammingLanguage("Ruby");
        employee3.addProgrammingLanguage("C++");

        Employee.setCompanyName("TestSavedName");

        extentCopy = new ArrayList<>(Employee.getExtent());
    }

    @After
    public void deleteFile() {
        for (Employee e : extentCopy) {
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
    public void persistenceTest() {
        Employee.saveExtent(TEST_FILE_PATH);

        try {
            Employee.loadExtent(TEST_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(Collections.unmodifiableList(extentCopy), Employee.getExtent());
        assertEquals("TestSavedName", Employee.getCompanyName());
    }
}
