def buildApp () {
    echo 'building the application...'
}

def testApp() {
    echo 'testing the application...'
}

def packageApp() {
    echo 'packaging the application'
}

def deployApp() {
    echo 'deploying the application...'
    echo "deploying version ${params.VERSION}"
}

return this
