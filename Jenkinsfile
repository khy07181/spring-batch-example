pipeline {
    agent any

    stages {
        stage('Github connection') {
            steps {
                git branch: 'main', url: 'https://github.com/khy07181/spring-batch-example.git'
            }
        }
        stage('Build') {
            steps {
                sh 'docker build -t khy07181/spring-batch-practice:latest .'
            }
        }
        stage('SimpleJob') {
            steps {
                sh 'java -jar -Dspring.profiles.active=${PROFILE} ./build/libs/*.jar'
            }
        }
        stage('Run') {
            steps {
                sh 'docker run -d --name batch-practice -p 5432:5432 -e PROFILE=prod khy07181/spring-batch-practice'
            }
        }
        stage('Finish') {
            steps{
                sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
            }
        }
    }
}
