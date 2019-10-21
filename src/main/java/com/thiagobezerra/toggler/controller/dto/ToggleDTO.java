package com.thiagobezerra.toggler.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.thiagobezerra.toggler.model.Service;
import com.thiagobezerra.toggler.model.Toggle;

import java.util.Set;
import java.util.stream.Collectors;

public class ToggleDTO {
    private String name;
    private Boolean value;
    private Set<ServiceDTO> restrictions;
    private Set<ServiceDTO> overrides;
    private Set<ServiceDTO> exceptions;

    @JsonCreator
    public ToggleDTO(String name, Boolean value, Set<ServiceDTO> restrictions, Set<ServiceDTO> overrides, Set<ServiceDTO> exceptions) {
        this.name = name;
        this.value = value;
        this.restrictions = restrictions;
        this.overrides = overrides;
        this.exceptions = exceptions;
    }

    public String getName() {
        return name;
    }

    public Boolean getValue() {
        return value;
    }

    public Set<ServiceDTO> getRestrictions() {
        return restrictions;
    }

    public Set<ServiceDTO> getOverrides() {
        return overrides;
    }

    public Set<ServiceDTO> getExceptions() {
        return exceptions;
    }

    public Toggle toToggle() {
        var toggle = new Toggle();

        toggle.setName(this.getName());
        toggle.setValue(this.getValue());
        toggle.setRestrictions(convert(this.getRestrictions()));
        toggle.setOverrides(convert(this.getOverrides()));
        toggle.setExceptions(convert(this.getExceptions()));

        return toggle;
    }

    private Set<Service> convert(Set<ServiceDTO> services) {
        return services.stream()
                       .map(dto -> new Service(dto.getName(), dto.getVersion()))
                       .collect(Collectors.toSet());
    }
}
