package org.scenic.orchestrator.core.pivotal;

import java.time.Instant;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class PivotalHeartbeat {

    private final PivotalClient pivotalClient;

    public PivotalHeartbeat(PivotalClient pivotalClient) {
        this.pivotalClient = pivotalClient;
    }

    @Scheduled(initialDelay = 20_000, fixedRate = 300_000l)
    public void checkApplications() {
        try {
            System.out.println("*Heartbeat");
            this.pivotalClient.refresh();
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println(Instant.now().toString() + ": Error with Pivotal heartbat -----------------------------------> " + e.getCause());
            System.out.println("------------------------------------------------------------------------------------------------");
        }
    }
}
