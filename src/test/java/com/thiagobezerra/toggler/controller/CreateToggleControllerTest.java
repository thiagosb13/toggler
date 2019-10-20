package com.thiagobezerra.toggler.controller;

import com.thiagobezerra.toggler.exception.ExceptionHandlers;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CreateToggleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ToggleService toggleService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ToggleController(toggleService))
                                      .setControllerAdvice(new ExceptionHandlers())
                                      .build();
    }

    @Test
    public void when_toggle_is_valid_should_return_created() throws Exception {
        var request = "{ \"name\" : \"isButtonBlue\", \"value\" : true }";
        mockMvc.perform(post("/toggles").contentType(MediaType.APPLICATION_JSON)
                                        .content(request))
               .andExpect(status().isCreated());
    }

    @Test
    public void when_toggle_already_exists_should_return_unprocessable_entity() throws Exception {
        when(toggleService.save(any(Toggle.class))).thenThrow(new IllegalArgumentException());

        var request = "{ \"name\" : \"isButtonBlue\", \"value\" : true }";
        mockMvc.perform(post("/toggles").contentType(MediaType.APPLICATION_JSON)
                                        .content(request))
               .andExpect(status().isUnprocessableEntity());

    }
}