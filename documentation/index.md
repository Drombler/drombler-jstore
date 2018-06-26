Under Construction


## Components
The proposed solution consists of 4 major software parts.

### Drombler JStore Client Agent
The [Drombler JStore Client Agent](../../drombler-jstore-client-agent) is a peace of software running on the end-user devices.
It manages, downloads and updates applications and native components such as JREs.

### Drombler JStore
The [Drombler JStore](jstore.md) is the actual backend for the proposed solution.

### Drombler JStore Client
The [Drombler JStore Client](../../drombler-jstore-client) is a JavaFX-based rich client.
It manages multiple remote _Drombler JStore Client Agents_ and allows the user to discover new
applications in _Drombler JStore_.

### JAP Maven Plugin
The [JAP Maven Plugin](../../jap-maven-plugin) creates a platform-independent application packaging format and meta data
using Maven tooling.

In the future there could be similar solutions for other build tools such as Gradle.

## Overview
See [Overview](overview.md)