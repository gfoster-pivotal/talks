#!/bin/sh

cd pairing-demo/core-practices/how-to-pair/pairing-demo/;
ls -altr;
pwd;
which gradle;
ls -altr gradlew;
# gradle book-producer:assemble;
gradlew book-producer:assemble;
./gradlew book-producer:assemble;
