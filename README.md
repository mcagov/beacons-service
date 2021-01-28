[![code style: prettier](https://img.shields.io/badge/code_style-prettier-ff69b4.svg?style=flat-square)](https://github.com/prettier/prettier)
![CI/CD Pipeline](https://github.com/madetech/mca-beacons-service/workflows/CI/CD%20Pipeline/badge.svg)

# Beacons Registration API

This is a Spring Boot API to enable:

- [406Mhz beacon](https://www.gov.uk/maritime-safety-weather-and-navigation/register-406-mhz-beacons) owners to register their details with the Maritime & Coastguard Agency
- Search and rescue [Mission Control Centres](<https://en.wikipedia.org/wiki/Mission_control_centre_(Cospas-Sarsat)>) to retrieve information about beacons during distress signal activations

## Dependencies

The following dependencies are required to build and test the application.

| Dependency                                               | Version        |
| -------------------------------------------------------- | -------------- |
| [Java](https://adoptopenjdk.net/)                        | 11             |
| [Docker](https://www.docker.com/products/docker-desktop) | Latest         |
| [nodejs](https://nodejs.org/en/)                         | 12.x \|\| 14.x |

## Development

Clone this repo

Install node packages (needed for code formatting): `npm install`

## Testing

Integration tests use the naming convention `<name>IntegrationTest`.

Unit tests use the naming convention `<name>Test`.

Both unit and integration tests go in [src/test/java/uk/gov/mca/beacons/service](src/test/java/uk/gov/mca/beacons/service).

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

The service can be run either locally in your IDE of choice or from the command line by running: `./gradlew clean bootRun`

The PostgreSQL backend can be stood up by running: `docker-compose up postgres`

You can also use `docker-compose up` to bring up both the service and the PostgreSQL backend in docker.

## Style Guide

We use [Prettier-Java](https://github.com/jhipster/prettier-java/tree/c1f867092f74ebfdf68ccb843f8186c943bfdeca) to format our code and use [Husky](https://typicode.github.io/husky/#/) to run the formatting as a pre-commit hook.

The choices that Prettier makes can be found [here](https://prettier.io/docs/en/rationale.html).

## Deployment

A Continuous Integration and Deployment (CI/CD) pipeline is configured to deploy to our development environment on merges into the `main` branch.

Please see the [MCA Beacons Integration](https://github.com/madetech/mca-beacons-integration) project which manages the infrastructure-as-code and deployments for the application.

- License
