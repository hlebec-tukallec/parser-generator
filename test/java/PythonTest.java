//import org.testng.annotations.Test;
//
//import java.text.ParseException;
//
//public class PythonTest {
//    final String SIMPLE_CORRECT = "lambda x : x + 5";
//    final String SIMPLE_HI_CORRECT = "lambda x, n, hii : hii * 5 - (n + x)";
//    final String SIMPLE_DIV_CORRECT = "lambda x: x / x";
//    final String ALL_OP_CORRECT = "lambda x: 100 * 5674 * (38 - x) / 34 + x";
//    final String NO_LAMBDA_VAR_CORRECT = "lambda : 3 + 6";
//
//    @Test
//    public void correct() throws ParseException {
//        new GeneratedParser(SIMPLE_CORRECT).e();
//        new GeneratedParser(SIMPLE_HI_CORRECT).e();
//        new GeneratedParser(SIMPLE_DIV_CORRECT).e();
//        new GeneratedParser(ALL_OP_CORRECT).e();
//        new GeneratedParser(NO_LAMBDA_VAR_CORRECT).e();
//    }
//
//    @Test(expectedExceptions = ParseException.class)
//    public void incorrect() throws ParseException {
//        new GeneratedParser("x: 4673 + 5").e();
//        new GeneratedParser("lambds a : 54 + 3").e();
//        new GeneratedParser("lambda x x + 43").e();
//        new GeneratedParser("lambda x n: 54 - 3").e();
//        new GeneratedParser("lambda x, n: 54 + (643 - x").e();
//    }
//
//}
