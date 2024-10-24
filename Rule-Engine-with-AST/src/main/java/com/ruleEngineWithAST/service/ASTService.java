package com.ruleEngineWithAST.service;

import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.model.Rule;
import com.ruleEngineWithAST.model.UserData;
import com.ruleEngineWithAST.repository.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class ASTService {

    private static final Logger logger = LoggerFactory.getLogger(ASTService.class);

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ASTParserService astParserService;

    public void createRule(String ruleString) {
        try {
            Rule rule = new Rule();
            rule.setRuleString(ruleString);
            ruleRepository.save(rule);
            logger.info("Rule created successfully: {}", ruleString);
        } catch (Exception e) {
            logger.error("Error creating rule: {}", e.getMessage());
        }
    }

    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public void deleteRuleById(Long id) {
        ruleRepository.deleteById(id);
    }


    public ASTNode combineRules(List<Long> ruleIds) {
        // Fetch the selected rules
        List<Rule> selectedRules = ruleRepository.findAllById(ruleIds);

        // Initialize a stack for combining the rules
        Stack<ASTNode> astNodes = new Stack<>();
        StringBuilder combinedRuleString = new StringBuilder();

        for (Rule rule : selectedRules) {
            ASTNode ruleAST = astParserService.createAST(rule.getRuleString());
            astNodes.push(ruleAST);

            if (combinedRuleString.length() > 0) {
                combinedRuleString.append(" OR ");
            }
            combinedRuleString.append(rule.getRuleString());
        }


        ASTNode combinedAST = null;
        while (astNodes.size() > 1) {
            ASTNode left = astNodes.pop();
            ASTNode right = astNodes.pop();
            ASTNode orNode = new ASTNode("operator", left, right, "OR");
            astNodes.push(orNode);
        }


        if (!astNodes.isEmpty()) {
            combinedAST = astNodes.pop();
        }

        Rule combinedRule = new Rule();
        combinedRule.setRuleString(combinedRuleString.toString());
        ruleRepository.save(combinedRule);

        return combinedAST;
    }

    public boolean evaluateRule(int age, String department, int salary, int experience) {
        List<Rule> rules = getAllRules();
        UserData userData = new UserData(age, department, salary, experience);
        boolean allEligible = true;

        for (Rule rule : rules) {
            try {
                boolean isEligible = astParserService.evaluateAST(astParserService.createAST(rule.getRuleString()), userData);
                logger.info("Evaluating rule: {} | Result: {}", rule.getRuleString(), isEligible);
                if (!isEligible) {
                    allEligible = false;
                    break;
                }
            } catch (Exception e) {
                logger.error("Error evaluating rule '{}': {}", rule.getRuleString(), e.getMessage());
                allEligible = false;
            }
        }

        return allEligible;
    }



}
