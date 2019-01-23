package org.scenic.orchestrator.core.modifier;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Created by Jose on 23/01/19.
 */
@Service
public class TopologyModifierService {

    private final List<TopologyModifier> modifiers;

    public TopologyModifierService(List<TopologyModifier> modifiers) {
        this.modifiers=modifiers;
    }

    public String apply(String topology){

        String result = topology;
        for(TopologyModifier modifier : modifiers) {
            result = modifier.modify(result);
        }
        return result;
    }


}
