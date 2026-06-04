pipeline {
    agent none

    stages {
        stage('Checkout') {
            agent { label 'build-agent' }
            steps {
                sh 'echo "Checking out source code on $NODE_NAME"'
            }
        }

        stage('Build & Test') {
            parallel {
                stage('Unit Tests') {
                    agent { label 'tier-1' }
                    steps {
                        sh '''
                            echo "Running unit tests on $NODE_NAME"
                            echo "Architecture: $(uname -m)"
                            echo "OS: $(uname -s)"
                            sleep 3
                            echo "Unit tests: PASSED"
                        '''
                    }
                }
                stage('Integration Tests') {
                    agent { label 'tier-2' }
                    steps {
                        sh '''
                            echo "Running integration tests on $NODE_NAME"
                            echo "Architecture: $(uname -m)"
                            echo "OS: $(uname -s)"
                            sleep 3
                            echo "Integration tests: PASSED"
                        '''
                    }
                }
            }
        }

        stage('Package') {
            agent { label 'build-agent' }
            steps {
                sh '''
                    echo "Packaging artifacts on $NODE_NAME"
                    echo "Build artifacts ready for deployment"
                '''
            }
        }

        stage('Report') {
            agent { label 'build-agent' }
            steps {
                sh 'echo "Pipeline completed successfully across distributed nodes"'
            }
        }
    }

    post {
        success {
            echo 'Distributed build pipeline passed!'
        }
        failure {
            echo 'Distributed build pipeline failed. Check agent logs.'
        }
    }
}