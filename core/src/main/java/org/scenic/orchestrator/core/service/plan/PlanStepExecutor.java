package org.scenic.orchestrator.core.service.plan;

import org.scenic.orchestrator.core.deployer.DeployerProxy;
import org.scenic.orchestrator.core.dto.EntityStatus;
import org.scenic.orchestrator.core.dto.PlanOperation;
import org.scenic.orchestrator.core.dto.PlanStep;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Created by Jose on 24/01/19.
 */
@Service
public class PlanStepExecutor {

    private static final String EXECUTION_MESSAGE = "Trying to Execute in entity %s(%s) the operation %s";

    private DeployerProxy deployerProxy;

    public PlanStepExecutor(DeployerProxy deployerProxy) {
        this.deployerProxy = deployerProxy;
    }

    public void executeStep(PlanStep step, RunningAppContext context) {

        try{
            System.out.println("PlanStepExecutor.class -- Execute taks: " + step.getOperation());

        if(step.getOperation() == PlanOperation.START) {
            String entityId = context.getEntityByDisplayName(step.getNode()).getId();
            System.out.println(String.format(EXECUTION_MESSAGE, step.getNode(), entityId, step.getOperation()));
            deployerProxy.startEffector(context, entityId);
            context.getStatus().setCurrentEntityStatus(step.getNode(), EntityStatus.STARTED);
        }
        else if((step.getOperation() == PlanOperation.RELEASE ) || (step.getOperation() == PlanOperation.STOP) ){
            String entityId = context.getEntityByDisplayName(step.getNode()).getId();
            System.out.println(String.format(EXECUTION_MESSAGE, step.getNode(), entityId, step.getOperation()));
            deployerProxy.stopEffector(context, entityId);
            context.getStatus().setCurrentEntityStatus(step.getNode(), EntityStatus.UNAVAILABLE);
        }
        } catch (Exception e) {
            context.getStatus().setCurrentEntityStatus(step.getNode(), EntityStatus.FAILED);
            throw e;
        }


    }


}
