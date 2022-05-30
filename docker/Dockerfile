# Base Container  
FROM azul/zulu-openjdk:11

# Expose operational port and the health port
EXPOSE 8700 9001

# Make the directory we need
RUN mkdir -p /app

# change working directory
WORKDIR /app

# copy jar into image
COPY build/libs/csv-kafka-*.jar /app/app.jar

ENTRYPOINT java -Xmx128m -Xmx128m -jar app.jar