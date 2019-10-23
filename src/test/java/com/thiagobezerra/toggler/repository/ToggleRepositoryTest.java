package com.thiagobezerra.toggler.repository;

import com.thiagobezerra.toggler.model.Service;
import com.thiagobezerra.toggler.model.Toggle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.neo4j.ogm.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataNeo4jTest
public class ToggleRepositoryTest {
    private static ServerControls embeddedDatabaseServer;

    private final ToggleRepository toggleRepository;

    @Autowired
    public ToggleRepositoryTest(ToggleRepository toggleRepository) {
        this.toggleRepository = toggleRepository;
    }

    @BeforeAll
    static void startEmbeddedDatabaseServer() {
        embeddedDatabaseServer = TestServerBuilders.newInProcessBuilder()
                                                   .newServer();
    }

    @AfterAll
    static void stopEmbeddedDatabaseServer() {
        embeddedDatabaseServer.close();
    }

    @BeforeEach
    void setup() {
        var abcService = new Service("ABC", "1.0");

        var isButtonBlue = new Toggle("isButtonBlue", false, Set.of(), Set.of(abcService), Set.of());
        toggleRepository.save(isButtonBlue);

        var isButtonGreen = new Toggle("isButtonGreen", true, Set.of(abcService), Set.of(), Set.of());
        toggleRepository.save(isButtonGreen);

        var isButtonRed = new Toggle("isButtonRed", true, Set.of(), Set.of(), Set.of(abcService));
        toggleRepository.save(isButtonRed);
    }

    @AfterEach
    void teardown() {
        toggleRepository.deleteAll();
    }

    @TestConfiguration
    static class Config {
        @Bean
        public org.neo4j.ogm.config.Configuration configuration() {
            return new Configuration.Builder()
                                    .uri(embeddedDatabaseServer.boltURI().toString())
                                    .build();
        }
    }

    @Test
    void given_finding_toggles_by_service_should_consider_overriding() {
        var toggles = toggleRepository.findByService("ABC", "1.0");

        assertThat(toggles.stream().anyMatch(t -> t.equals("isButtonBlue")), is(true));
    }

    @Test
    void given_finding_toggles_by_service_should_consider_restriction() {
        var abcToggles = toggleRepository.findByService("ABC", "1.0");

        assertThat(abcToggles.stream().anyMatch(t -> t.equals("isButtonGreen")), is(true));

        var defToggles = toggleRepository.findByService("DEF", "1.0");

        assertThat(defToggles.stream().noneMatch(t -> t.equals("isButtonGreen")), is(true));
    }

    @Test
    void given_finding_toggles_by_service_should_consider_exceptions() {
        var toggles = toggleRepository.findByService("ABC", "1.0");

        assertThat(toggles.stream().noneMatch(t -> t.equals("isButtonRed")), is(true));
    }
}
