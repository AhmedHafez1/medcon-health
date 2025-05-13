pipeline {
    agent {
        docker {
            image 'node:22.15.0-bullseye'
            args '--privileged'
        }
    }

    tools {
        nodejs 'Node 22.15.0'
    }

    stages {
        stage('Setup Chrome') {
            steps {
                sh '''
                    apt-get update -y
                    apt-get install -y wget gnupg
                    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
                    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list
                    apt-get update -y
                    apt-get install -y google-chrome-stable libglib2.0-0 libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 \
                    libdrm2 libxkbcommon0 libxcomposite1 libxdamage1 libxfixes3 libxrandr2 libgbm1 libasound2
                '''
            }
        }
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
                    sh ''
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
