package org.scenic.orchestrator.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.scenic.orchestrator.core.service.plan.PlanStepExecutor;
import org.scenic.orchestrator.core.service.plan.strategy.ParallelStepExecutorStrategy;
import org.scenic.orchestrator.core.service.plan.strategy.SingleStepExecutorStrategy;
import org.scenic.orchestrator.manager.ManagerAnalyzerClient;
import org.scenic.orchestrator.manager.SecuentialManagerAnalyzerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 01/10/19.
 */
@Configuration
public class ThreadPoolExecutorConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    @Profile("!secuential")
    ParallelStepExecutorStrategy parallelStepExecutorStrategy(PlanStepExecutor planStepExecutor,
                                                              ExecutorService executorService) {
        return new ParallelStepExecutorStrategy(planStepExecutor, executorService);
    }

    @Bean
    @Profile("!secuential")
    ManagerAnalyzerClient managerAnalyzerClient(RestTemplate restTemplate){
        return new ManagerAnalyzerClient(restTemplate);
    }

    @Bean
    @Profile("secuential")
    SingleStepExecutorStrategy singleStepExecutorStrategy(PlanStepExecutor planStepExecutor) {
        return new SingleStepExecutorStrategy(planStepExecutor);
    }


    @Bean
    @Profile("secuential")
    ManagerAnalyzerClient secuentialManagerAnalyzerClient(RestTemplate restTemplate){
        return new SecuentialManagerAnalyzerClient(restTemplate);
    }
}
