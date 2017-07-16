FROM openjdk:8-alpine

COPY wtfc.sh .
COPY caronte.jar .

ENTRYPOINT ["./wtfc.sh", "-T", "40", "nc -z rabbit 5672", "&&", "java", "-jar", "caronte.jar"]
