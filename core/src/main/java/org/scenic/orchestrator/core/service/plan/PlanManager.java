package org.scenic.orchestrator.core.service.plan;

import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.strategy.PlanStepExecutorStrategy;
import org.springframework.stereotype.Service;

/**
 * Created by Jose on 25/01/19.
 */
@Service
public class PlanManager {


    //private final PlanExecutor planExecutor;
    private final RunningAppContextSynchronizer runningAppContextSynchronizer;
    //private final PlanStepExecutor planStepExecutor;
    private final PlanStepExecutorStrategy strategy;

    public PlanManager(PlanExecutor planExecutor,
                       RunningAppContextSynchronizer runningAppContextSynchronizer,
                       //PlanStepExecutor planStepExecutor
                       PlanStepExecutorStrategy strategy
    ) {
        //this.planExecutor = planExecutor;
        this.runningAppContextSynchronizer = runningAppContextSynchronizer;
        //this.planStepExecutor = planStepExecutor;
        this.strategy = strategy;
    }


    public void update(RunningAppContext appContext) {
        System.out.println("Solving wrong application status.");
        synchronizeAppContext(appContext);
        deploy(appContext);
    }

    public void deploy(RunningAppContext appContext) {

        System.out.println("Executing application plan for: " + appContext.getApplicationName() + " " + appContext.getAppId());

        //Coge el plan y lo ejecuta en paralelo
        while (!appContext.getPlan().isEmpty()) {


            //Cambiar esta linea para coger todo lo que nos de y ejecutarlo en paralelo (hay que cambir el endpoint)
            //PlanStep firstStep = appContext.getPlan().getPlan().get(0);
            //executeStep(appContext, firstStep);
            strategy.executePlan(appContext);

            synchronizeAppContext(appContext);
        }
    }

    /*
    private void executeStep(RunningAppContext appContext, PlanStep step) {
        try {
            planStepExecutor.executeStep(step, appContext);
        } catch (Exception e) {
            System.out.println("Error managed.");
        }
        synchronizeAppContext(appContext);
    }
    */

    private void synchronizeAppContext(RunningAppContext appContext) {
        System.out.println("-------Synchronizing App Context");
        appContext.updateStatus();

        //Actualiza el estado en el manager y carga el plan de nuevo
        runningAppContextSynchronizer.updateContext(appContext);
    }

    //private void executePlan(RunningAppContext appContext) {
    //    planExecutor.executePlan(appContext);
    //}

}
