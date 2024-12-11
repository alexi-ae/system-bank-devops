pipeline {
    agent any
    environment {
        // Variables globales
        DOCKER_IMAGE = 'system-bank-devops-image'
        IMAGE_NAME = 'system-bank-devops-container'
    }
    stages {
        stage('Prepare Environment') {
                steps {
                    script {
                        // Levantar contenedor de PostgreSQL
                        sh 'docker run -d --name postgres -p 5432:5432 -e POSTGRES_USER=userbanksystem -e POSTGRES_PASSWORD=sql -e POSTGRES_DB=db-bank-system-devops postgres:latest'

                        // Levantar contenedor de Redis
                        sh 'docker run -d --name redis -p 6379:6379 redis:latest'
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
                    sh 'docker run -d -p 8081:8081 --name ${IMAGE_NAME} ${DOCKER_IMAGE}'
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
