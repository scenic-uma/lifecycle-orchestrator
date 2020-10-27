package org.scenic.orchestrator.core.pivotal;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudFoundryException;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.springframework.http.HttpStatus;

/**
 * Created by Jose on 10/12/19.
 */

public class PivotalClient {

    private static CloudFoundryClient CLIENT;

    public PivotalClient(String user, String pass) {
        setUpClient(user, pass);
    }

    private static void setUpClient(String pivotalUser, String pivotalPass) {
        if (CLIENT == null) {
            CloudCredentials credentials =
                    new CloudCredentials(pivotalUser, pivotalPass);
            CLIENT = new CloudFoundryClient(credentials, getTargetURL("https://api.run.pivotal.io"),
                    "gsoc", "development", true);
            CLIENT.login();
        }
    }

    public CloudFoundryClient client(){
        return CLIENT;
    }

    public void refresh() {
        int i = 0;
        while (i < 10)
            try {
                CLIENT.login().getRefreshToken();
                return;
            } catch (Exception e) {
                System.out.println("Error in refresh: retrying");
                i++;
                if (i == 10) {
                    throw e;
                }
            }
    }

    private static URL getTargetURL(String target) {
        try {
            return URI.create(target).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("The target URL is not valid: " + e.getMessage());
        }
    }

    public List<CloudApplication> getApplications() {
        return CLIENT.getApplications();
    }

    private CloudApplication getApplication(String name) {
        return CLIENT.getApplication(name);

    }

    public boolean applicationExist(String appName) {
        System.out.println("Checking if application exist");
        //int i=0;
        //int limit=20;
        //while(i<limit){

        try {
            //return getApplication(appName) != null;
            return CLIENT.getApplications()
                    .stream()
                    .anyMatch(a -> appName.equals(a.getName()));
        } catch (CloudFoundryException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                System.out.println("NotFoundApplication" + e.getCause());
                return false;
            }
            System.out.println("Retring checking because" + e.getCause());
            //  if(i>limit){
            throw e;
            //  }
        } catch (Exception ee) {
            System.out.println("Retring checking because" + ee.getCause());
            //if(i>limit){
            throw ee;
            //}

        }
        //    i++;
        //}
        //return false;
    }
}
