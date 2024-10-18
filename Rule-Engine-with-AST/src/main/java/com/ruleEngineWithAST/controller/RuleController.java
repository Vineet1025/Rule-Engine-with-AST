package com.ruleEngineWithAST.controller;

import com.ruleEngineWithAST.model.Rule;
import com.ruleEngineWithAST.service.RuleService;
import com.ruleEngineWithAST.service.ASTParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/create")
    public Rule createRule(@RequestBody String ruleString) {
        return ruleService.createRule(ruleString);
    }

    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody RuleEvaluationRequest request) {
        return ruleService.evaluateRule(request.getRuleId(), request.getUserData());
    }
}
