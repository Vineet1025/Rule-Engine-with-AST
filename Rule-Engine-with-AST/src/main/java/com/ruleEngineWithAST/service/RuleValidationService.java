package com.ruleEngineWithAST.service;

import com.ruleEngineWithAST.model.Rule;
import com.ruleEngineWithAST.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleValidationService {

    @Autowired
    private RuleRepository ruleRepository;

    // Check if the rule already exists
    public boolean ruleExists(String ruleString) {
        List<Rule> existingRules = ruleRepository.findAll();
        for (Rule rule : existingRules) {
            if (rule.getRuleString().equals(ruleString)) {
                return true;
            }
        }
        return false;
    }

    // Validate that rule starts with '(' and ends with ')'
    public boolean isValidRuleFormat(String ruleString) {
        return ruleString.startsWith("(") && ruleString.endsWith(")");
    }
}
