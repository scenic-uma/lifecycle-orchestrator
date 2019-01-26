package org.scenic.orchestrator.core.service.plan;

import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Created by Jose on 24/01/19.
 */
@Service
public class PlanExecutor {

    private final PlanStepExecutor planStepExecutor;

    public PlanExecutor(PlanStepExecutor planStepExecutor) {
        this.planStepExecutor = planStepExecutor;
    }

    public void executePlan(RunningAppContext appContext) {

        System.out.println("Executing application plan for: " + appContext.getApplicationName() + " " + appContext.getAppId());

        for(PlanStep step : appContext.getPlan().getPlan()) {
            planStepExecutor.executeStep(step, appContext);
        }

    }


}
