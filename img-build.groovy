node {
    // Define environment variables
    def DOCKER_IMAGE = "yourdockerhub/yourimage:latest"
    def K8S_NAMESPACE = "your-namespace"
    def K8S_DEPLOYMENT = "your-deployment"

    try {
        stage('Checkout') {
            // Checkout the code from your repository
            checkout scm
        }

        stage('Build Docker Image') {
            // Build Docker image from the Dockerfile
            sh "docker build -t ${DOCKER_IMAGE} ."
        }

        stage('Push Docker Image') {
            // Login and push the image to Docker Hub
            withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                sh "docker push ${DOCKER_IMAGE}"
            }
        }

        stage('Deploy to Kubernetes') {
            // Deploy the Docker image to Kubernetes
            withCredentials([file(credentialsId: 'k8s-config', variable: 'K8S_CONFIG')]) {
                sh "kubectl --kubeconfig=${K8S_CONFIG} set image deployment/${K8S_DEPLOYMENT} ${DOCKER_IMAGE}"
            }
        }
    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        // Clean workspace after build
        cleanWs()
    }
}
