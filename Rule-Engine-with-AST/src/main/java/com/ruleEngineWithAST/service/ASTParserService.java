package com.ruleEngineWithAST.service;

import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.model.UserData;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class ASTParserService {

    public ASTNode createAST(String ruleString) {
        Stack<ASTNode> nodeStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        String[] tokens = ruleString.split(" ");

        for (String token : tokens) {
            switch (token) {
                case "AND":
                case "OR":
                    operatorStack.push(token);
                    break;

                case "(":
                    operatorStack.push(token);
                    break;

                case ")":
                    while (!operatorStack.isEmpty() && !"(".equals(operatorStack.peek())) {
                        String operator = operatorStack.pop();

                        ASTNode rightNode = nodeStack.pop();
                        ASTNode leftNode = nodeStack.pop();

                        ASTNode operatorNode = new ASTNode("operator", leftNode, rightNode, operator);
                        nodeStack.push(operatorNode);
                    }
                    operatorStack.pop();
                    break;

                default:
                    nodeStack.push(new ASTNode("operand", null, null, token));
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.pop();
            ASTNode rightNode = nodeStack.pop();
            ASTNode leftNode = nodeStack.pop();

            ASTNode operatorNode = new ASTNode("operator", leftNode, rightNode, operator);
            nodeStack.push(operatorNode);
        }

        return nodeStack.isEmpty() ? null : nodeStack.pop();
    }



    public boolean evaluateAST(ASTNode node, UserData userData) {
        if (node == null) {
            return false;
        }


        if ("operator".equals(node.getType())) {
            boolean leftResult = evaluateAST(node.getLeft(), userData);
            boolean rightResult = evaluateAST(node.getRight(), userData);


            if ("AND".equals(node.getValue())) {
                return leftResult && rightResult;
            } else if ("OR".equals(node.getValue())) {
                return leftResult || rightResult;
            }
        } else if ("operand".equals(node.getType())) {
            return evaluateCondition(node.getValue(), userData);
        }

        return false;
    }



    public boolean evaluateCondition(String condition, UserData userData) {
        condition = condition.replaceAll("[()]", "").trim();

        String operatorRegex = "\\s*(=|>=|<=|>|<)\\s*";
        String[] parts = condition.split(operatorRegex);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }

        String lhs = parts[0].trim();
        String rhs = parts[1].trim();

        String operator = condition.replaceAll(".*?(" + operatorRegex + ").*", "$1").trim();

        if (rhs.startsWith("'") && rhs.endsWith("'")) {
            rhs = rhs.substring(1, rhs.length() - 1);
        }

        switch (lhs) {
            case "age":
                return evaluateNumericCondition(userData.getAge(), operator, Integer.parseInt(rhs));
            case "salary":
                return evaluateNumericCondition(userData.getSalary(), operator, Integer.parseInt(rhs));
            case "experience":
                return evaluateNumericCondition(userData.getExperience(), operator, Integer.parseInt(rhs));
            case "department":
                return evaluateStringCondition(userData.getDepartment(), operator, rhs);
            default:
                throw new IllegalArgumentException("Unknown condition: " + lhs);
        }
    }



    private boolean evaluateNumericCondition(int userValue, String operator, int ruleValue) {
        switch (operator) {
            case ">":
                return userValue > ruleValue;
            case "<":
                return userValue < ruleValue;
            case ">=":
                return userValue >= ruleValue;
            case "<=":
                return userValue <= ruleValue;
            case "=":
                return userValue == ruleValue;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private boolean evaluateStringCondition(String userValue, String operator, String ruleValue) {
        if ("=".equals(operator)) {
            return userValue.equals(ruleValue);
        } else {
            throw new IllegalArgumentException("Unknown operator for string condition: " + operator);
        }
    }




}
