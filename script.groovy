def buildJar () {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage () {
    echo 'building the the docker image...'
    withCredentials([usernamePassword(credentialsId: 'DOCKER-LOGIN', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t vicdg8t/my-repo:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push vicdg8t/my-repo:jma-2.0'
    }
}

def testApp() {
    echo 'testing the application...'
}

def deployApp() {
    echo 'deploying the application...'
}

return this
