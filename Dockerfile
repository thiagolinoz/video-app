FROM amazoncorretto:21-al2023-jdk
RUN dnf update -y && dnf install -y shadow-utils \
    && dnf clean all
RUN useradd -ms /bin/bash video-app
USER video-app
COPY ./target/video-app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]