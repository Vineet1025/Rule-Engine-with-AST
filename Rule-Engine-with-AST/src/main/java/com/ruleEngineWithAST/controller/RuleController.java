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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

        if (ruleString.isEmpty()) {
            model.addAttribute("errorMessage", "Rule Cannot Be Empty!");
            model.addAttribute("rules", astService.getAllRules());
            return "index";
        }

        if (!ruleValidationService.isValidRuleFormat(ruleString)) {
            model.addAttribute("errorMessage", "Invalid Rule Format! Ensure the rule is enclosed with '(' and ')'.");
            model.addAttribute("rules", astService.getAllRules());
            return "index";
        }


        if (ruleValidationService.ruleExists(ruleString)) {
            model.addAttribute("errorMessage", "Rule Already Exists!");
            model.addAttribute("rules", astService.getAllRules());
            return "index";
        }


        ASTNode astNode = astParserService.createAST(ruleString);
        astService.createRule(ruleString);

        model.addAttribute("successMessage", "Rule Created Successfully!");
        model.addAttribute("rules", astService.getAllRules());
        return "index";
    }

    @PostMapping("/delete/{id}")
    public String deleteRule(@PathVariable("id") Long id, Model model , RedirectAttributes redirectAttributes) {
        astService.deleteRuleById(id);
        model.addAttribute("rules", astService.getAllRules());
        redirectAttributes.addFlashAttribute("message", "Rule Deleted Successfully!");
        return "redirect:/api/rule/index";
    }

    @PostMapping("/combineRules")
    public String combineRules(@RequestParam List<Long> ruleIds, Model model, RedirectAttributes redirectAttributes) {

        ASTNode combinedAST = astService.combineRules(ruleIds);


        model.addAttribute("combinedAST", combinedAST);
        redirectAttributes.addFlashAttribute("combinemessage", "Rule Combined Successfully!");


        return "redirect:/api/rule/index";
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
