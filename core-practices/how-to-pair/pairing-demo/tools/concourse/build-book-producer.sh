#!/bin/sh

set -x;
cd book-producer/core-practices/how-to-pair/pairing-demo/;
gradle book-producer:assemble;

cd -;
git clone resource-gist updated-gist;
cp book-producer/core-practices/how-to-pair/pairing-demo/book-producer/build/libs/book-producer-0.0.1-SNAPSHOT.jar updated-gist;
git add .;
git commit -m "updating build";
