package org.scenic.orchestrator.core.service;

import org.apache.brooklyn.rest.client.BrooklynApi;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 23/01/19.
 */
@Component
public class AsycnDeploymentOrchestrator implements DeploymentOrchestrator {


    private final BrooklynApi brooklynApi;

    public AsycnDeploymentOrchestrator(BrooklynApi brooklynApi) {
        this.brooklynApi = brooklynApi;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void deploy(RunningAppContext runningAppContext) throws InterruptedException {

        System.out.println("===============");
        Thread.sleep(5000);
        System.out.println("--------------------");
        System.out.println("--------------------");
        brooklynApi.getApplicationApi().createFromForm(runningAppContext.getApplicationTopology());
        System.out.println("************");
    }


}
