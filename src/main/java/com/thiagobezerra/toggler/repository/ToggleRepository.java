package com.thiagobezerra.toggler.repository;

import com.thiagobezerra.toggler.model.Toggle;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToggleRepository extends Neo4jRepository<Toggle, Long> {
}
