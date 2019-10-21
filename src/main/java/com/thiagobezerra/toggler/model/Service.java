package com.thiagobezerra.toggler.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import static java.lang.String.format;

@NodeEntity
public class Service {

    @Id
    private String id;

    @Property
    private String name;

    @Property
    private String version;

    public Service() {
    }

    public Service(String name, String version) {
        this.id = format("%s;%s", name, version);
        this.name = name;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
