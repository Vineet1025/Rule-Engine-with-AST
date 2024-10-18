package com.ruleEngineWithAST.ast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ASTNode {
    private String type; // "operator" or "operand"
    private String value; // "age > 30", "AND", etc.
    private ASTNode left; // Left child
    private ASTNode right; // Right child

    public ASTNode(ASTNodeType operator, String type, String value, Object o, Object object) {
        this.type = type;
        this.value = value;
    }
}
