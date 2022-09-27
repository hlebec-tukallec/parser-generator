import arithmetics.GeneratedParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

public class ArithmeticsTest {
    @Test
    public void testSum() throws ParseException {
        test("1 - 2 - 3", 1 - 2 - 3);
    }

    @Test
    public void testMul() throws ParseException {
        test("11 * 9 * 4", 11 * 9 * 4);
    }

    @Test
    public void testMulSum() throws ParseException {
        test("11 + 6 * 3", 11 + 6 * 3);
    }

    @Test
    public void testParens() throws ParseException {
        test("(11 + 6) * 3", (11 + 6) * 3);
    }

    @Test
    public void testMinusUnary() throws ParseException {
        test("-2", -2);
    }

    @Test
    public void testMinus() throws ParseException {
        test("2 - 2 - 2 - 2", 2 - 2 - 2 - 2);
    }

    @Test
    public void testC() throws ParseException {
        test("5, 4", 5);
    }

    @Test
    public void testCompl() throws ParseException {
        test("5, 4, 3", 5);
    }

    @Test
    public void testExpr() throws ParseException {
        test("(2 + 4), 3", 20);
    }

    @Test(expectedExceptions = ParseException.class)
    public void testErrorNoOperand() throws ParseException {
        testError("5 +");
    }

    @Test(expectedExceptions = ParseException.class)
    public void testErrorMismatchedParens() throws ParseException {
        testError("(5 + 3");
    }

    private void test(String expr, int expected) throws ParseException {
        GeneratedParser parser = new GeneratedParser(expr);
        var result = parser.e();
        System.out.println(result.val);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.val, expected);
    }

    private void testError(String expr) throws ParseException {
        new GeneratedParser(expr).e();
    }
}
