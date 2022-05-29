# Base Container  
FROM azul/zulu-openjdk:11

# Expose operational port and the health port
EXPOSE 8700 9001

# Make the directory we need
RUN mkdir -p /app

# change working directory
WORKDIR /app

# copy jar into image
COPY *.jar /app/

# create a symlink because we have to have a single file to pass to "java -jar some.jar
RUN ln -s $(ls -1 *.jar) app.jar

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh
CMD /entrypoint.sh
