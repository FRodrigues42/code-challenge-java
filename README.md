# CodeChallengeJava

This application was generated using JHipster 7.1.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v7.1.0](https://www.jhipster.tech/documentation-archive/v7.1.0).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use npm scripts and [Angular CLI][] with [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./gradlew -x webapp
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### Using Postgresql Database

Instead of running gradle with dev profile run with prod:

```
./gradlew -x webapp -Pprod
```

To easily start a database follow up with the [Using Docker to simplify development](<#using-docker-to-simplify-development-(optional)>) section.

But if you already have a postgres instance then add a user with username `CodeChallengeJava` and without password.

## Testing

To launch your application's tests, run:

```
./gradlew test integrationTest jacocoTestReport
```

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

Then, run a Sonar analysis:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube -Dsonar.host.url=http://localhost:9001
```

For more information, refer to the [Code quality page][].

### Using curl to test exercise services

After starting the backend application with gradle, just use `curl` in another shell to `http://localhost:8080` endpoint.

```
curl -X GET 'http://localhost:8080/difference?number=1'
```

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./gradlew bootJar -Pprod jibDockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.1.0 archive]: https://www.jhipster.tech/documentation-archive/v7.1.0
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v7.1.0/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v7.1.0/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v7.1.0/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v7.1.0/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v7.1.0/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v7.1.0/setting-up-ci/
[node.js]: https://nodejs.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[cypress]: https://www.cypress.io/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: https://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/documentation-archive/v7.1.0/doing-api-first-development/
