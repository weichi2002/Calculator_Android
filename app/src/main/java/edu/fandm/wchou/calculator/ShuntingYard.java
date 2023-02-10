package edu.fandm.wchou.calculator;

import java.util.*;

public class ShuntingYard {

    private static final Map<Character, Integer> PRECEDENCE = new HashMap<>();

    static {
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('-', 1);
        PRECEDENCE.put('*', 2);
        PRECEDENCE.put('/', 2);
        PRECEDENCE.put('^', 3);
    }

    public static List<String> convertToPostFix(String expression) {
        Stack<Character> operators = new Stack<>();
        List<String> output = new ArrayList<>();

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ') {
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                number.append(c);
                if (i == expression.length() - 1 || !(Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    output.add(number.toString());
                    number = new StringBuilder();
                }
            } else {
                while (!operators.empty() && PRECEDENCE.get(operators.peek()) >= PRECEDENCE.get(c)) {
                    output.add(String.valueOf(operators.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.empty()) {
            output.add(String.valueOf(operators.pop()));
        }

        return output;
    }

    public static double calculate(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String token : rpn) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();

                switch (token) {
                    case "^":
                        stack.push(Math.pow(a,b));
                        break;

                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;

                }
            }
        }

        return stack.pop();
    }

}