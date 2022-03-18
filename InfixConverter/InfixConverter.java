
import java.util.Scanner;

public class InfixConverter {

    public double evaluate(String expression) {
        char[] charsArr = expression.toCharArray();
        Stack<Double> numStack = new Stack<>();
        Stack<Character> operatorsStack = new Stack<>();
        double operand1, operand2;
        String str;
        for (int i = 0; i < charsArr.length; i++) {
            if (charsArr[i] == ' ') { // checks if the character is a whitespace, if true then go back
                continue;
            }
            if (charsArr[i] >= '0' && charsArr[i] <= '9') { // Current token is a number, push it to stack for numbers
                str = "";
                // There may be more than one digits in a number
                while (i < charsArr.length && charsArr[i] >= '0' && charsArr[i] <= '9') {
                    str += charsArr[i++];
                }
                numStack.push(Double.parseDouble(str));
                i--;
            } else if (charsArr[i] == '(') {
                operatorsStack.push(charsArr[i]);
            } else if (charsArr[i] == ')') { //solve the expression
                while (operatorsStack.top() != '(') {
                    operand1 = numStack.pop();
                    operand2 = numStack.pop();
                    numStack.push(getResult(operatorsStack.pop(), operand1, operand2));
                }
                operatorsStack.pop();
            } else if (charsArr[i] == '+' || charsArr[i] == '-' || charsArr[i] == '*' || charsArr[i] == '/') { //if this character is an operator
                while (!operatorsStack.isEmpty()) {
                    if (operatorsStack.top() == '(' || operatorsStack.top() == ')') {
                        break;
                    } else if ((charsArr[i] == '*' || charsArr[i] == '/') && (operatorsStack.top() == '+' || operatorsStack.top() == '-')) {
                        break;
                    }
                    operand1 = numStack.pop();
                    operand2 = numStack.pop();
                    numStack.push(getResult(operatorsStack.pop(), operand1, operand2));
                }
                operatorsStack.push(charsArr[i]);
            }
        }

        //apply remaining operations to the values
        while (!operatorsStack.isEmpty()) {
            operand1 = numStack.pop();
            operand2 = numStack.pop();
            numStack.push(getResult(operatorsStack.pop(), operand1, operand2));
        }
        return numStack.pop(); //result
    }

    public double getResult(char operator, double num1, double num2) {
        double result;
        if (operator == '+') {
            result = num1 + num2;
        } else if (operator == '-') {
            result = num1 - num2;
        } else if (operator == '*') {
            result = num1 * num2;
        } else if (operator == '/') {
            if (num2 == 0) {
                throw new ArithmeticException("Cannot divide by ZERO");
            }
            result = num1 / num2;
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * Finds the position of the operator
     *
     * @param operator operator to check
     * @return position
     */
    public int checkPosition(char operator) {
        switch (operator) {
            case '-':
            case '+':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Converts an infix expression into a prefix expression
     *
     * @param expression infix expression
     * @return prefix expression
     */
    public String prefix(String expression) {
        Stack<Character> operatorsStack = new Stack<>();
        Stack<String> operandsStack = new Stack<>();
        expression = expression.replace(" ", "");
        String resultStr, prefixExpression, operand1, operand2;
        for (int i = 0; i < expression.length(); i++) {
            //checks if character is an opening bracket
            if (expression.charAt(i) == '(') {
                operatorsStack.push(expression.charAt(i));
            } else if (expression.charAt(i) == ')') {
                //iterates until an opening bracket is not found
                while (!operatorsStack.isEmpty() && operatorsStack.top() != '(') {
                    operand1 = operandsStack.pop();
                    operand2 = operandsStack.pop();
                    operandsStack.push(operatorsStack.pop() + operand2 + operand1);
                }
                operatorsStack.pop();
            } else if (!(!(expression.charAt(i) >= 'a'
                    && expression.charAt(i) <= 'z')
                    && !(expression.charAt(i) >= '0'
                    && expression.charAt(i) <= '9')
                    && !(expression.charAt(i) >= 'A'
                    && expression.charAt(i) <= 'Z'))) { //checks if character is an operand then push it into operandsStack
                operandsStack.push(expression.charAt(i) + "");
            } else { //if character is an operator, then push it into operatorsStack
                while (!operatorsStack.isEmpty() && checkPosition(expression.charAt(i)) <= checkPosition(operatorsStack.top())) {
                    operand1 = operandsStack.pop();
                    operand2 = operandsStack.pop();
                    operandsStack.push(operatorsStack.pop() + operand2 + operand1);
                }
                operatorsStack.push(expression.charAt(i));
            }
        }

        while (!operatorsStack.isEmpty()) { //iterates until operatorsStack is empty
            operand1 = operandsStack.pop();
            operand2 = operandsStack.pop();
            operandsStack.push(operatorsStack.pop() + operand2 + operand1);
        }

        //creates a prefix expression with space after each character
        resultStr = operandsStack.top();
        prefixExpression = "";
        for (int i = 0; i < resultStr.length(); i++) {
            prefixExpression += resultStr.charAt(i);
            if (i < resultStr.length() - 1) {
                prefixExpression += " ";
            }
        }
        return prefixExpression;
    }

    /**
     * Converts an infix expression into a postfix expression
     *
     * @param expression infix expression
     * @return postfix expression
     */
    public String postfix(String expression) {
        String resultStr = "", postExpression;
        Stack<Character> stack = new Stack<>();
        expression = expression.replace(" ", "");
        for (int i = 0; i < expression.length(); ++i) {
            char character = expression.charAt(i);
            //checks if character is a letter or digit
            if (Character.isLetterOrDigit(character)) {
                resultStr += character;
            } else if (character == '(') { //checks if character is an opening bracket
                stack.push(character);
            } else if (character == ')') { //checks if character is an closing bracket
                //iterates until opening bracket is found
                while (!stack.isEmpty() && stack.top() != '(') {
                    resultStr += stack.pop();
                }
                stack.pop();
            } else { // if character is an operator
                while (!stack.isEmpty() && checkPosition(character) <= checkPosition(stack.top())) {
                    resultStr += stack.pop();
                }
                stack.push(character);
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.top() == '(') {
                return "Invalid Expression";
            }
            resultStr += stack.pop();
        }

        //creates a postfix expression with space after each character
        postExpression = "";
        for (int i = 0; i < resultStr.length(); i++) {
            postExpression += resultStr.charAt(i);
            if (i < resultStr.length() - 1) {
                postExpression += " ";
            }
        }
        return postExpression;
    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        InfixConverter infixConverter = new InfixConverter();
        String expression;
        while (true) {
            System.out.print("Input infix expression or 'exit' to terminate: ");
            expression = input.nextLine();
            if (expression.equals("exit")) {
                break;
            }
            System.out.println("Postfix version: " + infixConverter.postfix(expression));
            System.out.println("Prefix version: " + infixConverter.prefix(expression));
            System.out.println("Evaluation: " + infixConverter.evaluate(expression) + "\n");
        }
    }
}
