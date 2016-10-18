#!/bin/sh

set -x;
cd pairing-demo/core-practices/how-to-pair/pairing-demo/;
ls -altr;
pwd;
which gradle;
ls -altr gradlew;
./gradlew book-producer:assemble;
gradle book-producer:assemble;
