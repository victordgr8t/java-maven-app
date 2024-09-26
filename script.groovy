def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage(def IMAGE_NAME) {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t $IMAGE_NAME"
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push $IMAGE_NAME"
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
