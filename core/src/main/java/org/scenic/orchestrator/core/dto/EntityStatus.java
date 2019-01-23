package org.scenic.orchestrator.core.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Jose on 23/01/19.
 */
public enum EntityStatus {

    // States
    UNAVAILABLE("unavailable"),
    STARTED("started"),
    STOPPED("stopped"),
    FAILED("failed");

    private final String alias;

    EntityStatus(String alias) {
        this.alias = alias;
    }

    @JsonValue
    public String getAlias() {
        return alias;
    }
}
