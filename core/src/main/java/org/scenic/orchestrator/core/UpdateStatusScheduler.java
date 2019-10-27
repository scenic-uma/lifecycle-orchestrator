package org.scenic.orchestrator.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 17/10/19.
 */
@ConditionalOnProperty(name="scheduleUpdating")
@Component
public class UpdateStatusScheduler {

    private final UpdaterManagement updaterManagement;

    public UpdateStatusScheduler(UpdaterManagement updaterManagement) {
        this.updaterManagement = updaterManagement;
    }

    @Scheduled(fixedRateString = "${instructionSchedularTime}")
    public void deployApp() throws InterruptedException {
        updaterManagement.updateApplication();
    }

}
