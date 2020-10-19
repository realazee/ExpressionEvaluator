
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpressionEvaluator.
 */
public class ExpressionEvaluator extends Application {

	/** The data stack. */
	private GenericStack<Integer> dataStack = new GenericStack<Integer>();

	/** The operator stack. */
	private GenericStack<String>  operStack = new GenericStack<String>();;

	/** The output results that will be displayed to the user */
	private Text outputResults;

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	private GridPane main = new GridPane();

	private TextField expressionField;
	private String answer;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Scene scene = new Scene(main, 400, 400);
		Label textLabel = new Label("Enter data here");
		Label output = new Label("output displayed here");
		Button eval = new Button("Evaluate");
		eval.setOnAction(e -> output.setText(evaluateExpression(expressionField.getText())));
		
		
		primaryStage.setTitle("Expression Evaluator");	
		expressionField = new TextField();



		main.add(textLabel,0,0);
		main.add(expressionField,0,100);
		main.add(output,100,300);
		main.add(eval,100 , 600);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	/*
	private void setAnswerLabel() {
		answer = evaluateExpression(expressionField.getText());
		System.out.println(answer);
	}
	*/
	/**
	 * Evaluates the expression by first massaging the string, and then splitting it
	 * into "tokens" that are either operations or data operands. 
	 *
	 * @param str this is the string that the user typed in the text field
	 * @return the string that is the result of the evaluation. It should include the original
	 *         expression and the value that it equals, or indicate if some error occurred.
	 */
	private String[] strArray;
	private int d1;
	private int d2;
	protected String evaluateExpression(String str) {
		str = str.replaceAll("([+-/\\(\\)\\*])", " $1 ");
		String orgInput = str;
		strArray = str.split("\\s+");
		

//this is still not operational. it returns the last digit entered somehow.
		for(int i = 0; i < strArray.length; i++) {
			System.out.println(strArray[i]);
		}

		for(int i = 0; i < strArray.length; i++) {
			try {
				dataStack.push(Integer.parseInt(strArray[i]));
			}catch(Exception e) {
				if(strArray[i] == "(") {
					operStack.push(strArray[i]);
				}

				if(strArray[i] == ")") {
					while(operStack.peek() != "(") {
						dataStack.push(evaluateTopOfStack());
					}
					operStack.pop();
				}

				if(strArray[i] == "+" || strArray[i] == "-" || strArray[i] == "*" ||strArray[i] == "/") {
					if(getPrecedence(operStack.peek()) >= getPrecedence(strArray[i])) {
						dataStack.push(evaluateTopOfStack());

					}
					operStack.push(strArray[i]);
				}

			}

		}
		while(operStack.isEmpty() == false) {
			dataStack.push(evaluateTopOfStack());
		}
		return orgInput + " = " + dataStack.pop();
		
	}




	//failed code 
	/*		
				while(operStack.isEmpty() == false && getPrecedence(operStack.peek()) >= getPrecedence(strArray[i])) {
					dataStack.push(evaluateTopOfStack());
				}

				operStack.push(strArray[i]);
			}
		}

		while(operStack.isEmpty() == false) {

		}


	 */
	/*
		for(int i = 0; i < strArray.length; i++) {
			if(operStack.isEmpty()) {
				if(strArray[i] == "+" ||strArray[i] == "-" || strArray[i] == "*" || strArray[i] == "/") {
					operStack.push(strArray[i]);
				}
			}
			if(strArray[i] == "(") {
				operStack.push(strArray[i]);
			}
			if(strArray[i] == "+" ||strArray[i] == "-" && operStack.peek() != "(") {
				dataStack.push(evaluateTopOfStack());
				operStack.push(strArray[i]);
			}
			if(strArray[i] == "*" || strArray[i] == "/") {
				if(operStack.peek() == "*" || operStack.peek() == "/") {
					dataStack.push(evaluateTopOfStack());
					operStack.push(strArray[i]);
				}
			}
			if(strArray[i] == ")") {
				while(operStack.peek() != "(") {
					dataStack.push(evaluateTopOfStack());
				}
			}
			else {
				dataStack.push(Integer.parseInt(strArray[i]));
			}
		}
		return str + "=" + dataStack.pop();

	 */


private int getPrecedence(String op) {
	if(op == "(") {
		return 3;
	}
	if(op == "*" || op =="/") {
		return 2;
	}
	else {
		return 1;
	}
}
private int evaluateTopOfStack() {
	d2 = dataStack.pop();
	d1 = dataStack.pop();
	String op = operStack.pop();
	return calculate(op, d1, d2);
}
/*
private int evaluateStack() {
	int result = 0;
	while(dataStack.getSize() != 0) {
		d2 = dataStack.pop();
		d1 = dataStack.pop();
		String op = operStack.pop();
		dataStack.push(calculate(op, d1, d2));
		if(dataStack.getSize() ==1) {
			result = dataStack.pop();
			break;
		}

	}
	return result;
}
*/


private int calculate(String operator, int a, int b) {
	if(operator.equals("+")) {
		return a+b;
	}
	if(operator.equals("-")) {
		return a-b;
	}
	if(operator.equals("*")) {
		return a*b;
	}
	else{
		return a/b;
	}
}
/**
 * The main method.
 *
 * @param args the arguments
 */
public static void main(String[] args) {
	Application.launch(args);

}

}
