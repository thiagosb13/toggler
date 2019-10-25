package com.thiagobezerra.toggler.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.io.IOException;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Toggle {

    @Id
    private String name;

    @Property
    private Boolean value;

    @Relationship(type = "RESTRICTED_TO")
    private Set<Service> restrictions;

    @Relationship(type = "OVERRIDEN_BY", direction = INCOMING)
    private Set<Service> overrides;

    @Relationship(type = "EXCEPTED_BY")
    private Set<Service> exceptions;

    public Toggle() {
    }

    public Toggle(String name, Boolean value, Set<Service> restrictions, Set<Service> overrides, Set<Service> exceptions) {
        this.name = name;
        this.value = value;
        this.restrictions = restrictions;
        this.overrides = overrides;
        this.exceptions = exceptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Set<Service> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Set<Service> restrictions) {
        this.restrictions = restrictions;
    }

    public Set<Service> getOverrides() {
        return overrides;
    }

    public void setOverrides(Set<Service> overrides) {
        this.overrides = overrides;
    }

    public Set<Service> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Set<Service> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            return String.format("{ id: %s }", getName());
        }
    }
}
