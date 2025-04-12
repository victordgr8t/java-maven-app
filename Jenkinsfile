
#!/usr/bin/env groovy

// testing  webhook on pipeline...
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


    stages {
        stage ('test') {
            steps {
                script {
                    echo 'testing the application....'
                    echo "executing pipeline for $BRANCH_NAME"
                }
            }
        }
        stage ('build') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    echo 'building the application...'
                }
            }
        }
        stage ('deploy') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    echo 'deploying the application...'

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
        stage ('increment version') {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion}\
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
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
                    buildImage "vicdg8t/my-repo:${IMAGE_VERSION}"
                    dockerLogin()
                    dockerPush "vicdg8t/my-repo:${IMAGE_VERSION}"
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
        stage('commit version update') {
            steps {
                script {
                    commitUpdate()
                }
            }
        }
    }   
}
