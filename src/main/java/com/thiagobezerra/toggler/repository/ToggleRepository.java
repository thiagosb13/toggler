package com.thiagobezerra.toggler.repository;

import com.thiagobezerra.toggler.model.Toggle;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToggleRepository extends Neo4jRepository<Toggle, String> {
    @Query("MATCH (n:Toggle { name: {name} }) "
         + "SET n.value = {value} "
         + "RETURN n.name")
    String update(String name, Boolean value);

    @Query("MATCH (n:Toggle { value : false })<-[OVERRIDEN_BY]-(s:Service {name : {name}, version : {version}}) RETURN n.name "
         + "UNION "
         + "MATCH (n:Toggle { value : true })-[r:RESTRICTED_TO]->(s:Service {name : {name}, version : {version}}) RETURN n.name "
         + "UNION "
         + "MATCH (n:Toggle { value : true }) WHERE NOT (n)-[:RESTRICTED_TO]->() AND NOT (n)-[:EXCEPTED_BY]->(:Service { name : {name}, version : {version} }) RETURN n.name")
    List<String> findByService(String name, String version);
}
