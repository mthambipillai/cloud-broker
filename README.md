[![Build Status](https://travis-ci.com/streamr-dev/cloud-broker.svg?branch=master)](https://travis-ci.com/streamr-dev/cloud-broker)

# Cloud Broker
An essential service of the Streamr cloud architecture responsible for message brokering. Listens to Apache Kafka for
new data and forwards it to Apache Cassandra (for long-term persistence) and Redis (for immediate consumption
by Streamr's engine-and-editor).

![Where Cloud Broker sits in Streamr cloud stack](high-level.png)


## Building

Project uses Gradle for build automation. We provide sensible default configurations for IntelliJ IDEA but project can be developed with other IDEs as well.

- Use Gradle task `test` to run tests.
- Use Gradle task `shadowJar` to build project into a Jar.


## Running
In most cases, you will want to run this service as a [pre-built Docker image](https://hub.docker.com/r/streamr/broker/).
See https://github.com/streamr-dev/streamr-docker-dev for more information on how to run the Streamr cloud architecture.

If you are developing this service in particular, or are otherwise inclined, you can run this service by running method
`main` of `com.streamr.broker.Main` via your IDE.

## Publishing
A [Docker image](https://hub.docker.com/r/streamr/broker/) is automatically built and pushed to DockerHub when commits
are pushed to branch `master`.

Currently project has no CI system configured nor are any .jar artifacts published to central repositories. 

## License

This software is open source, and dual licensed under [AGPLv3](https://www.gnu.org/licenses/agpl.html) and an enterprise-friendly commercial license.
