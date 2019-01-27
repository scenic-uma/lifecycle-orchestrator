package org.scenic.orchestrator.core.service.plan;

import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.Plan;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.ManagerAnalyzerClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Jose on 25/01/19.
 */
@Service
public class RunningAppContextSynchronizer {


    private final ManagerAnalyzerClient managerAnalyzerClient;

    public RunningAppContextSynchronizer(ManagerAnalyzerClient managerAnalyzerClient) {
        this.managerAnalyzerClient = managerAnalyzerClient;
    }


    public void updateContext(RunningAppContext appContext) {
        String appName = appContext.getApplicationName();
        this.managerAnalyzerClient.putStatus(appName, appContext.getStatus());
        appContext.setPlan(this.managerAnalyzerClient.getPlan(appName));
        System.out.println("--------**");
        System.out.println("Status: " + statusToString(appContext.getStatus()));
        System.out.println("Synchronizing new plan: " + planToString(appContext.getPlan()));
        System.out.println("--------**");
    }


    //TODO: avoid this... **** ... with lombok
    public String planToString(Plan plan){
        try{
            return new ObjectMapper().writeValueAsString(plan);
        } catch(Exception e){
            throw  new RuntimeException("Error printing the plan");
        }
    }

    //TODO: avoid this... **** ... with lombok
    public String statusToString(ApplicationStatus status){
        try{
            return new ObjectMapper().writeValueAsString(status);
        } catch(Exception e){
            throw  new RuntimeException("Error printing the status");
        }
    }

}
