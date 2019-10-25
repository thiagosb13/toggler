package com.thiagobezerra.toggler.service;

import com.thiagobezerra.toggler.exception.NotFoundException;
import com.thiagobezerra.toggler.model.Toggle;
import com.thiagobezerra.toggler.repository.ToggleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToggleServiceTest {
    private ToggleService toggleService;

    @Mock
    private ToggleRepository toggleRepository;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setup() {
        toggleService = new ToggleService(toggleRepository, notificationService);
    }

    @Test
    void given_saving_a_toggle_when_it_already_exists_should_throw_an_exception() {
        when(toggleRepository.existsById("isButtonBlue")).thenReturn(true);

        var toggle = new Toggle();
        toggle.setName("isButtonBlue");

        var exception = assertThrows(IllegalArgumentException.class, () -> toggleService.save(toggle));
        assertThat(exception.getMessage(), is(equalTo("There's already a toggle named isButtonBlue. You should updated it if you wish.")));

        verify(toggleRepository, never()).save(toggle);
    }

    @Test
    void given_saving_a_toggle_when_it_is_a_new_one_should_save_it() {
        when(toggleRepository.existsById("isButtonBlue")).thenReturn(false);

        var toggle = new Toggle();
        toggle.setName("isButtonBlue");

        toggleService.save(toggle);

        verify(toggleRepository, times(1)).save(toggle);
        verify(notificationService, times(1)).notify(any());
    }

    @Test
    void given_updating_a_toggle_when_it_does_not_exist_should_thrown_an_exception() {
        when(toggleRepository.findById("isButtonBlue")).thenReturn(Optional.empty());

        var toggle = new Toggle();
        toggle.setName("isButtonBlue");

        var exception = assertThrows(NotFoundException.class, () -> toggleService.update(toggle));
        assertThat(exception.getMessage(), is(equalTo("A toggle named isButtonBlue was not found. You can create one if you wish.")));

        verify(toggleRepository, never()).save(toggle);
    }

    @Test
    void given_updating_a_toggle_when_it_exists_should_update_it() {
        var foundToggle = new Toggle();
        foundToggle.setName("isButtonBlue");
        when(toggleRepository.findById("isButtonBlue")).thenReturn(Optional.of(foundToggle));

        var toggle = new Toggle();
        toggle.setName("isButtonBlue");

        toggleService.update(toggle);

        verify(toggleRepository, times(1)).save(foundToggle);
        verify(notificationService, times(1)).notify(any());
    }

    @Test
    void given_updating_value_of_a_toggle_when_it_does_not_exist_should_thrown_an_exception() {
        when(toggleRepository.update("isButtonBlue", true)).thenReturn(Optional.empty());

        var toggle = new Toggle();
        toggle.setName("isButtonBlue");

        var exception = assertThrows(NotFoundException.class, () -> toggleService.update("isButtonBlue", true));
        assertThat(exception.getMessage(), is(equalTo("Cannot update toggle named isButtonBlue due to it was not found.")));
    }

    @Test
    void given_updating_value_of_a_toggle_when_it_exists_should_update_it() {
        var toggle = new Toggle("isButtonBlue", true, Set.of(), Set.of(), Set.of());
        var toggleName = toggle.getName();
        when(toggleRepository.update(toggleName, true)).thenReturn(Optional.of(toggle));

        toggleService.update(toggleName, true);

        verify(toggleRepository, times(1)).update(toggleName, true);
        verify(notificationService, times(1)).notify(any());
    }

    @Test
    void given_getting_toggles_to_a_service_should_get_them_from_repository() {
        when(toggleRepository.findByService("ABC", "1.0")).thenReturn(List.of("isButtonBlue", "isButtonGreen"));

        toggleService.findByService("ABC", "1.0");

        verify(toggleRepository, times(1)).findByService("ABC", "1.0");
    }
}
