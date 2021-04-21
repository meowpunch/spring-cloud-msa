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
- service-discovery
- configuration
- api-gateway
- bootiful-java, bootiful-kotlin

Get Started
## Local
- gradle build all services
```shell
# in root directory
> ./gradlew build 
```
- docker compose build
```shell
> docker-compose --env-file .env up --build
```

