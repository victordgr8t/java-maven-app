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
    
    
    stages {
        stage("init") {
            steps {
                script {
                   gv = load "script.groovy"
                }
            }
        }
        stage('increment version') {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "Building application jar... "
                    gv.buildJar()
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
                    buildImage (env.IMAGE_NAME)
                    dockerLogin()
                    echo "${IMAGE_NAME}"
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
         stage('commit version update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'gitlab-token', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        // git config here for the first time run
                        sh 'git config --global user.email "victornta32@gmail.com"'
                        sh 'git config --global user.name "Victor"'

                        sh "git remote set-url origin https://${USER}:${PASS}@gitlab.com/victornta32/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                }
            }
        }
    
    }
}

