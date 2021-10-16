```

  _____ ____  ____   ____  ____    ____         __  _       ___   __ __  ___        ___ ___  _____  ____ 
 / ___/|    \|    \ |    ||    \  /    |       /  ]| |     /   \ |  |  ||   \      |   |   |/ ___/ /    |
(   \_ |  o  )  D  ) |  | |  _  ||   __|      /  / | |    |     ||  |  ||    \     | _   _ (   \_ |  o  |
 \__  ||   _/|    /  |  | |  |  ||  |  |     /  /  | |___ |  O  ||  |  ||  D  |    |  \_/  |\__  ||     |
 /  \ ||  |  |    \  |  | |  |  ||  |_ |    /   \_ |     ||     ||  :  ||     |    |   |   |/  \ ||  _  |
 \    ||  |  |  .  \ |  | |  |  ||     |    \     ||     ||     ||     ||     |    |   |   |\    ||  |  |
  \___||__|  |__|\_||____||__|__||___,_|     \____||_____| \___/  \__,_||_____|    |___|___| \___||__|__|
                                                                                                         
                                                                          
```
cloned and customized from [here](https://github.com/joneconsulting/msa_with_spring_cloud)

This project includes the followings
- [service-discovery](https://github.com/meowpunch/spring-cloud-msa/tree/main/service-discovery)
- [configuration](https://github.com/meowpunch/spring-cloud-msa/tree/main/configuration), [repository](https://github.com/meowpunch/spring-cloud-msa/tree/main/repository)
- [api-gateway](https://github.com/meowpunch/spring-cloud-msa/tree/main/api-gateway)
- [bootiful-java](https://github.com/meowpunch/spring-cloud-msa/tree/main/bootiful-java), [bootiful-kotlin](https://github.com/meowpunch/spring-cloud-msa/tree/main/bootiful-kotlin)

# Get Started
## Environment
- spring version compatibility

|        | spring boot   | spring cloud|
|--------|---------------|-------------|
| hubble | 2.2.5.RELEASE | Hoxton.SR3  |
| calc   | 2.3.x         | Hoxton.SR10 |
| latest | 2.4.4         | 2020.0.2    |

- Spring Cloud Netflix is placed into maintenance mode. [doc](https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now)

|                 | maintenance                 | replacement                    |
|-----------------|-----------------------------|--------------------------------|
| circuit breaker | Hystrix                     | Resilience4j                   |
| monitor         | Hystrix Dashboard / Turbine | Micrometer + Monitoring System |
| load balancer   | Ribbon                      | Spring Cloud Loadbalancer      |
| api gateway     | Zuul 1                      | Spring Cloud Gateway           |
| configuration   | Archaius 1                  | Spring Cloud Config            |

## Test
- gradle build all services
```shell
# in root directory
> ./gradlew build 
```

- docker compose build
```shell
> docker-compose --env-file .env up --build
```

- request to services
```shell
> curl -XGET 'http://localhost:8000/bootiful-java/message'
```

# Laboratory
## Config First vs Discovery First
- bootiful-java config for [discovery first](https://cloud.spring.io/spring-cloud-static/Greenwich.RELEASE/multi/multi__spring_cloud_config_client.html#discovery-first-bootstrap)
```
spring:
  cloud:
    config:
      # fail startup of a service if it cannot connect to the Config Server
      fail-fast: true
      discovery:
        # discovery-first
        enabled: true
        # config server id in service discovery
        service-id: configuration
```
- docker-compose for discovery first test
```dockerfile
configuration:
    container_name: configuration
    ...
    depends_on:
      service-discovery:
        condition: service_healthy
```

## Auto Refreshing Config
Explain how to auto refresh configuration.

# Furthermore
## Container Environment
in case of docker network `bridge`, services register docker subnet IP address to service discovery (eureka).
If api gateway and microservices are running on different hosts, api gateway cannot request to microservices. 
```shell
# check eureka which IP address are registered.
> curl -XGET 'http://localhost:8761/eureka/apps'
...
<application>
  <name>BOOTIFUL-JAVA</name>
  <instance>
    <instanceId>bootiful-java:fae5c706c532b6897a44588c4e54ebf8</instanceId>
    <hostName>172.21.0.6</hostName>
    <app>BOOTIFUL-JAVA</app>
    <ipAddr>172.21.0.6</ipAddr>
    <status>UP</status>
    <overriddenstatus>UNKNOWN</overriddenstatus>
    <port enabled="true">41429</port>
    ...
</application>
...
```
So, in this situation, set the docker network to `host` to use the host network directly.
```shell
> docker run -d --network host --name {container_name} {image}
```
Note that you can only use the host networking driver on Linux hosts, not Mac or Windows. [Doc](https://docs.docker.com/network/host/)
> The host networking driver only works on Linux hosts, and is not supported on Docker Desktop for Mac, Docker Desktop for Windows, or Docker EE for Windows Server.

# Reference
- [Need to tell eureka client running in Docker to use host's IP](https://github.com/spring-cloud/spring-cloud-netflix/issues/432)
- [Deploying Microservices: Spring Cloud vs Kubernetes](https://dzone.com/articles/deploying-microservices-spring-cloud-vs-kubernetes)