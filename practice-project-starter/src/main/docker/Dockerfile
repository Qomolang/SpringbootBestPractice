#FROM reg.docker.alibaba-inc.com/alibase/alios7u2-min
FROM openjdk:8-slim

ENV JAVA_OPTS="-Xms1024M -Xmx1500M -XX:+UseG1GC -Xloggc:logs/gc.log"
ENV PROFILES="daily"

ARG TARGET_PATH

# 通过docker-maven-plugin传入arg，并固定为环境变量
ARG APP_NAME
ENV APP_NAME=${APP_NAME}

## 增加jdk路径
#ENV PATH "$PATH:/opt/taobao/java/bin/"

# 测试用
# ARG用法 不推荐
RUN echo ${APP_NAME} >> test.txt
RUN echo ${TARGET_PATH} >> test.txt
# ENV用法 推荐
RUN echo $APP_NAME.jar >> test.txt
# 打印docker内目录
RUN echo pwd = $(pwd) >> test.txt
RUN echo ls = $(ls) >> test.txt

#build上下文由docker-maven-plugin传入
ADD target/$APP_NAME.jar /home/admin/$APP_NAME/target/

WORKDIR /home/admin/

ENTRYPOINT ["/bin/sh","-c", "java ${JAVA_OPTS} -jar /home/admin/$APP_NAME/target/$APP_NAME.jar --spring.profiles.active=${PROFILES}"]