# Drombler JStore
Drombler JStore (working title) starts the next generation of application deployment solutions.

Drombler JStore provides first class support for Java applications.

For more background information see: http://www.drombler.org/drombler-jstore

[![Join the chat at https://gitter.im/Drombler/drombler-jstore](https://badges.gitter.im/Drombler/drombler-jstore.svg)](https://gitter.im/Drombler/drombler-jstore?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[org.drombler.jstore.JStoreApp](https://github.com/Drombler/drombler-jstore/blob/14-Document-JRE-REST-resources/drombler-jstore-web/src/main/java/org/drombler/jstore/JStoreApp.java) is a Spring Boot application.

Local Swagger: http://localhost:5000/swagger-ui.html

Staging Swagger: http://drombler-jstore-staging.us-east-1.elasticbeanstalk.com/swagger-ui.html

To interact with the Drombler JStore manually, you can use the
[Drombler JStore Client](https://github.com/Drombler/drombler-jstore-client), a JavaFX-based desktop client (work in progress).


## Protocol
Drombler JStore provides a REST API with JSON payloads.

You can find the latest JSON Schemas in the GitHub project [Drombler JStore protocol](https://github.com/Drombler/drombler-jstore-protocol).

## Managed Components

Beside applications Drombler JStore also manages some native components.

Drombler JStore applications can reference in their 
[Application Descriptor](https://github.com/Drombler/drombler-jstore-protocol/blob/develop/src/main/resources/org/drombler/jstore/protocol/json/Application.json)
managed native components in a platform independent way.

Managed components (applications and native components) can be downloaded and 
upgraded by the 
[Drombler JStore Client Agent](https://github.com/Drombler/drombler-jstore-client-agent)
(work in progress), which has to be installed on the client platform.

### JREs
Drombler JStore manages JREs (Java Runtime Environments).

Currently only JREs provided by Oracle are supported.
In the future this project could also support other JRE vendors.

The currently supported Oracle JREs can be found 
[here](https://github.com/Drombler/drombler-jstore/blob/develop/drombler-jstore-managed-jre/src/main/resources/org/drombler/jstore/managed/jre/impl/oracle/oracleJreInfo.json).

#### JRE Version Search
To search for JRE versions for a particular platform the following REST service can be used:

[POST /managed-components/jre-version-search](http://drombler-jstore-staging.us-east-1.elasticbeanstalk.com/swagger-ui.html#/JreVersionSearchController_V1)

(Please note that our staging environment is for testing purpose only!)

Here is a sample request payload:
```json
{
  "selectedJREs": [
    {
      "jreInfo": {
        "jreVendorId": "oracle",
        "javaSpecificationVersion": "11"
      },
      "installedImplementationVersion": "11+28"
    },
    {
      "jreInfo": {
        "jreVendorId": "oracle",
        "javaSpecificationVersion": "8"
      },
      "installedImplementationVersion": "8u181-b13"
    }
  ],
  "systemInfo": {
    "osName": "linux",
    "osArch": "x64"
  }
}
```
Please note that the `installedImplementationVersion` property is optional.
Provide it if you already have a JRE installed for the provided `jreVendorId` and `javaSpecificationVersion`.
In this case the `upgradableJREs` property of the response will only contain entries if the latest version is newer.
If you omit it then you will always get the latest version.

In the `systemInfo` section you can specify the details of the platform.

The response will look something like this (please not that the specification is not final yet):
```json
{
  "upgradableJREs": [
    {
      "jreInfo": {
        "jreVendorId": "oracle",
        "javaSpecificationVersion": "11",
        "customJREId": "",
        "modules": []
      },
      "latestUpgradableJREImplementationVersion": "11.0.1+13",
      "jreImplementationId": "openjdk-11.0.1_linux-x64_bin.tar.gz",
      "jreImplementationFileName": "openjdk-11.0.1_linux-x64_bin.tar.gz",
      "checksums": [
        {
          "algorithm": "sha256",
          "hashValue": "7a6bb980b9c91c478421f865087ad2d69086a0583aeeb9e69204785e8e97dcfd"
        }
      ]
    },
    {
      "jreInfo": {
        "jreVendorId": "oracle",
        "javaSpecificationVersion": "8",
        "customJREId": "",
        "modules": []
      },
      "latestUpgradableJREImplementationVersion": "8u191-b12",
      "jreImplementationId": "jre-8u191-linux-x64.tar.gz",
      "jreImplementationFileName": "jre-8u191-linux-x64.tar.gz",
      "checksums": [
        {
          "algorithm": "sha256",
          "hashValue": "813f48192558fea793af22c96b1b708694bffe36111a33d354f312c1c1ae4bf2"
        },
        {
          "algorithm": "md5",
          "hashValue": "6f3cce9c6e35b4e53844e6cfe64781ea"
        }
      ]
    }
  ]
}
```

#### JRE Download

With the specified `jreVendorId` and the returned `jreImplementationId` you can then download the JRE like this:

[GET /managed-components/jres/{jreVendorId}/{jreImplementationId}](http://drombler-jstore-staging.us-east-1.elasticbeanstalk.com/swagger-ui.html#/JreController_V1)

(Please note that our staging environment is for testing purpose only!)

(Please note that this call is not supported by Swagger yet, but works e.g. from the 
[Drombler JStore Client Agent](https://github.com/Drombler/drombler-jstore-client-agent).)

The call will be redirected to the according download link.

For the provided Java 11 entry in the response above the call could look like this:

http://drombler-jstore-staging.us-east-1.elasticbeanstalk.com/webresources/v1/managed-components/jres/oracle/openjdk-11.0.1_linux-x64_bin.tar.gz

If the `jreImplementationId` is not a proper file name you can use the `jreImplementationFileName` property to get a valid file name.

You can validate the downloaded files using the `checksums` provided in the search response.

You can then use the `latestUpgradableJREImplementationVersion` in the next search as the value for the property `installedImplementationVersion`.
