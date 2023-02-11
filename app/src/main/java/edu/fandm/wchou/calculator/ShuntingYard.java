package edu.fandm.wchou.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

//https://www.geeksforgeeks.org/java-program-to-implement-shunting-yard-algorithm/  and some other stackoverflow posts
public class ShuntingYard {

    private static Map<Character, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('-', 1);
        PRECEDENCE.put('*', 2);
        PRECEDENCE.put('/', 2);
        PRECEDENCE.put('^',3);
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isOperator(char c) {
        return PRECEDENCE.containsKey(c);
    }

    public static double evaluate(String expression) {
        Deque<Double> values = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                try {
                    values.push(Double.parseDouble(sb.toString()));
                } catch (NumberFormatException e) {
                    System.err.println("Error: invalid decimal point");
                    return Double.NaN;
                }
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && PRECEDENCE.get(operators.peek()) >= PRECEDENCE.get(c)) {
                    try {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                    }catch(NoSuchElementException e){
                        return Double.NaN;
                    }
                }
                operators.push(c);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            }
        }

        while (!operators.isEmpty()) {
            try {
                values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
            }catch(NoSuchElementException e){
                return Double.NaN;
            }
        }

        return values.pop();
    }

    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '^':
                return Math.pow(a,b);
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if(b==0) return Double.NaN;
                return a / b;
            default:
                throw new IllegalArgumentException("Error: unknown operator " + operator);
        }
    }
}