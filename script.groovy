def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t victornta32/my-repo:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push victornta32/my-repo:jma-2.0'
    }
} 

def deployApp() {
    def dockerCmd='docker run -p 3080:8080 -d nanajanashia/demo-app:1.0'

    sshagent(['azure_server_key']) {
        
        sh "ssh -o StrichHostKeyChecking=no victornta@51.143.97.22 $(dockerCmd)"
    
    }
} 

return this
