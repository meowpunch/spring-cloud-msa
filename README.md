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
- 분산 시스템에 흩어져 있는 configuration 파일을 한곳에 모아 관리한다.
- Config Client 들은 Config Server 에 config 파일을 요청하여 사용한다.
- git 저장소에서 변경 사항을 webhook 을 통해 Config Server 에 알리고 메세지 브로커는 이러한 이벤트를 Config Client 들에게 전달하여 `refresh` 를 통해 변경된 config 를 사용한다.

# TODO
- [ ] git 저장소 변경 및 private 적용
- [ ] 

# Get Started
## Setup
- configuration repository
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

# Test
## With Bootify(Test Config Client)
### Spec
1. `client/bootfiy-java`
    - env: Spring Boot `2.2.5` & Spring Cloud `Hoxton.SR3` & Spring Cloud Config `2.2.2` [2021.03 허블 기준]
    - config-repo: http://yona.antock.com:8999/meowpunch/config-repo/code/master#bootiful-java 
2. `client/bootify-kotlin`
    - env: Spring Boot `2.4.4` & Spring Cloud `2020.0.2` & Spring Cloud Config `3.0.3` [2021.03 기준 최신]
    - config repo: http://yona.antock.com:8999/meowpunch/config-repo/code/master#bootiful-kotlin
### Env
- 아래의 설정을 변경하면서 두 클라이언트 들의 프로파일을 변경해가며 테스트 할 수 있다.
```properties
# .env
BOOTIFUL_JAVA_PROFILE=local
BOOTIFUL_KOTLIN_PROFILE=dev
```
### How
- build config server and two client
```shell
>  ./gradlew build
>  ./gradlew build -p client/bootiful-java
>  ./gradlew build -p client/bootiful-kotlin
```
- exec with docker-compose
```shell
> docker-compose up --build
```
