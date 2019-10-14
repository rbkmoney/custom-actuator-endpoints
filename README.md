# custom-actuator-endpoints

** КАК ДОБАВИТЬ В ПРОЕКТ? ** 

1) добавить зависимость в pom.yml
```
<dependency>
    <groupId>com.rbkmoney</groupId>
    <artifactId>custom-actuator-endpoints</artifactId>
    <version>0.0.1</version>
</dependency>
```
2) добавить в application.yml проперти - `management.endpoint.health.show-details: always`
