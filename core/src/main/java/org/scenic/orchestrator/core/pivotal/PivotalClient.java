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

    public boolean applicationExist(String appName){
        try{
            return getApplication(appName) != null;
        } catch (CloudFoundryException e ){
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
            {
                return false;
            }
            throw e;
        } catch (Exception ee){
            throw ee;

        }
    }
}
