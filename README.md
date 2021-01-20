# Beacons Registration Sprint Boot API

This is an API to allow users to register 406Hz Distress Beacons with the Maritime and Coastguard Agency (MCA).

### Testing

Integration tests use the naming convention `<name>IntegrationTest`.

Unit tests use the naming convention `<name>Test`.

Both unit and integration tests go in [src/test/java/uk/gov/mca/beacons/service](src/test/java/uk/gov/mca/beacons/service).

#### Running tests

- `./gradlew test` runs unit tests
- `./gradlew integrationTest` runs integration tests
- `./gradlew check` runs both unit and integration tests

## Style Guide

We use [Prettier-Java](https://github.com/jhipster/prettier-java/tree/c1f867092f74ebfdf68ccb843f8186c943bfdeca) to format our code and we have [Husky](https://typicode.github.io/husky/#/) to run the formatting as a pre-commit hook.

The choices that Prettier makes can be found [here](https://prettier.io/docs/en/rationale.html).

- Building the Service
- Requirements
- Running
- Tests
- Deployment
- License
