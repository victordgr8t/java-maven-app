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
        //            env.ENV = input message: "Select the environment to deploy to", ok: "Done", parameters: [choice(name: 'ONE', choices: ['dev', 'staging', 'prod'], description: '')]
                    echo "No more waiting"
                    gv.deployApp()
                    echo "Done Deploying"
                }
            }
        }
    }
}
