#!/usr/bin/env groovy
library identifier: 'jenkins-shared-library@master', retriever: modernSCM(
    [$class:'GitSCMSource',
      remote: 'https://gitlab.com/victornta32/jenkinssharedlibrary.git',
      credentialsId: 'jenkinssharedlibrary'
    
    ]
)

pipeline {
    agent any
    tools{
        maven 'maven-3.6'
    }
    environment{
        IMAGE_NAME='victornta32/my-repo:jma-2.0'
    }
    stages {
        stage("init") {
            steps {
                script {
                   gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
                    buildImage (env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying docker image to EC2..."
                    def shellCmd= "bash ./servercmdscript.sh ${IMAGE_NAME}"
                    def ec2Instance = "victornta@51.143.97.22"
                    sshagent(['azure_server_key']) {
                        sh "scp servercmdscript.sh ${ec2Instance}:/home/victornta"
                        sh "scp docker-compose.yaml ${ec2Instance}:/home/victornta"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
    
                    }
                
                }
            }
        }
    }
}

