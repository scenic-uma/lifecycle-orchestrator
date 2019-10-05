package org.scenic.orchestrator.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Plan {

    @JsonAlias({"parallelSteps", "plan"})
    private List<PlanStep> plan;

    public Plan() {
        plan = new ArrayList<>();
    }

    public Plan(List<PlanStep> plan) {
        this.plan = plan;
    }

    public void addStep(String node, String intf, PlanOperation operation) {
        this.plan.add(new PlanStep(node, intf, operation));
    }

    public List<PlanStep> getPlan() {
        return this.plan;
    }

    @JsonIgnore
    public List<String> getEntities() {
        return plan.stream().map(PlanStep::getNode).collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isEmpty() {
        return plan != null && plan.isEmpty();
    }

}
