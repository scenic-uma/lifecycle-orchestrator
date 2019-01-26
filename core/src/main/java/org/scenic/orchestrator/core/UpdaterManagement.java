package org.scenic.orchestrator.core;

import java.util.List;

import org.apache.brooklyn.util.collections.MutableList;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.PlanManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 26/01/19.
 */
@Component
public class UpdaterManagement {


    private final ManagementContext managementContext;
    private PlanManager planManager;

    public UpdaterManagement(ManagementContext managementContext, PlanManager planManager){
        this.managementContext=managementContext;
        this.planManager = planManager;
    }


    @Scheduled(fixedRateString = "${instructionSchedularTime}")
    public void updateApplication() {

        List<RunningAppContext> applicationsToUpdate = MutableList.of();
        for (RunningAppContext appContext: managementContext.runningApplications()) {
            String appId=appContext.getAppId();
            if (!appContext.isUp()) {
                applicationsToUpdate.add(appContext);
                managementContext.removeRunningApplications(appId);
                manageApplication(appContext);
            }
        }
    }

    @Async("threadPoolTaskExecutor")
    public void manageApplication(RunningAppContext appContext){
        planManager.update(appContext);
        managementContext.addRunningAppContext(appContext);
    }


}
