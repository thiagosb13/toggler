package com.thiagobezerra.toggler.controller.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Service", description = "Service or application configured by toggle")
public class ServiceDTO {
    private String name;
    private String version;

    public ServiceDTO() {
    }

    public ServiceDTO(String name, String version) {
        this.name = name;
        this.version = version;
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
