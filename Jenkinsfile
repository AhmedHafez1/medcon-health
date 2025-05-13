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
                    sh 'apt-get update && apt-get install -y \
                        libglib2.0-0 \
                        libnss3 \
                        libatk1.0-0 \
                        libatk-bridge2.0-0 \
                        libcups2 \
                        libdrm2 \
                        libxkbcommon0 \
                        libxcomposite1 \
                        libxdamage1 \
                        libxfixes3 \
                        libxrandr2 \
                        libgbm1 \
                        libasound2'
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
