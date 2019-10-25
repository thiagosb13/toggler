package com.thiagobezerra.toggler.service;

import com.thiagobezerra.toggler.exception.NotFoundException;
import com.thiagobezerra.toggler.model.Toggle;
import com.thiagobezerra.toggler.repository.ToggleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
public class ToggleService {
    private final ToggleRepository toggleRepository;
    private final NotificationService notificationService;

    public ToggleService(ToggleRepository toggleRepository, NotificationService notificationService) {
        this.toggleRepository = toggleRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public String save(Toggle toggle) {
        if (toggleRepository.existsById(toggle.getName())) {
            throw new IllegalArgumentException(format("There's already a toggle named %s. You should updated it if you wish.", toggle.getName()));
        }

        return saveOrUpdate(toggle);
    }

    @Transactional
    public String update(Toggle toggle) {
        var foundToggle = toggleRepository.findById(toggle.getName())
                                          .orElseThrow(() -> new NotFoundException(format("A toggle named %s was not found. You can create one if you wish.", toggle.getName())));

        foundToggle.setRestrictions(toggle.getRestrictions());
        foundToggle.setOverrides(toggle.getOverrides());
        foundToggle.setExceptions(toggle.getExceptions());
        foundToggle.setValue(toggle.getValue());

        return saveOrUpdate(foundToggle);
    }

    private String saveOrUpdate(Toggle toggle) {
        toggleRepository.save(toggle);

        notificationService.notify(toggle);

        return toggle.getName();
    }

    @Transactional
    public void update(String name, Boolean value) {
        var updatedToggle = toggleRepository.update(name, value);

        if (updatedToggle.isEmpty()) {
            throw new NotFoundException(format("Cannot update toggle named %s due to it was not found.", name));
        }

        notificationService.notify(updatedToggle);
    }

    public List<String> findByService(String name, String version) {
        return toggleRepository.findByService(name, version);
    }
}
