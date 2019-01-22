import groovy.json.JsonSlurper

//expects json string with appropriate content to be passed in
def proxyMavenRepository = new JsonSlurper().parseText(args)

repository.createMavenProxy(proxyMavenRepository.name, proxyMavenRepository.remoteUrl)
