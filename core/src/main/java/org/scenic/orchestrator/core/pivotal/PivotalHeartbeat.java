package org.scenic.orchestrator.core.pivotal;

import java.time.Instant;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@Configuration
public class PivotalHeartbeat {

    private final PivotalClient pivotalClient;
            OAuth2AccessToken login;
    public int status=0;

    public PivotalHeartbeat(PivotalClient pivotalClient) {
        this.pivotalClient = pivotalClient;
        login = pivotalClient.client().login();
    }

    @Scheduled(initialDelay = 3_000, fixedRate = 60_000l)
    public void checkApplications() {
        try {
            System.out.println(Instant.now()+": *Heartbeat: " + login.getExpiresIn());


            if(status >= 300){
                System.out.println(Instant.now()+": Status has to been refresed ");
                this.pivotalClient.refresh();
                status=0;
            } else {
                status = status + 60;
            }


            this.pivotalClient.getApplications();
            System.out.println(Instant.now()+":Fin refresh: "+ login.getExpiresIn());
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println(Instant.now().toString() + ": Error with Pivotal heartbat -----------------------------------> " + e.getCause());
            System.out.println("------------------------------------------------------------------------------------------------");
        }
    }
}
