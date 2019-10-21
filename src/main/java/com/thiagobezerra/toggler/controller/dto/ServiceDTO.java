package com.thiagobezerra.toggler.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ServiceDTO {
    private String name;
    private String version;

    @JsonCreator
    public ServiceDTO(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
