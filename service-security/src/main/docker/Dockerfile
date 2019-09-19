FROM openjdk:8-jdk-alpine
#宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到tomcat容器的工作目录/tmp目录
VOLUME /tmp
ADD service-security-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.profiles.active=eureka02"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.profiles.active=eureka03"]