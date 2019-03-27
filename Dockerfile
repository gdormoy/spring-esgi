FROM maven: 3 - alpine
RUN mkdir - p / usr / src / app
WORKDIR / usr / src / app
COPY. / usr / src / app
RUN mvn clean package
VOLUME["/kotlin-data"]
EXPOSE 8082
CMD[&quot;java&quot;, &quot;-jar&quot;, &quot;target/<name jar="" kotlin="" of="" your="">.jar&quot;]