# How to run

```shell script
mvn package

java -javaagent:${HOME}/.m2/repository/com/jvmbytes/agent/inst-agent/1.0.1/inst-agent-1.0.1.jar \
      -jar target/spy-example-1.0-SNAPSHOT.jar

```