package org.scenic.orchestrator.manager;

import org.scenic.orchestrator.core.dto.Plan;
import org.springframework.web.client.RestTemplate;


public class SecuentialManagerAnalyzerClient extends ManagerAnalyzerClient {

    private static final String GET_PLAN_TEMPLATE = APPLICATION_RESOURCE + "/plan";

    public SecuentialManagerAnalyzerClient(RestTemplate restTemplate) {
        super(restTemplate);
    }


    @Override
    protected String getPlanResource(){
        return GET_PLAN_TEMPLATE;
    }


}
