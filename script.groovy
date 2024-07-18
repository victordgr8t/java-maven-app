def buildApp() {
    echo "Building Application"
}


def testApp() {
    echo "Testing Application"
}

def deployApp() {
    echo "Deploying Application"
    echo "deploying version ${params.VERSION}"
}

return this
