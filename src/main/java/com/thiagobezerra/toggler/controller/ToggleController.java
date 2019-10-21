package com.thiagobezerra.toggler.controller;

import com.thiagobezerra.toggler.controller.dto.ToggleDTO;
import com.thiagobezerra.toggler.service.ToggleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ToggleController {
    private final ToggleService toggleService;

    public ToggleController(ToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @PostMapping("/toggles")
    @ResponseStatus(CREATED)
    public String save(@RequestBody ToggleDTO toggleDTO) {
        return toggleService.save(toggleDTO.toToggle());
    }
}
