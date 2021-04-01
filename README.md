```
 ██████╗ ██████╗ ███╗   ██╗███████╗██╗ ██████╗     ███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗ 
██╔════╝██╔═══██╗████╗  ██║██╔════╝██║██╔════╝     ██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗
██║     ██║   ██║██╔██╗ ██║█████╗  ██║██║  ███╗    ███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝
██║     ██║   ██║██║╚██╗██║██╔══╝  ██║██║   ██║    ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗
╚██████╗╚██████╔╝██║ ╚████║██║     ██║╚██████╔╝    ███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║
 ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝     ╚═╝ ╚═════╝     ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝
                                                                                                                                                                        ░                   
```
**Config Server** 는 다음과 가능 기능을 한다.
- 분산 시스템에 흩어져 있는 configuration 파일을 한곳([Config-Repo](http://meowpunch@yona.antock.com:8999/meowpunch/config-repo))에 모아 관리하고, Config Client 들은 Config Server 에 config 파일을 요청하여 사용한다.
- git 저장소에서 변경 사항을 webhook 을 통해 Config Server 에 알리고 메세지 브로커는 이러한 이벤트를 Config Client 들에게 전달하여 `refresh` 를 통해 변경된 config 를 사용한다.

# TODO
- [ ] git 저장소 변경 및 private 적용
- [ ] Spring Cloud Bus 및 webhook 적용

# SETUP
## Config Server
- dependency: `build.gradle` 주석 확인
- properties: `resource/application.yml` 주석 확인
```yaml
# application.yml
server:
  port: 8888
spring.cloud.config.server:
  git:
    uri: http://meowpunch@yona.antock.com:8999/meowpunch/config-repo
    searchPaths:
      - hubble
      - hubble-api
    ...
```

## Config Client
2021.03 Spring Boot `2.2.5.RELEASE` 버전 기준, 아래와 같은 방법으로 기존 서비스들을 Config Client 로 만들어 줄 수 있다. 
최신 버전은 `bootify-kotlin` 참

### dependency
- 현재 spring boot ver[`2.2.5.RELEASE`] 와 호환되는 spring cloud ver[`Hoxton.SR3`] 을 잘 맞춰야 한다.
```groovy
// build.gradle
ext {
	set('springCloudVersion', "Hoxton.SR3")
}
dependencies {
	// spring cloud
	implementation 'org.springframework.cloud:spring-cloud-starter-config'
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
}
dependencyManagement {
	imports {
		mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
	}
}
```

### config files

- 개발 및 운영 프로퍼티들은 Config Repo 로 옮긴다. default 프로퍼티 경우 아래와 같은 설정을 추가해야 이후 로컬 환경에서의 프로퍼티로 override 가 가능하다.
```properties
# override the remote properties with their own System properties or config files
spring.cloud.config.allowOverride=true
# override from any local property source.
spring.cloud.config.overrideNone=true
```
더 자세한 사항은 [여기](https://cloud.spring.io/spring-cloud-commons/multi/multi__spring_cloud_context_application_context_services.html#overriding-bootstrap-properties) 참고

- `resoruces/bootstrap.properties` 를 추가해준다. (최신버전은 다름)
```properties
# bootstrap.properties
# follow up Spring Cloud Config 2.2.2 doc
# https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.2.2.RELEASE/reference/html/
# default `application`
spring.application.name=hubble-api
# default `http://localhost:8888`
spring.cloud.config.uri=${CONFIG_SERVER:http://localhost:8888}
# Spring Boot Actuator 에서 endpoint 중 config 를 재설정하는 endpoint `/actuator/refresh` 만 열어둔다.
management.endpoints.jmx.exposure.exclude=*
management.endpoints.web.base-path=/actuator
management.endpoints.web.exposure.include=refresh
```

- `resources/application-{profile}.properties` 을 추가하여 로컬환경에서 자유롭게 테스트 

# Test

## With Bootiful Test Client
두가지 테스트 Client 들 와 함께 테스트를 진행해볼 수 있다.

### Spec
1. `client/bootfiy-java`
    - env: Spring Boot `2.2.5` & Spring Cloud `Hoxton.SR3` & Spring Cloud Config `2.2.2` [2021.03 허블 기준]
    - config-repo: http://yona.antock.com:8999/meowpunch/config-repo/code/master#bootiful-java
2. `client/bootify-kotlin`
    - env: Spring Boot `2.4.4` & Spring Cloud `2020.0.2` & Spring Cloud Config `3.0.3` [2021.03 기준 최신]
    - config repo: http://yona.antock.com:8999/meowpunch/config-repo/code/master#bootiful-kotlin

### Env
- 아래의 설정을 변경하면서 두 클라이언트 들의 프로파일 및 포트(host, not container)를 변경해가며 테스트 할 수 있다
```properties
# .env
BOOTIFUL_JAVA_PROFILE=local
BOOTIFUL_JAVA_PORT=8007
BOOTIFUL_KOTLIN_PROFILE=dev
BOOTIFUL_KOTLIN_PORT=8008
```

### How
- build jar files, config server and two client 
```shell
>  ./gradlew build
>  ./gradlew build -p client/bootiful-java
>  ./gradlew build -p client/bootiful-kotlin
```

- build docker image and exec with docker-compose
```shell
> docker-compose --env-file .env up --build
```

- request GET method to `/message`
```shell
> curl -XGET 'http://localhost:8005/message'
"Hello, I'm from bootiful-java-dev.properties"   
> curl -XGET 'http://localhost:8006/message'
"Hello, I'm from application-local.properties of bootiful-kotlin"
```

- reflect the changed state of configuration
  
  custom:message: `Hello, I'm from bootiful-java-dev.properties` -> `Hello, I'm from bootiful-java-dev.properties changed
```shell
> curl -XPOST 'http://localhost:8005/actuator/refresh'
# custom message 를 변경하여 아래와 같은 response 받음
["config.client.version","custom.message"]                    
>  curl -XGET 'http://localhost:8005/message'
"Hello, I'm from bootiful-java-dev.properties changed"
```
