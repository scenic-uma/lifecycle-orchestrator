package org.scenic.orchestrator.core.service.plan.strategy;

import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.PlanStepExecutor;

/**
 * Ejecuta el primer step del plan
 */
public class SingleStepExecutorStrategy extends PlanStepExecutorStrategy{

    public SingleStepExecutorStrategy(PlanStepExecutor planStepExecutor) {
        super(planStepExecutor);
    }

    @Override
    public void executePlan(RunningAppContext appContext) {
        PlanStep firstStep = appContext.getPlan().getPlan().get(0);
        executeStep(appContext, firstStep);
    }


}
