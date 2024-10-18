package com.ruleEngineWithAST.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEvaluationRequest {

    private Long ruleId;
    private Map<String, Object> userData; // User data as key-value pairs




}
