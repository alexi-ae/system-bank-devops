pipeline {
    agent any
    environment {
        // Variables globales
        DOCKER_IMAGE = 'my-app-image'
        IMAGE_NAME = 'my-app-container'
    }
    stages {
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
