# Utiliza una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR a la imagen Docker
COPY target/my-app.jar /app/my-app.jar

# Exponemos el puerto 8081 (puedes cambiarlo si es necesario)
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "my-app.jar"]
