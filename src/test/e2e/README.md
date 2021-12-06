# e2e

See [https://github.com/wimdeblauwe/testcontainers-cypress](https://github.com/wimdeblauwe/testcontainers-cypress).

These tests are run by JUnit using the test at `src/java/BeaconsEndToEndTests.java`.

## Opening Cypress for development

To open Cypress and run end-to-end tests while developing, run `./gradlew openCypress` from the root directory.

## Remember!

- End-to-end tests are expensive. Write the minimum necessary, and prefer unit and integration tests instead.
- One happy path end-to-end test per user journey (e.g. "create an account", "register a beacon") is _probably_
  sufficient.
- The tested journey should include visibility in the Backoffice application. Don't just test that an AccountHolder can
  do something: test that the AccountHolder can do something _and_ that Backoffice users can see the outcome.
