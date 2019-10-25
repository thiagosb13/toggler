# Toggler REST API
---

A REST API to manage toggles and services/applications relationships.

## Prerequisites
---

- Java 11+
- Maven
- Docker (optional)

## Usage
---

### Tests

    mvn test

### Dependencies

Linux environment (command line):

    docker-compose up -d

Others environments (with docker):

    docker run --name testneo4j -p7474:7474 -p7687:7687 -d -v $HOME/neo4j/data:/data -v $HOME/neo4j/logs:/logs -v $HOME/neo4j/import:/var/lib/neo4j/import -v $HOME/neo4j/plugins:/plugins --env NEO4J_AUTH=neo4j/test neo4j:latest

    docker run --name activemq -it --rm -P -v /data/activemq:/data -v /var/log/activemq:/var/log/activemq -p 1883:1883 -p 8161:8161 -p 5672:5672 -p 61614:61614 -p 61616:61616 -p 61613:61613 webcenter/activemq:latestApplication

Without docker it needs to install Neo4J and ActiveMQ latest versions.

### Application

    mvn spring-boot:run

### Credentials

1. Admin
    - username: admin
    - password: admin

2. User without admin privileges
    - username: user
    - password: user

## Accessing data
---

### Neo4J

    http://localhost:7474/browser/
    username: neo4j
    password: test

### ActiveMQ

    http://localhost:8161/

### API Docs

    http://localhost:8080/swagger-ui.html

## Toggle JSON Example
```json
    {
    	"name" : "isButtonBlue",
    	"value" : true,
    	"restrictions" : [
    		{
    			"name" : "DEF",
    			"version" : "1.0"
    		}
    	],
    	"overrides" : [
    		{
    			"name" : "ABC",
    			"version" : "1.0"
    		}
    	],
    	"exceptions" : [
    		{
    			"name" : "GHI",
    			"version" : "1.0"
    		}
    	]
    }
```

## License
---

Distributed under the MIT License. See [LICENSE](https://opensource.org/licenses/MIT) for more information.
