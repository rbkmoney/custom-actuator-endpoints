#!groovy
build('custom-actuator-endpoints', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()

    def javaLibPipeline
    runStage('load JavaLib pipeline') {
        javaLibPipeline = load("build_utils/jenkins_lib/pipeJavaLib.groovy")
    }

    def buildImageTag = "442c2c274c1d8e484e5213089906a4271641d95e"
    javaLibPipeline(buildImageTag)
}
