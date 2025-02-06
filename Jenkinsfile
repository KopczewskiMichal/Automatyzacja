pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir('app') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                dir('app') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'app/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
