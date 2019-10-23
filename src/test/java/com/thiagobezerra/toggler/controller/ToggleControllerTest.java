package com.thiagobezerra.toggler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagobezerra.toggler.controller.dto.ServiceDTO;
import com.thiagobezerra.toggler.controller.dto.ToggleDTO;
import com.thiagobezerra.toggler.exception.ExceptionHandlers;
import com.thiagobezerra.toggler.exception.NotFoundException;
import com.thiagobezerra.toggler.model.Toggle;
import com.thiagobezerra.toggler.service.ToggleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ToggleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ToggleService toggleService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ToggleController(toggleService))
                                      .setControllerAdvice(new ExceptionHandlers())
                                      .build();
    }

    @Test
    void given_creating_a_toggle_when_it_is_valid_should_return_created() throws Exception {
        when(toggleService.save(any(Toggle.class))).thenReturn("name");

        var toggleDTO = new ToggleDTO("isButtonBlue", false, Set.of(), Set.of(new ServiceDTO("ABC", "1.0")), Set.of());
        var request = new ObjectMapper().writeValueAsString(toggleDTO);

        mockMvc.perform(post("/toggles").contentType(MediaType.APPLICATION_JSON)
                                        .content(request))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$", is("name")));

        verify(toggleService, times(1)).save(any(Toggle.class));
    }

    @Test
    void given_creating_a_toggle_when_it_already_exists_should_return_unprocessable_entity() throws Exception {
        when(toggleService.save(any(Toggle.class))).thenThrow(new IllegalArgumentException());

        var toggleDTO = new ToggleDTO("isButtonBlue", false, Set.of(), Set.of(new ServiceDTO("ABC", "1.0")), Set.of());
        var request = new ObjectMapper().writeValueAsString(toggleDTO);
        mockMvc.perform(post("/toggles").contentType(MediaType.APPLICATION_JSON)
                                        .content(request))
               .andExpect(status().isUnprocessableEntity());

        verify(toggleService, times(1)).save(any(Toggle.class));
    }

    @Test
    void given_updating_a_toggle_when_it_is_valid_should_return_created() throws Exception {
        when(toggleService.update(any(Toggle.class))).thenReturn("name");

        var toggleDTO = new ToggleDTO("isButtonBlue", false, Set.of(), Set.of(new ServiceDTO("ABC", "1.0")), Set.of());
        var request = new ObjectMapper().writeValueAsString(toggleDTO);

        mockMvc.perform(put("/toggles").contentType(MediaType.APPLICATION_JSON)
                                       .content(request))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$", is("name")));

        verify(toggleService, times(1)).update(any(Toggle.class));
    }

    @Test
    void given_updating_a_toggle_when_it_already_exists_should_return_unprocessable_entity() throws Exception {
        when(toggleService.update(any(Toggle.class))).thenThrow(new IllegalArgumentException());

        var toggleDTO = new ToggleDTO("isButtonBlue", false, Set.of(), Set.of(new ServiceDTO("ABC", "1.0")), Set.of());
        var request = new ObjectMapper().writeValueAsString(toggleDTO);
        mockMvc.perform(put("/toggles").contentType(MediaType.APPLICATION_JSON)
                                       .content(request))
               .andExpect(status().isUnprocessableEntity());

        verify(toggleService, times(1)).update(any(Toggle.class));
    }

    @Test
    void given_updating_a_toggle_value_when_it_is_valid_should_return_no_content() throws Exception {
        doNothing().when(toggleService).update("isButtonBlue", true);

        mockMvc.perform(patch("/toggles/isButtonBlue?value=true"))
               .andExpect(status().isNoContent());

        verify(toggleService, times(1)).update("isButtonBlue", true);
    }

    @Test
    void given_updating_a_toggle_value_when_it_is_not_found_should_return_not_found() throws Exception {
        doThrow(new NotFoundException("Not Found")).when(toggleService).update("isButtonBlue", true);

        mockMvc.perform(patch("/toggles/isButtonBlue?value=true"))
               .andExpect(status().isNotFound());

        verify(toggleService, times(1)).update("isButtonBlue", true);
    }

    @Test
    void given_getting_toggles_to_a_service_should_return_ok() throws Exception {
        when(toggleService.findByService("ABC", "1.0")).thenReturn(List.of("isButtonBlue", "isButtonGreen"));

        mockMvc.perform(get("/services/ABC/1.0/toggles"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]", is("isButtonBlue")))
               .andExpect(jsonPath("$[1]", is("isButtonGreen")));

        verify(toggleService, times(1)).findByService("ABC", "1.0");
    }
}
