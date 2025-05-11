pipeline {
    agent any

    tools {
        nodejs 'Node 22.15.0'
    }

    stages {
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm ci'
                    sh 'npm run lint'
                    sh 'npm run build --configuration=production'
                }
            }
        }

        stage('Test Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm run test -- --watch=false --browsers=ChromeHeadlessNoSandbox'
                    junit '**/test-results/*.xml'
                }
            }
        }

        stage('Package Application') {
            steps {
                sh 'mkdir -p dist'
                sh 'cp backend/target/*.jar dist/'
                sh 'cp -r frontend/dist/* dist/'
                archiveArtifacts artifacts: 'dist/**', fingerprint: true
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
