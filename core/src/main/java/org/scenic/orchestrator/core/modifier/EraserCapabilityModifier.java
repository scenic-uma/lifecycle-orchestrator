package org.scenic.orchestrator.core.modifier;

import org.apache.brooklyn.util.text.Strings;
import org.springframework.stereotype.Component;


/**
 * Modifier to normalize capability in a NodeTemplate's relations, Brooklyn wait to find the capability type instead of the capability name
 */
@Component
public class EraserCapabilityModifier implements TopologyModifier {

    private static final String CAPABILITY_REGEX = "capability: [a-zA-Z_]+";

    @Override
    public String modify(String topology) {
        return topology.replaceAll(CAPABILITY_REGEX, Strings.EMPTY);
    }
}
