pipeline {
    agent none

    stages {
        stage('Parallel Build') {
            parallel {
                stage('Build on Agent 1') {
                    agent { label 'linux' }
                    steps {
                        sh '''
                            echo "=== Agent 1 Build ==="
                            echo "Node: $NODE_NAME"
                            echo "Workspace: $WORKSPACE"
                            hostname
                            echo "Simulating build task..."
                            sleep 5
                            echo "Build complete on Agent 1"
                        '''
                    }
                }
                stage('Build on Agent 2') {
                    agent { label 'linux' }
                    steps {
                        sh '''
                            echo "=== Agent 2 Build ==="
                            echo "Node: $NODE_NAME"
                            echo "Workspace: $WORKSPACE"
                            hostname
                            echo "Simulating test task..."
                            sleep 5
                            echo "Tests complete on Agent 2"
                        '''
                    }
                }
            }
        }
        stage('Results') {
            agent { label 'linux' }
            steps {
                sh 'echo "All parallel stages completed successfully"'
            }
        }
    }
}