package com.ruleEngineWithAST.service;

import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.model.UserData;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class ASTParserService {

    public ASTNode createAST(String ruleString) {

        Stack<ASTNode> stack = new Stack<>();
        String[] tokens = ruleString.split(" ");

        for (String token : tokens) {
            switch (token) {
                case "AND":
                case "OR":
                    ASTNode rightNode = stack.pop();
                    ASTNode leftNode = stack.pop();
                    ASTNode operatorNode = new ASTNode("operator", leftNode, rightNode, token);
                    stack.push(operatorNode);
                    break;

                case "(":
                    break;

                case ")":
                    break;

                default:
                    stack.push(new ASTNode("operand", null, null, token));
                    break;
            }
        }

        return stack.isEmpty() ? null : stack.pop();
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

    private boolean evaluateCondition(String condition, UserData userData) {
        String[] parts;

        if (condition.contains(">")) {
            parts = condition.split(">");
            String field = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());

            if ("age".equals(field)) {
                return userData.getAge() > value;
            } else if ("salary".equals(field)) {
                return userData.getSalary() > value;
            } else if ("experience".equals(field)) {
                return userData.getExperience() > value;
            }

        } else if (condition.contains("<")) {
            parts = condition.split("<");
            String field = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());

            if ("age".equals(field)) {
                return userData.getAge() < value;
            } else if ("salary".equals(field)) {
                return userData.getSalary() < value;
            } else if ("experience".equals(field)) {
                return userData.getExperience() < value;
            }

        } else if (condition.contains("=")) {
            parts = condition.split("=");
            String field = parts[0].trim();
            String value = parts[1].trim().replace("'", "");

            if ("department".equals(field)) {
                return userData.getDepartment().equals(value);
            }
        }

        return false;
    }
}
