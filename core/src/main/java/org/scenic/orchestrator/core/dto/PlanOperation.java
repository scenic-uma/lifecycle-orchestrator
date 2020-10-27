package org.scenic.orchestrator.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Jose on 25/01/19.
 */
public enum PlanOperation {

    START("start"),
    STOP("stop"),
    RELEASE("release"),
    RESTART("restart");

    private String alias;

    PlanOperation(String alias) {
        this.alias = alias;
    }

    @JsonValue
    public String getAlias() {
        return alias;
    }

    @JsonCreator
    public static PlanOperation  fromAlias(String alias){
        for(PlanOperation e : PlanOperation.values()) {
            if(e.name().equalsIgnoreCase(alias)) {
                return e;
            }
        }
        return null;
    }


}
