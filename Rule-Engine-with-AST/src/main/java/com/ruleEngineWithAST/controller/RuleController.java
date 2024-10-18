package com.ruleEngineWithAST.controller;

import com.ruleEngineWithAST.ast.ASTNode;
import com.ruleEngineWithAST.model.UserData;
import com.ruleEngineWithAST.service.ASTParserService;
import com.ruleEngineWithAST.service.ASTService;
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

    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("rules", astService.getAllRules());
        return "index";
    }

    @PostMapping("/create")
    public String createRule(@RequestParam("rule") String ruleString, Model model) {
        ASTNode astNode = astParserService.createAST(ruleString);
        astService.createRule(ruleString);
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
