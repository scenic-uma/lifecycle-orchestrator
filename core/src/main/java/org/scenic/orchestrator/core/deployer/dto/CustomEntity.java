package org.scenic.orchestrator.core.deployer.dto;

import java.util.Map;

/**
 * Created by Jose on 24/01/19.
 */

public class CustomEntity {

    private String id;
    private String name;
    private String type;
    private String catalogItemId;
    private Map<String, String> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }


    /*"id": "nDQUMK7J",
            "name": "SoftcareWS",
            "type": "org.apache.brooklyn.entity.webapp.tomcat.TomcatServer",
            "catalogItemId": "org.apache.brooklyn.entity.webapp.tomcat.TomcatServer:0.9.0",
            "links": {
        "self": "/v1/applications/SprxUBQA/entities/nDQUMK7J",
                "parent": "/v1/applications/SprxUBQA/entities/SprxUBQA",
                "application": "/v1/applications/SprxUBQA",
                "children": "/v1/applications/SprxUBQA/entities/nDQUMK7J/children",
                "config": "/v1/applications/SprxUBQA/entities/nDQUMK7J/config",
                "sensors": "/v1/applications/SprxUBQA/entities/nDQUMK7J/sensors",
                "effectors": "/v1/applications/SprxUBQA/entities/nDQUMK7J/effectors",
                "policies": "/v1/applications/SprxUBQA/entities/nDQUMK7J/policies",
                "activities": "/v1/applications/SprxUBQA/entities/nDQUMK7J/activities",
                "locations": "/v1/applications/SprxUBQA/entities/nDQUMK7J/locations",
                "tags": "/v1/applications/SprxUBQA/entities/nDQUMK7J/tags",
                "expunge": "/v1/applications/SprxUBQA/entities/nDQUMK7J/expunge",
                "rename": "/v1/applications/SprxUBQA/entities/nDQUMK7J/name",
                "spec": "/v1/applications/SprxUBQA/entities/nDQUMK7J/spec",
                "catalog": "/v1/catalog/entities/org.apache.brooklyn.entity.webapp.tomcat.TomcatServer/0.9.0"
    }
    */


}
