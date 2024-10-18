package com.ruleEngineWithAST.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.model.Rule;
import com.ruleEngineWithAST.model.UserData;
import com.ruleEngineWithAST.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ASTParserService astParserService;

    @Autowired
    private ObjectMapper objectMapper; // To handle JSON serialization and deserialization

    // Method to create and save the rule
    public Rule createRule(String ruleString) {
        ASTNode ast = astParserService.createAST(ruleString);
        // Serialize AST to JSON
        String astJson = serializeASTToJson(ast);
        Rule rule = new Rule();
        rule.setRuleString(ruleString);
        rule.setAstJson(astJson);
        return ruleRepository.save(rule);
    }

    // Method to evaluate a rule by ruleId with the provided data
    public boolean evaluateRule(Long ruleId, Map<String, Object> data) {
        Rule rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        ASTNode ast = deserializeASTFromJson(rule.getAstJson());
        return astParserService.evaluateAST(ast, (UserData) data);
    }

    // Serialize the ASTNode to JSON using Jackson
    private String serializeASTToJson(ASTNode ast) {
        try {
            return objectMapper.writeValueAsString(ast); // Converts ASTNode to JSON string
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize AST to JSON", e);
        }
    }

    // Deserialize the JSON string back to ASTNode using Jackson
    private ASTNode deserializeASTFromJson(String astJson) {
        try {
            return objectMapper.readValue(astJson, ASTNode.class); // Converts JSON string to ASTNode
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize AST from JSON", e);
        }
    }
}
