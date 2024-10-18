package com.ruleEngineWithAST.service;

import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.ast.ASTNodeType;
import com.ruleEngineWithAST.model.UserData;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class ASTParserService {

    // Method to parse rule string into an Abstract Syntax Tree (AST)
    public ASTNode createAST(String ruleString) {
        // Remove spaces from the ruleString for easier parsing
        ruleString = ruleString.replaceAll("\\s+", "");

        // Use a stack to build the AST
        Stack<ASTNode> stack = new Stack<>();

        // Temporary variables
        StringBuilder operand = new StringBuilder();
        ASTNode root = null;

        for (int i = 0; i < ruleString.length(); i++) {
            char ch = ruleString.charAt(i);

            if (ch == '(') {
                // Push new sub-expression start onto the stack
                stack.push(new ASTNode(ASTNodeType.OPERATOR, null, "(", null, null));
            } else if (ch == ')') {
                // Pop from the stack until we find a matching '('
                while (!stack.isEmpty() && !stack.peek().getValue().equals("(")) {
                    ASTNode right = stack.pop();
                    ASTNode operator = stack.pop();
                    ASTNode left = stack.pop();
                    operator.setLeft(left);
                    operator.setRight(right);
                    stack.push(operator);
                }
                // Pop the '('
                stack.pop();
            } else if (ch == '&' || ch == '|') {
                // Add operator node (& = AND, | = OR)
                if (ch == '&' && ruleString.charAt(i + 1) == '&') {
                    stack.push(new ASTNode(ASTNodeType.OPERATOR, "AND", null, null, null));
                    i++; // skip next character
                } else if (ch == '|' && ruleString.charAt(i + 1) == '|') {
                    stack.push(new ASTNode(ASTNodeType.OPERATOR, "OR", null, null, null));
                    i++; // skip next character
                }
            } else {
                // Append to operand (for conditions like age>30, salary<50000, etc.)
                operand.append(ch);

                // If it's the end of the condition or a logical operator is coming up
                if (i == ruleString.length() - 1 || ruleString.charAt(i + 1) == '&' || ruleString.charAt(i + 1) == '|'
                        || ruleString.charAt(i + 1) == '(' || ruleString.charAt(i + 1) == ')') {
                    String condition = operand.toString();
                    stack.push(new ASTNode(ASTNodeType.OPERAND, null, condition, null, null));
                    operand.setLength(0); // reset operand for next condition
                }
            }
        }

        // Build final AST
        while (!stack.isEmpty()) {
            ASTNode right = stack.pop();
            if (!stack.isEmpty()) {
                ASTNode operator = stack.pop();
                ASTNode left = stack.pop();
                operator.setLeft(left);
                operator.setRight(right);
                root = operator;
                stack.push(root);
            } else {
                root = right; // The single node left is the root
            }
        }

        return root;
    }

    // Method to evaluate the AST against the user data
    public boolean evaluateAST(ASTNode node, UserData data) {
        if (node == null) {
            return false;
        }

        // If it's an operand, evaluate the condition
        if (node.getType() .equals(ASTNodeType.OPERAND) ) {
            return evaluateCondition(node.getValue(), data);
        }

        // If it's an operator (AND/OR), recursively evaluate the left and right subtrees
        boolean leftEval = evaluateAST(node.getLeft(), data);
        boolean rightEval = evaluateAST(node.getRight(), data);

        if ("AND".equals(node.getValue())) {
            return leftEval && rightEval;
        } else if ("OR".equals(node.getValue())) {
            return leftEval || rightEval;
        }

        return false;
    }

    // Helper method to evaluate a single condition
    private boolean evaluateCondition(String condition, UserData data) {
        // Basic parsing of conditions like "age>30" or "salary<=50000"
        if (condition.contains(">")) {
            String[] parts = condition.split(">");
            String attribute = parts[0];
            int value = Integer.parseInt(parts[1]);
            return getUserDataValue(attribute, data) > value;
        } else if (condition.contains("<")) {
            String[] parts = condition.split("<");
            String attribute = parts[0];
            int value = Integer.parseInt(parts[1]);
            return getUserDataValue(attribute, data) < value;
        } else if (condition.contains("=")) {
            String[] parts = condition.split("=");
            String attribute = parts[0];
            String value = parts[1].replace("'", ""); // Remove quotes if any
            return getUserDataString(attribute, data).equals(value);
        }
        return false;
    }

    // Helper method to get integer attributes (like age, salary, etc.)
    private int getUserDataValue(String attribute, UserData data) {
        switch (attribute) {
            case "age":
                return data.getAge();
            case "salary":
                return data.getSalary();
            case "experience":
                return data.getExperience();
            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute);
        }
    }

    // Helper method to get string attributes (like department)
    private String getUserDataString(String attribute, UserData data) {
        switch (attribute) {
            case "department":
                return data.getDepartment();
            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute);
        }
    }
}
