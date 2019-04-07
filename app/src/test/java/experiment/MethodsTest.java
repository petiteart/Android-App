package experiment;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class MethodsTest {

    final int EXPECTED = -1;
    @Test
    public void testLessThanZero(){
        Methods method = new Methods();
        int result = method.Multiple3n5(-1);
        assertEquals(EXPECTED, result);
    }

    @Test
    public void testGreaterThanThousand(){
        Methods method = new Methods();
        int result = method.Multiple3n5(41256);
        assertEquals(EXPECTED, result);
    }

    @Ignore
    public void testSumOfDivisive(){
        Methods method = new Methods();
        int result = method.Multiple3n5(16);
        assertEquals(23, result);
    }

}