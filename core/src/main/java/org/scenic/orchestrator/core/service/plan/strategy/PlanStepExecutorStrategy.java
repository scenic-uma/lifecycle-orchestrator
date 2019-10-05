package org.scenic.orchestrator.core.service.plan.strategy;

import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.PlanStepExecutor;

public abstract class PlanStepExecutorStrategy {

    final PlanStepExecutor planStepExecutor;

    public PlanStepExecutorStrategy(PlanStepExecutor planStepExecutor) {
        this.planStepExecutor = planStepExecutor;
    }


    public abstract void executePlan(RunningAppContext appContext);

    void executeStep(RunningAppContext appContext, PlanStep step) {
        try {
            planStepExecutor.executeStep(step, appContext);
        } catch (Exception e) {
            System.out.println("Error managed");
        }
    }


}
