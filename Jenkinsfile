
pipeline {

    agent any


    parameters {
        
        choice(name:'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '' )
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

    environment{
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials') // referenced credentials in jenkins server
    }

    stages {

        stage("build") {
           // when {
               //  expression {
                    //BRANCH_NAME = 'dev' && CODE_CHANGES == true
                //}
            steps{
                echo 'building the application...'
                echo "building version ${params.VERSION}"
                sh "mvn install"
            }
        }

        stage("test") {
            when {
                expression {
                    params.executeTests == true
                    //BRANCH_NAME == 'dev' || BRANCH_NAME == 'master' // this stage will execute when the branch is dev or master
                }
            }
            steps{
                echo 'testing the application...'
            }
        }

        stage("package") {
            steps{
                echo 'packaging the application...'
            }
        }

        stage("deploy") {
            steps{
                echo 'deploying the application...'
                echo "deploying version ${params.VERSION}"
                sh "deploy-script.sh ${params.VERSION}"
                withCredentials([
                    usernamePassword(credentials: 'server-credentials', usernameVariable: USER, usernamePassword: PWD)
                ]) {
                    sh "some script ${USER} ${PWD}"
                }
            }
        }
    }    
}
