package com.thiagobezerra.toggler.controller;

import com.thiagobezerra.toggler.controller.dto.ToggleDTO;
import com.thiagobezerra.toggler.service.ToggleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.annotation.Secured;
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
@Api(value = "Toggle")
public class ToggleController {
    private final ToggleService toggleService;

    public ToggleController(ToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @PostMapping("/toggles")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Creates a toggle with its configuration")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Toggle created"),
        @ApiResponse(code = 401, message = "User is not authenticated"),
        @ApiResponse(code = 403, message = "User is not authorized"),
        @ApiResponse(code = 422, message = "There is already a toggle with name")
    })
    public String save(@RequestBody ToggleDTO toggleDTO) {
        return toggleService.save(toggleDTO.toToggle());
    }

    @PutMapping("/toggles")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Updates a toggle and its configuration")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Toggle updated"),
        @ApiResponse(code = 401, message = "User is not authenticated"),
        @ApiResponse(code = 403, message = "User is not authorized"),
        @ApiResponse(code = 404, message = "A toggle with informed name was not found")
    })
    public String update(@RequestBody ToggleDTO toggleDTO) {
        return toggleService.update(toggleDTO.toToggle());
    }

    @PatchMapping("/toggles/{name}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "Changes the value of a toggle")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 401, message = "User is not authenticated"),
        @ApiResponse(code = 403, message = "User is not authorized"),
        @ApiResponse(code = 404, message = "A toggle with informed name was not found")
    })
    public void changeToggle(@PathVariable String name, @RequestParam(value = "value") Boolean value) {
        toggleService.update(name, value);
    }

    @ApiOperation(value = "Gets a list of valid toggles of a service/application")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A list with toggles of a service/application"),
        @ApiResponse(code = 401, message = "User is not authenticated")
    })
    @GetMapping("/services/{name}/{version}/toggles")
    public List<String> findToggles(@PathVariable String name, @PathVariable String version) {
        return toggleService.findByService(name, version);
    }
}
