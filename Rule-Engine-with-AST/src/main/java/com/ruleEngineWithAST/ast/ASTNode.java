package com.ruleEngineWithAST.ast;



public class ASTNode {
    private String type;
    private ASTNode left;
    private ASTNode right;
    private String value;

    public ASTNode(String type, ASTNode left, ASTNode right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    // Getters and setters
    public String getType() { return type; }
    public ASTNode getLeft() { return left; }
    public ASTNode getRight() { return right; }
    public String getValue() { return value; }

    public void setLeft(ASTNode left) { this.left = left; }
    public void setRight(ASTNode right) { this.right = right; }
}
