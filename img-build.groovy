pipeline {
    agent any

    environment {
        IMAGE_NAME = "buysmart-backend"
        ACR_NAME = "youracr.azurecr.io"
        ACR_REPO = "${ACR_NAME}/${IMAGE_NAME}"
        IMAGE_TAG = "v${BUILD_NUMBER}"
        K8S_DEPLOYMENT = "k8s/deployment.yaml"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $ACR_REPO:$IMAGE_TAG .'
            }
        }

        stage('Login to ACR') {
            steps {
                sh 'az acr login --name youracr'
            }
        }

        stage('Push to ACR') {
            steps {
                sh 'docker push $ACR_REPO:$IMAGE_TAG'
            }
        }

        stage('Update Kubernetes Manifests') {
            steps {
                script {
                    sh "sed -i 's|image: .*$|image: $ACR_REPO:$IMAGE_TAG|' ${K8S_DEPLOYMENT}"
                }
            }
        }

        stage('Deploy to AKS') {
            steps {
                sh 'kubectl apply -f k8s/'
            }
        }
    }

    post {
        success {
            echo "Deployment complete: $ACR_REPO:$IMAGE_TAG"
        }
        failure {
            echo "Pipeline failed"
        }
    }
}
