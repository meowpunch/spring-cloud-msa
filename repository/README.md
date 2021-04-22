```

 ██████╗ ██████╗ ███╗   ██╗███████╗██╗ ██████╗     ██████╗ ███████╗██████╗  ██████╗ 
██╔════╝██╔═══██╗████╗  ██║██╔════╝██║██╔════╝     ██╔══██╗██╔════╝██╔══██╗██╔═══██╗
██║     ██║   ██║██╔██╗ ██║█████╗  ██║██║  ███╗    ██████╔╝█████╗  ██████╔╝██║   ██║
██║     ██║   ██║██║╚██╗██║██╔══╝  ██║██║   ██║    ██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║
╚██████╗╚██████╔╝██║ ╚████║██║     ██║╚██████╔╝    ██║  ██║███████╗██║     ╚██████╔╝
 ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝     ╚═╝ ╚═════╝     ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ 

                                                                                    
```
Config Repo는 다음과 같은 역할을 한다.
- 각 서비스(Config Client)들이 사용할 프로퍼티를 저장하고 Config Server 를 통해 서비스들에게 전달된다.

## File Structure & Name Convention
- Config Server는 Config Client의 app name과 profile을 통해, `{spring.application.name}-{spring.profiles-active}.properties(yml)` 파일을 가져온다.(default 포함)
- 예를들어 bootiful-java 서비스 경우 profile `cld-test`를 active 한 경우 bootiful-java.yml(default) 와 bootiful-java-dev.yml(dev)를 가져온다.
- `config-repo/` 안에 전부 넣어도 읽어들이는 데 문제는 없지만 앱 이름으로 디렉토리를 만들어 구분했다.

## Override Remote Properties
- 로컬 환경에서 변경하여 테스트하기 위해서는 아래와 같은 설정을 추가해야 로컬 파일로 부터 덮어쓰기가 가능하다.
  > The property sources that are added to your application by the bootstrap context are often “remote” (from example, from Spring Cloud Config Server). 
  > By default, they cannot be overridden locally. If you want to let your applications override the remote properties with their own system properties or config files, 
  > the remote property source has to grant it permission by setting spring.cloud.config.allowOverride=true (it does not work to set this locally). 
  > Once that flag is set, two finer-grained settings control the location of the remote properties in relation to system properties and the application’s local configuration
    
  - `spring.cloud.config.overrideNone=true`: 어떠한 로컬 프로퍼티 소스든 덮어쓰기 가능
  - `spring.cloud.config.overrideSystemProperties=false`: 로컬 파일로는 불가능하고 오직 system properties, command line arguments 그리고 environment variables 을 통해서만 가능

```properties
# spring cloud config
spring.cloud.config.allowOverride=true
spring.cloud.config.overrideNone=true
```
