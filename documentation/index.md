Under Construction

## Overview
See [Overview](overview.md)

## Components
Drombler JStore consists of 4 major software parts.

### Drombler JStore Client Agent
The [Drombler JStore Client Agent](../../drombler-jstore-client-agent) is a headless background service which runs on end-user devices (desktops, NAS, IoT devices,...).

It manages, downloads and updates applications and native components such as JREs.

The _Drombler JStore Client Agent_ can access multiple backends.

### Drombler JStore
The [Drombler JStore](jstore.md) project provides the actual backend with RESTful services.

### Drombler JStore Client
The [Drombler JStore Client](../../drombler-jstore-client) is a JavaFX-based rich client.

It manages multiple remote _Drombler JStore Client Agents_ and allows the user to discover new
applications in _Drombler JStore_.

### JAP Maven Plugin
The [JAP Maven Plugin](../../jap-maven-plugin) creates a platform-independent application packaging format and meta data
using Maven tooling.

In the future there could be similar solutions for other build tools such as Gradle.
