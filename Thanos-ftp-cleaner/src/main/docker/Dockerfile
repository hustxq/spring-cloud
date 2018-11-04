FROM registry.ssecloud.com/library/jre8

ADD Thanos-ftp-cleaner.jar ./

EXPOSE 18051
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT java $JAVA_OPTS -jar Thanos-ftp-cleaner.jar

#ADD Additional Arguments In CMD