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
                    def dockerCmd = 'docker run -p 3080:8080 -d  victornta32/my-repo:jma-2.0'

                    sshagent(['azure_server_key']) {
                    
                        sh "ssh -o StrictHostKeyChecking=no victornta@51.143.97.22 $(dockerCmd)"
    
                    }
                
                }
            }
        }
    }
}

