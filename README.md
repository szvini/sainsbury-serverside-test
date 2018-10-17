# Sainsbury product site scraper

This is a Java console application that scrapes the Sainsbury’s grocery site’s - Berries, Cherries, Currants page and returns a JSON array of all the products on the page.
Provide a proper sainsbury product list site link to get the result.

## Requirements
Java 1.8 need to be installed. Add JAVA_HOME environment variable or set java executable on the path.

## Build
Use gradle to build the artifact. Artifact will include all the required dependencies.
```
gradlew clean build
```
Run only tests
```
gradlew clean test
```

## Command line interface usage
Run artifact
```
  java -jar <artifact> <args>
```
Example
```
java -jar build/libs/serverside-test-all-1.0.jar https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html
```