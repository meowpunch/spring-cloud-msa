```

8""""8                                8""""8                            8                  
8      eeeee eeeee  e  eeeee eeeee    8    " e     eeeee e   e eeeee    8     eeeee eeeee  
8eeeee 8   8 8   8  8  8   8 8   8    8e     8     8  88 8   8 8   8    8e    8   8 8   8  
    88 8eee8 8eee8e 8e 8e  8 8e       88     8e    8   8 8e  8 8e  8    88    8eee8 8eee8e 
e   88 88    88   8 88 88  8 88 "8    88   e 88    8   8 88  8 88  8    88    88  8 88   8 
8eee88 88    88   8 88 88  8 88ee8    88eee8 88eee 8eee8 88ee8 88ee8    88eee 88  8 88eee8 
                                                                                           
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

- Spring Cloud Netflix -> Spring Cloud

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


# Furthermore
## Multi Host Env
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
