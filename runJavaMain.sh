#!/bin/sh

mvn clean install
cd ./target/
java -jar YAJSWTuts-0.0.1.jar
