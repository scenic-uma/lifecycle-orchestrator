package org.scenic.orchestrator.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Plan {
    
        private List<PlanStep> plan;
        
        public Plan() { }
        
        public Plan(List<PlanStep> plan) {
            this.plan = plan;
        }
        
        public void addStep(String node, String intf, String operation) {
            this.plan.add(new PlanStep(node,intf,operation));
        }
        
        public List<PlanStep> getPlan() {
            return this.plan;
        }
    
}
