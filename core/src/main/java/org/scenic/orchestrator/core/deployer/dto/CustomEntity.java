package org.scenic.orchestrator.core.deployer.dto;

import static java.util.Arrays.stream;

import java.util.List;
import java.util.Map;

import org.apache.brooklyn.rest.domain.LocationSummary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 24/01/19.
 */

public class CustomEntity {

    private static final String IS_UP_SENSOR = "/service.isUp";
    private static final String STATUS_SENSOR = "/service.state";

    private String id;
    private String name;
    private String type;
    private String catalogItemId;
    private Map<String, String> links;

    private RestTemplate brooklynRestTemplate;
    private String sensorlink;

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
        this.sensorlink = links.get("sensors");
        this.links = links;
    }

    public void setBrooklynRestTemplate(RestTemplate brooklynRestTemplate) {
        this.brooklynRestTemplate = brooklynRestTemplate;
    }

    public Boolean isUp() {
        return brooklynRestTemplate.getForEntity(sensorlink + IS_UP_SENSOR, Boolean.class).getBody();
    }

    public BrooklynEntityStatus getStatus() {
        return brooklynRestTemplate.getForEntity(sensorlink + STATUS_SENSOR, BrooklynEntityStatus.class).getBody();
    }

    public String getSensor(String sensorName){
        return brooklynRestTemplate.getForEntity(sensorlink +"/" +sensorName, String.class).getBody();
    }

    public boolean hasSshLiveCloudResources(){
        return stream(brooklynRestTemplate.getForEntity(getLinks().get("locations"), LocationSummary[].class).getBody())
                .anyMatch(this::hasSshResources);
    }

    private boolean hasSshResources(LocationSummary l){
        return l.getType().toLowerCase().contains("ssh");
    }

    public boolean isInPaas(){
        return stream(brooklynRestTemplate.getForEntity(getLinks().get("locations"), LocationSummary[].class).getBody())
                .anyMatch(l -> l.getType().toLowerCase().contains("cloudfoundrypaaslocation"));
    }

    public static class LocationSummary{

        String id;
        String type;

        public LocationSummary(){
            this.id=null;
            this.type=null;
        }

        public String getId(){
            return id;
        }

        public String getType(){
            return type;
        }

        public String setId(String id){
            return this.id=id;
        }

        public String getType(String type){
            return this.type=type;
        }
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
