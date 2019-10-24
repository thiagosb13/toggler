package com.thiagobezerra.toggler.controller;

import com.thiagobezerra.toggler.controller.dto.ToggleDTO;
import com.thiagobezerra.toggler.service.ToggleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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

    @PutMapping("/toggles")
    @ResponseStatus(CREATED)
    public String update(@RequestBody ToggleDTO toggleDTO) {
        return toggleService.update(toggleDTO.toToggle());
    }

    @PatchMapping("/toggles/{name}")
    @ResponseStatus(NO_CONTENT)
    public void changeToggle(@PathVariable String name, @RequestParam(value = "value") Boolean value) {
        toggleService.update(name, value);
    }

    @GetMapping("/services/{name}/{version}/toggles")
    public List<String> findToggles(@PathVariable String name, @PathVariable String version) {
        return toggleService.findByService(name, version);
    }
}
