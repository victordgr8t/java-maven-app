#!/usr/bin/env groovy

// testing  webhook
// @Library('jenkins-shared-library') reference to shared library created on jenkins system config
// @Library('jenkins-shared-library@2.0') in-case you want to have different versions in each project
library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/victordgr8t/jenkins-shared-library.git',
         credentialsId: 'github-credentials'
        ]
)

def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.9'
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
                    buildJar()
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
                    buildImage 'vicdg8t/my-repo:jma-3.1'
                    dockerLogin()
                    dockerPush 'vicdg8t/my-repo:jma-3.1'
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}
