#!/usr/bin/env groovy

def gv

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
        stage("build image") {

            steps {
                script {
                    gv.buildImage(env.IMAGE_NAME)
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying docker image to EC2..."
                    def shellCmd= "bash ./servercmdscript.sh ${IMAGE_NAME}"

                    sshagent(['azure_server_key']) {
                        sh "scp servercmdscript.sh victornta@51.143.97.22:/home/victornta"
                        sh "scp docker-compose.yaml victornta@51.143.97.22:/home/victornta"
                        sh "ssh -o StrictHostKeyChecking=no victornta@51.143.97.22 ${shellCmd}"
    
                    }
                
                }
            }
        }
    }
}

