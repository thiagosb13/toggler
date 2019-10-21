package com.thiagobezerra.toggler.service;

import com.thiagobezerra.toggler.model.Toggle;
import com.thiagobezerra.toggler.repository.ToggleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ToggleService {
    private final ToggleRepository toggleRepository;

    public ToggleService(ToggleRepository toggleRepository) {
        this.toggleRepository = toggleRepository;
    }

    @Transactional
    public String save(Toggle toggle) {
        toggleRepository.save(toggle);

        return toggle.getName();
    }
}
