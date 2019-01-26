package org.scenic.orchestrator.core.service.plan;

import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Created by Jose on 25/01/19.
 */
@Service
public class PlanManager {


    private final PlanExecutor planExecutor;
    private final RunningAppContextSynchronizer runningAppContextSynchronizer;

    public PlanManager(PlanExecutor planExecutor, RunningAppContextSynchronizer runningAppContextSynchronizer){
        this.planExecutor=planExecutor;
        this.runningAppContextSynchronizer=runningAppContextSynchronizer;
    }

    public void manage(RunningAppContext appContext) {
        try{
            this.executePlan(appContext);
        } catch (Exception e) {
            synchronizeAppContext(appContext);
            manage(appContext);
        }
    }

    private void synchronizeAppContext(RunningAppContext appContext) {
        System.out.println("Synchronizing App Context");
        runningAppContextSynchronizer.updateContext(appContext);
    }

    private void executePlan(RunningAppContext appContext){
        planExecutor.executePlan(appContext);
    }

}
