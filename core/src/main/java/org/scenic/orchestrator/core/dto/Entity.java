package org.scenic.orchestrator.core.dto;


/**
 * The Entity class models a component. Its keeps its state and models its changes.
 */
public class Entity {

    private EntityStatus status;

    public Entity() {
        status = EntityStatus.UNAVAILABLE;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void start() {
        status = EntityStatus.STARTED;
    }

    public void stop() {
        status = EntityStatus.STOPPED;
    }

    public void release() {
        status = EntityStatus.UNAVAILABLE;
    }

    public void restart() {
        status = EntityStatus.STARTED;
    }
}
