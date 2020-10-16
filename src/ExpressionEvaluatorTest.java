import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpressionEvaluatorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;
		String[] results;
		
		// test #1
		evalResults = exprEval.evaluateExpression("3+6*9-4");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # 1\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("53"));
		
		// test #2 - with arbitrary spacing
		evalResults = exprEval.evaluateExpression("3+ 6   * 9  -4");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # 2\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("53"));
		
		// test #3
		evalResults = exprEval.evaluateExpression("3*9*7-4");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # 3\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("185"));
		
		// test #4
		evalResults = exprEval.evaluateExpression("3*9*(7-4)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # 4\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("81"));
		
		// test #5
		evalResults = exprEval.evaluateExpression("3*(9*7-4)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # 5\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("177"));
		
		// test #6
		evalResults = exprEval.evaluateExpression("((6-7)*9+15/3)/4+((5*2)+7)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # 6\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("16"));
		
	}

}
