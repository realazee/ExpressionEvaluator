
import java.util.ArrayList;
import java.util.Arrays;

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
	private GenericStack<Double> dataStack = new GenericStack<Double>();

	/** The operator stack. */
	private GenericStack<String>  operStack = new GenericStack<String>();;

	/** The output results that will be displayed to the user */

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	private GridPane main = new GridPane();

	private TextField expressionField;

	@Override
	public void start(Stage primaryStage) throws Exception {

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



	/**
	 * Evaluates the expression by first massaging the string, and then splitting it
	 * into "tokens" that are either operations or data operands. 
	 *
	 * @param str this is the string that the user typed in the text field
	 * @return the string that is the result of the evaluation. It should include the original
	 *         expression and the value that it equals, or indicate if some error occurred.
	 */
	private String[] strArray;
	private double d1;
	private double d2;

	protected String evaluateExpression(String str) {
		//implicit muliplication string processor
		for(int i = 0; i < str.length() - 1; i++) {
			if(isNumber(str.substring(i, i+1)) && str.substring(i+1, i+2).equals("(")) {
				str = addChar(str, '*', i+1);
			}
			if(str.substring(i, i+1).equals(")") && str.substring(i+1, i+2).equals("(")) {
				str = addChar(str, '*', i+1);
			}
			if(str.substring(i, i+1).equals(")") && isNumber(str.substring(i+1, i+2))) {
				str = addChar(str, '*', i+1);
			}
		}


		str = str.replaceAll("(\\+|-|/|\\(|\\)|\\*)", " $1 ");
		str = str.trim();
		String orgInput = str;
		strArray = str.split("\\s+");


		//negation processor




		ArrayList<String> temp = new ArrayList<String>();


		boolean isNegation = false;



		if(strArray[0].equals("-")) {
			isNegation = true;
		}
		else {
			temp.add(strArray[0]);
		}
		for(int i = 1; i < strArray.length; i++) {
			/*
			if(i == 0 && strArray[i].equals("-")) {
				temp.add("-" + strArray[i+1]);
				continue;
			}
			 */
			if(isNegation) {
				if(isNumber(strArray[i])){
					temp.add("-" + strArray[i]);
					isNegation = false;
					continue;
				}
				else {
					return "Op Error: " + orgInput;
				}
			}
			if(isOperator(strArray[i-1]) && strArray[i].equals("-")){
				isNegation = true;
				continue;
			}

			temp.add(strArray[i]);


		}





		String[] tempStrArray = new String[temp.size()];
		for(int i = 0; i < temp.size(); i++) {
			tempStrArray[i] = temp.get(i);
		}
		strArray = tempStrArray;
		System.out.println(Arrays.toString(strArray));






		//op error checking
		if(strArray[strArray.length - 1].equals("*")|| strArray[strArray.length-1].equals("/")||strArray[strArray.length-1].equals("-")||strArray[strArray.length-1].equals("+")) {
			System.out.println("op errored 1");
			return "Op Error: " + orgInput;
		}
		for(int i = 0; i < strArray.length-2; i++) {
			if(strArray[i].equals("-") && strArray[i+1].equals("-") && strArray[i+2].equals("-")) {
				return "Op Error: " + orgInput;
			}
		}

		for(int i = 0; i < strArray.length-1; i++) {
			//System.out.println(strArray[i]);
			if(i == 0 && !strArray[i].equals("(")) {
				if(strArray[i].equals("+") || strArray[i].equals("-") || strArray[i].equals("*") || strArray[i].equals("/")) {
					System.out.println("op errored 2");
					return "Op Error: " + orgInput;
				}
			}
			if(strArray[i].equals("+") || strArray[i].equals("-") || strArray[i].equals("*") || strArray[i].equals("/") || strArray[i].equals("(")) {
				if(strArray[i+1].equals("+") ||strArray[i+1].equals("*") || strArray[i+1].equals("/") || strArray[i+1].equals(")")) {
					System.out.println("op errored 3");
					return "Op Error: " + orgInput;
				}
			}

		}


		//paren error checking
		int parenCount =0;
		for(int i = 0; i < strArray.length; i++) {
			if(strArray[i].equals("(")) {
				parenCount++;
			}
			if(strArray[i].equals(")")) {
				parenCount--;
			}
		}
		System.out.println(parenCount);
		if(parenCount != 0) {
			return "Paren Error:" + orgInput;
		}

		//data error checking
		System.out.println(Arrays.toString(strArray));
		for(int i = 0; i < strArray.length; i++) {
			if(strArray[i].contains("f") || strArray[i].contains("d")) {
				return "Data Error: 1" + orgInput;
			}
			if(!strArray[i].equals("(") && !strArray[i].equals(")") && !strArray[i].equals("+") && !strArray[i].equals("-") && !strArray[i].equals("*") && !strArray[i].equals("/")) {
				if(!isNumber(strArray[i])) {
					return "Data Error: 2" + orgInput;
				}
			}		
		}
		for(int i = 0; i < strArray.length - 1; i++) {

			if(isNumber(strArray[i]) && isNumber(strArray[i+1])){
				return "Data Error: 3" + orgInput;
			}
		}










		//evaluation block
		double evalResult;
		for(int i = 0; i < strArray.length; i++) {
			//	System.out.println("dataStack = " + dataStack.toString());
			//System.out.println("operStack = " + operStack.toString());

			if(strArray[i].equals("(")) {				
				operStack.push(strArray[i]);
			}
			else if(strArray[i].equals(")")) {
				while(!operStack.peek().equals("(")) {
					evalResult = evaluateTopOfStack();
					if(Double.isInfinite(evalResult)) {
						return "Div0 Error:" + orgInput;
					}
					dataStack.push(evalResult);				
				}
				operStack.pop();				
			}
			else if(strArray[i].equals("+") || strArray[i].equals("-") || strArray[i].equals("*") ||strArray[i].equals("/")) {

				while(!shouldPush(strArray[i]) && dataStack.getSize() >=2) {
					evalResult = evaluateTopOfStack();
					if(Double.isInfinite(evalResult)) {
						return "Div0 Error:" + orgInput;
					}
					dataStack.push(evalResult);		
				}
				operStack.push(strArray[i]);
			}

			else {

				dataStack.push(Double.parseDouble(strArray[i]));

			}
		}
		while(operStack.isEmpty() == false && dataStack.getSize() >= 2) {
			evalResult = evaluateTopOfStack();
			if(Double.isInfinite(evalResult)) {
				return "Div0 Error:" + orgInput;
			}
			dataStack.push(evalResult);		
		}
		return orgInput + " = " + dataStack.pop();
	}







	private boolean isOperator(String s) {
		if(s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-") || s.equals("(")) {
			return true;
		}
		else {
			return false;
		}
	}

	private String addChar(String str, char itemToInsert, int position) {
		return str.substring(0, position) + itemToInsert + str.substring(position);
	}
	private boolean isNumber(String d) {
		try {
			Double.parseDouble(d);
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	private int getPrecedence(String op) {
		if(op.equals("(")) {
			return 3;
		}
		if(op.equals("*") || op.equals("/")) {
			return 2;
		}
		else {
			return 1;
		}
	}
	private double evaluateTopOfStack() {
		d2 = dataStack.pop();
		d1 = dataStack.pop();
		String op = operStack.pop();
		return calculate(op, d1, d2);
	}

	private boolean shouldPush(String curr) {
		if(operStack.isEmpty() || operStack.peek().equals("(")) {
			return true;
		}
		else if(curr.equals("(")) {
			return true;
		}

		else if(curr.equals("*") || curr.equals("/")){
			if(operStack.peek().equals("+") || operStack.peek().equals("-")) {
				return true;
			}
		}


		return false;

	}



	private double calculate(String operator, double a, double b) {
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



	//failed code for negation
	/*
	int countDifference = 0;
	String[] temp;
	String tempString;
	for(int i = 0; i < strArray.length-2; i++) {
		if(strArray[i].equals("+") || strArray[i].equals("-") || strArray[i].equals("/") || strArray[i].equals("*")) {
			if(strArray[i+1].equals("-")) {
				countDifference++;
			}
		}
	}
	temp = new String[strArray.length-countDifference];

	for(int i = 0; i < strArray.length-2; i++) {
		if(strArray[i].equals("+") || strArray[i].equals("-") || strArray[i].equals("/") || strArray[i].equals("*")) {
			if(strArray[i+1].equals("-")) {
				tempString = "-"+strArray[i+2];
				temp[i] = (strArray[i]);
				temp[i+1] = tempString;
				i= i+2;
			}
			else {
				temp[i] = strArray[i];
			}
		}
		else{
			temp[i] = strArray[i];
		}

	}
	strArray = temp;

	 */
	/*
	ArrayList<String> temp = new ArrayList<String>();

	String tempString;
	for(int i = 0; i < strArray.length-2; i++) {
		if(strArray[i].equals("+") || strArray[i].equals("-") || strArray[i].equals("/") || strArray[i].equals("*")) {
			if(strArray[i+1].equals("-")) {
				tempString = "-"+strArray[i+2];
				temp.add(strArray[i]);
				temp.add(tempString);
				i= i+2;
			}
			else {
				temp.add(strArray[i]);
			}
		}
		else if(i == strArray.length - 2) {
			temp.add(strArray[i]);
			temp.add(strArray[i+1]);
			temp.add(strArray[i+2]);
			break;
		}
		else{
			temp.add(strArray[i]);
		}


	}
	String[] tempStrArray = new String[temp.size()];
	for(int i = 0; i < temp.size(); i++) {
		tempStrArray[i] = temp.get(i);
	}
	strArray = tempStrArray;
	System.out.println(Arrays.toString(strArray));

	 */



	//failed code for evaluation 


	/*		

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
				while(getPrecedence(operStack.peek()) >= getPrecedence(strArray[i])) {
					dataStack.push(evaluateTopOfStack());

				}
				operStack.push(strArray[i]);
			}

		}
	 */
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
	/*
	private void setAnswerLabel() {
		answer = evaluateExpression(expressionField.getText());
		System.out.println(answer);
	}
	 */

}
