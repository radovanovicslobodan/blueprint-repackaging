# java-cucumber-automation-template

## Introduction

java-cucumber-automation-template test automation framework will be used mostly for rest/graphql integration tests and UI E2E tests.

---
## Prerequisites
* gradle 7.3
* Open JDK 17
* Setup Allure on local machine if you want to generate report or install plugin for Jenkins
* IntelliJ IDEA - with lombok, cucumber plugins (set settings/gradle to Java 17)

## Dependencies
* For running tests - JUnit runner
* For BDD - cucumber(core, java, JVM, JUnit, gherkin), groovy-all
* For sending rest requests - rest-assured
* For serialization/deserialization body request/response - Gson, Jackson
* For UI - selenium and webdrivermanager
* Replacement getters and setter - Lombok
* Accessing yaml files - snake-yaml
* Logging - slf4j
* Reporting - allure (cucumber4-jvm, rest-assured), cukedoctor
* Javax mail, commons-email - for managing emails
* Awaitility - express expectations of an asynchronous system in a concise and easy to read manner
* Assertj-core - advance assertion methods
* Poiji - working with excel files
* Opencsv - working with csv files
* Postgresql - postgres database driver 

## Project structure:
* src/test/java/cucumber_blueprint/common - will be used for storing general helpers in common packages
* src/test/java/cucumber_blueprint/data_containers - will use for PicoContainer DI
* src/test/java/cucumber_blueprint/graphql - will be used for storing graphql steps, requests, responses and helpers
* src/test/java/cucumber_blueprint/rest - will be used for storing rest steps, requests, responses and helpers  
* src/test/java/rs/cucumber_blueprint/ui - will be used for storing selenium E2E tests
* src/test/resources -  will store features in the features package. Then feature package will have packages related to each functionality with specific features files. Also,  stores yaml for different environments (URL, credentials etc…) and graphql body files

## How to run tests:
* By Junit runner - just run class CucumberRunner in runners package. Default it will run tests on dev environment.
* By terminal type command:
  gradle cucumber -Denv={envName} -Ddriver={driverName} -Dcucumber.options="-t @tag1,@tag2 -t ~@igonredTag"

Note: envName is stored in properties.yml file.
If you have some tests that are currently failing because they are waiting future implementation, annotate them with @failing so we can ignore them during our run.

## Reporting:
* If you want to generate Allure report type in terminal: allure generate <yourPathToAllureReport/allure-results> --clear.
  After the task is finished check the allure-report package and open with the preferred browser open index.html file.
  (If you run as Junit run Class it allure-result will be generated in the root package if it is run via terminal/Gradle
  run it is stored in root/build/allure-result)
* Adoc file is generated at the end of run

