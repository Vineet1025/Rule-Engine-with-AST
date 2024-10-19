package com.ruleEngineWithAST.controller;

import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.model.UserData;
import com.ruleEngineWithAST.service.ASTParserService;
import com.ruleEngineWithAST.service.ASTService;
import com.ruleEngineWithAST.service.RuleValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/rule")
public class RuleController {

    @Autowired
    private ASTService astService;
    @Autowired
    private ASTParserService astParserService;
    @Autowired
    private RuleValidationService ruleValidationService;

    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("rules", astService.getAllRules());
        return "index";
    }

    @PostMapping("/create")
    public String createRule(@RequestParam("rule") String ruleString, Model model) {

        // Validate the rule format
        if (ruleString.isEmpty()) {
            model.addAttribute("errorMessage", "Rule string cannot be empty.");
            model.addAttribute("rules", astService.getAllRules());
            return "index";
        }

        if (!ruleValidationService.isValidRuleFormat(ruleString)) {
            model.addAttribute("errorMessage", "Invalid rule format! Ensure the rule is enclosed with '(' and ')'.");
            model.addAttribute("rules", astService.getAllRules());
            return "index";
        }

        // Check if rule already exists
        if (ruleValidationService.ruleExists(ruleString)) {
            model.addAttribute("errorMessage", "Rule already exists!");
            model.addAttribute("rules", astService.getAllRules());
            return "index";
        }

        // If valid, create the AST and save the rule
        ASTNode astNode = astParserService.createAST(ruleString);
        astService.createRule(ruleString);

        model.addAttribute("successMessage", "Rule created successfully!");
        model.addAttribute("rules", astService.getAllRules());
        return "index";
    }


    @PostMapping("/evaluate")
    public String evaluateRule(
            @RequestParam("age") int age,
            @RequestParam("department") String department,
            @RequestParam("salary") int salary,
            @RequestParam("experience") int experience,
            Model model) {

        boolean result = astService.evaluateRule(age, department, salary, experience);
        model.addAttribute("evaluationResult", result ? "Eligible" : "Not Eligible");
        model.addAttribute("rules", astService.getAllRules());
        return "index";
    }

}
