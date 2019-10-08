#!groovy
build('custom-actuator-endpoints', 'docker-host') {
    checkoutRepo()
    loadBuildUtils()

    def javaLibPipeline
    runStage('load JavaLib pipeline') {
        javaLibPipeline = load("build_utils/jenkins_lib/pipeJavaLib.groovy")
    }

    def buildImageTag = "bdc05544014b3475c8e0726d3b3d6fc81b09db96"
    javaLibPipeline(buildImageTag)
}
