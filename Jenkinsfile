#!/usr/bin/env groovy

@Library('jenkins-shared-library') // add "_" if you want to omit next line
def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }

    stages {
        stage ("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage('build jar') {
            steps {
                script {
                    buildJar()
                }
            }
        }

        stage('build image') {
            steps {
                script {
                   buildImage 'vicdg8t/my-repo:jma-5.0'
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    gv.deployApp()

                }
            }
        }
    }
}
