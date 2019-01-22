package org.scenic.orchestrator.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanStep {

    private String node;

    @JsonProperty("interface")
    private String intf;

    private String operation;

    public PlanStep() {
    }

    public PlanStep(String node, String intf, String operation) {
        this.node = node;
        this.intf = intf;
        this.operation = operation;
    }

    public String getNode() {
        return this.node;
    }

    public String getIntf() {
        return this.intf;
    }

    public String getOperation() {
        return this.operation;
    }
}
