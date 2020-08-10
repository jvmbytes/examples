# spring boot example

run example:
```bash
mvn clean package
java -javaagent:~/.m2/repository/com/jvmbytes/agent/inst-agent/1.0.1/inst-agent-1.0.1.jar \
    -jar spring-boot-example/target/spring-boot-example-1.0-SNAPSHOT.jar

# get user when plugin not loaded
curl http://localhost:8080/user?name=wongoo

# load plugin
curl http://localhost:8080/load_example

# get user when plugin loaded
curl http://localhost:8080/user?name=wongoo

# unload plugin
curl http://localhost:8080/unload_example

# get user when plugin not loaded
curl http://localhost:8080/user?name=wongoo
```
