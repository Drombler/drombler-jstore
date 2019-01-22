import groovy.json.JsonSlurper

//expects json string with appropriate content to be passed in
def proxyMavenRepositry = new JsonSlurper().parseText(args)

repository.getRepositoryManager().get("foo").configuration.attributes("group").child("members").
