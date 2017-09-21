FROM jetty
COPY ./target/app.war /var/lib/jetty/webapps
EXPOSE 8080