pipeline {
    agent any

    environment {
        ENV = "${jobName}"
    }

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
        stage('simpleJob') {
            when {
                environment name:"ENV", value:"simpleJob"
            }
            steps {
                sh 'java -jar -Dspring.profiles.active=${PROFILES} ./build/libs/*SNAPSHOT.jar --job.name=simpleJob startDateTime=${startDateTime}'
            }
        }
        stage('dayJob') {
            when {
                environment name:"ENV", value:"dayJob"
            }
            steps {
                sh 'java -jar -Dspring.profiles.active=${PROFILES} ./build/libs/*SNAPSHOT.jar --job.name=dayJob startDateTime=${startDateTime}'
            }
        }
    }
}
