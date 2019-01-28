package org.scenic.orchestrator.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.brooklyn.util.collections.MutableList;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 26/01/19.
 */
@Component
public class ManagementContext {

    private Map<String, RunningAppContext> runningApplications = new ConcurrentHashMap<>();

    public void addRunningAppContext(RunningAppContext runningAppContext) {
        runningApplications.put(runningAppContext.getAppId(), runningAppContext);
    }


    public List<RunningAppContext> runningApplications() {
        return runningApplications.values().stream().collect(Collectors.toList());
    }

    public void removeRunningApplications(String applicationId) {
        runningApplications.remove(applicationId);
    }




}
