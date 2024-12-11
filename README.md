# BANKING SYSTEM CI/DI

Sistema bancario con la configuración para la utilización de herramientas de integración continua y despliegue continuo.




# Deploy
## Requisitos previos
Antes de realizar el despliegue de system-bank-devops:
- Instalar docker
- Proyecto subido en un repositorio para conexion remota

### Configuración

### 1. Dockerfile
```bash
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

```

### 2. Jenkinsfile
```bash
pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'system-bank-devops-image'
        IMAGE_NAME = 'system-bank-devops-container'
    }
    stages {
        stage('Setup Network') {
                steps {
                    script {
                        sh 'docker network create system-bank-devops-network || true'
                    }
                }
        }
        stage('Prepare Environment') {
                steps {
                    script {
                        sh '''
                        if [ $(docker ps -q -f name=postgres) ]; then
                            docker rm -f postgres
                        fi
                        '''
                        sh '''
                        if [ $(docker ps -q -f name=redis) ]; then
                            docker rm -f redis
                        fi
                        '''
                        // Levantar contenedor de PostgreSQL
                        sh 'docker run -d --name postgres --network system-bank-devops-network -p 5433:5432 -e POSTGRES_USER=userbanksystem -e POSTGRES_PASSWORD=sql -e POSTGRES_DB=db-bank-system-devops postgres:latest'
                        // Levantar contenedor de Redis
                        sh 'docker run -d --name redis --network system-bank-devops-network -p 6379:6379 redis:latest'
                    }
                }
            }
        stage('Checkout') {
            steps {
                // Clona el repositorio desde GitHub
                checkout scm
            }
        }
        stage('Build') {
            steps {
                // Compila el proyecto utilizando Maven
                script {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Verifica si el archivo JAR está presente
                    sh 'ls -l target/'
                    // Construye la imagen Docker
                    sh '''
                    docker build -t ${DOCKER_IMAGE} .
                    '''
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    // Verifica si el contenedor ya está corriendo y lo elimina si es necesario
                    sh '''
                    if [ $(docker ps -q -f name=${IMAGE_NAME}) ]; then
                        docker rm -f ${IMAGE_NAME}
                    fi
                    '''
                    // Ejecuta el contenedor Docker en segundo plano
                    // Se mapea el puerto 8081 del contenedor al puerto 8081 del host
                    sh 'docker run -d -p 8081:8081 --name ${IMAGE_NAME} --network system-bank-devops-network ${DOCKER_IMAGE} '
                }
            }
        }
        stage('Post-deployment') {
            steps {
                // Otras acciones después de ejecutar el contenedor (puedes agregar pruebas aquí)
                echo 'Aplicación desplegada exitosamente en el contenedor Docker'
            }
        }
    }
    post {
        always {
            // Limpiar recursos o enviar notificaciones, si es necesario
            echo 'Pipeline finalizado.'
        }
    }
}

```


## Pasos del Despliegue

Se considera que el jenkins tambien se levante en un contenedor.

### 1. Crear contenedor

```bash
docker run -d --name jenkins  -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins/jenkins:lts
```

### 2. Open bash:

```bash
docker exec -u root -it jenkins bash
```

### 3. Install maven en el contenedor de jenkins

```bash
apt-get update
apt-get update && apt-get install -y maven
```

### 4. Instalar docker

```bash
apt-get install -y docker.io
```

### 5. Validar instalación maven y docker

```bash
mvn -v
docker ps
```

### 6. Crear grupo de docker si no existe

```bash
groupadd docker
```

### 7. Agregar al grupo el usuario de jenkins

```bash
usermod -aG docker jenkins
```
### 8. Restaurar contenedor

```bash
docker restart jenkins
```
### 9. COnfigurar jenkins

#### Configurar jenkins
- Consultar repositorio (SCM): Cada 5 minutos
```bash
H/5 * * * * 
```

#### Pipeline
- Definición
```bash
Pipeline script from SCM
```
- SCM
```bash
Git
Repository: https://github.com/alexi-ae/system-bank-devops.git
Credentials: Agregar las credenciales de github en la sección de "Credentials"

```
Branches to build
```bash
main
```
Script Path
```bash
Jenkinsfile
```


### Validar contenedores

```bash
docker ps
```
Esto debería mostrar algo como:
```bash
CONTAINER ID   IMAGE                      COMMAND                  CREATED          STATUS          PORTS                                              NAMES
fa09721ee5d4   system-bank-devops-image   "java -jar /app/syst…"   54 minutes ago   Up 54 minutes   0.0.0.0:8081->8081/tcp                             system-bank-devops-container
f7d9febe30f6   redis:latest               "docker-entrypoint.s…"   55 minutes ago   Up 55 minutes   0.0.0.0:6379->6379/tcp                             redis
c71c49b54b94   postgres:latest            "docker-entrypoint.s…"   55 minutes ago   Up 55 minutes   0.0.0.0:5433->5432/tcp                             postgres
8f4de5d07dd9   jenkins/jenkins:lts        "/usr/bin/tini -- /u…"   3 hours ago      Up 3 hours      0.0.0.0:8080->8080/tcp, 0.0.0.0:50000->50000/tcp   jenkins
```
Asegúrate de que el estado de todos los contenedores sea Up. Si algún contenedor no está "Up", revisa los logs para detectar posibles errores.

## Autor
- **Nombre**: Alexi Acuña
- **Rol**: Desarrollador Principal
- **Descripción**: Desarrollador de software con experiencia en aplicaciones Java y Spring Boot.
  Apasionado por la creación de soluciones eficientes y escalables.
- **GitHub**: [github.com/alexi-ae](https://github.com/alexi-ae)
- **LinkedIn**: [linkedin.com/in/ronald-alexi-ae](https://www.linkedin.com/in/ronald-alexi-ae/)