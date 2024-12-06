# Utiliza una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR desde el directorio target al contenedor
# Usamos un comodín para copiar cualquier JAR generado
COPY target/*.jar /app/system-bank-devops.jar

# Exponemos el puerto 8081 (puedes cambiarlo si es necesario)
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/system-bank-devops.jar"]
