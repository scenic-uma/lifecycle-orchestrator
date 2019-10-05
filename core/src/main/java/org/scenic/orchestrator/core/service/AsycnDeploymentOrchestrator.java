package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.ManagementContext;
import org.scenic.orchestrator.core.deployer.DeployerProxy;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.PlanManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 23/01/19.
 */
@Component
public class AsycnDeploymentOrchestrator implements DeploymentOrchestrator {


    private final DeployerProxy deployerProxy;
    private final PlanManager planManager;
    private final ManagementContext managementContext;

    public AsycnDeploymentOrchestrator(DeployerProxy deployerProxy, PlanManager planManager, ManagementContext managementContext) {
        this.deployerProxy = deployerProxy;
        this.planManager = planManager;
        this.managementContext=managementContext;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void deploy(RunningAppContext runningAppContext) throws InterruptedException {

        //Esto se hace bajo demanda cuando se hace deploy
        System.out.println("Start to deploy application: " + runningAppContext.getApplicationName());
        String appId = deployerProxy.deployApp(runningAppContext.getApplicationName(), runningAppContext.getApplicationTopology());
        //Crea la app sin iniciar en brooklyn
        runningAppContext.setAppId(appId);
        System.out.println("Add to the deployer application: " + runningAppContext.getApplicationName() + " with id " + appId);
        //Pones las entidades en el runnign context
        runningAppContext.setEntities(deployerProxy.getApplicationEntities(appId));
        System.out.println("Start the management");
        //
        planManager.deploy(runningAppContext);
        managementContext.addRunningAppContext(runningAppContext);
    }


}
