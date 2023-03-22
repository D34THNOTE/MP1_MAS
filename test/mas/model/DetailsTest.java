package mas.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DetailsTest {

    private Details details1;
    private Details details2;

    @Before
    public void setup() {
        details1 = new Details("Warsaw", "Katiuszy", "Poland", "04-453", "PKO", "018295743434");
        details2 = new Details("Warsaw", "Rybacka", "Poland", "04-235", "mBank", "46657456458");
    }

    @Test
    public void testSetGetCity() { // This test can be exactly the same for Street, Country and Bank - therefore won't bother
        assertThrows(IllegalArgumentException.class, () -> details1.setCity(null));
        assertThrows(IllegalArgumentException.class, () -> details1.setCity(""));
        assertThrows(IllegalArgumentException.class, () -> details1.setCity("    "));
        details1.setCity("TestCity");
        assertEquals("TestCity", details1.getCity());
    }

    @Test
    public void testSetGetPostalCode() {
        assertThrows(IllegalArgumentException.class, () -> details1.setPostalCode(null));
        assertThrows(IllegalArgumentException.class, () -> details1.setPostalCode(""));
        assertThrows(IllegalArgumentException.class, () -> details1.setPostalCode("    "));
        assertThrows(IllegalArgumentException.class, () -> details1.setPostalCode("0324-234"));
        assertThrows(IllegalArgumentException.class, () -> details1.setPostalCode("03-a234"));
        assertThrows(IllegalArgumentException.class, () -> details1.setPostalCode("er-sgf"));
        details1.setPostalCode("21-753");
        assertEquals("21-753", details1.getPostalCode());
    }

    @Test
    public void testSetGetAccountNumber() {
        assertThrows(IllegalArgumentException.class, () -> details1.setAccountNumber(null));
        assertThrows(IllegalArgumentException.class, () -> details1.setAccountNumber(""));
        assertThrows(IllegalArgumentException.class, () -> details1.setAccountNumber("    "));
        assertThrows(IllegalArgumentException.class, () -> details1.setAccountNumber("sadegteytjtyjr6"));
        assertThrows(IllegalArgumentException.class, () -> details1.setAccountNumber("1243cd434gf"));
        assertThrows(IllegalArgumentException.class, () -> details1.setAccountNumber("23546575t"));
        details1.setAccountNumber("909089090089909");
        assertEquals("909089090089909", details1.getAccountNumber());
    }
}
