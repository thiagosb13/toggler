package com.thiagobezerra.toggler.service;

import com.thiagobezerra.toggler.model.Toggle;
import org.springframework.stereotype.Service;

@Service
public class ToggleService {
    public String save(Toggle toggle) {
        return toggle.getName();
    }
}
