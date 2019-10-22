package com.thiagobezerra.toggler.repository;

import com.thiagobezerra.toggler.model.Toggle;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToggleRepository extends Neo4jRepository<Toggle, String> {
    @Query("MATCH (n:Toggle { name: {name} }) " +
           "SET n.value = {value} " +
           "RETURN n.name")
    String update(String name, Boolean value);

//    List<String> findByService(String serviceName, String serviceVersion);
}
