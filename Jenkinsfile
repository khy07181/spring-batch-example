pipeline {
    agent {
            docker { image 'node:16.13.1-alpine' }
    }

    stages {
        stage('Github connection') {
            steps {
                git branch: 'main', credentialsId: 'node1', url: 'https://github.com/khy07181/spring-batch-example.git'
            }
        }
        stage('Build') {
            when { changeset "**/*" }
            steps {
                echo 'Building..'
                sh './gradlew clean build'
            }
        }
        stage('SimpleJob') {
            steps {
                sh 'java -jar -Dspring.profiles.active=${PROFILES} ./build/libs/*.jar'
            }
        }
    }
}
