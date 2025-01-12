FROM openjdk:17-alpine
COPY components/services/implementation/build/libs/jira-sd-implementation.jar /jira-sd-implementation.jar
ENTRYPOINT ["java","-jar","/jira-sd-implementation.jar"]
