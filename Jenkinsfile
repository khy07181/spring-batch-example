pipeline {
    agent any

    stages {
        stage('Github connection') {
            steps {
                git branch: 'main', url: 'https://github.com/khy07181/spring-batch-example.git'
            }
        }
        stage('Build') {
            when { changeset "**/*" }
            steps {
                echo 'Building..'
                sh './gradlew clean build'
            }
        }
        stage('run') {
            steps {
                sh 'java -jar -Dspring.profiles.active=${PROFILES} ./build/libs/*SNAPSHOT.jar --job.name=${jobName} startDateTime=${startDateTime}'
            }
        }
    }
}
