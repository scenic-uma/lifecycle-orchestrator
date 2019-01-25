package org.scenic.orchestrator.core.deployer.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jose on 24/01/19.
 */
public class CustomApplicationEntities {

    private final Map<String, CustomEntity> entitiesByDisplayName;
    private final Map<String, CustomEntity> entitiesById;


    public CustomApplicationEntities(CustomEntity[] entities) {
        entitiesByDisplayName = new HashMap<>();
        entitiesById = new HashMap<>();

        for (CustomEntity cEntity : entities) {
            entitiesById.put(cEntity.getId(), cEntity);
            entitiesByDisplayName .put(cEntity.getName(), cEntity);
        }
    }

    public CustomEntity getEntityById(String id){
        return entitiesById.get(id);
    }

    public CustomEntity getEntityByName(String name){
        return entitiesByDisplayName.get(name);
    }

}
