FROM jetty
COPY ./target/example.war /var/lib/jetty/webapps
EXPOSE 8080