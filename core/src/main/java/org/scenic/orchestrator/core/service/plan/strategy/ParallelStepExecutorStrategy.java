package org.scenic.orchestrator.core.service.plan.strategy;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.PlanStepExecutor;

/**
 * Ejecuta todos los steps que hay en el plan de manera paralela.
 */
public class ParallelStepExecutorStrategy extends PlanStepExecutorStrategy {

    private final ExecutorService executorService;

    public ParallelStepExecutorStrategy(PlanStepExecutor planStepExecutor,
                                        ExecutorService executorService) {
        super(planStepExecutor);
        this.executorService = executorService;
    }

    @Override
    public void executePlan(RunningAppContext appContext) {

        List<StepExecutionTask> stepExecutions = appContext.getPlan().getPlan().stream()
                .map(p -> new StepExecutionTask(planStepExecutor, p, appContext))
                .collect(toList());

        try {
            executorService.invokeAll(stepExecutions);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public class StepExecutionTask implements Callable<Void> {

        private final PlanStepExecutor planStepExecutor;
        private final PlanStep planStep;
        private final RunningAppContext appContext;

        public StepExecutionTask(PlanStepExecutor planStepExecutor, PlanStep planStep, RunningAppContext appContext) {
            this.planStepExecutor = planStepExecutor;
            this.planStep = planStep;
            this.appContext = appContext;
        }

        @Override
        public Void call() throws Exception {
            planStepExecutor.executeStep(planStep, appContext);
            return null;
        }
    }
}
