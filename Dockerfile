FROM tomcat:9.0.80-jre11-temurin
COPY target/treinamento.war $CATALINA_HOME/webapps/
RUN mv $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps/ && \
    sed 's/allow="\([^"]\|\\"\)*"/allow='\".*\"'/g' $CATALINA_HOME/webapps/manager/META-INF/context.xml
EXPOSE 8080
CMD ["catalina.sh", "run"]

