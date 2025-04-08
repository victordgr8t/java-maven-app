def gv

pipeline {

    agent any

    parameters {

        choice(name:'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '' )
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

    stages {

        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage("build") {
            steps {
                script {
                    gv.buildApp()
                }
            }
        }

        stage("test") {
            when {
                expression {
                    params.executeTests
                }
            }
            steps{
                script {
                    gv.testApp()
                }
            }
        }

        stage("package") {
            steps{
                script {
                    gv.packageApp()
                }
            }
        }

        stage("deploy") {
            input {
                message "Select the environment to deoloy to"
                ok "Done"
                parameters {
                    choice(name:'ENV', choices: ['dev', 'staging', 'prod'], description: '' )
                }
            }
            steps{
                script {
                    gv.deployApp()
                    echo "Dpeloying to ${ENV}"
                }
            }
        }
    }
}
