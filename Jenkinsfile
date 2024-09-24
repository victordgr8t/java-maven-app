#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    tools{
        maven 'maven-3.6'
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
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying docker image to EC2..."
                    def dockerComposeCmd = "docker compose -f docker-compose.yaml up --detach"

                    sshagent(['azure_server_key']) {
                        sh "scp docker-compose.yaml victornta@51.143.97.22:/home/victornta"
                        sh "ssh -o StrictHostKeyChecking=no victornta@51.143.97.22 ${dockerComposeCmd}"
    
                    }
                
                }
            }
        }
    }
}

