[![code style: prettier](https://img.shields.io/badge/code_style-prettier-ff69b4.svg?style=flat-square)](https://github.com/prettier/prettier)
![CI/CD Pipeline](https://github.com/mcagov/beacons-service/workflows/CI/CD%20Pipeline/badge.svg)

# Beacons Registration API

This is a Spring Boot API to enable:

- [406Mhz beacon](https://www.gov.uk/maritime-safety-weather-and-navigation/register-406-mhz-beacons) owners to register
  their details with the Maritime & Coastguard Agency
- Search and rescue [Mission Control Centres](<https://en.wikipedia.org/wiki/Mission_control_centre_(Cospas-Sarsat)>) to
  retrieve information about beacons during distress signal activations

## Dependencies

The following dependencies are required to build and test the application.

| Dependency                                               | Version        |
| -------------------------------------------------------- | -------------- |
| [Java](https://adoptopenjdk.net/)                        | 11             |
| [Docker](https://www.docker.com/products/docker-desktop) | Latest         |
| [nodejs](https://nodejs.org/en/)                         | 12.x \|\| 14.x |

Gradle is the build tool for the application. See
the [docs](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:upgrading_wrapper) for updating the Gradle
Wrapper.

## Development

Clone this repo

Install node packages (needed for code formatting): `npm install`

## Testing

Integration tests use the naming convention `<name>IntegrationTest`.

Unit tests use the naming convention `<name>UnitTest`.

Both unit and integration tests go in [src/test/java/uk/gov/mca/beacons/service](src/test/java/uk/gov/mca/beacons/api).

### Running tests

- `./gradlew test` runs unit tests
- `./gradlew integrationTest` runs integration tests
- `./gradlew check` runs both unit and integration tests

## Building

The service can be built locally in your IDE of choice.

You can also build from the command line:

- And run the tests: `./gradlew clean build`
  - This will require standing up the PostgreSQL backend for the Integration tests.
- Without running the tests: `./gradlew clean assemble`

## Running

The service can be run either locally in your IDE of choice or from the command line by
running: `./gradlew bootRun --args='--spring.profiles.active=dev'`

Local development instances of the backing services, such as PostgreSQL and OpenSearch, can be initiated with the
command `docker compose up`.

## Style Guide

We use [Prettier-Java](https://github.com/jhipster/prettier-java/tree/c1f867092f74ebfdf68ccb843f8186c943bfdeca) to
format our code and use [Husky](https://typicode.github.io/husky/#/) to run the formatting as a pre-commit hook.

The choices that Prettier makes can be found [here](https://prettier.io/docs/en/rationale.html).

Wildcard imports, `import java.util.*;` should not be used within the application.
See [GDS Programming Languages](https://gds-way.cloudapps.digital/manuals/programming-languages/java.html#imports)
guidance on this and how to configure IntelliJ to ensure it does not use wildcard imports.

Run the formatter using:

```bash
$ npm run format
```

## Deployment

A Continuous Integration and Deployment (CI/CD) pipeline is configured to deploy to our development environment on
merges into the `main` branch.

Please see the [Beacons Integration](https://github.com/mcagov/beacons-integration) project which manages the
infrastructure-as-code and deployments for the application.

## Database Schema Generation

To generate a database schema diagram for the Beacons API:

- Ensure that the PostgreSQL instance is running
- Ensure that the Spring Boot service is running and Flyway has applied the migration scripts
- Run `docker compose up schemacrawler`

This will output a `beacons-schema.html` diagram in [this directory](./schemacrawler).

## Licence

Unless stated otherwise, the codebase is released under [the MIT License][mit]. This covers both the codebase and any
sample code in the documentation.

The documentation is [&copy; Crown copyright][copyright] and available under the terms of the [Open Government 3.0][ogl]
licence.

[mit]: LICENCE
[copyright]: http://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/uk-government-licensing-framework/crown-copyright/
[ogl]: http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/
