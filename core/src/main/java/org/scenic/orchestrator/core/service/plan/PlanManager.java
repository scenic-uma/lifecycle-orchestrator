package org.scenic.orchestrator.core.service.plan;

import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Created by Jose on 25/01/19.
 */
@Service
public class PlanManager {


    private final PlanExecutor planExecutor;
    private final RunningAppContextSynchronizer runningAppContextSynchronizer;
    private final PlanStepExecutor planStepExecutor;

    public PlanManager(PlanExecutor planExecutor,
                       RunningAppContextSynchronizer runningAppContextSynchronizer,
                       PlanStepExecutor planStepExecutor
    ) {
        this.planExecutor = planExecutor;
        this.runningAppContextSynchronizer = runningAppContextSynchronizer;
        this.planStepExecutor = planStepExecutor;
    }


    public void update(RunningAppContext appContext) {
        System.out.println("Solving wrong application status.");
        synchronizeAppContext(appContext);
        deploy(appContext);
    }

    public void deploy(RunningAppContext appContext) {
        /*try {
            this.executePlan(appContext);
        } catch (Exception e) {
            synchronizeAppContext(appContext);
            deploy(appContext);
        }*/
        System.out.println("Executing application plan for: " + appContext.getApplicationName() + " " + appContext.getAppId());
        while (!appContext.getPlan().isEmpty()) {
            PlanStep firstStep = appContext.getPlan().getPlan().get(0);
            executeStep(appContext, firstStep);
        }
    }

    private void executeStep(RunningAppContext appContext, PlanStep step) {
        try {
            planStepExecutor.executeStep(step, appContext);
        } catch (Exception e) {
            System.out.println("Error managed.");
        }
        synchronizeAppContext(appContext);
    }

    private void executePlan(RunningAppContext appContext) {
        planExecutor.executePlan(appContext);
    }

    private void synchronizeAppContext(RunningAppContext appContext) {
        System.out.println("Synchronizing App Context");
        appContext.updateStatus();
        runningAppContextSynchronizer.updateContext(appContext);
    }

}
